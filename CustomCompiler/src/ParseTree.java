
import java.util.ArrayList;
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
    
    Object root;
    String kind;    
    Node node = new Node();
    String cur; 
    String parent;
        LinkedList<String> children;


    
    

    

    
   
    
    
    class Node { 
        Object node;
        String parent;
        LinkedList<String> children;
        String name; 
        String cur;
        public Node() {
            this.parent = (String) node;
            this.children = (LinkedList<String>) node;
            this.name = (String) node;
            
        }
        
    }


    public ParseTree(Object root, String cur) {
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
        
        this.kind = kind;
        node.name = name;
        
        // Check to see if it needs to be the root node.
        if (this.root == null) {
            // We are the root node.
            
            this.root = node;
        } else {
            // We are the children.
            // Make our parent the CURrent node...
                node.parent = this.cur;
            // ... and add ourselves (via the unfrotunately-named
            // "push" function) to the children array of the current node.
                this.cur.children.push(node.name);
        }
        // If we are an interior/branch node, then...
        if (kind.equals("branch")) {
            // ... update the CURrent node pointer to ourselves.
            this.cur = node.cur;
        }
    }

    // Note that we're done with this branch of the tree...
    public void endChildren() {
        // ... by moving "up" to our parent node (if possible).
        if ((node.parent != null) && (node.name != undefined)) {
            this.cur = node.parent;
            
        } else {
            // TODO: Some sort of error logging.
            // This really should not happen, but it will, of course.
        }
    }


    // Recursive function to handle the expansion of the nodes.
    public String expand(Node node, int depth) {
        
        String traversalResult = "";

        // Space out based on the current depth so
        // this looks at least a little tree-like.
        for (int i = 0; i < depth; i++) {
            traversalResult += "-";
        }

        // If there are no children (i.e., leaf nodes)...
        if (node.children.isEmpty()) {
            // ... note the leaf node.
            traversalResult += "[" + node.name + "]";
            traversalResult += "\n";
        } else {
            // There are children, so note these interior/branch nodes and ...
            traversalResult += "<" + node.name + "> \n";
            // .. recursively expand them.
            for (int i = 0; i < node.children.size(); i++) {
                expand(node, depth + 1);
            }
        }
        
        // Make the initial call to expand from the root.
        expand((Node) this.root, 0);
        // Return the result.
        return traversalResult;  
    }  
    public static void main(String args[]) {
        
        ParseTree t = new ParseTree("", "");
            t.addNode("Root", "branch");
            t.addNode("Expr", "branch");
            t.addNode("Term", "branch");
            t.addNode("Factor", "branch");
            t.addNode("a", "leaf");
            t.endChildren();
            t.endChildren();
            t.endChildren();
            // t.endChildren();  // Un-comment this to test guards against moving "up" past the root of the tree.

            t.addNode("Op", "branch");
            t.addNode("+", "leaf");
            t.endChildren();

            t.addNode("Term", "branch");
            t.addNode("Factor", "branch");
            t.addNode("2", "leaf");
            t.endChildren();
            t.endChildren();
            
            System.out.println(t.node.cur);
    }

  
}
