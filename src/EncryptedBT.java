public class EncryptedBT {


    public static boolean findElement( int[] tree, int element)
    {
        if( tree == null) // checks to see if tree has values
        {
            return false;
        }
        if ( tree.length == 0) // checks to see if the tree has values
        {
            return false;
        }
        if ( tree[0] == -1) // checks to see that the root has a value
        {
            return false;
        }

        return checkVal(tree, element, 0, 1 );
    }

    public static boolean checkVal( int[] tree, int element, int index, int val)
    {
        if (index >= tree.length) // checks to see if the current index is in bounds
        {
            return false;
        }
        if (tree[index] == -1) // checks to see if current node is empty
        {
            return false;
        }

        if ( val == element)// checks if the value is the element we're looking for
        {
            return true;
        }

        int left = 3*val + 1; // makes left child value
        int right = 2*val + 5; // makes right child value

        return checkVal(tree, element, index+1, left) || // runs check again with the left child
         checkVal(tree, element, index+2, right);// runs check again with the right child


    }

    public static void main(String[] args)
    {
        int[] etree = { -2, -2, -1, -2, -1};

        System.out.println(findElement(etree, 1));


    }



}
