import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Color;

public class SequentialFilter {

    public static void applyGrayscale(String inputPath, String outputPath) {
        try {
            BufferedImage img = ImageIO.read(new File(inputPath));
            int width = img.getWidth();
            int height = img.getHeight();

            // Convert pixel by pixel (Sequential)
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Color color = new Color(img.getRGB(x, y));
                    int gray = (int)((color.getRed() + color.getGreen() + color.getBlue()) / 3.0);
                    Color newColor = new Color(gray, gray, gray);
                    img.setRGB(x, y, newColor.getRGB());
                }
            }

            File outputFile = new File(outputPath);
            System.out.println("🖼️ Writing to: " + outputFile.getAbsolutePath());
            ImageIO.write(img, "jpg", outputFile);

            System.out.println("✅ Sequential grayscale processing complete!");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
