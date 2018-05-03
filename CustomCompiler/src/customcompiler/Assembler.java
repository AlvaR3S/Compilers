/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customcompiler;

import customcompiler.Lexer.Parser;
import java.util.ArrayList;
import java.util.LinkedList;



/**
 *
 * @author reynaldoalvarez
 */
public class Assembler {
    
    
    public enum Mnemonic {
        
    }
    
    public static enum OPCode {
        LDAC("A9"), 
        LDAM("AD"), 
        STAM("8D"), 
        ADC("6D"),
        LDXC("A2"), 
        LDXM("AE"), 
        LDYC("A0"), 
        LDYM("AC"),
        NOP("EA"), 
        BRK("00"), 
        CPX("EC"), 
        BNE("D0"),
        INC("EE"), 
        SYS("FF");
        
        private final String opCode;
        
        OPCode(String opCode) {
            this.opCode = opCode;
        }
        
        public String toString(){return opCode;}
    }
    
    String[][] heap = new String[12][8];
    int heapRow = 0;
    int heapColumn = 0;
    
    char[] currentRegister = {'T','0'};
    LinkedList<String> variables;
    LinkedList<Integer> variableScopes;
    
    Parser parser;
    customAST ast;
    ArrayList<String> idList;
    ArrayList<Integer> scopeList;
    
    
    
    public Assembler(Parser parser) {
        variables = new LinkedList<String>();
        variableScopes = new LinkedList<Integer>();
        
        this.parser = parser;
        ast = parser.getAst();
        idList = parser.getIdList();
        scopeList = parser.getScopeList();
        initialize();
    }
    
    private void initialize() {
        LinkedList<astNodes> operations = searchChildren(ast.root);
        dissassembleOperations(operations);
        
        for(int i = 0; i < operations.size(); i++) {
            System.out.println(operations.get(i).name);
        }
        
        checkHeap();
    }
    
    private LinkedList<astNodes> searchChildren(astNodes node) {
        LinkedList<astNodes> output = new LinkedList<astNodes>();
        
         if(node.name.equals("Variable Declaration")) {
           output.add(node);
        } else if(node.name.equals("Assignment Statement")) {
            output.add(node);
        } else if(node.name.equals("Print Statement")) {
            output.add(node);
        }
        
        if(node.hasChildren()){
            for(int i = 0; i < node.children.size(); i++) {
                LinkedList<astNodes> temp = searchChildren(node.children.get(i));
                    
                for(int j = 0; j < temp.size(); j++) {
                    output.add(temp.get(j));
                }
                
                /*if(node.children.get(i).hasChildren()) {
                    LinkedList<astNodes> temp = searchChildren(node.children.get(i));
                    
                    for(int j=0;j<temp.size();j++){
                        output.add(temp.get(j));
                    }
                } else {
                    if(node.children.get(i).name.equals("Variable Declaration")){
                        output.add(node.children.get(i));
                    } else if(node.name.equals("Assignment Statement")){
                        output.add(node.children.get(i));
                    } else if(node.name.equals("Print Statement")){
                        output.add(node.children.get(i));
                    }
                }*/
            }
        }
        
        return output;
    }
    
    /**
     * Loops through the stored operations and 
     * if one of these options are found then execute
     * the proper action for that operation
     * @param operations 
     */
    private void dissassembleOperations(LinkedList<astNodes> operations) {
        for(int i = 0; i < operations.size(); i++) {
            if(operations.get(i).name.equals("Variable Declaration")) {
                handleVarDecl(operations.get(i));
            } else if(operations.get(i).name.equals("Assignment Statement")) {
               handleAssStat(operations.get(i));
            } else if(operations.get(i).name.equals("Print Statement")) {
               handlePrintStat(operations.get(i)); 
            } else {
                System.out.println("Error: Improper operation attempted");
            }
        }        
    }
    
    private void handleVarDecl(astNodes varDecl) {
        heap[heapRow][heapColumn] = "A9";
        incrementHeapRow();
        heap[heapRow][heapColumn] = "00";
        incrementHeapRow();
        heap[heapRow][heapColumn] = "8D";
        incrementHeapRow();
        heap[heapRow][heapColumn] = "" + currentRegister[0] + currentRegister[1];
        incrementHeapRow();
        incrementRegister();
        
        variables.add(varDecl.children.get(1).name);
        endOperation();
    }
    
    private void handleAssStat(astNodes assState) {
        //Load the heap with the necessary OPcodes for the assign statement
        //from the information in the ASTNode
        
        char[] temp = currentRegister;
        boolean newRegister = true;
        
        for(int i = 0; i < variables.size(); i++) {
            if(variables.get(i).equals(assState.children.get(0).name)) {
                temp = getVariableRegister(i);
                newRegister = false;
                break;
            }
        }
        
        heap[heapRow][heapColumn] = "A9";
        incrementHeapRow();
        heap[heapRow][heapColumn] = assState.children.get(1).name;
        incrementHeapRow();
        heap[heapRow][heapColumn] = "8D";
        incrementHeapRow();
        heap[heapRow][heapColumn] = "" + temp[0] + temp[1];
        incrementHeapRow();
        incrementRegister();
        
        //if(newRegister){variables.add(assState.children.get(0).name);}
        
        endOperation();
    }
    
    private void handlePrintStat(astNodes printStat) {
     //Load the heap w/ the necessary OPcodes for the print statement
     
        endOperation();
    }
    
    private void endOperation() {
        heap[heapRow][heapColumn] = "XX";
        incrementHeapRow();
    }
    
    private void incrementRegister() {
        if((int)currentRegister[1] < 57) {
            currentRegister[1] = (char)(currentRegister[1] + 1);
        } else {
            currentRegister[0] = (char)(currentRegister[0] + 1);
            currentRegister[1] = '0';
        }
    }
    
    private boolean incrementHeapColumn() {
        heapColumn++;
        
        if(heapColumn >= 12) {
            heapColumn = 0;
            return incrementHeapRow();
        }
        
        return true;
    }
    
    private boolean incrementHeapRow() {
        heapRow++;
        
        if(heapRow >= 8) {
            heapRow=0;
            return false;
        }
        
        return true;
    }
    
    private char[] getVariableRegister(int n) {
        char[] output = new char[2];
        
        output[0] = (char)('T' + (n / 10));
        output[1] = (char)(n % 10);
        
        return output;
    }
    
    private void checkHeap(){
        for(int i = 0; i < heap.length; i++) {
            for(int j = 0; j < heap[0].length; i++) {
                System.out.println(heap[i][j]);
            }
        }
    }
}
