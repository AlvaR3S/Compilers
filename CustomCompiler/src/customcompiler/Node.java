package customcompiler;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.LinkedList;

/**
 *
 * @author reynaldoalvarez
 */
public class Node {
    //Object node;
    LinkedList<Node> children = new LinkedList<Node>();
    Node parent = null;
    String name = null;
        
    public Node() {
       
    }
    
    public Node(String name) {
        this.name = name;
    }
    
    public Node(String name, Node parent, LinkedList<Node> children) {
        this.name = name;
        this.parent = parent;
        this.children = children;
    }
    
    public void push(Node child) {
        //System.out.println(child);
        //System.out.println(children);
        child.parent = this;
        children.add(child);
    }
    


    
    
    
}
