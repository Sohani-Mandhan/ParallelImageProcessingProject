import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Color;

public class ParallelFilter {

    private enum Mode { GRAYSCALE, BRIGHTNESS, INVERSION }

    static class Worker extends Thread {
        private final BufferedImage img;
        private final int startY, endY;
        private final Mode mode;
        private final int brightnessValue;

        public Worker(BufferedImage img, int startY, int endY, Mode mode, int brightnessValue) {
            this.img = img;
            this.startY = startY;
            this.endY = endY;
            this.mode = mode;
            this.brightnessValue = brightnessValue;
        }

        @Override
        public void run() {
            int width = img.getWidth();

            for (int y = startY; y < endY; y++) {
                for (int x = 0; x < width; x++) {
                    Color c = new Color(img.getRGB(x, y));

                    int r = c.getRed();
                    int g = c.getGreen();
                    int b = c.getBlue();

                    if (mode == Mode.GRAYSCALE) {
                        int gray = (r + g + b) / 3;
                        img.setRGB(x, y, new Color(gray, gray, gray).getRGB());

                    } else if (mode == Mode.BRIGHTNESS) {
                        int nr = clamp(r + brightnessValue);
                        int ng = clamp(g + brightnessValue);
                        int nb = clamp(b + brightnessValue);
                        img.setRGB(x, y, new Color(nr, ng, nb).getRGB());

                    } else if (mode == Mode.INVERSION) {
                        img.setRGB(x, y, new Color(255 - r, 255 - g, 255 - b).getRGB());
                    }
                }
            }
        }

        private int clamp(int v) {
            if (v < 0) return 0;
            if (v > 255) return 255;
            return v;
        }
    }

    // 1) Parallel Grayscale (already in your project)
    public static void applyParallelGrayscale(String inputPath, String outputPath, int numThreads) {
        applyParallelInternal(inputPath, outputPath, numThreads, Mode.GRAYSCALE, 0);
    }

    // 2) Parallel Brightness (NEW)
    public static void applyParallelBrightness(String inputPath, String outputPath, int numThreads, int brightnessValue) {
        applyParallelInternal(inputPath, outputPath, numThreads, Mode.BRIGHTNESS, brightnessValue);
    }

    // 3) Parallel Inversion (NEW)
    public static void applyParallelInversion(String inputPath, String outputPath, int numThreads) {
        applyParallelInternal(inputPath, outputPath, numThreads, Mode.INVERSION, 0);
    }

    // Shared internal runner
    private static void applyParallelInternal(String inputPath, String outputPath, int numThreads, Mode mode, int brightnessValue) {
        try {
            BufferedImage img = ImageIO.read(new File(inputPath));
            int height = img.getHeight();

            Worker[] threads = new Worker[numThreads];
            int chunkSize = height / numThreads;

            for (int i = 0; i < numThreads; i++) {
                int startY = i * chunkSize;
                int endY = (i == numThreads - 1) ? height : startY + chunkSize;

                threads[i] = new Worker(img, startY, endY, mode, brightnessValue);
                threads[i].start();
            }

            for (Worker t : threads) {
                t.join();
            }


            ImageIO.write(img, "jpg", new File(outputPath));

            System.out.println("\nParallel " + mode + " done using " + numThreads + " threads!"
                    + (mode == Mode.BRIGHTNESS ? (" Value=" + brightnessValue) : ""));
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
