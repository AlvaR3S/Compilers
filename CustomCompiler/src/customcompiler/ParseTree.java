//-----------------------------------------
// treeDemo.js
//
// By Alan G. Labouseur, based on the 2009
// work by Michael Ardizzone and Tim Smith.
//-----------------------------------------
package customcompiler;


import static jdk.nashorn.internal.objects.Global.undefined;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author Alan G. Labouseur
 * @author reynaldoalvarez
 */
public class ParseTree {
    
    Node root;
    Node cur = new Node();

    public ParseTree() {
       // Root node is Program
       this.root = null;
    } 
    
 
  
        // -- ------- --
        // -- Methods --
        // -- ------- --
    
        // Add a node: kind in {branch, leaf}.

    /**
     *
     * @param name
     * @param kind 
     */
    public void addNode(String name, String kind) {
        // Construct the node object.
        Node node = new Node(name);
        
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
                this.cur.push(node);
        }
        // If we are an interior/branch node, then...
        if (kind.equals("branch")) {
            // ... update the CURrent node pointer to ourselves.
            this.cur = node;
        }       
    }

    // Note that we're done with this branch of the tree...
    public void endChildren() {
        Node node = new Node();
        // ... by moving "up" to our parent node (if possible).
        if ((this.cur.parent != null) && (this.cur.parent.name != undefined)) {
            this.cur = this.cur.parent;
            
        } else {
            // TODO: Some sort of error logging.
            // This really should not happen, but it will, of course.
        }
    }


    // Recursive function to handle the expansion of the nodes.

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        // Make the initial call to expand from the root.
        return expand((Node) this.root, 0);
        // Return the result.
    } 
        
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
                traversalResult += expand(node.children.get(i), depth + 1);
            }
        }
        
        return traversalResult;
    }  


  
}
