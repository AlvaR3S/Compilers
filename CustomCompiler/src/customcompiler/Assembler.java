/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customcompiler;

import customcompiler.Lexer.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import javax.swing.JTextArea;



/**
 *
 * @author reynaldoalvarez
 */
public class Assembler {
    String[] heap = new String[96];
    int heapNum = 0;
    int heapCount = 0;
    int g = 0;
    
    char[] currentRegister = {'T','0'};
    LinkedList<String> variables;
    LinkedList<Integer> variableScopes;
    Lexer lex = new Lexer();
    Parser parser;
    customAST ast;
    ArrayList<String> idList;
    ArrayList<Integer> scopeList;

    
    public enum Mnemonic {}
    
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
    
  
    /**
     * Starts the Code Generation phase
     * Initializes and declares variables that we will use
     * Leads into the initialize class
     * @param parser 
     */
    public Assembler(Parser parser) {
        variables = new LinkedList<String>();
        variableScopes = new LinkedList<Integer>();
        this.parser = parser;
        ast = parser.getAst();
        idList = parser.getIdList();
        scopeList = parser.getScopeList();
    }
    
    
    /**
     * First step and Last step
     * Begins by searching and storing information
     * needed for code generation
     * Ends by calling generateCode, which outputs the opCode
     */
    public void gatherAndGenerate() {
        // Grabs the already created AST and places the pointer at the root node
        LinkedList<astNodes> operations = searchChildren(ast.root);
        
        // Takes the AST information and implements code gen
        disassembleOperations(operations);
        
        // Testing if the operations found are correct 
        // for personal checking (Console output)
//        for(int i = 0; i < operations.size(); i++) {
//            System.out.println(operations.get(i).name);
//        }
        
        generateCode(); // Output generated code
    }
    
    
    /**
     * Searches through AST
     * Finds Parent info and children info
     * Stores children information for later use
     * @param node
     * @return 
     */
    private LinkedList<astNodes> searchChildren(astNodes node) {
        // Location where important information found will be stored
        LinkedList<astNodes> output = new LinkedList<astNodes>();
        
        // Pointers only hit Parents and checks if there are children
        if(node.hasChildren()) { 
            if(node.name.equals("Variable Declaration")) {
                output.add(node);
            } else if(node.name.equals("Assignment Statement")) {
                output.add(node);
            } else if(node.name.equals("Print Statement")) {
                output.add(node);
            }
            
            // Searching if current parent has any children and stores the value 
            for(int i = 0; i < node.children.size(); i++) { 
                LinkedList<astNodes> temp = searchChildren(node.children.get(i));
                // When the parents has a child that is also a parent keep searching through their children
                for(int j = 0; j < temp.size(); j++) { // Eventually moving back up the tree if there still more children
                    output.add(temp.get(j));
                }
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
    private void disassembleOperations(LinkedList<astNodes> operations) {
        for(int i = 0; i < operations.size(); i++) { // Loops through operations and finds specific names
            if(operations.get(i).name.equals("Variable Declaration")) {
               handleVarDecl(operations.get(i));
            } else if(operations.get(i).name.equals("Assignment Statement")) {
               handleAssignStatement(operations.get(i));
            } else if(operations.get(i).name.equals("Print Statement")) {
               handlePrintStatement(operations.get(i)); 
            } else {
               System.out.println("Error: Improper operation attempted");
            }
        }        
    }
    
    
    /**
     * When a Parent is VarDecl
     * This contains the correct OPCodes for VarDecl instances
     * @param varDecl 
     */
    private void handleVarDecl(astNodes varDecl) {
        heap[heapNum] = "A9";
        heapNum++;
        heap[heapNum] = "00";
        heapNum++;
        heap[heapNum] = "8D";
        heapNum++;
        heap[heapNum] = "" + currentRegister[0] + currentRegister[1];
        heapNum++;
        incrementRegister();
        
        variables.add(varDecl.children.get(1).name);
        endOperation();
    }
    
    
    /**
     * When a Parent is Assignment Statement
     * This contains the correct OPCodes for Assignment Statement instances
     * @param assignStatement
     */
    private void handleAssignStatement(astNodes assignStatement) {
        char[] temp = currentRegister;
        boolean newRegister = true;
        
        for(int i = 0; i < variables.size(); i++) {
            if(variables.get(i).equals(assignStatement.children.get(0).name)) {
                temp = getVariableInRegister(i);
                newRegister = false;
                break;
            }
        }
        
        heap[heapNum] = "A9";
        heapNum++;
        heap[heapNum] = assignStatement.children.get(1).name;
        heapNum++;
        heap[heapNum] = "8D";
        heapNum++;
        heap[heapNum] = "" + temp[0] + temp[1];
        heapNum++;
        incrementRegister();
        
        endOperation();
    }
    
    
    /**
     * When a Parent is Print Statement
     * This contains the correct OPCodes for Print Statement instances
     * @param printStat 
     */
    private void handlePrintStatement(astNodes printStat) {
        //Load the heap w/ the necessary OPcodes for the print statement
        endOperation();
    }
    
    
    /**
     * Double X's are placed after a statement
     */
    private void endOperation() {
        heap[heapNum] = "XX";
        heapNum++;
    }
    
    
    /**
     * Increments the Saved Number of VarDecls accordingly
     * When 9 is reached the saving Letter e.g T turns into next Letter
     * This is based off of ASCII order
     */
    private void incrementRegister() {
        if((int)currentRegister[1] < 57) {
            currentRegister[1] = (char)(currentRegister[1] + 1);
        } else {
            currentRegister[0] = (char)(currentRegister[0] + 1);
            currentRegister[1] = '0';
        }
    }

    
    /**
     * Increments the Letter part of the VarDecl
     * When 9 is reached the saving Letter e.g T turns into next Letter
     * This is based off of ASCII order
     * @param n
     * @return 
     */
    private char[] getVariableInRegister(int n) {
        char[] output = new char[1];
        output[0] = (char)('T' + (n / 10));
        output[1] = (char)(n % 10);
        return output;
    }
    
    
    /**
     * Outputs generated code from the disassembled
     */
    private void generateCode() {
        parser.getAstOutputAreaCodeGen().append("\n                Program 1 Code Generation\n" +
                                        "   -----------------------------------------\n\t   ");
        for(int k = 0; k < heap.length; k++) {
            if(g == 12) {
                g = 0;
                parser.getAstOutputAreaCodeGen().append("\n\t   ");
            }
            if(heap[k] == null) {
                g++;
                parser.getAstOutputAreaCodeGen().append("00 ");
            } else {
                g++;
                parser.getAstOutputAreaCodeGen().append(heap[k] + " ");
            }
        }
        parser.getAstOutputAreaCodeGen().append("\n   -----------------------------------------\n");
    }
}
