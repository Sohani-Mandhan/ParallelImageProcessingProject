public class Main {
    public static void main(String[] args) {
        String input = "src/images/input2.jpg";
        String outputSeq = "src/images/output2_sequential.jpg";
        String outputPar = "src/images/output2_parallel.jpg";

        // Sequential
        long startSeq = System.currentTimeMillis();
        SequentialFilter.applyGrayscale(input, outputSeq);
        long endSeq = System.currentTimeMillis();

        // Parallel
        long startPar = System.currentTimeMillis();
        ParallelFilter.applyParallelGrayscale(input, outputPar, 4);
        long endPar = System.currentTimeMillis();

        System.out.println("🕒 Sequential Time: " + (endSeq - startSeq) + " ms");
        System.out.println("⚡ Parallel Time: " + (endPar - startPar) + " ms");
        System.out.println("🚀 Speedup: " + (double)(endSeq - startSeq) / (endPar - startPar));

        
    }
}
