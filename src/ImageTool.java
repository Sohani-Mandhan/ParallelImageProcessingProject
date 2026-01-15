import java.util.Scanner;

public class ImageTool {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String input = "images/input2.jpg";
        String output = "images/output.jpg";

        System.out.println("=== Parallel Image Processing Tool ===");
        System.out.println("1. Grayscale");
        System.out.println("2. Brightness");
        System.out.println("3. Inversion");
        System.out.print("Choose filter (1-3): ");
        int filter = sc.nextInt();

        System.out.print("Mode (1 = Sequential, 2 = Parallel): ");
        int mode = sc.nextInt();

        int threads = 1;
        int brightness = 0;

        if (mode == 2) {
            System.out.print("Enter number of threads: ");
            threads = sc.nextInt();
        }

        if (filter == 2) {
            System.out.print("Enter brightness value (-100 to 100): ");
            brightness = sc.nextInt();
        }

        long start = System.currentTimeMillis();

        // Run selected option
        if (filter == 1) { // Grayscale
            if (mode == 1)
                SequentialFilter.applyGrayscale(input, output);
            else
                ParallelFilter.applyParallelGrayscale(input, output, threads);

        } else if (filter == 2) { // Brightness
            if (mode == 1)
                SequentialFilter.applyBrightness(input, output, brightness);
            else
                ParallelFilter.applyParallelBrightness(input, output, threads, brightness);

        } else if (filter == 3) { // Inversion
            if (mode == 1)
                SequentialFilter.applyInversion(input, output);
            else
                ParallelFilter.applyParallelInversion(input, output, threads);
        }

        long end = System.currentTimeMillis();

        System.out.println("\n Processing complete");
        System.out.println(" Time taken: " + (end - start) + " ms");
        System.out.println(" Output saved as: " + output);

        sc.close();
    }
}
