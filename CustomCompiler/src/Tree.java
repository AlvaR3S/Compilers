/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author reynaldoalvarez
 */
public class Tree {
   
    
    public static void main(String args[]) {

        
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

        System.out.println(t);
    }
}
