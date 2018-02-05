/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customcompiler;

import static customcompiler.Lexer.TokenType.EOP;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 *
 * @author Reynaldo Alvarez
 */
public class Lexer {
    
    // Defining our token types with their corresponding expression names 
    public static enum TokenType {
        
        
        EOP("[$]"),
        
        CHAR("[a|b|c|d|e|f|g|h|i|j|k|l|"
                + "m|n|o|p|q|r|s|t|u"
                + "|v|w|x|y|z]"),
        
        digit("[1-9][0-9]*"), 
        
        symbol("[*|/|+|-]"), 
        
        space("[ \t\f\r\n]+");
        
        
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
            return String.format("'%s' --> [%s]", data, type.name());
        }
    }
    
    
    public static ArrayList<Token> lex(String input) {
        // Returns tokens using the stored and formatted token information
        ArrayList<Token> tokens = new ArrayList<Token>(); 
        
        // Lexer takes the input, finds the patterns and places them into token format
        StringBuffer tokenPatternsBuffer = new StringBuffer();
        for (TokenType tokenType : TokenType.values())
            tokenPatternsBuffer.append(String.format("|(?<%s>%s)", tokenType.name(), tokenType.pattern));
        Pattern tokenPatterns = Pattern.compile(new String(tokenPatternsBuffer.substring(1)));
        
        // Lexer Matches the patterns and if they are valid, they will be added to the new tokens array for output
        Matcher matcher = tokenPatterns.matcher(input);
        while(matcher.find()) {
            if(matcher.group(TokenType.space.name()) != null) 
                continue;                
            else if(matcher.group(TokenType.CHAR.name()) != null) {
                tokens.add(new Token(TokenType.CHAR, matcher.group(TokenType.CHAR.name())));             
            } else if(matcher.group(TokenType.digit.name()) != null) {
                tokens.add(new Token(TokenType.digit, matcher.group(TokenType.digit.name())));            
            } else if(matcher.group(TokenType.symbol.name()) != null) {
                tokens.add(new Token(TokenType.symbol, matcher.group(TokenType.symbol.name())));            
            } else if(matcher.group(TokenType.EOP.name()) != null) {
                tokens.add(new Token(TokenType.EOP, matcher.group(TokenType.EOP.name())));
                
            }
        }
    
        return tokens;
    }
    
    public static void main(String[] args) {
        int i = 1;
        String input = "a + hat + 100 + 20 - 30$"
                + "T > l >$"; //the input that will be tested
        
        boolean t = true;
       
        while(t = true) {
            
             

            // Outputs a stream of tokens from the given input
            ArrayList<Token> tokens = lex(input);
            System.out.println("LEXER: Lexing program " + i + "...");
            for(Token token : tokens) {
                
                if(token.type == EOP){ // Lex ends program when "$" is found
                    System.out.println("LEXER:" + token);
                    System.out.println("LEXER: Lex completed successfully\n\n\n");
                    i = i + 1;
                    System.out.println("LEXER: Lexing program " + i + "...");
                } else {
                    System.out.println("LEXER:" + token); // Prints out tokens  
                    
                }
            }
            break;
            
        }  
         
    }
}
