import java.util.Hashtable;

public class Huffman {

    public Hashtable<Character, Integer> counts;

    public Hashtable<Character, Integer> frequencyCount(String input) {
        Hashtable<Character, Integer> counts = new Hashtable<>();
        for (int a = 0; a < input.length(); a++)
        {
            if (counts.containsKey(input.charAt(a)))
            {
                counts.replace(input.charAt(a), (int)counts.get(input.charAt(a)) + 1);
            }
            lse
            {
                counts.put(input.charAt(a), 1);
            }
        }
        return counts;
    }





}
