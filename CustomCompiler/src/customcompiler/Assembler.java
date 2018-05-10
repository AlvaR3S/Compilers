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
    int heapNum = 0;
    int heapCount = 0;
    int g = 0;
    int savedPoint;
    
    char[] varNumList;
    char[] currentRegister = {'T','0'};
    
    String[] heap = new String[256];
    
    Lexer lex = new Lexer();
    Parser parser;
    customAST ast;
    
    ArrayList<Integer> scopeList;
    
    ArrayList<String> variables;
    ArrayList<String> regVariables;
    ArrayList<String> idList;
    ArrayList<String> stringList;
    ArrayList<String> printList;
    ArrayList<String> typeList;
  
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
        String currentVariable = assignStatement.children.get(0).name;
        String currentValue = assignStatement.children.get(1).name;
       // String heapNum = Integer.toString(heapNum);
        heap[heapNum] = "A9";
        for(int i = 0; i < heap[heapNum].length(); i++) {
            System.out.println(Arrays.toString(heap[heapNum].toCharArray()));
            System.out.println(Arrays.toString(heap[heapNum].getBytes()));
            break;
        }
        heapNum++;
        
        for(int h = 0; h < variables.size(); h++) {
            if(variables.get(h).equals(currentVariable)) {
               
               System.out.println("matched");
                if(variables.indexOf(h) == typeList.indexOf(h)) { // If this var pos matches their type position
                   System.out.println("value: " + currentValue);
                   System.out.println("var: " + currentVariable);
                   System.out.println("type: " + typeList.get(h));
                    if(typeList.get(h).contains("string")) {
                        System.out.println("CHECK: "+ DatatypeConverter.parseByte(Integer.toString(heapNum)));
                        heap[heapNum] = currentValue + "";
                        heapNum++;
                    } else if(typeList.get(h).contains("int")) {
                        heap[heapNum] = "0" + currentValue;
                        heapNum++;
                    } else if(typeList.get(h).contains("boolean")) {
                        heap[heapNum] = currentValue;
                        heapNum++;
                    } else {
                        System.out.println("blues clues");
                   }
               }
            } else {
               System.out.println("not a match");
            }
        }
        
        System.out.println(currentValue);
        
        heap[heapNum] = "8D";
        heapNum++;
        
        for(int i = 0; i < variables.size(); i++) {
            if(variables.get(i).equals(currentVariable)) {
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
        
//        for(int p = 0; p < ; p++) {
//            
//        }
//        
        
        if(variables.size() > 0 && currentPrintStatement.length() == 1) {
            heap[heapNum] = "AC";
            heapNum++;
            
            for(int i = 0; i < variables.size(); i++) {
                if(variables.get(i).equals(currentPrintStatement)) {
                    heap[heapNum] = "" + regVariables.get(i);
                    System.out.println("" + regVariables.get(i));
                    System.out.println("print: "+ currentPrintStatement);
                    System.out.println("printSize: "+ currentPrintStatement.length());
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
//                ByteBuffer endian = ByteBuffer.allocate(4);
//                endian.order(ByteOrder.LITTLE_ENDIAN);
//                while(endian.hasRemaining()) {
//                   System.out.println();
//                   System.out.println(f);
//                   System.out.println(Arrays.toString(src));
//                }
                


                System.out.println("YEA: " + currentPrintStatement.length());
                heap[heapNum] = "AD";
                heapNum++;
                
                savedPoint = heapNum;
                System.out.println("sa: " + savedPoint);
                System.out.println("he: " + heapNum);
                
                
                
                endOperation();
                A2(); 
                Num01();
                SystemCall();
                StringToHex(currentPrintStatement);
                System.out.println("printList: " + printList.get(0));
                System.out.println("sa: " + savedPoint);
                for(int n = 0; n < heap.length; n++) {
                    if(n == (heap.length - (1 + savedPoint))) {
                        heap[heapNum] = printList.get(0);
                    } else {
                        heapNum--;
                    }
                    
                }
                
                System.out.println("printList: " + printList.get(0));
                
            } else {
                for(int k = 0; k < currentPrintStatement.length(); k++) {
                    if(Character.isLetter(currentPrintStatement.charAt(0))) {
                        System.out.println("I am a letter");
                        System.out.println(currentPrintStatement.charAt(k));
                        
                        AD(); // Load the accumulator from memory
                        String store = StringToHex(currentPrintStatement); // Location where value is stored 
                        endOperation();
                        A0(); // Load the Y register with a constant
                        heap[heapNum] = store; // Location of where the string value is for the Y register
                        heapNum++;
                        Num8D(); // Store the accumulator in memory
                        // ****** VALUE WILL STORE THE CURRENT STRING INTO MEMMORY *****
                        endOperation();
                        A2(); // Load the X register with a constant
                        Num02(); // Print the 00-terminated string stored at the address in the Y register
                        SystemCall(); // Print letter out
                    } else {
                        System.out.println("I am a digit");
                        System.out.println(currentPrintStatement.charAt(k));
                        A0(); // Load the Y register with a constant
                        GetConstant(currentPrintStatement); // Returns the Current integer being evaluated
                        A2(); // Load the X register with a constant
                        Num01(); // Print the integer stored in the Y register
                        SystemCall();
                    }
                }
                
                System.out.println("var: " + currentPrintStatement);
                System.out.println("NOT a var: " + currentPrintStatement.length());
                System.out.println("current: " + printStatement.children.get(0).name);
            }
        }
    }
    
    
    /**
     * Returns the Current integer being evaluated
     * @return
     */
    private String GetConstant(String constant) {
        heap[heapNum] = "0" + constant;
        heapNum++;
        return heap[heapNum];
    }
    
    
    /**
     * Load the X register with a constant
     */
    private void A2() {
        heap[heapNum] = "A2";
        heapNum++;
    }
    
    
    /**
     * Print the 00-terminated string stored 
     * at the address in the Y register
     */
    private void Num02() {
        heap[heapNum] = "02";
        heapNum++;
    }
    
    
    /**
     * Print the integer stored in the Y register
     */
    private void Num01() {
        heap[heapNum] = "01";
        heapNum++;
    }
    
    
    /**
     * Store the accumulator in memory
     */
    private void Num8D() {
        heap[heapNum] = "8D";
        heapNum++;
    }
    
    
    /**
     * Load the Y register with a constant
     */
    private void A0() {
        heap[heapNum] = "A0";
        heapNum++;
    }
    
    
    /**
     * Load the accumulator from memory
     */
    private void AD() {
        heap[heapNum] = "AD";
        heapNum++;
    }
    

    /**
     * Double 00's are placed after a statement
     */
    private void endOperation() {
        heap[heapNum] = "00";
        heapNum++;
    }
    
    
    /**
     * Double FF's are placed after
     * finishing a print statement
     */
    private void SystemCall() {
        heap[heapNum] = "FF";
        heapNum++;
    }
    
    
    /**
     * Converts Strings into Hexadecimals and 
     * adds them to the heap at the end of table
     * @return 
     */
    private String StringToHex(String currentString) {
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
        
        for(int j = heapNum; j < heap.length - 1; j++) {
            heapNum++;
        }
        stringList.clear();
        for(int b = 0; b < printList.size(); b++) { 
            heapNum--;
        }
       
        
        for(int b = 0; b < printList.size(); b++) { 
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
