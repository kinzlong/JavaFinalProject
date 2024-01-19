package FinalProject;
import java.util.HashMap;
import java.util.Iterator;

//Hashamp storing sequences by a custom name
public class SequencesDB {
    private HashMap<String, Sequence> database = new HashMap<>();

    Boolean addSequence(String name, String sequence){
        // add new sequence to database if it does not already exist
        // returns false if sequence already exists
        if (database.containsKey(name) == false){
            database.put(name, new Sequence(sequence));
            return true;
        }
        else return false;
    }

    Boolean removeSequence(String name){
        // removes sequence from database if it exists
        if (database.containsKey(name)){
            database.remove(name);
            return true;
        }
        else return false;
    }

    Sequence getSequence(String name){
        //extracts a String sequence from the database
       try{
            Sequence sequence = database.get(name);
            return sequence;
        }
        catch (Exception e){
            System.err.println("No sequence with " + name + "in database.");
            return null;
        }
    }

    String [] getSequenceNames(Boolean print){
        //getter of list of sequence names in the database.
        //if print = true, it will also print all of the sequence names
        Iterator<HashMap.Entry<String, Sequence>> seq_iterator = database.entrySet().iterator();
        String [] seq_name_list = new String[database.size()];
        int i = 1;

        while (seq_iterator.hasNext()){
            HashMap.Entry<String, Sequence> sequence_current = seq_iterator.next();
            String name = sequence_current.getKey();
            if (print) {
                System.out.println(i + ". " + name);
            }
            seq_name_list[i - 1] = name;
            i++;
        }
        return seq_name_list;
    }


}
