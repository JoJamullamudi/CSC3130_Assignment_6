import java.util.ArrayList;

public class AllLess {

    public static ArrayList<String> checkLess( String[] heap, int len)
    {
        ArrayList<String> lessthan = new ArrayList<>();

        for ( int a = 0; a< heap.length; a++)
        {
            if ( heap[a].length() < len)
            {
                lessthan.add(heap[a]);
            }
        }

        return lessthan;

    }

    public static void main (String[] args)
    {
        String[] h = {"zero", " size", "nutella", "jojo", "luna", "isse", "astor", "as", "entretien", "", "cal"};

        System.out.println(checkLess(h,3));
    }
}
