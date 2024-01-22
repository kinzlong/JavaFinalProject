package FinalProject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class main {
    //main action menu
    public static void mainmenu(SequencesDB db) throws java.io.IOException {
        System.out.println("---- MAIN MENU ----");
        System.out.println("Type the number corresponding to the action you want to execute, followed by the ENTER key.");
        System.out.println("1. Add a Sequence"); //COMPLETE
        System.out.println("2. Remove a Sequence"); //COMPLETE
        System.out.println("3. Single Sequence Toolkit"); //COMPLETE
        System.out.println("4. Multiple Sequences Toolkit"); //COMPLETE
        System.out.println("--------------------");

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in), 1);
        try {
            int action = Integer.parseInt(stdin.readLine()); 
            if (action == 1){
                addSequenceUI(db);
            }
            else if (action == 2){
                removeSequenceUI(db);
            }
            else if (action == 3){
                singleSequenceUI(db);
            }
            else if (action == 4){
                multipleSequenceUI(db);
            }
            else {
                System.err.println("Invalid option selected. Please try again.");
            }
        } catch(Exception e){
            System.err.println("Invalid option selected. Please try again.");
        }
        mainmenu(db);
    }

    //UI for adding a sequence
    public static void addSequenceUI(SequencesDB db ) throws java.io.IOException {
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in), 1);

        System.out.println("Type the name of the DNA sequence, followed by the ENTER key: ");
        String name = stdin.readLine();
        System.out.println("Type in the DNA sequence corresponding to " + name + ", followed by the ENTER key");
        String sequence = stdin.readLine();
        try {
            if (db.addSequence(name, sequence)){
                System.out.println(name + " successfully added.");
            }
            else {
                overwriteSequenceUI(db, name, sequence);
            }
        } catch(Exception e) {
            System.err.println("Error adding sequence. Please try again.");
        }
    }

    // UI if user wants to overwrite the DNA sequence
    public static void overwriteSequenceUI(SequencesDB db, String name, String sequence) throws java.io.IOException {
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in), 1);
        System.out.println("Sequence with that name currently exists. Do you wish to overwrite the sequence?");
        System.out.println("Type 1 for yes, type 2 for change sequence name, type 3 to cancel, followed by ENTER:");
        try {        
            int action = Integer.parseInt(stdin.readLine()); 
            if (action == 1){
                db.removeSequence(name);
                db.addSequence(name, sequence);
                System.out.println(name + " has been updated.");
            }
            else if (action == 2){
                System.out.println("Type the new name of the DNA sequence, followed by ENTER:");
                String newname = stdin.readLine();
                db.addSequence(newname, sequence);
                System.out.println(newname + " successfully added.");
            }
            else if (action == 3){
                System.out.println("Operation canceled.");
            }
            else {
                System.err.println("Invalid option selection. Please try again.");
                overwriteSequenceUI(db, name, sequence);
            }
        }
        catch (Exception e){
            System.err.println("Invalid option selection. Please try again.");
            overwriteSequenceUI(db, name, sequence);
        }
    }

    //UI for removing a sequence
    public static void removeSequenceUI(SequencesDB db ) throws java.io.IOException {
        if (db.getSequenceNames(false).length < 1){
            System.err.println("There are no sequences available to remove. Please add a sequence first.");
        }
        else {
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in), 1);
            System.out.println("Type the number corresponding to the DNA sequence you want to remove, followed by the ENTER key: ");
            String [] seq_name_list = db.getSequenceNames(true);
            int seq_index = Integer.parseInt(stdin.readLine()); 
            String name = seq_name_list[seq_index - 1];
            db.removeSequence(name);
            System.out.println(name + " successfully removed from the database.");
        }
    }

    //UI for viewing a sequence
    public static void singleSequenceUI(SequencesDB db) throws java.io.IOException {
        try{
            Sequence selectSeq = chooseSequenceUI(db);
            System.out.println("---- Analyze a Sequence ----");
            System.out.println("Type the number corresponding to the action you want to execute, followed by the ENTER key.");
            System.out.println("1. View Sequence");
            System.out.println("2. Complementary Sequence");
            System.out.println("3. Sequence length and GC content");
            System.out.println("4. Count Subsequence"); 
            System.out.println("5. Transcribe Coding Strand");
            System.out.println("6. Translate Coding Strand");
            System.out.println("7. Return to main menu");
            System.out.println("--------------------");
    
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in), 1);
            try {
                int action = Integer.parseInt(stdin.readLine()); 
                if (action == 1){
                    System.out.println(selectSeq.getSequence());
                }
                else if (action == 2){
                    System.out.println(selectSeq.getComplement());
                }
                else if (action == 3){
                    selectSeq.getSummaryReport();
                }
                else if (action == 4){
                    System.out.println("What subsequence do you want to count?");
                    String subseq = stdin.readLine();
                    int subseq_count = selectSeq.countSubsequence(subseq);
                    System.out.println("Your sequence has "+ subseq_count + "occurences of " + subseq);
                }
                else if (action == 5){
                    System.out.println("The 5'->3' mRNA sequence of your coding strand is:");
                    System.out.println(selectSeq.transcribe());
                }
                else if (action == 6){
                    System.out.println("The translated sequence (excluding stop codon) of your coding strand is:");
                    System.out.println(selectSeq.translate());
                }
                else if (action == 7){
                    return;
                }
                else {
                    System.out.println("Invalid option selected. Please try again.");
                }
            } catch(Exception e){
                System.out.println("Invalid option selected. Please try again.");
            }
        } catch (Exception e){
            System.out.println("Error. Returning to main menu.");
        }
    }

    //UI for single sequence analysis - choosing a sequence to analyze
    public static Sequence chooseSequenceUI(SequencesDB db) throws java.io.IOException {
        if (db.getSequenceNames(false).length < 1){
            System.err.println("There are no sequences available to analyze. Please add a sequence first.");
            return null;
        }
        else {
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in), 1);
            System.out.println("Type the number corresponding to the DNA sequence you want to analyze, followed by the ENTER key: ");
            String [] seq_name_list = db.getSequenceNames(true);
            try {
                int seq_index = Integer.parseInt(stdin.readLine()); 
                String name = seq_name_list[seq_index - 1];
                return db.getSequence(name);
            } catch (Exception e){
                System.err.println("Invalid option selection. Please try again.");
                return null;
            }
        }
    }

        //UI for multiple sequence comparisons
        public static void multipleSequenceUI(SequencesDB db) throws java.io.IOException {
            if (db.getSequenceNames(false).length < 2){
                System.err.println("There are not enough sequences available for multi-sequence analysis. Please add at least 2 sequences first.");
                return;
            }

            try{
                System.out.println("---- Analyze Multiple Sequences ----");
                System.out.println("Type the number corresponding to the action you want to execute, followed by the ENTER key.");
                System.out.println("1. Side-by-side Sequence View");
                System.out.println("2. Calculate Smith-Waterman Sequence Similarity Score");
                System.out.println("3. All-vs-all Pairwise Smith-Waterman Score");
                System.out.println("4. Return to main menu");
                System.out.println("--------------------");
        
                BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in), 1);
                try {
                    int action = Integer.parseInt(stdin.readLine()); 
                    if (action == 1){
                        Sequence[] sequences = chooseMultipleSequenceUI(db);
                        Sequence seq1 = sequences[0];
                        Sequence seq2 = sequences[1];
                        System.out.println(seq1.getSequence());
                        System.out.println(seq2.getSequence());
                    }
                    else if (action == 2){
                        Sequence[] sequences = chooseMultipleSequenceUI(db);
                        double alignment_result = ComparativeToolkit.performAlignment(sequences[0].getSequence(), sequences[1].getSequence(), 3, -1, -1);
                        System.out.println("The Smith-Waterman score is " + alignment_result);
                    }
                    else if (action == 3){
                        String [] sequence_names = db.getSequenceNames(false);
                        Sequence[] best_seq_pair_seqs = new Sequence[2];
                        String[] best_seq_pair_names = new String[2];
                        double best_score = Double.NEGATIVE_INFINITY;
                        for (int i = 0; i < sequence_names.length; i++){
                            Sequence seq1 = db.getSequence(sequence_names[i]);
                            for (int j = i + 1; j < sequence_names.length; j++){
                                Sequence seq2 = db.getSequence(sequence_names[j]);
                                double alignment_result = ComparativeToolkit.performAlignment(seq1.getSequence(), seq2.getSequence(), 3, -1, -1);
                                if (alignment_result > best_score){
                                    best_score = alignment_result;
                                    best_seq_pair_seqs = new Sequence[]{seq1, seq2};
                                    best_seq_pair_names = new String[]{sequence_names[i], sequence_names[j]};
                                }
                            }
                        }
                        System.out.println("Most Similar Sequences: " + best_seq_pair_names[0] + " and " + best_seq_pair_names[1]);
                        System.out.println("They have an alignment score of " + best_score);
                        System.out.println(best_seq_pair_seqs[0].getSequence());
                        System.out.println(best_seq_pair_seqs[1].getSequence());
                    }
                    else if (action == 4){
                        return;
                    }
                    else {
                        System.out.println("Invalid option selected. Please try again.");
                    }
                } catch(Exception e){
                    System.out.println("Invalid option selected. Please try again.");
                }
            } catch (Exception e){
                System.out.println("Error. Returning to main menu.");
            }
        }

        //UI to choose two sequences from database to compare
        public static Sequence[] chooseMultipleSequenceUI(SequencesDB db) throws java.io.IOException {
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in), 1);
            System.out.println("Type the numbers corresponding to the two DNA sequences you want to analyze, following each with ENTER key: ");
            String [] seq_name_list = db.getSequenceNames(true);
            try {
                int seq1_index = Integer.parseInt(stdin.readLine()); 
                String seq1_name = seq_name_list[seq1_index - 1];
                int seq2_index = Integer.parseInt(stdin.readLine()); 
                String seq2_name = seq_name_list[seq2_index - 1];
                return new Sequence[]{db.getSequence(seq1_name), db.getSequence(seq2_name)};
            } catch (Exception e){
                System.err.println("Invalid option selection. Please try again.");
                return null;
            }
        }

    public static void main(String[] argv) throws java.io.IOException {
        SequencesDB sequence_db = new SequencesDB();

        System.out.println("--------------------------");
        System.out.println("Welcome to the Basic Bioinformatics Toolkit!");
        System.out.println("Developed by: Kinsey Long");
        System.out.println("To begin, please add your first DNA sequence.");
        addSequenceUI(sequence_db);
        mainmenu(sequence_db);
    }
}
