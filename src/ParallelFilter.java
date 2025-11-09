import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Color;

public class ParallelFilter {

    static class Worker extends Thread {
        private BufferedImage img;
        private int startY, endY;

        public Worker(BufferedImage img, int startY, int endY) {
            this.img = img;
            this.startY = startY;
            this.endY = endY;
        }

        public void run() {
            for (int y = startY; y < endY; y++) {
                for (int x = 0; x < img.getWidth(); x++) {
                    Color color = new Color(img.getRGB(x, y));
                    int gray = (int)((color.getRed() + color.getGreen() + color.getBlue()) / 3.0);
                    Color newColor = new Color(gray, gray, gray);
                    img.setRGB(x, y, newColor.getRGB());
                }
            }
        }
    }

    public static void applyParallelGrayscale(String inputPath, String outputPath, int numThreads) {
        try {
            BufferedImage img = ImageIO.read(new File(inputPath));
            int height = img.getHeight();
            int chunkSize = height / numThreads;

            Worker[] threads = new Worker[numThreads];

            for (int i = 0; i < numThreads; i++) {
                int startY = i * chunkSize;
                int endY = (i == numThreads - 1) ? height : (startY + chunkSize);
                threads[i] = new Worker(img, startY, endY);
                threads[i].start();
            }

            for (Worker t : threads) {
                t.join();
            }

            File outputFile = new File(outputPath);
            System.out.println("🖼️ Writing to: " + outputFile.getAbsolutePath());
            ImageIO.write(img, "jpg", outputFile);

            System.out.println("⚡ Parallel grayscale processing complete using " + numThreads + " threads!");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
