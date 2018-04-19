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
