/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customcompiler;

import customcompiler.Lexer.Parser;
import java.util.ArrayList;



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
    
    Parser parser;
    customAST ast;
    ArrayList<String> idList;
    ArrayList<Integer> scopeList;
    
    
    public Assembler(Parser parser) {
        this.parser = parser;
        ast = parser.getAst();
        idList = parser.getIdList();
        scopeList = parser.getScopeList();
        initialize();
    }
    
    private void initialize() {
        customAST operations[];
        dissassembleOperations(operations[]);
    }
    
    private astNodes getOperations(customAST ast) {
        // Goes through AST tree and finds all
        // operations such as VarDecl, AssignStatement, Print Statement
        // stores them in an array of ASTNodes
        // returns the array
        
        return null;
    }
    
    private void dissassembleOperations(customAST operations[]) {
        for(int i = 0; i < operations.length; i++) {
            if(operations[i].name = "Variable Declaration") {
                handleVarDecl(operations[i]);
            } else if(operations[i].name = "Assignment Statement") {
               handleAssStat(operations[i]);
            } else if(operations[i].name = "Print Statment") {
               handlePrintStat(operations[i]); 
            } else {
                System.out.println("Error: Improper operation attempted");
            }
        }
        
        // Should convert all operations into their Op code
        // as well as store them in the heap
    }
    
    private void handleVarDecl(customAST varDecl){
        heap[heapRow][heapColumn] = "A9";
        incrementHeapRow();
        heap[heapRow][heapColumn] = "00";
        incrementHeapRow();
        heap[heapRow][heapColumn] = "8D";
        incrementHeapRow();
        heap[heapRow][heapColumn] = "" + currentRegister[0] + currentRegister[1];
        incrementHeapRow();
    }
    
    private void handleAssStat(customAST assState) {
        //Load the heap with the necessary OPcodes for the assign statement
        //from the information in the ASTNode
    }
    
    private void handlePrintStat(customAST printStat) {
     //Load the heap w/ the necessary OPcodes for the print statement   
    }
    }
    
    private void incrementRegister() {
        if((int)currentRegister[1] < 9){
            currentRegister[1] = currentRegister[1] + 1;
        } else {
            currentRegister[0] = currentRegister[0] + 1;
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
    
}
