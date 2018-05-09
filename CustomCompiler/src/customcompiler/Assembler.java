/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customcompiler;

import customcompiler.Lexer.*;
import java.nio.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import javax.swing.JTextArea;
import javax.xml.bind.DatatypeConverter;



/**
 *
 * @author reynaldoalvarez
 */
public class Assembler {
    String[] heap = new String[256];
    int heapNum = 0;
    int heapCount = 0;
    int g = 0;
    
    char[] currentRegister = {'T','0'};
    ArrayList<String> variables;
    ArrayList<String> regVariables;
    Lexer lex = new Lexer();
    Parser parser;
    customAST ast;
    ArrayList<String> idList;
    ArrayList<Integer> scopeList;
    char[] varNumList;
    ArrayList<String> stringList;
    ArrayList<String> printList;
    ArrayList<String> typeList;
    

    
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
        variables = new ArrayList<String>();
        regVariables = new ArrayList<String>();
        this.parser = parser;
        ast = parser.getAst();
        idList = parser.getIdList();
        scopeList = parser.getScopeList();
        stringList = new ArrayList<String>();
        printList = new ArrayList<String>();
        typeList = new ArrayList<String>();
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
        
        regVariables.add("" + currentRegister[0] + currentRegister[1]);
        variables.add(varDecl.children.get(1).name);
        typeList.add(varDecl.children.get(0).name);
        
        incrementRegister();
        System.out.println("type: " + varDecl.children.get(0).name);
        System.out.println("VARDEcl " + regVariables.get(0));
        endOperation();
    }
    
    
    /**
     * When a Parent is Assignment Statement
     * This contains the correct OPCodes for Assignment Statement instances
     * @param assignStatement
     */
    private void handleAssignStatement(astNodes assignStatement) { 
        heap[heapNum] = "A9";
        for(int i = 0; i < heap[heapNum].length(); i++) {
            System.out.println(Arrays.toString(heap[heapNum].toCharArray()));
            System.out.println(Arrays.toString(heap[heapNum].getBytes()));
            break;
        }
        heapNum++;
        
        for(int h = 0; h < variables.size(); h++) {
            if(variables.get(h).equals(assignStatement.children.get(0).name)) {
               System.out.println("matched");
                if(variables.indexOf(h) == typeList.indexOf(h)) { // If this var pos matches their type position
                   System.out.println("value: " + assignStatement.children.get(1).name);
                   System.out.println("var: " + assignStatement.children.get(0).name);
                   System.out.println("type: " + typeList.get(h));
                    if(typeList.get(h).contains("string")) {
                        System.out.println("im a string");
                    } else if(typeList.get(h).contains("int")) {
                        System.out.println("im a int");
                    } else if(typeList.get(h).contains("boolean")) {
                        System.out.println("im a boolean");
                    } else {
                        System.out.println("blues clues");
                   }
               }
           } else {
               System.out.println("not a match");
           }
        }
        heap[heapNum] = "0" + assignStatement.children.get(1).name;
        System.out.println(assignStatement.children.get(1).name);
        heapNum++;
        heap[heapNum] = "8D";
        heapNum++;
        
        for(int i = 0; i < variables.size(); i++) {
            if(variables.get(i).equals(assignStatement.children.get(0).name)) {
                heap[heapNum] = "" + regVariables.get(i);
                break;
            }
        }
        
        heapNum++;
        // Instead of INCREMENTING REGISTER FIND A WAY TO SEARCH UP SAVED T(NUM) LOCATIONs
        
        endOperation();
    }
    
    
    /**
     * When a Parent is Print Statement
     * This contains the correct OPCodes for Print Statement instances
     * @param printStatement 
     */
    private void handlePrintStatement(astNodes printStatement) {
        String currentPrintStatement = printStatement.children.get(0).name;
        
        System.out.println("there"); 
        
        if(variables.size() > 0) {
            heap[heapNum] = "AC";
            heapNum++;
            
            for(int i = 0; i < variables.size(); i++) {
                if(variables.get(i).equals(currentPrintStatement)) {
                    heap[heapNum] = "" + regVariables.get(i);
                    System.out.println("" + regVariables.get(i));
                    System.out.println(currentPrintStatement);
                    break;
                } else {
                   System.out.println("Vars are not the same.\n");
                }
            }
            
            heapNum++;
            endOperation();
            heap[heapNum] = "A2";
            heapNum++;
            heap[heapNum] = "01";
            heapNum++;
            SystemCall();
        } else {
            if(currentPrintStatement.length() > 1) {
                System.out.println("YEA: " + currentPrintStatement.length());
                heap[heapNum] = "AD";
                heapNum++;
                String f = "S0XX";
                byte[] src = f.getBytes();
                ByteBuffer endian = ByteBuffer.wrap(src);
                endian.order(ByteOrder.LITTLE_ENDIAN);
                while(endian.hasRemaining()) {
                   System.out.println();
                   System.out.println(f);
                   System.out.println(Arrays.toString(src));
                }
                
                endOperation();
                heap[heapNum] = "A2";
                heapNum++;
                heap[heapNum] = "01";
                heapNum++;
                SystemCall();
                AddStringToHex(currentPrintStatement);
                
            } else {
                System.out.println("var: " + currentPrintStatement);
                System.out.println("NOT a var: " + currentPrintStatement.length());
                System.out.println("current: " + printStatement.children.get(0).name);  
            }
        }
    }

    /**
     * Double X's are placed after a statement
     */
    private void endOperation() {
        heap[heapNum] = "XX";
        heapNum++;
    }
    
    
    /**
     * Double X's are placed after a statement
     */
    private void SystemCall() {
        heap[heapNum] = "FF";
        heapNum++;
    }
    
    
    /**
     * Converts Strings into Hexadecimals and 
     * adds them to the heap
     * @return 
     */
    private String AddStringToHex(String currentString) {
        byte[] f = currentString.getBytes();
            
        String stringStream = DatatypeConverter.printHexBinary(f);

        stringStream = stringStream.replaceAll("..", "$0 ").trim();

        for(int k = 0; k < stringStream.length(); k+=1) {
            char charAt = stringStream.charAt(k);
            stringList.add("" + charAt);
        }
        
        for(int i = 0; i < stringList.size(); i+=4) {
            printList.add(stringList.get(i) + stringList.get(i + 1));
            i--;
        }

        for(int b = 0; b < currentString.length(); b++) { 
            heap[heapNum] = printList.get(b);
            heapNum++;
        }
        return heap[heapNum];
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
        char[] output = new char[2];
        output[0] = (char) ('T' + (n / 10));
        output[1] = (char) (n % 10);
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
