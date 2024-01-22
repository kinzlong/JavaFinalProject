package FinalProject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class main {
    //main action menu
    public static void mainmenu(SequencesDB db) throws java.io.IOException {
        System.out.println("---- MAIN MENU ----");
        System.out.println("Type the number corresponding to the action you want to execute, followed by the ENTER key.");
        System.out.println("1. Add a Sequence"); //COMPLETE
        System.out.println("2. Remove a Sequence"); //COMPLETE
        System.out.println("3. Single Sequence Toolkit"); //IN-PROGRESS
        System.out.println("4. Multiple Sequences Toolkit"); //TO-DO
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
            System.out.println("5. Transcribe Coding Strand"); //TO-DO
            System.out.println("6. Translate Coding Strand"); //TO-DO
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
                    System.out.println("The translated sequence of your coding strand is:");
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
