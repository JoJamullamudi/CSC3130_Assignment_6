import java.util.ArrayList;

public class AllLess {

    public static ArrayList<String> checkLess( String[] heap, int len)
    {
        ArrayList<String> lessthan = new ArrayList<>(); // create an array list to

        for ( int a = 0; a< heap.length; a++)
        {
            if ( heap[a].length() < len && !heap[a].isEmpty()) // checks to see if the string is less than the input length and is not empty
            {
                lessthan.add(heap[a]);// adds string to the list
            }
        }

        return lessthan; // return less than list

    }

    public static void main (String[] args)
    {
        String[] h = {"zero", " size", "nutella", "jojo", "luna", "isse", "astor", "as", "entretien", "", "cal"};

        System.out.println(checkLess(h,3));
    }
}
