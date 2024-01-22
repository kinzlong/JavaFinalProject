package FinalProject;
import java.util.*;

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

    //transcribes sequence (5' -> 3')
    public String transcribe(){
        StringBuilder mrna = new StringBuilder();
        for (char nucleotide : sequence.toCharArray()) {
            switch (nucleotide) {
                case 'A':
                    mrna.append('A');
                    break;
                case 'T':
                    mrna.append('U');
                    break;
                case 'C':
                    mrna.append('C');
                    break;
                case 'G':
                    mrna.append('G');
                    break;
            }
        }
        return mrna.toString();
    }

    //determines the open reading frame from a coding sequence
    public String openReadingFrame(){
        StringBuilder orf = new StringBuilder();
        String mrna_transcript = transcribe();
        String start_codon = "AUG";
        List<String> stop_codons = Arrays.asList("UAA", "UAG", "UGA");
        
        //remove 5' UTR
        int index = mrna_transcript.indexOf(start_codon);
        if (index != -1) {
            mrna_transcript = mrna_transcript.substring(index);
        } else {
            return "Error: No Start Codon Found.";
        }

        for (int i = 0; i <= mrna_transcript.length() - 3; i += 3) {
            String codon = mrna_transcript.substring(i, i + 3);
            if (stop_codons.contains(codon)){
                return orf.toString();
            }
            else {
                orf.append(codon);
            }
        }
        return orf.toString();

    }

    //translates sequence (assume 5' -> 3' coding strand) into 
    public String translate(){
        Map<String, String> codonToAminoAcid = new HashMap<String, String>() {{
            put("UUU", "F"); put("UUC", "F"); // Phenylalanine
            put("UUA", "L"); put("UUG", "L"); // Leucine
            put("CUU", "L"); put("CUC", "L"); put("CUA", "L"); put("CUG", "L"); // Leucine
            put("AUU", "I"); put("AUC", "I"); put("AUA", "I"); // Isoleucine
            put("AUG", "M"); // Methionine, Start
            put("GUU", "V"); put("GUC", "V"); put("GUA", "V"); put("GUG", "V"); // Valine
            put("UCU", "S"); put("UCC", "S"); put("UCA", "S"); put("UCG", "S"); // Serine
            put("CCU", "P"); put("CCC", "P"); put("CCA", "P"); put("CCG", "P"); // Proline
            put("ACU", "T"); put("ACC", "T"); put("ACA", "T"); put("ACG", "T"); // Threonine
            put("GCU", "A"); put("GCC", "A"); put("GCA", "A"); put("GCG", "A"); // Alanine
            put("UAU", "Y"); put("UAC", "Y"); // Tyrosine
            put("CAU", "H"); put("CAC", "H"); // Histidine
            put("CAA", "Q"); put("CAG", "Q"); // Glutamine
            put("AAU", "N"); put("AAC", "N"); // Asparagine
            put("AAA", "K"); put("AAG", "K"); // Lysine
            put("GAU", "D"); put("GAC", "D"); // Aspartic Acid
            put("GAA", "E"); put("GAG", "E"); // Glutamic Acid
            put("UGU", "C"); put("UGC", "C"); // Cysteine
            put("UGG", "W"); // Tryptophan
            put("CGU", "R"); put("CGC", "R"); put("CGA", "R"); put("CGG", "R"); // Arginine
            put("AGU", "S"); put("AGC", "S"); // Serine
            put("AGA", "R"); put("AGG", "R"); // Arginine
            put("GGU", "G"); put("GGC", "G"); put("GGA", "G"); put("GGG", "G"); // Glycine
            put("UAA", "*"); put("UAG", "*"); put("UGA", "*"); // Stop codons
        }};
        String orf = openReadingFrame();
        StringBuilder aa_sequence = new StringBuilder();

        for (int i = 0; i <= orf.length() - 3; i += 3) {
            String codon = orf.substring(i, i + 3);
            aa_sequence.append(codonToAminoAcid.get(codon));
        }
        return aa_sequence.toString();
    }

}