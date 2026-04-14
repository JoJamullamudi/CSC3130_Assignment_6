public class EncryptedBT {


    public boolean findElement( int[] tree, int element)
    {
        if( tree == null)
        {
            return false;
        }
        if ( tree.length == 0)
        {
            return false;
        }
        if ( tree[0] == -1)
        {
            return false;
        }

        return checkVal( )
    }

    public boolean checkVal( int[] tree, int element, int index, int val)
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

        checkVal(tree, element, index+1, left); // runs check again with the left child
        checkVal(tree, element, index+2, right);// runs check again with the right child


    }


}
