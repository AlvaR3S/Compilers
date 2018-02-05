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
        
        
        
        // -----------------|End of Program Marker|---------------------
        EOP("[$]"),
        
        
        // -----------------|Keywords|Numbers|Letters|---------------------
        // Keywords
        type_int("[int]"),
        type_String("[String]"),
        type_boolean("[boolean]"),
        type_abstract("[abstract]"),
        type_continue("[continue]"),
        type_for("[for]"),
        type_new("[new]"),
        type_switch("[switch]"),
        type_assert("[assert]"),
        type_default("[default]"),
        type_goto("[goto]"),
        type_package("[package]"),
        type_synchronized("[synchronized]"),
        type_do("[do]"),
        type_if("[if]"),
        type_private("[private]"),
        type_this("[this]"),
        type_break("[break]"),
        type_double("[double]"),
        type_implements("[implements]"),
        type_protected("[protected]"),
        type_throw("[throw]"),
        type_byte("[byte]"),
        type_else("[else]"),
        type_import("[import]"),
        type_public("[public]"),
        type_throws("[throws]"),
        type_case("[case]"),
        type_enum("[enum]"),
        type_instanceof("[instanceof]"),
        type_return("[return]"),
        type_transient("[transient]"),
        type_catch("[catch]"),
        type_extends("[extends]"),
        type_short("[short]"),
        type_try("[try]"),
        type_char("[char]"),
        type_final("[final]"),
        type_interface("[interface]"),
        type_static("[static]"),
        type_void("[void]"),
        type_class("[class]"),
        type_finally("[finally]"),
        type_long("[long]"),
        type_strictfp("[strictfp]"),
        type_volatile("[volatile]"),
        type_const("[const]"),
        type_float("[float]"),
        type_native("[native]"),
        type_super("[super]"),
        type_while("[while]"),
        
        // Booleans
        boolval_false("[false]"),
        boolval_true("[true]"),
        
        // Chars
        CHAR("[a|b|c|d|e|f|g|h|i|j|k|l|"
                + "m|n|o|p|q|r|s|t|u"
                + "|v|w|x|y|z]"),
        
        // Numbers
        digit("[1-9][0-9]*"), 
        
        
        // -----------------|Symbols|---------------------
        // Assignment Operator
        intop_assignment("[=]"),
        
        // Arithmetic Operators
        intop_remainder("[%]"),
        intop_substraction("[-]"),
        intop_addition("[+]"),
        intop_multiplication("[*]"), 
        intop_division("[/]"),
        
        // Unary Operators
        intop_increment("[++]"),
        intop_decrement("[--]"),
        
        //Equality and Relational Operators
        boolop_equalTo("[==]"),
        boolop_notEqualTo("[!=]"),
        boolop_greaterThan("[>]"),
        boolop_greaterThanOrEqualTo("[>=]"),
        boolop_lessThan("[<]"),
        boolop_lessThanOrEqualTo("[<=]"),
        
        // Conditional Operators
        boolop_AND("[&&]"),
        boolop_OR("[||]"),
        
        // Brackets, Parenthesis, and Quotation Mark
        opening_bracket("[{]"),
        closing_bracket("[}]"),
        opening_parenthesis("[(]"),
        closing_parenthesis("[)]"),
        //quote("[\" ]"),
        
        
        // -----------------|Whitespace|---------------------
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
            } else if(matcher.group(TokenType.intop_remainder.name()) != null) {
                tokens.add(new Token(TokenType.intop_remainder, matcher.group(TokenType.intop_remainder.name())));            
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
