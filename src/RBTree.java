import java.util.ArrayList;

class RBTNode {
    public int key;
    public RBTNode parent;
    public RBTNode left;
    public RBTNode right;
    public boolean isRed;

    public RBTNode(int nodeVal, RBTNode parentNode, boolean color, RBTNode LChild, RBTNode RChild) { //constructor for node
        key = nodeVal;
        parent = parentNode;
        left = LChild;
        right = RChild;
        isRed = color;
    }

    public RBTNode(int nodeVal, RBTNode parentNode, boolean color) {// allows you to make a node without knowing their children.
        this(nodeVal, parentNode, color, null, null);
    }
    // setter and getters
    public int getKey() {
        return key;
    }
    public void setKey(int key) {
        this.key = key;
    }
    public RBTNode getParent()
    {
        return parent;
    }
    public void setParent(RBTNode parent) {
        this.parent = parent;
    }
    public RBTNode getLeft() {
        return left;
    }
    public RBTNode getRight() {
        return right;
    }
    public boolean isRed() {
        return isRed;
    }
    public boolean isBlack()
    { return !isRed;
    }

    public void setLeft(RBTNode newChild) { //sets left child and make sure the parents are pointing to the correct child
        left = newChild;
        if (left != null) {
            left.parent = this;
        }
    }

    public void setRight(RBTNode newChild) { //sets right child and make sure the parents are pointing to the correct child
        right = newChild;
        if (right != null) {
            right.parent = this;
        }
    }

    public RBTNode getGrandparent() { // gets parent's parent
        if (getParent() == null) {
            return null;
        }
        return parent.getParent();
    }

    public RBTNode getPredecessor() { // gets nodes predecessor from the left subtree
        RBTNode node = getLeft();
        while (node.getRight() != null) {
            node = node.getRight();
        }
        return node;
    }

    public RBTNode getSibling() { // gets other node that shares the same parent
        if (getParent() != null) {
            if (this == getParent().getLeft()) {
                return getParent().getRight();
            }
            return getParent().getLeft();
        }
        return null;
    }

    public RBTNode getUncle() { // gets parents sibling
        RBTNode grandparent = getGrandparent();
        if (grandparent == null) {
            return null;
        }
        if (grandparent.getLeft() == getParent()) {
            return grandparent.getRight();
        }
        return grandparent.getLeft();
    }

    public boolean areBothChildrenBlack() { // checks if both children are black nodes
        if (getLeft() != null && getLeft().isRed()) {
            return false;
        }
        if (getRight() != null && getRight().isRed()) {
            return false;
        }
        return true;
    }

    public boolean replaceChild(RBTNode currentChild, RBTNode newChild) { //switches children
        if (getLeft() == currentChild) {
            setLeft(newChild);
            return true;
        } else if (getRight() == currentChild) {
            setRight(newChild);
            return true;
        }
        return false;
    }
}

public class RBTree {
    private RBTNode root;

    public RBTree() {
        root = null;
    }

    private RBTNode search(int key) { // searches for a node by the key
        RBTNode current = root;
        while (current != null) {
            if (current.getKey() == key) {
                return current;
            } else if (key < current.getKey()) {
                current = current.getLeft();
            } else {
                current = current.getRight();
            }
        }
        return null;
    }

    public boolean insertKey(int key) { // inserts key into tree
        if (search(key) != null) {
            return false; // return false if key is alright there
        }
        RBTNode newNode = new RBTNode(key, null, true);
        insertNode(newNode);
        return true;
    }

    private void insertNode(RBTNode node) {
        if (root == null) {
            root = node;
        } else {
            RBTNode current = root;
            while (current != null) {
                if (node.getKey() < current.getKey()) { // if less than current then move left
                    if (current.getLeft() == null) {
                        current.setLeft(node);
                        break;
                    } else {
                        current = current.getLeft();
                    }
                } else {
                    if (current.getRight() == null) { // else move right
                        current.setRight(node);
                        break;
                    } else {
                        current = current.getRight();
                    }
                }
            }
        }
        node.isRed = true; // makes the inserted node red
        insertionBalance(node); // balances the tree with the RB Tree rules
    }

    private void insertionBalance(RBTNode node) { //
        if (node.getParent() == null) { // if the node is the parent then set it black
            node.isRed = false;
            return;
        }
        if (node.getParent().isBlack()) { // if the parent is black then nothing else needed
            return;
        }
        // will need the parent, grandparent, and uncle for balancing
        RBTNode parent = node.getParent();
        RBTNode grandparent = node.getGrandparent();
        RBTNode uncle = node.getUncle();

        if (uncle != null && uncle.isRed()) { // if parent and unc are red then make them both back and the grandparent red
            parent.isRed = false;
            uncle.isRed = false;
            grandparent.isRed = true;
            insertionBalance(grandparent); // balance from grandparent
            return;
        }

        if (node == parent.getRight() && parent == grandparent.getLeft()) { // checks for left right case
            rotateLeft(parent);
            node = parent;
            parent = node.getParent();
        } else if (node == parent.getLeft() && parent == grandparent.getRight()) { // checks for right left case
            rotateRight(parent);
            node = parent;
            parent = node.getParent();
        }

        parent.isRed = false; // makes parent black and grand red
        grandparent.isRed = true;
        if (node == parent.getLeft()) {
            rotateRight(grandparent); // rotates right or left at grandparent depends on the case
        } else {
            rotateLeft(grandparent);
        }
    }

    private void rotateLeft(RBTNode node) { // rotates node to the left
        RBTNode rightLeftChild = node.getRight().getLeft();
        if (node.getParent() != null) {
            node.getParent().replaceChild(node, node.getRight());
        } else {
            root = node.getRight();
            root.setParent(null);
        }
        node.getRight().setLeft(node);
        node.setRight(rightLeftChild);
    }

    private void rotateRight(RBTNode node) {// rotates node to the right
        RBTNode leftRightChild = node.getLeft().getRight();
        if (node.getParent() != null) {
            node.getParent().replaceChild(node, node.getLeft());
        } else {
            root = node.getLeft();
            root.setParent(null);
        }
        node.getLeft().setRight(node);
        node.setLeft(leftRightChild);
    }

    private void bstRemove(int key) {
        RBTNode node = search(key); // checks if node is their to remove
        bstRemoveNode(node);
    }

    private void bstRemoveNode(RBTNode node) {
        if (node == null) {
            return;
        }
        // removal has diffent cases depending on the situation and what your removing

        // Case 1: Internal node with 2 children
        if (node.getLeft() != null && node.getRight() != null) {
            RBTNode successorNode = node.getRight();
            while (successorNode.getLeft() != null) {
                successorNode = successorNode.getLeft();
            }
            int successorKey = successorNode.getKey();
            bstRemoveNode(successorNode);
            node.setKey(successorKey);
        }

        // Case 2: Root node with 1 or 0 children
        else if (node == root) {
            if (node.getLeft() != null) {
                root = node.getLeft();
            } else {
                root = node.getRight();
            }
            if (root != null) {
                root.setParent(null);
            }
        }

        // Case 3: Internal with left child only
        else if (node.getLeft() != null) {
            node.getParent().replaceChild(node, node.getLeft());
        }

        // Case 4: Internal with right child or leaf
        else {
            node.getParent().replaceChild(node, node.getRight());
        }
    }

    public boolean isNullOrBlack(RBTNode node) {// checks if node is null or black
        if (node == null) {
            return true;
        }
        return node.isBlack();
    }

    public boolean isNotNullAndRed(RBTNode node) { // checks if node is not null or red
        if (node == null) {
            return false;
        }
        return node.isRed();
    }

    private void prepareForRemoval(RBTNode node) { // fixes RB rules before removing a node
        // the different case happens depending on the removal situation.
        if (tryCase1(node)) {
            return;
        }
        RBTNode sibling = node.getSibling();
        if (tryCase2(node, sibling)) {
            sibling = node.getSibling();
        }
        if (tryCase3(node, sibling)) {
            return;
        }
        if (tryCase4(node, sibling)) {
            return;
        }
        if (tryCase5(node, sibling)) {
            sibling = node.getSibling();
        }
        if (tryCase6(node, sibling)) {
            sibling = node.getSibling();
        }

        sibling.isRed = node.getParent().isRed; // recoloring and  rotating
        node.getParent().isRed = false;
        if (node == node.getParent().getLeft()) {
            sibling.getRight().isRed = false;
            rotateLeft(node.getParent());
        } else {
            sibling.getLeft().isRed = false;
            rotateRight(node.getParent());
        }
    }

    public boolean removeKey(int key) { // removes key from the tree
        RBTNode node = search(key);
        if (node != null) {
            removeNode(node);
            return true;
        }
        return false;
    }

    private void removeNode(RBTNode node) { // if a node it has two children swap with predecessor and remove
        if (node.getLeft() != null && node.getRight() != null) {
            RBTNode predecessorNode = node.getPredecessor();
            int predecessorKey = predecessorNode.getKey();
            removeNode(predecessorNode);
            node.setKey(predecessorKey);
            return;
        }
        if (node.isBlack()) {
            prepareForRemoval(node);
        }
        bstRemove(node.getKey());
        if (root != null && root.isRed()) {
            root.isRed = false;
        }
    }

    private boolean tryCase1(RBTNode node) { // if the note is red or the root
        if (node.isRed() || node.getParent() == null) {
            return true;
        }
        return false;
    }

    private boolean tryCase2(RBTNode node, RBTNode sibling) { // if sibling is red
        if (sibling.isRed()) {
            node.getParent().isRed = true;
            sibling.isRed = false;
            if (node == node.getParent().getLeft()) {
                rotateLeft(node.getParent());
            } else {
                rotateRight(node.getParent());
            }
            return true;
        }
        return false;
    }

    private boolean tryCase3(RBTNode node, RBTNode sibling) { //if parent sibling and the children are black
        if (node.getParent().isBlack() && sibling.areBothChildrenBlack()) {
            sibling.isRed = true;
            prepareForRemoval(node.getParent());
            return true;
        }
        return false;
    }

    private boolean tryCase4(RBTNode node, RBTNode sibling) { // if parent is red and sibling and children are black
        if (node.getParent().isRed() && sibling.areBothChildrenBlack()) {
            node.getParent().isRed = false;
            sibling.isRed = true;
            return true;
        }
        return false;
    }

    private boolean tryCase5(RBTNode node, RBTNode sibling) { // siblings left child is red, right is black
        if (isNotNullAndRed(sibling.getLeft()) && isNullOrBlack(sibling.getRight())) {
            if (node == node.getParent().getLeft()) {
                sibling.isRed = true;
                sibling.getLeft().isRed = false;
                rotateRight(sibling);
                return true;
            }
        }
        return false;
    }

    private boolean tryCase6(RBTNode node, RBTNode sibling) { //siblings right child is red
        if (isNullOrBlack(sibling.getLeft()) && isNotNullAndRed(sibling.getRight())) {
            if (node == node.getParent().getRight()) {
                sibling.isRed = true;
                sibling.getRight().isRed = false;
                rotateLeft(sibling);
                return true;
            }
        }
        return false;
    }

    public void inRange(int a, int b) {
        ArrayList<Integer> toDelete = new ArrayList<>();// make a list of store what we need to delete
        collectInRange(root, a, b, toDelete);
        for (int c = 0; c < toDelete.size(); c++) { // call the remove key method to remove it from the tree
            removeKey(toDelete.get(c));
        }
    }

    private void collectInRange(RBTNode node, int a, int b, ArrayList<Integer> result) {
        if (node == null) {
            return;
        }
        collectInRange(node.getLeft(), a, b, result); // checks left sub tree
        if (node.getKey() >= a && node.getKey() <= b) { // checks if no one is in range
            result.add(node.getKey());
        }
        collectInRange(node.getRight(), a, b, result); // checks right sub tree
    }

    public void printInorder() {
        printInorder(root);
        System.out.println();
    }

    private void printInorder(RBTNode node) { // Prince using the in order traversal left current right
        if (node == null) {
            return;
        }
        printInorder(node.getLeft());
        System.out.print(node.getKey() + " ");
        printInorder(node.getRight());
    }


    public static void main(String[] args) {
        int[] tree = {10, 19, 20, 30, 42, 55, 77};

        RBTree t = new RBTree();
        for (int a = 0; a < tree.length; a++) {
            t.insertKey(tree[a]);
        }
        t.inRange(15, 20);
        t.printInorder();

        t = new RBTree();
        for (int a = 0; a < tree.length; a++) {
            t.insertKey(tree[a]);
        }
        t.inRange(25, 60);
        t.printInorder();

    }
}