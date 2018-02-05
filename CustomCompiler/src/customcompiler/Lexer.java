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
public class Lexer {
    
    // Defining our token types with their corresponding expression names 
    public static enum TokenType {
        NUMBER("-?[0-9]+"), BINARYOP("[*|/|+|-]"), WHITESPACE("[ \t\f\r\n]+");
        
        public final String pattern;
        
        private TokenType(String pattern) {
            this.pattern = pattern;
        }
    }
    
    // Stores the token data
    public static class Token {
        public TokenType type;
        public String data;
        
        public Token(TokenType type, String data) {
            this.type = type;
            this.data = data;
        }
        
        @Override
        public String toString() { // Structures token data for later output
            return String.format("(%s %s", type.name(), data);
        }
    }
    
    public static void main(String[] args) {
        String input = "50 + 20 - 30"; //the input that will be tested
    }
}
