
import java.util.LinkedList;
import static jdk.nashorn.internal.objects.Global.undefined;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author reynaldoalvarez
 */
public class ParseTree {
    
    LinkedList<String> cur;        
    Object root;
    LinkedList<String> children;
    Object parent;
    String name;
    String kind;
    Node node;

    
    class Node {       
        LinkedList<String> children;
        Object parent;
        String name;
        public Node(Object parent, LinkedList<String> children, String name) {
            this.parent = parent;
            this.children = children;
            this.name = name;
        }
        
    }

    public ParseTree(Object root, LinkedList<String> cur) {
        // ----------
        // Attributes
        // ----------
        this.root = null;  // Note the NULL root node of this tree.
        this.cur = cur;   // Note the EMPTY current node of the tree we're building.
        
    } 
  
        // -- ------- --
        // -- Methods --
        // -- ------- --
    
        // Add a node: kind in {branch, leaf}.

    /**
     *
     * @param name
     * @param kind
     * @param children
     * @param parent
     */
    public void addNode(String name, String kind) {
        // Construct the node object.
        node.name = name;
        this.children = node.children;
        this.parent = node.parent;
        
        
        // Check to see if it needs to be the root node.
        if (this.root == null) {
            // We are the root node.
            this.root = node;
        } else {
            // We are the children.
            // Make our parent the CURrent node...
            this.parent = this.cur;
            // ... and add ourselves (via the unfrotunately-named
            // "push" function) to the children array of the current node.
            this.cur.addAll(node.children);
            
        }
        // If we are an interior/branch node, then...
        if (kind.equals("branch")) {
            // ... update the CURrent node pointer to ourselves.
            this.cur.addAll(node.children);
        }
    }

    // Note that we're done with this branch of the tree...
    public void endChildren() {
        // ... by moving "up" to our parent node (if possible).
        if ((this.parent != null) && (this.name != undefined)) {
            this.cur = (LinkedList<String>) this.parent;
        } else {
            // TODO: Some sort of error logging.
            // This really should not happen, but it will, of course.
        }
    }


    // Recursive function to handle the expansion of the nodes.
    public String expand(Object node, int depth) {
        
        String traversalResult = "";

        // Space out based on the current depth so
        // this looks at least a little tree-like.
        for (int i = 0; i < depth; i++) {
            traversalResult += "-";
        }

        // If there are no children (i.e., leaf nodes)...
        if (this.children.isEmpty()) {
            // ... note the leaf node.
            traversalResult += "[" + this.name + "]";
            traversalResult += "\n";
        } else {
            // There are children, so note these interior/branch nodes and ...
            traversalResult += "<" + this.name + "> \n";
            // .. recursively expand them.
            for (int i = 0; i < this.children.size(); i++) {
                expand(this.children.get(i), depth + 1);
            }
        }
    
        // Make the initial call to expand from the root.
        expand(this.root, 0);
        // Return the result.
        return traversalResult;  
    }  

  
}
