// Question 4.b)
public class BrothersBinaryTree {
    // Definition of a binary tree node
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    private int xDepth = -1;
    private int yDepth = -1;
    private TreeNode xParent = null;
    private TreeNode yParent = null;

    // Function to check if two nodes are brothers in a binary tree
    public boolean areBrothers(TreeNode root, int x, int y) {
        findNodes(root, null, 0, x, y);
        return xDepth == yDepth && xParent != yParent;
    }

    // Recursive function to find nodes and their depths and parents
    private void findNodes(TreeNode node, TreeNode parent, int depth, int x, int y) {
        if (node == null) {
            return;
        }

        // Check if the current node is one of the target nodes
        if (node.val == x) {
            xDepth = depth;
            xParent = parent;
        } else if (node.val == y) {
            yDepth = depth;
            yParent = parent;
        }

        // Recursively search in the left and right subtrees
        findNodes(node.left, node, depth + 1, x, y);
        findNodes(node.right, node, depth + 1, x, y);
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);

        int x = 4;
        int y = 3;

        BrothersBinaryTree solution = new BrothersBinaryTree();
        boolean result = solution.areBrothers(root, x, y);
        System.out.println("Nodes " + x + " and " + y + " are brothers: " + result);
    }
}