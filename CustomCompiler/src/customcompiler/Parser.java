/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customcompiler;

import static customcompiler.Lexer.TokenType.*;

/**
 *
 * @author reynaldoalvarez
 */


public class Parser {
    
//    Lexer lex = new Lexer();
//    
//    public void parse() {
//        parseProgram();
//    }
//    
//    private void parseProgram() {
//        
//    }
//    
//    
    /**
     * 
     * 
     * Expr ::== IntExpr        ::== digit intopExpr, digit
     *      ::== StringExpr     ::== " CharList "
     *      ::== BooleanExpr    ::== ( Expr boolop Expr ), boolval
     *      ::== ID             ::== char
     */
    private void Expr() {
        
    }
}
    
    
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