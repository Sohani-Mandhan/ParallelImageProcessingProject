import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Color;

public class SequentialFilter {

    // 1) Grayscale (already in your project)
    public static void applyGrayscale(String inputPath, String outputPath) {
        try {
            BufferedImage img = ImageIO.read(new File(inputPath));
            int width = img.getWidth();
            int height = img.getHeight();

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Color c = new Color(img.getRGB(x, y));
                    int gray = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
                    Color newColor = new Color(gray, gray, gray);
                    img.setRGB(x, y, newColor.getRGB());
                }
            }

            ImageIO.write(img, "jpg", new File(outputPath));
            System.out.println("Sequential grayscale done!");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

        // 2) Brightness Adjustment
        public static void applyBrightness(String inputPath, String outputPath, int brightnessValue) {
            try {
                BufferedImage img = ImageIO.read(new File(inputPath));
                int width = img.getWidth();
                int height = img.getHeight();

                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        Color c = new Color(img.getRGB(x, y));

                        int r = clamp(c.getRed() + brightnessValue);
                        int g = clamp(c.getGreen() + brightnessValue);
                        int b = clamp(c.getBlue() + brightnessValue);

                        img.setRGB(x, y, new Color(r, g, b).getRGB());
                    }
                }

                ImageIO.write(img, "jpg", new File(outputPath));
                System.out.println("Sequential brightness done! Value = " + brightnessValue);
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
        }

        // 3) Color Inversion
        public static void applyInversion(String inputPath, String outputPath) {
            try {
                BufferedImage img = ImageIO.read(new File(inputPath));
                int width = img.getWidth();
                int height = img.getHeight();

                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        Color c = new Color(img.getRGB(x, y));

                        int r = 255 - c.getRed();
                        int g = 255 - c.getGreen();
                        int b = 255 - c.getBlue();

                        img.setRGB(x, y, new Color(r, g, b).getRGB());
                    }
                }

                ImageIO.write(img, "jpg", new File(outputPath));
                System.out.println("Sequential inversion done!");
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
        }

        private static int clamp(int v) {
            if (v < 0) return 0;
            if (v > 255) return 255;
            return v;
        }
    }
