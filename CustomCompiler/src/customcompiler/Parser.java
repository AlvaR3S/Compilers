/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customcompiler;

import java.util.ArrayList;
import static java.util.Objects.requireNonNull;



/**
 *
 * @author Reynaldo Alvarez
 */
public class Parser extends Lexer { 
    

    TokenType tokenType;
    Lexer lexer;
    Token input;
    String output;
    
    
    
    /**
     * Recieving the Lexer's input and output
     * also figures out the next token if there is any
     * @param input
     * @param output 
     */
    public Parser(Token input, String output) {
        input = this.input;
        output = this.output;
    }
    
    
    
    /**
     * Program       ::== Block $
     * Block         ::== { StatementList }
     * StatementList ::== Statement StatementList
     *               ::== ε <-- (empty set)
     */
    void Program() {
        Block();
    }
    
    char peek( ) {
        return currentTokenPosition < input.data.length( ) ? input.data.charAt( currentTokenPosition ) : (char)0;
    }
    
    
    
    void Block() {
//        if(input.data.equals("{")) {
//            match(tokenPostion);
//        }
    }


    
    /**
     * 
     * 
     * Expr ::== IntExpr        ::== digit intopExpr, digit
     *      ::== StringExpr     ::== " CharList "
     *      ::== BooleanExpr    ::== ( Expr boolop Expr ), boolval
     *      ::== ID             ::== char
     */
  
    
    
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