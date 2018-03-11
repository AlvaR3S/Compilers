/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customcompiler;



/**
 *
 * @author Reynaldo Alvarez
 */
public class Parser extends Lexer { 
    
    Token token;
    TokenType tokenType;
    Lexer lexer;
    String input;
    String output;
    int getNextToken;
    
    
    

    public Parser(String input, String output) {
        this.input = lexer.getInput();
        this.output = lexer.getOutputArea();
        this.getNextToken = lexer.currentTokenPosition;
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

    private void Block() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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