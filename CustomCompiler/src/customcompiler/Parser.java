/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customcompiler;

/**
 *
 * @author reynaldoalvarez
 */


import customcompiler.Lexer.Token;
import java.util.ArrayList;




public class Parser {
    Lexer lex = new Lexer();
    Token token;
    
    private final ArrayList<Token> tokens;
    private int currentToken = 0;
    
    Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
    } 
    
    void nextToken() {
        
    }
    
    // Exceptions are thrown when Parser discovers an error
    public class ParserException extends Exception {
        public ParserException() { }

        // Formates the exception to return a message that details the error and gives the error
        public ParserException(String errorMessage, Throwable error) {
          super(errorMessage, error);
        }   
    }
    
    
    
    
    
    
    
    
    
    /**
     * 
     * 
     * Expr ::== IntExpr        ::== digit intopExpr, digit
     *      ::== StringExpr     ::== " CharList "
     *      ::== BooleanExpr    ::== ( Expr boolop Expr ), boolval
     *      ::== ID             ::== char
     */
    
//    public static int Expr(ArrayList<Token> token) {
//
//    }
    
   
    
    
    /**
     * 
     * Statement ::== Printokentatement        ::== print ( Expr )
     *           ::== Assignmentokentatement   ::== Id = Expr
     *           ::== VarDecl               ::== type Id
     *           ::== WhileStatement        ::== while BooleanExpr Block
     *           ::== IfStatement           ::== if BooleanExpr Block
     *           ::== Block                 ::== Program
     */ 



    
}

    