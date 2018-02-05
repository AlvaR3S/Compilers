/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customcompiler;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 *
 * @author Reynaldo Alvarez
 */
public class Lexer {
    
    // Defining our token types with their corresponding expression names 
    public static enum TokenType {
        digit("-?[0-9]+"), symbol("[*|/|+|-]"), space("[ \t\f\r\n]+");
        
        public final String pattern;
        
        private TokenType(String pattern) {
            this.pattern = pattern;
        }
    }
    
    // Stores token type and data
    public static class Token {
        public TokenType type;
        public String data;
        
        public Token(TokenType type, String data) {
            this.type = type;
            this.data = data;
        }
        
        @Override
        public String toString() { // Structures token type and data for output
            return String.format("(%s %s", type.name(), data);
        }
    }
    
    
    public static ArrayList<Token> lex(String input) {
        // Returns tokens using the stored and formatted token information
        ArrayList<Token> tokens = new ArrayList<Token>(); 
        
        // 
        StringBuffer tokenPatternsBuffer = new StringBuffer();
        for (TokenType tokenType : TokenType.values())
            tokenPatternsBuffer.append(String.format("|(?<%s>%s)", tokenType.name(), tokenType.pattern));
        Pattern tokenPatterns = Pattern.compile(new String(tokenPatternsBuffer.substring(1)));
        
        return tokens;
    }
    
    public static void main(String[] args) {
        String input = "50 + 20 - 30"; //the input that will be tested
        
        // Outputs a stream of tokens from the given input
        ArrayList<Token> tokens = lex(input);
        for (Token token : tokens)
            System.out.println(token);
    }
}
