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
    
    // Defining our token typeS with their corresponding expression names 
    public static enum TokenType {
        
        
        
        // -----------------|End of Program Marker|--------------------- \\
        EOP("[$]"),
        
        
        // -------------|Keywords|Numbers|Letters|Booleans|---------------- \\
        // Keywords
        typeInt("int"),
        typeString("String"),
        typeBoolean("boolean"),
        typeAbstract("abstract"),
        typeContinue("continue"),
        typeFor("for"),
        typeNew("new"),
        typeSwitch("switch"),
        typeAssert("assert"),
        typeDefault("default"),
        typeGoto("goto"),
        typePackage("package"),
        typeSynchronized("synchronized"),
        typeDo("do"),
        typeIf("if"),
        typePrivate("private"),
        typeThis("this"),
        typeBreak("break"),
        typeDouble("double"),
        typeImplements("implements"),
        typeProtected("protected"),
        typeThrow("throw"),
        typeByte("byte"),
        typeElse("else"),
        typeImport("import"),
        typePublic("public"),
        typeThrows("throws"),
        typeCase("case"),
        typeEnum("enum"),
        typeInstanceof("instanceof"),
        typeReturn("return"),
        typeTransient("transient"),
        typeCatch("catch"),
        typeExtends("extends"),
        typeShort("short"),
        typeTry("try"),
        typeChar("char"),
        typeFinal("final"),
        typeInterface("interface"),
        typeStatic("static"),
        typeVoid("void"),
        typeClass("class"),
        typeFinally("finally"),
        typeLong("long"),
        typeStrictfp("strictfp"),
        typeVolatile("volatile"),
        typeConst("const"),
        typeFloat("float"),
        typeNative("native"),
        typeSuper("super"),
        typeWhile("while"),
        
        // Booleans
        boolvalFalse("false"),
        boolvalTrue("true"),
        
        // Letters
        CHAR("[a-z][A-Z]"), 
        
        // Numbers
        digit("[1-9][0-9]+"), 
        
        
        // --------------------|Symbols|--------------------------- \\
        // Assignment Operator
        intopAssignment("="),
        
        // Arithmetic Operator
        intopRemainder("[%]"),
        intopSubstraction("[-]"),
        intopAddition("[+]"),
        intopMultiplication("[*]"), 
        intopDivision("[/]"),
        
        // Unary Operator
        boolopNotEqualTo("!="),
        boolopGreaterThan(">"),
        boolopLessThan("<"),
        boolopAND("&&"),
        
        // Brackets
        openBracket("[{]"),
        closeBracket("[}]"),
        
        // Parenthesis
        openParenthesis("[(]"),
        closeParenthesis("[)]"),
        
        // Quotation Mark
        QUOTE("\""),
        
        // Whitespace
        space("[ \t\f\r\n+]");
        
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
            return String.format("\"%s\" --> [%s]", data, type.name());
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
            else if(matcher.group(TokenType.typeInt.name()) != null) {
                tokens.add(new Token(TokenType.typeInt, matcher.group(TokenType.typeInt.name()))); 
            } else if(matcher.group(TokenType.typeString.name()) != null) {
                tokens.add(new Token(TokenType.typeString, matcher.group(TokenType.typeString.name()))); 
            } else if(matcher.group(TokenType.typeBoolean.name()) != null) {
                tokens.add(new Token(TokenType.typeBoolean, matcher.group(TokenType.typeBoolean.name()))); 
            } else if(matcher.group(TokenType.typeAbstract.name()) != null) {
                tokens.add(new Token(TokenType.typeAbstract, matcher.group(TokenType.typeAbstract.name()))); 
            } else if(matcher.group(TokenType.typeContinue.name()) != null) {
                tokens.add(new Token(TokenType.typeContinue, matcher.group(TokenType.typeContinue.name()))); 
            } else if(matcher.group(TokenType.typeFor.name()) != null) {
                tokens.add(new Token(TokenType.typeFor, matcher.group(TokenType.typeFor.name()))); 
            } else if(matcher.group(TokenType.typeNew.name()) != null) {
                tokens.add(new Token(TokenType.typeNew, matcher.group(TokenType.typeNew.name()))); 
            } else if(matcher.group(TokenType.typeSwitch.name()) != null) {
                tokens.add(new Token(TokenType.typeSwitch, matcher.group(TokenType.typeSwitch.name()))); 
            } else if(matcher.group(TokenType.typeAssert.name()) != null) {
                tokens.add(new Token(TokenType.typeAssert, matcher.group(TokenType.typeAssert.name()))); 
            } else if(matcher.group(TokenType.typeDefault.name()) != null) {
                tokens.add(new Token(TokenType.typeDefault, matcher.group(TokenType.typeDefault.name()))); 
            } else if(matcher.group(TokenType.typeGoto.name()) != null) {
                tokens.add(new Token(TokenType.typeGoto, matcher.group(TokenType.typeGoto.name()))); 
            } else if(matcher.group(TokenType.typePackage.name()) != null) {
                tokens.add(new Token(TokenType.typePackage, matcher.group(TokenType.typePackage.name()))); 
            } else if(matcher.group(TokenType.typeSynchronized.name()) != null) {
                tokens.add(new Token(TokenType.typeSynchronized, matcher.group(TokenType.typeSynchronized.name()))); 
            } else if(matcher.group(TokenType.typeDo.name()) != null) {
                tokens.add(new Token(TokenType.typeDo, matcher.group(TokenType.typeDo.name()))); 
            } else if(matcher.group(TokenType.typeIf.name()) != null) {
                tokens.add(new Token(TokenType.typeIf, matcher.group(TokenType.typeIf.name()))); 
            } else if(matcher.group(TokenType.typePrivate.name()) != null) {
                tokens.add(new Token(TokenType.typePrivate, matcher.group(TokenType.typePrivate.name()))); 
            } else if(matcher.group(TokenType.typeThis.name()) != null) {
                tokens.add(new Token(TokenType.typeThis, matcher.group(TokenType.typeThis.name()))); 
            } else if(matcher.group(TokenType.typeBreak.name()) != null) {
                tokens.add(new Token(TokenType.typeBreak, matcher.group(TokenType.typeBreak.name()))); 
            } else if(matcher.group(TokenType.typeDouble.name()) != null) {
                tokens.add(new Token(TokenType.typeDouble, matcher.group(TokenType.typeDouble.name()))); 
            } else if(matcher.group(TokenType.typeImplements.name()) != null) {
                tokens.add(new Token(TokenType.typeImplements, matcher.group(TokenType.typeImplements.name()))); 
            } else if(matcher.group(TokenType.typeProtected.name()) != null) {
                tokens.add(new Token(TokenType.typeProtected, matcher.group(TokenType.typeProtected.name()))); 
            } else if(matcher.group(TokenType.typeThrow.name()) != null) {
                tokens.add(new Token(TokenType.typeThrow, matcher.group(TokenType.typeThrow.name()))); 
            } else if(matcher.group(TokenType.typeByte.name()) != null) {
                tokens.add(new Token(TokenType.typeByte, matcher.group(TokenType.typeByte.name()))); 
            } else if(matcher.group(TokenType.typeElse.name()) != null) {
                tokens.add(new Token(TokenType.typeElse, matcher.group(TokenType.typeElse.name()))); 
            } else if(matcher.group(TokenType.typeImport.name()) != null) {
                tokens.add(new Token(TokenType.typeImport, matcher.group(TokenType.typeImport.name()))); 
            } else if(matcher.group(TokenType.typePublic.name()) != null) {
                tokens.add(new Token(TokenType.typePublic, matcher.group(TokenType.typePublic.name()))); 
            } else if(matcher.group(TokenType.typeThrows.name()) != null) {
                tokens.add(new Token(TokenType.typeThrows, matcher.group(TokenType.typeThrows.name()))); 
            } else if(matcher.group(TokenType.typeCase.name()) != null) {
                tokens.add(new Token(TokenType.typeCase, matcher.group(TokenType.typeCase.name()))); 
            } else if(matcher.group(TokenType.typeEnum.name()) != null) {
                tokens.add(new Token(TokenType.typeEnum, matcher.group(TokenType.typeEnum.name()))); 
            } else if(matcher.group(TokenType.typeInstanceof.name()) != null) {
                tokens.add(new Token(TokenType.typeInstanceof, matcher.group(TokenType.typeInstanceof.name()))); 
            } else if(matcher.group(TokenType.typeReturn.name()) != null) {
                tokens.add(new Token(TokenType.typeReturn, matcher.group(TokenType.typeReturn.name()))); 
            } else if(matcher.group(TokenType.typeTransient.name()) != null) {
                tokens.add(new Token(TokenType.typeTransient, matcher.group(TokenType.typeTransient.name()))); 
            } else if(matcher.group(TokenType.typeCatch.name()) != null) {
                tokens.add(new Token(TokenType.typeCatch, matcher.group(TokenType.typeCatch.name()))); 
            } else if(matcher.group(TokenType.typeExtends.name()) != null) {
                tokens.add(new Token(TokenType.typeExtends, matcher.group(TokenType.typeExtends.name()))); 
            } else if(matcher.group(TokenType.typeShort.name()) != null) {
                tokens.add(new Token(TokenType.typeShort, matcher.group(TokenType.typeShort.name()))); 
            } else if(matcher.group(TokenType.typeTry.name()) != null) {
                tokens.add(new Token(TokenType.typeTry, matcher.group(TokenType.typeTry.name()))); 
            } else if(matcher.group(TokenType.typeChar.name()) != null) {
                tokens.add(new Token(TokenType.typeChar, matcher.group(TokenType.typeChar.name()))); 
            } else if(matcher.group(TokenType.typeFinal.name()) != null) {
                tokens.add(new Token(TokenType.typeFinal, matcher.group(TokenType.typeFinal.name()))); 
            } else if(matcher.group(TokenType.typeInterface.name()) != null) {
                tokens.add(new Token(TokenType.typeInterface, matcher.group(TokenType.typeInterface.name()))); 
            } else if(matcher.group(TokenType.typeStatic.name()) != null) {
                tokens.add(new Token(TokenType.typeStatic, matcher.group(TokenType.typeStatic.name()))); 
            } else if(matcher.group(TokenType.typeVoid.name()) != null) {
                tokens.add(new Token(TokenType.typeVoid, matcher.group(TokenType.typeVoid.name()))); 
            } else if(matcher.group(TokenType.typeClass.name()) != null) {
                tokens.add(new Token(TokenType.typeClass, matcher.group(TokenType.typeClass.name()))); 
            } else if(matcher.group(TokenType.typeFinally.name()) != null) {
                tokens.add(new Token(TokenType.typeFinally, matcher.group(TokenType.typeFinally.name()))); 
            } else if(matcher.group(TokenType.typeLong.name()) != null) {
                tokens.add(new Token(TokenType.typeLong, matcher.group(TokenType.typeLong.name()))); 
            } else if(matcher.group(TokenType.typeStrictfp.name()) != null) {
                tokens.add(new Token(TokenType.typeStrictfp, matcher.group(TokenType.typeStrictfp.name()))); 
            } else if(matcher.group(TokenType.typeVolatile.name()) != null) {
                tokens.add(new Token(TokenType.typeVolatile, matcher.group(TokenType.typeVolatile.name()))); 
            } else if(matcher.group(TokenType.typeConst.name()) != null) {
                tokens.add(new Token(TokenType.typeConst, matcher.group(TokenType.typeConst.name()))); 
            } else if(matcher.group(TokenType.typeFloat.name()) != null) {
                tokens.add(new Token(TokenType.typeFloat, matcher.group(TokenType.typeFloat.name()))); 
            } else if(matcher.group(TokenType.typeNative.name()) != null) {
                tokens.add(new Token(TokenType.typeNative, matcher.group(TokenType.typeNative.name()))); 
            } else if(matcher.group(TokenType.typeSuper.name()) != null) {
                tokens.add(new Token(TokenType.typeSuper, matcher.group(TokenType.typeSuper.name()))); 
            } else if(matcher.group(TokenType.typeWhile.name()) != null) {
                tokens.add(new Token(TokenType.typeWhile, matcher.group(TokenType.typeWhile.name()))); 
            } else if(matcher.group(TokenType.boolvalFalse.name()) != null) {
                tokens.add(new Token(TokenType.boolvalFalse, matcher.group(TokenType.boolvalFalse.name()))); 
            } else if(matcher.group(TokenType.boolvalTrue.name()) != null) {
                tokens.add(new Token(TokenType.boolvalTrue, matcher.group(TokenType.boolvalTrue.name()))); 
            } else if(matcher.group(TokenType.CHAR.name()) != null) {
                tokens.add(new Token(TokenType.CHAR, matcher.group(TokenType.CHAR.name())));             
            } else if(matcher.group(TokenType.digit.name()) != null) {
                tokens.add(new Token(TokenType.digit, matcher.group(TokenType.digit.name())));
            } else if(matcher.group(TokenType.intopAssignment.name()) != null) {
                tokens.add(new Token(TokenType.intopAssignment, matcher.group(TokenType.intopAssignment.name()))); 
            } else if(matcher.group(TokenType.intopRemainder.name()) != null) {
                tokens.add(new Token(TokenType.intopRemainder, matcher.group(TokenType.intopRemainder.name())));            
            } else if(matcher.group(TokenType.intopSubstraction.name()) != null) {
                tokens.add(new Token(TokenType.intopSubstraction, matcher.group(TokenType.intopSubstraction.name()))); 
            } else if(matcher.group(TokenType.intopAddition.name()) != null) {
                tokens.add(new Token(TokenType.intopAddition, matcher.group(TokenType.intopAddition.name()))); 
            } else if(matcher.group(TokenType.intopMultiplication.name()) != null) {
                tokens.add(new Token(TokenType.intopMultiplication, matcher.group(TokenType.intopMultiplication.name()))); 
            } else if(matcher.group(TokenType.intopDivision.name()) != null) {
                tokens.add(new Token(TokenType.intopDivision, matcher.group(TokenType.intopDivision.name()))); 
            } else if(matcher.group(TokenType.boolopNotEqualTo.name()) != null) {
                tokens.add(new Token(TokenType.boolopNotEqualTo, matcher.group(TokenType.boolopNotEqualTo.name()))); 
            } else if(matcher.group(TokenType.boolopGreaterThan.name()) != null) {
                tokens.add(new Token(TokenType.boolopGreaterThan, matcher.group(TokenType.boolopGreaterThan.name()))); 
            } else if(matcher.group(TokenType.boolopLessThan.name()) != null) {
                tokens.add(new Token(TokenType.boolopLessThan, matcher.group(TokenType.boolopLessThan.name()))); 
            } else if(matcher.group(TokenType.boolopAND.name()) != null) {
                tokens.add(new Token(TokenType.boolopAND, matcher.group(TokenType.boolopAND.name()))); 
            } else if(matcher.group(TokenType.openBracket.name()) != null) {
                tokens.add(new Token(TokenType.openBracket, matcher.group(TokenType.openBracket.name()))); 
            } else if(matcher.group(TokenType.closeBracket.name()) != null) {
                tokens.add(new Token(TokenType.closeBracket, matcher.group(TokenType.closeBracket.name()))); 
            } else if(matcher.group(TokenType.openParenthesis.name()) != null) {
                tokens.add(new Token(TokenType.openParenthesis, matcher.group(TokenType.openParenthesis.name()))); 
            } else if(matcher.group(TokenType.closeParenthesis.name()) != null) {
                tokens.add(new Token(TokenType.closeParenthesis, matcher.group(TokenType.closeParenthesis.name()))); 
            } else if(matcher.group(TokenType.QUOTE.name()) != null) {
                tokens.add(new Token(TokenType.QUOTE, matcher.group(TokenType.QUOTE.name()))); 
            } else if(matcher.group(TokenType.EOP.name()) != null) {
                tokens.add(new Token(TokenType.EOP, matcher.group(TokenType.EOP.name()))); 
            }
        }
   
        return tokens;
    }
    
    
    public static void main(String[] args) {
        int i = 1;
        String input = "\"neaw/int = a ||goto ==  >= &&$}\"$"; //the input that will be tested
        
        boolean t = true;
        
        while(t = true) {
            
            // Outputs a stream of tokens from the given input
            ArrayList<Token> tokens = lex(input);
            for(Token token : tokens) {
                
                if(token.type == EOP){ // Lex ends program when "$" is found
                    System.out.println("LEXER:" + token);
                    System.out.println("LEXER: Lex completed successfully\n\n\n");
                    
                } else {
                    System.out.println("LEXER:" + token); // Prints out tokens  
                    
                }
                if(token != null) {
                    System.out.println("LEXER: Lexing program " + i + "...");
                    i = i + 1;
                } 
            }
             
            break;
           
        }  
    }
}
