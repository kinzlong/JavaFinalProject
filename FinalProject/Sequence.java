package FinalProject;

public class Sequence {
    private String sequence;

    // Constructor to initialize the DNA sequence
    public Sequence(String sequence) throws IllegalArgumentException {
        validateSequence(sequence);
        this.sequence = sequence.toUpperCase();
    }

    // Method to validate the DNA sequence
    private void validateSequence(String sequence) throws IllegalArgumentException {
        if (!sequence.matches("[ATCGatcg]+")) {
            System.out.println("Invalid DNA sequence: only A, T, C, and G are allowed.");
            throw new IllegalArgumentException("Invalid DNA sequence: only A, T, C, and G are allowed.");
        }
    }

    //Getter
    public String getSequence(){
        return sequence;
    }

    // Method to get the length of the DNA sequence
    public int getLength() {
        return sequence.length();
    }

    // Method to calculate the GC content of the DNA sequence
    public double getGCContent() {
        int gcCount = 0;
        for (char nucleotide : sequence.toCharArray()) {
            if (nucleotide == 'G' || nucleotide == 'C') {
                gcCount++;
            }
        }
        return (double) gcCount / sequence.length();
    }

    // Method to print basic summary of a sequence
    public void getSummaryReport() {
        System.out.println("Sequence length: " + getLength() + " nucleotides");
        System.out.println("GC Content: " + getGCContent());
    }

    // Method to get the complement of the DNA sequence
    public String getComplement() {
        StringBuilder complement = new StringBuilder();
        for (char nucleotide : sequence.toCharArray()) {
            switch (nucleotide) {
                case 'A':
                    complement.append('T');
                    break;
                case 'T':
                    complement.append('A');
                    break;
                case 'C':
                    complement.append('G');
                    break;
                case 'G':
                    complement.append('C');
                    break;
            }
        }
        return complement.toString();
    }

    //helper recursive method for `countSubsequence`
    public int recursiveCountSubseq(String input_seq, String subsequence){
        if (sequence.contains(subsequence)){
            return 1 + recursiveCountSubseq(sequence.replaceFirst(subsequence, ""), subsequence);
        }
        return 0;
    }

    //counts number of sub-sequences in the sequence
    public int countSubsequence(String subsequence){
        return recursiveCountSubseq(sequence, subsequence);
    }

}