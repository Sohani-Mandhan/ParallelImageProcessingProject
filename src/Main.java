public class Main {
    public static void main(String[] args) {

        String input = "images/input2.jpg";
        int threads = 8;
        int brightness = 40;

        // ================= GRAYSCALE =================
        long startSeq = System.currentTimeMillis();
        SequentialFilter.applyGrayscale(input, "images/out_gray_seq.jpg");
        long endSeq = System.currentTimeMillis();

        long startPar = System.currentTimeMillis();
        ParallelFilter.applyParallelGrayscale(input, "images/out_gray_par.jpg", threads);
        long endPar = System.currentTimeMillis();

        print("Grayscale", endSeq - startSeq, endPar - startPar);

        // ================= BRIGHTNESS =================
        startSeq = System.currentTimeMillis();
        SequentialFilter.applyBrightness(input, "images/out_bright_seq.jpg", brightness);
        endSeq = System.currentTimeMillis();

        startPar = System.currentTimeMillis();
        ParallelFilter.applyParallelBrightness(input, "images/out_bright_par.jpg", threads, brightness);
        endPar = System.currentTimeMillis();

        print("Brightness", endSeq - startSeq, endPar - startPar);

        // ================= INVERSION =================
        startSeq = System.currentTimeMillis();
        SequentialFilter.applyInversion(input, "images/out_inv_seq.jpg");
        endSeq = System.currentTimeMillis();

        startPar = System.currentTimeMillis();
        ParallelFilter.applyParallelInversion(input, "images/out_inv_par.jpg", threads);
        endPar = System.currentTimeMillis();

        print("Inversion", endSeq - startSeq, endPar - startPar);
    }

    private static void print(String name, long seq, long par) {
        System.out.println("\n==== " + name + " ====");
        System.out.println("Sequential Time: " + seq + " ms");
        System.out.println("Parallel Time:   " + par + " ms");
        System.out.println("Speedup:         " + (double) seq / par);
    }
}
