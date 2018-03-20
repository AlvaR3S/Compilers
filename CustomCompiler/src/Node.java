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
    LinkedList<String> children;
    LinkedList<String> parent;
    String name;
        
    public Node(Object  node) {
        this.children = (LinkedList<String>) node;
        this.parent = node;   
        
    }
    
    
    
}
