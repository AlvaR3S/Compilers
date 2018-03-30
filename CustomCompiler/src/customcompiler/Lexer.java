/*  
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customcompiler;


import static customcompiler.Lexer.TokenType.*;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JTextArea;


/**
 *
 * @author Reynaldo Alvarez
 */

public class Lexer extends javax.swing.JFrame {

    public Pattern tokenPatterns;
    public Matcher matcher;
    /**
     * Creates lexer JFrame
     */
    public Lexer() {
        // Components for the Jframe
        initComponents();
    }
    
    
    // ------------------------------------------------------------
    // -----------------[Global Variables]-------------------------
    // ------------------------------------------------------------
    
    // Creates the ArrayList of Tokens
    public ArrayList<Token> tokens = new ArrayList<Token>();
    
    // Gets the current Token position
    public int currentTokenPosition = 0;
    
    // Gets the line number of the current Token
    public int lineNumber = 1;
    
    public int tokenID;
    
    public int errorCount = 0;
    
    private int reset = 0;
    
    
    // ------------------------------------------------------------
    // -----------------[Getters and Setters]----------------------
    // ------------------------------------------------------------
    
    
    public JTextArea getInputArea() {
        return inputArea;
    }
    
    public String getInput() {
        String input = inputArea.getText();
        return input;
    }
    
    public String getOutputArea() {
        String output = outputArea.getText();
        return output;
    }

    public JTextArea getOutputAreaParser() {
        return outputAreaParser;
    }

    public int getTokenID() {
        return tokenID;
    }
    
    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public int getLineNumber() {
        return lineNumber;
    }
    
    
    
    public Pattern getPatterns() {
         
        // Lexer takes the input, finds the patterns and places them into token format
        StringBuffer tokenPatternsBuffer = new StringBuffer();
        for(TokenType tokenType : TokenType.values()) 
            tokenPatternsBuffer.append(String.format("|(?<%s>%s)", tokenType.name(), tokenType.pattern));
        this.tokenPatterns = Pattern.compile(tokenPatternsBuffer.substring(1), Pattern.CASE_INSENSITIVE);

        return tokenPatterns;
    }
    
     public Matcher getMatcher() {
        Pattern patterns = getPatterns();
        
        String input = inputArea.getText();
        
        // Lexer Matches the patterns and if they are valid, they will be added to the new tokens array for output
        this.matcher = patterns.matcher(input);
        
        return matcher;
    }

    public JButton getButtonClearAll() {
        return buttonClearAll;
    }

    public void setButtonClearAll(JButton buttonClearAll) {
        this.buttonClearAll = buttonClearAll;
    }
    
    
    // ------------------------------------------------------------
    // ---------------------[Definitions]--------------------------
    // ------------------------------------------------------------
    
    // Defining our tokens with their corresponding expression names 
    public static enum TokenType {
        
        // -----------------|End of Program Marker|--------------------- \\
        EOP("[$]"),
        
        // -------------|Types|Numbers|Statements|Identifiers|Booleans|---------------- \\
        // Types
        typeInt("int"),
        typeString("string"),
        typeBoolean("boolean"),
        
        // Statemenets
        ifStatement("if"),
        whileStatement("while"),
        printStatement("print"),
        
        // Booleans
        boolvalFalse("false"),
        boolvalTrue("true"),
        
        // Identifiers
        
       
        // Numbers
        digit("[0-9]"), 
        
        // --------------------|Symbols|--------------------------- \\
        // Arithmetic Operator
        intopAddition("[+]"),
        
        // Unary Operator
        boolopNotEqualTo("!="),
        boolopEqualTo("([=]=)"),
        assignmentStatement("="),
        
        // Brackets
        openBracket("[{]"),
        closeBracket("[}]"),
        
        // Parenthesis
        openParenthesis("[(]"),
        closeParenthesis("[)]"),
        
        // Whitespace
        whiteSpace("[ \t\f\r]+"),
        
        // New line
        newLine("[\n|\r]"),
        
        // CHAR
        CHAR("[a-z]"), 
        
        // Unrecognized Tokens
        unrecognized("[A-Z|~|!|@|#|%|^|&|*|_|:|<|>|?|;|'|,|.]"),
        
        // Quote
        Quote("[\"\"]"),
        
        // Empty String
        //empty("({)(.*?)(})"),
        
        // Comments
        comment("(/\\*([^*]|[\\r\\n]|(\\*+([^*/]|[\\r\\n])))*\\*+/)|(//.*)");
        
        
        Pattern pattern;
        
        private TokenType(String pattern) {
            this.pattern = Pattern.compile(pattern);
        } 
        
        public Pattern getPattern() {
            return this.pattern;
        }
        
        public TokenType getType() {
            return this;
        }
        
        
    }
    
    // Stores token type and data
    public class Token {
        public TokenType type;
        public String data;

        // Creating the characteristics of a token
        public Token(TokenType type, String data) {
            this.type = type;
            this.data = data;
        }

        
        // Getter method for getting Token types
        public TokenType getType() {
            return type;
        }
        
        // Getter method for getting Token Data;
        public String getData() {
            return data;
        }
        
        @Override
        public String toString() { // Structures token type and data for output
            return String.format("\"%s\" --> [%s]" , data, type);
        }
       
        /**
         * Lex the input of characters 
         * and make them tokens 
         * if they are in our grammar
         * else they are unrecognized 
         */
        public Token() {

            int i = 1;
            int warningCount = 0;
            int numberOfEop = 0;
            int lexSuccess = 0;

            Parser info = new Parser();

            String input = inputArea.getText();
            String output = outputArea.getText();



            boolean errorToken = false;


            Matcher tokenMatcher = getMatcher();

            // Loops through the input and finds valid tokens
            while(tokenMatcher.find()) {
                 if(tokenMatcher.group(TokenType.newLine.name()) != null) {
                    tokens.add(new Token(TokenType.newLine, tokenMatcher.group(TokenType.newLine.name())));
                } else if(tokenMatcher.group(TokenType.whiteSpace.name()) != null) {
                     continue;
                } else if(tokenMatcher.group(TokenType.comment.name()) != null) {
                     continue;
                } else if(tokenMatcher.group(TokenType.typeInt.name()) != null) {
                    tokens.add(new Token(TokenType.typeInt, tokenMatcher.group(TokenType.typeInt.name())));
                } else if(tokenMatcher.group(TokenType.typeString.name()) != null) {
                    tokens.add(new Token(TokenType.typeString, tokenMatcher.group(TokenType.typeString.name())));
                } else if(tokenMatcher.group(TokenType.typeBoolean.name()) != null) {
                    tokens.add(new Token(TokenType.typeBoolean, tokenMatcher.group(TokenType.typeBoolean.name())));
                } else if(tokenMatcher.group(TokenType.ifStatement.name()) != null) {
                    tokens.add(new Token(TokenType.ifStatement, tokenMatcher.group(TokenType.ifStatement.name())));
                } else if(tokenMatcher.group(TokenType.whileStatement.name()) != null) {
                    tokens.add(new Token(TokenType.whileStatement, tokenMatcher.group(TokenType.whileStatement.name())));
                } else if(tokenMatcher.group(TokenType.printStatement.name()) != null) {
                    tokens.add(new Token(TokenType.printStatement, tokenMatcher.group(TokenType.printStatement.name())));
                } else if(tokenMatcher.group(TokenType.assignmentStatement.name()) != null) {
                    tokens.add(new Token(TokenType.assignmentStatement, tokenMatcher.group(TokenType.assignmentStatement.name())));
                } else if(tokenMatcher.group(TokenType.CHAR.name()) != null) {
                    tokens.add(new Token(TokenType.CHAR, tokenMatcher.group(TokenType.CHAR.name())));
                } else if(tokenMatcher.group(TokenType.boolvalFalse.name()) != null) {
                    tokens.add(new Token(TokenType.boolvalFalse, tokenMatcher.group(TokenType.boolvalFalse.name())));
                } else if(tokenMatcher.group(TokenType.boolvalTrue.name()) != null) {
                    tokens.add(new Token(TokenType.boolvalTrue, tokenMatcher.group(TokenType.boolvalTrue.name())));
                } else if(tokenMatcher.group(TokenType.digit.name()) != null) {
                    tokens.add(new Token(TokenType.digit, tokenMatcher.group(TokenType.digit.name())));
                } else if(tokenMatcher.group(TokenType.intopAddition.name()) != null) {
                    tokens.add(new Token(TokenType.intopAddition, tokenMatcher.group(TokenType.intopAddition.name())));
                } else if(tokenMatcher.group(TokenType.boolopNotEqualTo.name()) != null) {
                    tokens.add(new Token(TokenType.boolopNotEqualTo, tokenMatcher.group(TokenType.boolopNotEqualTo.name())));
                } else if(tokenMatcher.group(TokenType.boolopEqualTo.name()) != null) {
                    tokens.add(new Token(TokenType.boolopEqualTo, tokenMatcher.group(TokenType.boolopEqualTo.name())));
                } else if(tokenMatcher.group(TokenType.openBracket.name()) != null) {
                    tokens.add(new Token(TokenType.openBracket, tokenMatcher.group(TokenType.openBracket.name())));
                } else if(tokenMatcher.group(TokenType.closeBracket.name()) != null) {
                    tokens.add(new Token(TokenType.closeBracket, tokenMatcher.group(TokenType.closeBracket.name())));
                } else if(tokenMatcher.group(TokenType.openParenthesis.name()) != null) {
                    tokens.add(new Token(TokenType.openParenthesis, tokenMatcher.group(TokenType.openParenthesis.name())));
                } else if(tokenMatcher.group(TokenType.closeParenthesis.name()) != null) {
                    tokens.add(new Token(TokenType.closeParenthesis, tokenMatcher.group(TokenType.closeParenthesis.name())));
                } else if(tokenMatcher.group(TokenType.EOP.name()) != null) {
                    tokens.add(new Token(TokenType.EOP, tokenMatcher.group(TokenType.EOP.name())));
                } else if(tokenMatcher.group(TokenType.Quote.name()) != null) {
                    tokens.add(new Token(TokenType.Quote, tokenMatcher.group(TokenType.Quote.name())));
                } else if(tokenMatcher.group(TokenType.unrecognized.name()) != null) {
                    tokens.add(new Token(TokenType.unrecognized, tokenMatcher.group(TokenType.unrecognized.name())));
                    errorCount++;
                } else {
                    System.out.println("Unrecognized token found."); // Catches other tokens that aren't allowed if not in (unrecognized)
                    errorToken = true;
                    errorCount++;    
                }        
            }

            // Error if there is no input
            if((input.isEmpty())) { 
                outputArea.append("~ERROR: No input found~\n");
                errorCount++;
            }
            


            // Prints befeore anything else at the top once
            outputArea.append("\nLEXER: Lexing program 1...\n");

            // Outputs a stream of tokens from the given input
            for(Token token : tokens) {
                int index = token.data.indexOf("$");

                boolean moreThanOnce = index != -1 && index != input.lastIndexOf("$");

                // When an unrecognized token is found print error message else print the token
                if(token.type == unrecognized) {
                    outputArea.append("LEXER: ERROR: Unrecognized token: " + token.data + " on line " + lineNumber + "\n");
                } else if(token.type == newLine) { // Gets the current token line number and recognizes if new program is lexing
                    lineNumber++;
                } else {
                    outputArea.append("LEXER:" + token + " on line " + lineNumber + "\n"); // Prints out tokens   
                }


                if(token.type == EOP) {
                    outputArea.append("LEXER: Lex completed successfully.\n\n");
                    lexSuccess++;
                    i++;
                     // If there is more than one $ there is more than one lexeing program
                    numberOfEop++;

                    outputArea.append("\nLEXER: Lexing program " + i + "...\n");
                }
                

//                System.out.println("numberOfEop: " + numberOfEop);
//                System.out.println("i: " + i);
//                System.out.println("lexSuc: " + lexSuccess);
            }
            
            
            

            // Spits out a warning when input string does not end with a $ symbol
            if(!input.endsWith("$")) {
                if(input.endsWith("\n")) { // ignores newline and prints results
                    // Prints out total number of errors and warnings at the end of program
                    outputArea.append("Lex completed with:\n [" + warningCount + "] Warning(s) "
                                + "and [" + errorCount + "] Error(s).\n\n"); 
                } else {
                    outputArea.append("LEXER: WARNING: Missing a \"$\"" + " on line " + lineNumber + "\n");
                    outputArea.append("LEXER: Lex completed with mistakes\n\n");
                    warningCount++;
                }
            } else { // Ends with $
                // Prints out total number of errors and warnings at the end of program
                outputArea.append("Lex completed with:\n [" + warningCount + "] Warning(s) "
                                + "and [" + errorCount + "] Error(s).\n\n"); 
            }
            
            
            
           
        }
    }     
    
    /**
     * 
     * PARSER CLASS
     */
    public class Parser {
        Lexer lex = new Lexer();
        private int currentToken = 0;
        private int i = 1;
        int error = lex.getErrorCount();
        private int openBraceCount = 0;
        private int closeBraceCount = 0;
        TokenType tokenType;
        ParseTree t = new ParseTree();
       
        
        /**
         * Starts and finishes the parse - will be called through button run
         * @param token
         */
        public Parser(Token token){
            outputAreaParser.append("\nPARSER: Parsing program" + i + "...\n");
            outputAreaParser.append("PARSER: parse()\n");
            outputAreaParser.append("PARSER: parseProgram()\n");
            
            Program();
        }
        
        public Parser() {
            
        }

        public void matchAndDevour(TokenType tokenMatch) {
            if(tokenMatch.equals(tokens.get(currentToken).getType())) {
                System.out.println("current token: " + tokenMatch + "\n"); 
                System.out.println("current token pos: " + currentToken + "\n");
                currentToken++;
            }
        }

        
        /**
         * 
         * Program       ::== Block $
         */        
        private void Program() {
            if(tokens.get(currentToken).getType().equals(tokenType.EOP)) { // In case end comes sooner than expected
                // Error case when parser finishes with uneven number of '{' and '}'
                if(closeBraceCount != openBraceCount) {
                    outputAreaParser.append("PARSER: ERROR: Expected [" + tokens.get(currentToken).getType() + "] got [" + tokens.get(currentToken - 1).getType() + "] on line " + lineNumber + "\n");
                    outputAreaParser.append("PARSER: Parse failed with 1 error\n\n"); // incase of dupilicates (Block())
                    matchAndDevour(tokenType.EOP); 
                    System.out.println("matched $\n");
                    
                    cstOutputArea.append("CST for program " + i + ": Skipped due to PARSER error(s).\n\n");
                    
                } else { // Program found no bracket errors or parse errors - finish parse and cst 
                    t.endChildren();
                   
                    // Adding EOP leaf Node
                    t.addNode("$", "leaf");

                    cstOutputArea.append(t.toString());

                    matchAndDevour(tokenType.EOP); 
                    System.out.println("matched $\n");

                    outputAreaParser.append("PARSER: Parse completed successfully\n\n");
                }
                
                if(currentToken < tokens.size()) { // in case Program is not finished
                    System.out.println("Program running more than once\n");
                    i++;
                    outputAreaParser.append("\nPARSER: Parsing program " + i + "...\n");
                    outputAreaParser.append("PARSER: parse()\n");
                    outputAreaParser.append("PARSER: parseProgram()\n");
                    
                    t.restartFamily(); // Clear family tree for next program
                    
                    Program(); // when end reached loop back to the top
                }             
            } else {
                if(i > 1) { // Separates tress accordingly
                    cstOutputArea.append("\n\nCST for program " + i + "...\n");
                } else {
                    cstOutputArea.append("\nCST for program " + i + "...\n");
                }
                
                // Adding the root node
                t.addNode("Program", "branch");
                
                // Adds the block Node to the tree
                t.addNode("Block", "branch");
                
                Block();
            }
        }

        
        /**
         * 
         * Block     ::== { StatementList }
         */        
        private void Block() {
            if(tokens.get(currentToken).getType().equals(tokenType.openBracket)) {
                //Creates the leaf node of Block {
                t.addNode("{", "leaf");
                
                matchAndDevour(tokenType.openBracket);
                openBraceCount++;
                outputAreaParser.append("PARSER: parseBlock()\n");
                System.out.println("matched {\n");
                
                StatementList();
                
            } else if(tokens.get(currentToken).getType().equals(tokenType.closeBracket)) {                
                t.endChildren();
                t.endChildren();
                t.endChildren();
                
                //Creates the leaf node of Block }
                t.addNode("}", "leaf");
                
                matchAndDevour(tokenType.closeBracket);
                closeBraceCount++;
                outputAreaParser.append("PARSER: parseStatementList()\n");
                System.out.println("matched: }\n");
                
                // When looping of } finishes there should be a $
                if(tokens.get(currentToken).getType().equals(tokenType.EOP)) {
                    Program(); // Goes to program to finish program and continue if there are more programs
                } else {
                    Block();
                }
                
            } else if(tokens.get(currentToken).getType().equals(tokenType.newLine)) { // Accounting for a new line
                matchAndDevour(tokenType.newLine);
                System.out.println("matched: \n");
                if(tokens.get(currentToken).getType().equals(tokenType.openBracket)) { // incase of dupilicates (Block())
                    //Creates the leaf node of Block { (Incase of this case)
                    t.addNode("{", "leaf");
                    
                    matchAndDevour(tokenType.openBracket);
                    openBraceCount++;
                    outputAreaParser.append("PARSER: parseBlock()\n");
                    System.out.println("matched: {\n");
                    StatementList();
                } else {
                    StatementList();
                }    
            } else {
                // test for location
                t.endChildren();
                
                outputAreaParser.append("PARSER: ERROR: Expected [" + tokens.get(currentToken).getType() + "] got [" + tokens.get(currentToken - 1).getType() + "] on line " + lineNumber + "\n");
                outputAreaParser.append("PARSER: Parse failed with 1 error\n\n");
            }              
        }
        
        
        /**
         * 
         * StatementList ::== Statement StatementList
         *               ::== ε <-- (empty set)
         */
        private void StatementList() {              
            if(tokens.get(currentToken).getType().equals(tokenType.printStatement)) {
                // Adds Statement List branch to tree
                t.addNode("Statement List", "branch");
                
                outputAreaParser.append("PARSER: parseStatementList()\n");
                System.out.println("matched: print\n");
                Statement();
            
            } else if(tokens.get(currentToken).getType().equals(tokenType.CHAR)) {
                // Adds Statement List branch to tree
                t.addNode("Statement List", "branch");
                
                outputAreaParser.append("PARSER: parseStatementList()\n");
                System.out.println("matched: ID\n");
                Statement(); 
                
            } else if(tokens.get(currentToken).getType().equals(tokenType.typeInt)) {
                // Adds Statement List branch to tree
                t.addNode("Statement List", "branch");
                
                outputAreaParser.append("PARSER: parseStatementList()\n");
                System.out.println("matched: int\n");
                Statement();
                
            } else if(tokens.get(currentToken).getType().equals(tokenType.typeString)) {
                // Adds Statement List branch to tree
                t.addNode("Statement List", "branch");
                
                outputAreaParser.append("PARSER: parseStatementList()\n");
                System.out.println("matched: string\n");
                Statement();
                
            } else if(tokens.get(currentToken).getType().equals(tokenType.typeBoolean)) {
                // Adds Statement List branch to tree
                t.addNode("Statement List", "branch");
                
                outputAreaParser.append("PARSER: parseStatementList()\n");
                System.out.println("matched: booleant\n");
                Statement();
                
            } else if(tokens.get(currentToken).getType().equals(tokenType.ifStatement)) {
                // Adds Statement List branch to tree
                t.addNode("Statement List", "branch");
                
                outputAreaParser.append("PARSER: parseStatementList()\n");
                System.out.println("matched: if\n");
                Statement();
                
            } else if(tokens.get(currentToken).getType().equals(tokenType.whileStatement)) {
                // Adds Statement List branch to tree
                t.addNode("Statement List", "branch");
                
                outputAreaParser.append("PARSER: parseStatementList()\n");
                System.out.println("matched: while\n");
                Statement();
             
            } else if(tokens.get(currentToken).getType().equals(tokenType.closeBracket)) {
                if(tokens.get(currentToken - 1).getType().equals(tokenType.openBracket)) {
                    // Adds Statement List branch to tree
                    t.addNode("Statement List", "branch");
                }
                
                t.endChildren();
                
                //Creates the leaf node of Block }
                t.addNode("}", "leaf");

                matchAndDevour(tokenType.closeBracket);
                closeBraceCount++;
                outputAreaParser.append("PARSER: parseStatementList()\n"); // incase of dupilicates (Block())
                System.out.println("matched: }\n");
                
                // If EOP is found
                if(tokens.get(currentToken).getType().equals(tokenType.EOP)) {
                    Program(); // Goes to program to finish program and continue if there are more programs
                } else {
                    Statement(); // If this not the only }
                }
            } else if(tokens.get(currentToken).getType().equals(tokenType.openBracket)) { // incase of dupilicates (Block())
                // Adds Statement List branch to tree
                t.addNode("Statement List", "branch");
                
                // Adds Statement branch to tree
                t.addNode("Statement", "branch");

                // Adds the block Node to the tree
                t.addNode("Block", "branch");
                
                //Creates the leaf node of Block {
                t.addNode("{", "leaf"); 
                
                matchAndDevour(tokenType.openBracket);
                openBraceCount++;
                outputAreaParser.append("PARSER: parseStatementList()\n");
                outputAreaParser.append("PARSER: parseStatement()\n");
                outputAreaParser.append("PARSER: parseBlock()\n");
                System.out.println("matched: {\n");
                Statement();
                
            } else if(tokens.get(currentToken).getType().equals(tokenType.newLine)) { // Accounting for a new line
                matchAndDevour(tokenType.newLine);
                System.out.println("matched: \n");
                StatementList(); // loops to next section when end reached loop back to the top
            
            } else if(tokens.get(currentToken).getType().equals(tokenType.EOP)) { // Accounting for an early stop
                Program(); // loops back to the top
                  
            } else {
                // test for location
                t.endChildren();
                
                outputAreaParser.append("PARSER: ERROR: Expected [" + tokens.get(currentToken).getType() + "] got [" + tokens.get(currentToken - 1).getType() + "] on line " + lineNumber + "\n");
                outputAreaParser.append("PARSER: Parse failed with 1 error\n\n"); // incase of dupilicates (Block())
            }   
        }
        
        
        /**
         * 
         * Statement ::== PrintStatement        ::== print ( Expr )
         *           ::== AssignmentStatement   ::== Id = Expr
         *           ::== VarDecl               ::== type Id
         *           ::== WhileStatement        ::== while BooleanExpr Block
         *           ::== IfStatement           ::== if BooleanExpr Block
         *           ::== Block                 ::== Program
         */ 
        private void Statement() {
            // Adds Statement List branch to tree
            t.addNode("Statement", "branch");
            
            if(tokens.get(currentToken).getType().equals(tokenType.printStatement)) {
                matchAndDevour(tokenType.printStatement);
                outputAreaParser.append("PARSER: parseStatement()\n");
                System.out.println("matched: print\n");
                PrintStatement();
            
            } else if(tokens.get(currentToken).getType().equals(tokenType.CHAR)) {
                matchAndDevour(tokenType.CHAR);
                outputAreaParser.append("PARSER: parseStatement()\n");
                System.out.println("matched: ID\n");
                AssignmentStatement();
                
            } else if(tokens.get(currentToken).getType().equals(tokenType.typeInt)) {
                matchAndDevour(tokenType.typeInt);
                outputAreaParser.append("PARSER: parseStatement()\n");
                System.out.println("matched: int\n");
                VarDecl();
                
            } else if(tokens.get(currentToken).getType().equals(tokenType.typeString)) {
                matchAndDevour(tokenType.typeString);
                outputAreaParser.append("PARSER: parseStatement()\n");
                System.out.println("matched: string\n");
                VarDecl();
                
            } else if(tokens.get(currentToken).getType().equals(tokenType.typeBoolean)) {
                matchAndDevour(tokenType.typeBoolean);
                outputAreaParser.append("PARSER: parseStatement()\n");
                System.out.println("matched: boolean\n");
                VarDecl();
                
            } else if(tokens.get(currentToken).getType().equals(tokenType.ifStatement)) {
                matchAndDevour(tokenType.ifStatement);
                outputAreaParser.append("PARSER: parseStatement()\n");
                System.out.println("matched: if\n");
                IfStatement();
                
            } else if(tokens.get(currentToken).getType().equals(tokenType.whileStatement)) {
                matchAndDevour(tokenType.whileStatement);
                outputAreaParser.append("PARSER: parseStatement()\n");
                System.out.println("matched: while\n");
                WhileStatement();
                
            } else if(tokens.get(currentToken).getType().equals(tokenType.closeBracket)) {
                // Adds Statement List branch to tree
                t.addNode("Statement List", "branch");
                t.endChildren();
                
                //Creates the leaf node of Block }
                t.addNode("}", "leaf");
                
                matchAndDevour(tokenType.closeBracket);
                closeBraceCount++;
                outputAreaParser.append("PARSER: parseStatementList()\n"); // incase of dupilicates (Block())
                System.out.println("matched: }\n");
                
                // If EOP is found
                if(tokens.get(currentToken).getType().equals(tokenType.EOP)) {
                    Program(); // Goes to program to finish program and continue if there are more programs
                } else if(tokens.get(currentToken).getType().equals(tokenType.closeBracket)) {
                    Block(); // If there are a repeating number of } Block will loop them
                } else {
                    StatementList();
                }
            } else if(tokens.get(currentToken).getType().equals(tokenType.openBracket)) { // incase of dupilicates (Block())
                // Adds Statement List branch to tree
                t.addNode("Statement List", "branch");
                
                // Adds Statement branch to tree
                t.addNode("Statement", "branch");

                // Adds the block Node to the tree
                t.addNode("Block", "branch");
                
                //Creates the leaf node of Block {
                t.addNode("{", "leaf"); 
                
                matchAndDevour(tokenType.openBracket);
                openBraceCount++;
                outputAreaParser.append("PARSER: parseStatementList()\n");
                outputAreaParser.append("PARSER: parseStatement()\n");
                outputAreaParser.append("PARSER: parseBlock()\n");
                System.out.println("matched: {\n");
                StatementList(); // Considered as Block() loops back to begining to find possible $
                
            } else if(tokens.get(currentToken).getType().equals(tokenType.newLine)) { // Accounting for a new line
                matchAndDevour(tokenType.newLine);
                System.out.println("matched: \n");
                Program(); // loops to next section when end reached loop back to the top
                
            } else if(tokens.get(currentToken).getType().equals(tokenType.EOP)) { // In case end comes sooner than expected
                Program(); // Goes to program to finish program and continue if there are more programs
            } else {
                outputAreaParser.append("PARSER: ERROR: Expected [" + tokens.get(currentToken).getType() + "] got [" + tokens.get(currentToken - 1).getType() + "] on line " + lineNumber + "\n");
                outputAreaParser.append("PARSER: Parse failed with 1 error\n\n"); // incase of dupilicates (Block())
                Program(); // loop to the beginning
            }
        }
        
        
        //---------------------STATEMENTS---------------------------------------
        /**
         * 
         * Statement ::== PrintStatement   ::== print ( Expr )
         */ 
        private void PrintStatement() {
            if(tokens.get(currentToken).getType().equals(tokenType.openParenthesis)) {
                // Adds Statement List branch to tree
                t.addNode("PrintStatement", "branch");
                
                //Creates the leaf node print
                t.addNode("print", "leaf"); 
                
                //Creates the leaf node openParen
                t.addNode("(", "leaf");
                
                matchAndDevour(tokenType.openParenthesis);
                outputAreaParser.append("PARSER: parsePrintStatement()\n");
                outputAreaParser.append("PARSER: parseOpenParethesis()\n");
                Expr();
                
            } else if(tokens.get(currentToken).getType().equals(tokenType.closeParenthesis)) {
                t.endChildren();
                t.endChildren();
                //Creates the leaf node closeParen
                t.addNode(")", "leaf");
                
                matchAndDevour(tokenType.closeParenthesis);
                outputAreaParser.append("PARSER: parseCloseParenthesis()\n");
                
                // If next token continues a boolean expressions
                if(tokens.get(currentToken).getType().equals(tokenType.closeParenthesis)) {
                    BooleanExpr();
                  
                } else {
                   t.endChildren(); // testing for print
                   t.endChildren(); // testing for print
                   StatementList(); // If there are more statements left
                   
                }
            } else {
                outputAreaParser.append("PARSER: ERROR: Expected [" + tokens.get(currentToken).getType() + "] got [" + tokens.get(currentToken - 1).getType() + "] on line " + lineNumber + "\n");
                outputAreaParser.append("PARSER: Parse failed with 1 error\n\n"); // incase of dupilicates (Block())
                Program(); // loop to the beginning
            }    
        }
        
        
        /**
         * 
         * Statement ::== AssignmentStatement   ::== Id = Expr
         */ 
        private void AssignmentStatement() {
            // Adds Statement List branch to tree
            t.addNode("AssignmentStatement", "branch");
            
            if(tokens.get(currentToken).getType().equals(tokenType.assignmentStatement)) { // Checking for CHARS
                matchAndDevour(tokenType.assignmentStatement);
                outputAreaParser.append("PARSER: parseAssignment()\n"); // Assignment symbol is valid
                Expr(); // Gets Expr
            } else {
                outputAreaParser.append("PARSER: ERROR: Expected [" + tokens.get(currentToken).getType() + "] got [" + tokens.get(currentToken - 1).getType() + "] on line " + lineNumber + "\n");
                outputAreaParser.append("PARSER: Parse failed with 1 error\n\n"); // incase of dupilicates (Block())
                Program(); // loop to the beginning 
           }
        }

        
        /**
         * 
         * Statement ::== VarDecl    ::== type Id
         */ 
        private void VarDecl() {
            // Adds Statement List branch to tree
            t.addNode("VariableDeclaration", "branch");
            
           if(tokens.get(currentToken).getType().equals(tokenType.CHAR)) { // Checking for CHARS
                matchAndDevour(tokenType.CHAR);
                outputAreaParser.append("PARSER: parseID()\n"); // ID is valid
                outputAreaParser.append("PARSER: parseVarDecl()\n"); // VarDecl is valid
                Program();
            } else {
                outputAreaParser.append("PARSER: ERROR: Expected [" + tokens.get(currentToken).getType() + "] got [" + tokens.get(currentToken - 1).getType() + "] on line " + lineNumber + "\n");
                outputAreaParser.append("PARSER: Parse failed with 1 error\n\n"); // incase of dupilicates (Block())
                Program(); // loop to the beginning 
           }
        }

        
        /**
         * 
         * Statement ::== WhileStatement   ::== while BooleanExpr Block
         */ 
        private void WhileStatement() {
            // Adds Statement List branch to tree
            t.addNode("WhileStatement", "branch");
            
            if(tokens.get(currentToken).getType().equals(tokenType.openParenthesis)) { // Checking for openParenthesis
                outputAreaParser.append("PARSER: WhileStatement()\n"); // While is valid
                BooleanExpr();
            } else {
                outputAreaParser.append("PARSER: ERROR: Expected [" + tokens.get(currentToken).getType() + "] got [" + tokens.get(currentToken - 1).getType() + "] on line " + lineNumber + "\n");
                outputAreaParser.append("PARSER: Parse failed with 1 error\n\n"); // incase of dupilicates (Block())
                Program(); // loop to the beginning 
            } 
        }

        
        /**
         * 
         * Statement ::== IfStatement     ::== if BooleanExpr Block
         */ 
        private void IfStatement() {
            // Adds Statement List branch to tree
            t.addNode("IfStatement", "branch");
            
            if(tokens.get(currentToken).getType().equals(tokenType.openParenthesis)) { // Checking for openParenthesis
                outputAreaParser.append("PARSER: IfStatement()\n"); // IF is valid
                BooleanExpr();
            } else {
                outputAreaParser.append("PARSER: ERROR: Expected [" + tokens.get(currentToken).getType() + "] got [" + tokens.get(currentToken - 1).getType() + "] on line " + lineNumber + "\n");
                outputAreaParser.append("PARSER: Parse failed with 1 error\n\n"); // incase of dupilicates (Block())
                Program(); // loop to the beginning 
            }
        }

        
        //------------------EXPRESSIONS------------------------------------------
        /**
         * 
         * Expr ::== IntExpr        ::== digit intopExpr, digit
         *      ::== StringExpr     ::== " CharList "
         *      ::== BooleanExpr    ::== ( Expr boolop Expr ), boolval
         *      ::== ID             ::== char
         */
        private void Expr() {
            // Adds Statement List branch to tree
            t.addNode("Expression", "branch");
            
            if(tokens.get(currentToken).getType().equals(tokenType.digit)) { // Checking for digits
                outputAreaParser.append("PARSER: parseExpr()\n");
                IntExpr(); // If its a digit we will see if its valid IntExpr
                
            } else if(tokens.get(currentToken).getType().equals(tokenType.CHAR)) { // Checking for CHARS 
                outputAreaParser.append("PARSER: parseExpr()\n"); 
                ID();
                
            } else if(tokens.get(currentToken).getType().equals(tokenType.openParenthesis)) { // Checking for openParenthesis 
                outputAreaParser.append("PARSER: parseExpr()\n"); 
                BooleanExpr();
                
            } else if(tokens.get(currentToken).getType().equals(tokenType.boolvalTrue)) { // Checking for boolval 
                outputAreaParser.append("PARSER: parseExpr()\n");
                matchAndDevour(tokenType.boolvalTrue);
                outputAreaParser.append("PARSER: parseBoolvalTrue()\n");
                outputAreaParser.append("PARSER: parseAssignmentStatement()\n");
                Program();
            
            } else if(tokens.get(currentToken).getType().equals(tokenType.boolvalFalse)) { // Checking for boolval 
                outputAreaParser.append("PARSER: parseExpr()\n");
                matchAndDevour(tokenType.boolvalFalse);
                outputAreaParser.append("PARSER: parseBoolvalTrue()\n");
                outputAreaParser.append("PARSER: parseAssignmentStatement()\n");
                Program();
            
            } else if(tokens.get(currentToken).getType().equals(tokenType.Quote)) { // Checking for Quotes 
                outputAreaParser.append("PARSER: parseExpr()\n"); 
                StringExpr();    
                
            } else {
                Program(); // loop to the beginning
            }
        }
        
        
        /**
         * 
         * Expr ::== IntExpr   ::== digit intopExpr, digit
         */
        private void IntExpr() {
            // Adds Statement List branch to tree
            t.addNode("IntegerExpression", "branch");
            
            if(tokens.get(currentToken).getType().equals(tokenType.digit)) { // Checking for digits
                matchAndDevour(tokenType.digit);
                outputAreaParser.append("PARSER: parseDigit()\n");
                System.out.println("matched: Digit\n");
                
                if(tokens.get(currentToken).getType().equals(tokenType.intopAddition)) { // Checking for intop   
                    matchAndDevour(tokenType.intopAddition);
                    outputAreaParser.append("PARSER: parseIntop()\n");
                    System.out.println("matched: Intop\n");
                
                    if(tokens.get(currentToken).getType().equals(tokenType.digit)) { // Checking for digits
                        matchAndDevour(tokenType.digit);
                        outputAreaParser.append("PARSER: parseDigit()\n");
                        outputAreaParser.append("PARSER: parseIntExpr()\n"); // IntExpr is valid
                        
                        if(tokens.get(currentToken).getType().equals(tokenType.boolopNotEqualTo)) { // Checking for BOOLOP
                            BooleanExpr(); // continues the BooleanExpr

                        } else if(tokens.get(currentToken).getType().equals(tokenType.boolopEqualTo)) { //Checking for BOOLOP
                            BooleanExpr(); // continues the BooleanExpr
                        
                        } else if(tokens.get(currentToken).getType().equals(tokenType.closeParenthesis)) {                    
                            PrintStatement(); // Loop back to PrintStatement    

                        } else { // An AssignmentStatement
                            outputAreaParser.append("PARSER: parseAssignmentStatement()\n"); // incase of dupilicates (Block())
                            Program(); // loop to the beginning
                        }  
                    } else {
                        outputAreaParser.append("PARSER: ERROR: Expected [" + tokens.get(currentToken).getType() + "] got [" + tokens.get(currentToken - 1).getType() + "] on line " + lineNumber + "\n");
                        outputAreaParser.append("PARSER: Parse failed with 1 error\n\n"); // incase of dupilicates (Block())
                        Program(); // loop to the beginning
                    }
                } else if(tokens.get(currentToken).getType().equals(tokenType.closeParenthesis)) {                    
                    PrintStatement(); // Loop back to PrintStatement
                    
                } else { // An AssignmentStatement
                    outputAreaParser.append("PARSER: parseAssignmentStatement()\n"); // incase of dupilicates (Block())
                    Program(); // loop to the beginning
                }    
            } else { // If IntExpr doesn't start with a digit
                outputAreaParser.append("PARSER: ERROR: Expected [" + tokens.get(currentToken).getType() + "] got [" + tokens.get(currentToken - 1).getType() + "] on line " + lineNumber + "\n");
                outputAreaParser.append("PARSER: Parse failed with 1 error\n\n"); // incase of dupilicates (Block())
                Program(); // loop to the beginning
            }
        }
        
        
        /**
         * 
         * Expr ::== StringExpr    ::== " CharList "
         */
        private void StringExpr() {
            // Adds Statement List branch to tree
            t.addNode("StringExpression", "branch");
            
            if(tokens.get(currentToken).getType().equals(tokenType.Quote)) { // Checking for Quotes
                matchAndDevour(tokenType.Quote);
                outputAreaParser.append("PARSER: parseQuote()\n");
                
                while(tokens.get(currentToken).getType().equals(tokenType.CHAR)) {
                    matchAndDevour(tokenType.CHAR);
                    outputAreaParser.append("PARSER: parseCHAR()\n");
                }
                
                if(tokens.get(currentToken).getType().equals(tokenType.Quote)) {
                    matchAndDevour(tokenType.Quote);
                    outputAreaParser.append("PARSER: parseQuote()\n");
                    outputAreaParser.append("PARSER: parseStringExpr()\n");
                    
                    if(tokens.get(currentToken).getType().equals(tokenType.boolopNotEqualTo)) { // Checking for BOOLOP
                        BooleanExpr(); // continues the BooleanExpr

                    } else if(tokens.get(currentToken).getType().equals(tokenType.boolopEqualTo)) { // Checking for BOOLOP
                        BooleanExpr(); // continues the BooleanExpr

                    } else if(tokens.get(currentToken).getType().equals(tokenType.closeParenthesis)) {                    
                        PrintStatement(); // Loop back to PrintStatement    

                    } else { // An AssignmentStatement
                        outputAreaParser.append("PARSER: parseAssignmentStatement()\n"); // incase of dupilicates (Block())
                        Program(); // loop to the beginning
                    }    
                }
            } else { // Not a BoolopExpr so go to finish the printStatement
                outputAreaParser.append("PARSER: ERROR: Expected [" + tokens.get(currentToken).getType() + "] got [" + tokens.get(currentToken - 1).getType() + "] on line " + lineNumber + "\n");
                outputAreaParser.append("PARSER: Parse failed with 1 error\n\n"); // incase of dupilicates (Block())
                Program(); // loop to the beginning 
            }     
        }
        
        
        /**
         * 
         * Expr ::== BooleanExpr  ::== ( Expr boolop Expr ), boolval
         */
        private void BooleanExpr() {
            // Adds Statement List branch to tree
            t.addNode("BooleanExpression", "branch");
            
            if(tokens.get(currentToken).getType().equals(tokenType.openParenthesis)) { // Checking for openParenthesis 
                matchAndDevour(tokenType.openParenthesis);
                outputAreaParser.append("PARSER: parseOpenParenthesis()\n");
                
                // For while statement
                if(tokens.get(currentToken).getType().equals(tokenType.boolvalTrue)) { // Checking for boolval 
                    outputAreaParser.append("PARSER: parseExpr()\n");
                    matchAndDevour(tokenType.boolvalTrue);
                    outputAreaParser.append("PARSER: parseBoolvalTrue()\n");
                    
                    if(tokens.get(currentToken).getType().equals(tokenType.closeParenthesis)) { // Checking for closeParenthesis
                        matchAndDevour(tokenType.closeParenthesis);
                        outputAreaParser.append("PARSER: parseCloseParenthesis()\n");
                        outputAreaParser.append("PARSER: parseBooleanExpr()\n"); // BooleanExpr is valid
                        Program(); // loops back to beginning
                        
                    } else {
                        outputAreaParser.append("PARSER: ERROR: Expected [" + tokens.get(currentToken).getType() + "] got [" + tokens.get(currentToken - 1).getType() + "] on line " + lineNumber + "\n");
                        outputAreaParser.append("PARSER: Parse failed with 1 error\n\n"); // incase of dupilicates (Block())
                        Program(); // loop to the beginning 
                    }
                    
                } else if(tokens.get(currentToken).getType().equals(tokenType.boolvalFalse)) { // Checking for boolval 
                    outputAreaParser.append("PARSER: parseExpr()\n");
                    matchAndDevour(tokenType.boolvalFalse);
                    outputAreaParser.append("PARSER: parseBoolvalTrue()\n");
                    
                    if(tokens.get(currentToken).getType().equals(tokenType.closeParenthesis)) { // Checking for closeParenthesis
                        matchAndDevour(tokenType.closeParenthesis);
                        outputAreaParser.append("PARSER: parseCloseParenthesis()\n");
                        outputAreaParser.append("PARSER: parseBooleanExpr()\n"); // BooleanExpr is valid
                        Program(); // loops back to beginning
                        
                    } else {
                        outputAreaParser.append("PARSER: ERROR: Expected [" + tokens.get(currentToken).getType() + "] got [" + tokens.get(currentToken - 1).getType() + "] on line " + lineNumber + "\n");
                        outputAreaParser.append("PARSER: Parse failed with 1 error\n\n"); // incase of dupilicates (Block())
                        Program(); // loop to the beginning 
                    }
                } else {
                    Expr();
                }    
            } else if(tokens.get(currentToken).getType().equals(tokenType.closeParenthesis)) { // Checking for closeParenthesis
                matchAndDevour(tokenType.closeParenthesis);
                outputAreaParser.append("PARSER: parseCloseParenthesis()\n");
                outputAreaParser.append("PARSER: parseBooleanExpr()\n"); // BooleanExpr is valid
                Program(); // loops back to beginning
                
            } else if(tokens.get(currentToken).getType().equals(tokenType.boolopNotEqualTo)) { // Checking for BOOLOP
                matchAndDevour(tokenType.boolopNotEqualTo);
                outputAreaParser.append("PARSER: parseBoolopNotEqualTo()\n");
                Expr(); // continues the BooleanExpr
                
            } else if(tokens.get(currentToken).getType().equals(tokenType.boolopEqualTo)) {
                matchAndDevour(tokenType.boolopEqualTo);
                outputAreaParser.append("PARSER: parseBoolopEqualTo()\n");
                Expr(); // continues the BooleanExpr
            
            } else { // Not a BoolopExpr so go to finish the printStatement
                outputAreaParser.append("PARSER: ERROR: Expected [" + tokens.get(currentToken).getType() + "] got [" + tokens.get(currentToken - 1).getType() + "] on line " + lineNumber + "\n");
                outputAreaParser.append("PARSER: Parse failed with 1 error\n\n"); // incase of dupilicates (Block())
                Program(); // loop to the beginning 
            }   
        }   
        
        
        
        /**
         * 
         *  Expr ::== ID   ::== char
         */
        private void ID() {
            // Adds Statement List branch to tree
            t.addNode("ID", "branch");
            
            if(tokens.get(currentToken).getType().equals(tokenType.CHAR)) { // Checking for CHARS
                //Creates the leaf node char
                t.addNode(tokens.get(currentToken).getData(), "leaf"); // Allows me to get the String of current CHAR
                
                matchAndDevour(tokenType.CHAR);
                outputAreaParser.append("PARSER: parseCHAR()\n");
                System.out.println("matched: CHAR\n");
                outputAreaParser.append("PARSER: parseID()\n"); // ID is valid
                
                if(tokens.get(currentToken).getType().equals(tokenType.boolopNotEqualTo)) { // Checking for BOOLOP
                    BooleanExpr(); // continues the BooleanExpr
                    
                } else if(tokens.get(currentToken).getType().equals(tokenType.boolopEqualTo)) { // Checking for BOOLOP
                    BooleanExpr(); // continues the BooleanExpr

                } else if(tokens.get(currentToken).getType().equals(tokenType.closeParenthesis)) {                    
                    PrintStatement(); // Loop back to PrintStatement    
                    
                } else { // An AssignmentStatement
                    outputAreaParser.append("PARSER: parseAssignmentStatement()\n"); // incase of dupilicates (Block())
                    Program(); // loop to the beginning
                }    
            } else {
                outputAreaParser.append("PARSER: ERROR: Expected [" + tokens.get(currentToken).getType() + "] got [" + tokens.get(currentToken - 1).getType() + "] on line " + lineNumber + "\n");
                outputAreaParser.append("PARSER: Parse failed with 1 error\n\n"); // incase of dupilicates (Block())
                Program(); // loop to the beginning
            }    
        }
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     * 
     * (CONTAINS THE JFRAME INFORMATION CREATED UTILIZING JAVA SWING)
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane1 = new javax.swing.JDesktopPane();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        panelLexer = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        inputArea = new javax.swing.JTextArea();
        scrollPaneOutput = new javax.swing.JScrollPane();
        outputArea = new javax.swing.JTextArea();
        buttonLex = new javax.swing.JButton();
        buttonQuit = new javax.swing.JButton();
        labelInput = new javax.swing.JLabel();
        labelOutput = new javax.swing.JLabel();
        buttonClearAll = new javax.swing.JButton();
        buttonTestCases = new javax.swing.JButton();
        labelTitle = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        panelParser = new javax.swing.JPanel();
        scrollPaneInput1 = new javax.swing.JScrollPane();
        outputAreaParser = new javax.swing.JTextArea();
        scrollPaneOutput1 = new javax.swing.JScrollPane();
        cstOutputArea = new javax.swing.JTextArea();
        labelInput1 = new javax.swing.JLabel();
        labelTitle1 = new javax.swing.JLabel();
        labelOutput1 = new javax.swing.JLabel();
        buttonTestCases1 = new javax.swing.JButton();
        buttonQuit1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        buttonClearAll2 = new javax.swing.JButton();
        menuLexer = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuItemQuit = new javax.swing.JMenuItem();
        menuTools = new javax.swing.JMenu();
        menuItemTestCases = new javax.swing.JMenuItem();
        menuHelp = new javax.swing.JMenu();
        menutItemHelp = new javax.swing.JMenuItem();

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Custom Compiler: Lexer");
        setBounds(new java.awt.Rectangle(20, 20, 0, 0));
        setLocation(new java.awt.Point(20, 20));
        setName("frameLexer"); // NOI18N
        setPreferredSize(new java.awt.Dimension(1156, 835));
        setSize(new java.awt.Dimension(0, 0));

        jTabbedPane1.setFont(new java.awt.Font("Helvetica", 1, 16)); // NOI18N

        inputArea.setColumns(20);
        inputArea.setFont(new java.awt.Font("Helvetica Neue", 0, 20)); // NOI18N
        inputArea.setRows(5);
        inputArea.setTabSize(2);
        inputArea.setToolTipText("");
        inputArea.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        inputArea.setVerifyInputWhenFocusTarget(false);
        jScrollPane1.setViewportView(inputArea);

        outputArea.setEditable(false);
        outputArea.setColumns(20);
        outputArea.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        outputArea.setRows(5);
        outputArea.setTabSize(2);
        outputArea.setToolTipText("");
        outputArea.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        outputArea.setVerifyInputWhenFocusTarget(false);
        scrollPaneOutput.setViewportView(outputArea);

        buttonLex.setFont(new java.awt.Font("Helvetica", 0, 16)); // NOI18N
        buttonLex.setText("Compile");
        buttonLex.setToolTipText("Execute program");
        buttonLex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLexActionPerformed(evt);
            }
        });

        buttonQuit.setFont(new java.awt.Font("Helvetica", 0, 16)); // NOI18N
        buttonQuit.setText("Quit");
        buttonQuit.setToolTipText("Exits Program");
        buttonQuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonQuitActionPerformed(evt);
            }
        });

        labelInput.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        labelInput.setText("Input");

        labelOutput.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        labelOutput.setText("Output");

        buttonClearAll.setFont(new java.awt.Font("Helvetica", 0, 16)); // NOI18N
        buttonClearAll.setText("Reset Program");
        buttonClearAll.setToolTipText("Removes text from input and output fields");
        buttonClearAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonClearAllActionPerformed(evt);
            }
        });

        buttonTestCases.setFont(new java.awt.Font("Helvetica", 0, 16)); // NOI18N
        buttonTestCases.setText("Test Cases");
        buttonTestCases.setToolTipText("Opens the test case menu");
        buttonTestCases.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonTestCasesActionPerformed(evt);
            }
        });

        labelTitle.setFont(new java.awt.Font("Helvetica Neue", 3, 36)); // NOI18N
        labelTitle.setText("Custom Compiler: Lexer");
        labelTitle.setAlignmentX(45.0F);
        labelTitle.setAlignmentY(15.0F);

        jLabel2.setFont(new java.awt.Font("Helvetica", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 0, 0));
        jLabel2.setText("* Reset program to erase previous information");

        javax.swing.GroupLayout panelLexerLayout = new javax.swing.GroupLayout(panelLexer);
        panelLexer.setLayout(panelLexerLayout);
        panelLexerLayout.setHorizontalGroup(
            panelLexerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLexerLayout.createSequentialGroup()
                .addGroup(panelLexerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLexerLayout.createSequentialGroup()
                        .addGap(346, 346, 346)
                        .addComponent(labelTitle))
                    .addGroup(panelLexerLayout.createSequentialGroup()
                        .addGap(270, 270, 270)
                        .addComponent(labelInput)
                        .addGap(484, 484, 484)
                        .addComponent(labelOutput))
                    .addGroup(panelLexerLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(panelLexerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelLexerLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(103, 103, 103)
                                .addComponent(buttonLex, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelLexerLayout.createSequentialGroup()
                                .addComponent(buttonClearAll)
                                .addGap(358, 358, 358)
                                .addComponent(buttonTestCases, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(390, 390, 390)
                                .addComponent(buttonQuit, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelLexerLayout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 110, Short.MAX_VALUE)
                .addComponent(scrollPaneOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75))
        );
        panelLexerLayout.setVerticalGroup(
            panelLexerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLexerLayout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(labelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(panelLexerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelInput)
                    .addComponent(labelOutput))
                .addGap(18, 18, 18)
                .addGroup(panelLexerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(scrollPaneOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(88, 88, 88)
                .addGroup(panelLexerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonLex, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLexerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonClearAll, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonTestCases, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonQuit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jTabbedPane1.addTab("Lexer", panelLexer);

        outputAreaParser.setEditable(false);
        outputAreaParser.setColumns(20);
        outputAreaParser.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        outputAreaParser.setRows(5);
        outputAreaParser.setTabSize(2);
        outputAreaParser.setToolTipText("");
        outputAreaParser.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        outputAreaParser.setVerifyInputWhenFocusTarget(false);
        scrollPaneInput1.setViewportView(outputAreaParser);

        cstOutputArea.setEditable(false);
        cstOutputArea.setColumns(20);
        cstOutputArea.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        cstOutputArea.setRows(5);
        cstOutputArea.setTabSize(2);
        cstOutputArea.setToolTipText("");
        cstOutputArea.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        cstOutputArea.setVerifyInputWhenFocusTarget(false);
        scrollPaneOutput1.setViewportView(cstOutputArea);

        labelInput1.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        labelInput1.setText("Parser Output");

        labelTitle1.setFont(new java.awt.Font("Helvetica Neue", 3, 36)); // NOI18N
        labelTitle1.setText("Custom Compiler: Parser");
        labelTitle1.setAlignmentX(45.0F);
        labelTitle1.setAlignmentY(15.0F);

        labelOutput1.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        labelOutput1.setText("CST Output");

        buttonTestCases1.setFont(new java.awt.Font("Helvetica", 0, 16)); // NOI18N
        buttonTestCases1.setText("Test Cases");
        buttonTestCases1.setToolTipText("Opens the test case menu");
        buttonTestCases1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonTestCases1ActionPerformed(evt);
            }
        });

        buttonQuit1.setFont(new java.awt.Font("Helvetica", 0, 16)); // NOI18N
        buttonQuit1.setText("Quit");
        buttonQuit1.setToolTipText("Exits Program");
        buttonQuit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonQuit1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Helvetica", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(204, 0, 0));
        jLabel3.setText("* Reset program to erase previous information");

        buttonClearAll2.setFont(new java.awt.Font("Helvetica", 0, 16)); // NOI18N
        buttonClearAll2.setText("Reset Program");
        buttonClearAll2.setToolTipText("Removes text from input and output fields");
        buttonClearAll2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonClearAll2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelParserLayout = new javax.swing.GroupLayout(panelParser);
        panelParser.setLayout(panelParserLayout);
        panelParserLayout.setHorizontalGroup(
            panelParserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelParserLayout.createSequentialGroup()
                .addGroup(panelParserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelParserLayout.createSequentialGroup()
                        .addGap(360, 360, 360)
                        .addComponent(labelTitle1))
                    .addGroup(panelParserLayout.createSequentialGroup()
                        .addGap(210, 210, 210)
                        .addComponent(labelInput1)
                        .addGap(429, 429, 429)
                        .addComponent(labelOutput1))
                    .addGroup(panelParserLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(panelParserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(panelParserLayout.createSequentialGroup()
                                .addComponent(buttonClearAll2)
                                .addGap(356, 356, 356)
                                .addComponent(buttonTestCases1)
                                .addGap(399, 399, 399)
                                .addComponent(buttonQuit1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelParserLayout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(scrollPaneInput1, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 109, Short.MAX_VALUE)
                .addComponent(scrollPaneOutput1, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59))
        );
        panelParserLayout.setVerticalGroup(
            panelParserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelParserLayout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(panelParserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelParserLayout.createSequentialGroup()
                        .addComponent(labelTitle1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addGroup(panelParserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelInput1)
                            .addComponent(labelOutput1))
                        .addGap(20, 20, 20)
                        .addComponent(scrollPaneInput1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(scrollPaneOutput1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(93, 93, 93)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelParserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonTestCases1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonQuit1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonClearAll2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jTabbedPane1.addTab("Parser", panelParser);

        menuLexer.setToolTipText("");

        menuFile.setText("File");

        menuItemQuit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        menuItemQuit.setText("Quit");
        menuItemQuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemQuitActionPerformed(evt);
            }
        });
        menuFile.add(menuItemQuit);

        menuLexer.add(menuFile);

        menuTools.setText("Tools");

        menuItemTestCases.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        menuItemTestCases.setText("Test Cases");
        menuItemTestCases.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemTestCasesActionPerformed(evt);
            }
        });
        menuTools.add(menuItemTestCases);

        menuLexer.add(menuTools);

        menuHelp.setText("Help");

        menutItemHelp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        menutItemHelp.setText("User help");
        menutItemHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menutItemHelpActionPerformed(evt);
            }
        });
        menuHelp.add(menutItemHelp);

        menuLexer.add(menuHelp);

        setJMenuBar(menuLexer);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        getAccessibleContext().setAccessibleParent(this);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    
    
    // ------------------------------------------------------------
    // -----------------[BUTTON PERFORMANCE]-----------------------
    // ------------------------------------------------------------
        
    // Executes the run (Lexer) prints results onto the Output box
    private void buttonLexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLexActionPerformed
       
        
        
        Token token = new Token();
       
        // Creates a variable for the Parser class
        Parser parse = new Parser(token);
        
        ParseTree t = new ParseTree();
        //System.out.println(t);


        //--------------------FOR later use------------
        ///*
        // * To change this license header, choose License Headers in Project Properties.
        // * To change this template file, choose Tools | Templates
        // * and open the template in the editor.
        // */
        //
        ///**
        // *
        // * @author reynaldoalvarez
        // */
        //public class Tree {
        //   
        //    
        //    public static void main(String args[]) {
        //
        //        
        //        t.addNode("Root", "branch");
        //        t.addNode("Expr", "branch");
        //        t.addNode("Term", "branch");
        //        t.addNode("Factor", "branch");
        //        t.addNode("a", "leaf");
        //        t.endChildren();
        //        t.endChildren();
        //        t.endChildren();
        //        // t.endChildren();  // Un-comment this to test guards against moving "up" past the root of the tree.
        //
        //        t.addNode("Op", "branch");
        //        t.addNode("+", "leaf");
        //        t.endChildren();
        //
        //        t.addNode("Term", "branch");
        //        t.addNode("Factor", "branch");
        //        t.addNode("2", "leaf");
        //        t.endChildren();
        //        t.endChildren();
        //
        //        System.out.println(t);
        //    }
        //}

        // System.out.println(lexer);
        // Prints lexer output to parser input
        //outputAreaParser.append(outputArea.getText());
        
      
    }//GEN-LAST:event_buttonLexActionPerformed
    
    // Exits the Lexer
    private void menuItemQuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemQuitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_menuItemQuitActionPerformed
    
    // Exits the lexer
    private void buttonQuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonQuitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_buttonQuitActionPerformed

    // Button that deletes both the input and output data
    private void buttonClearAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonClearAllActionPerformed
        /* Create and display the new form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Lexer().setVisible(true);
            }
        });
        this.setVisible(false);
        this.setEnabled(false);
    }//GEN-LAST:event_buttonClearAllActionPerformed

    // Opens the Test Cases frame which you can add onto the lexer input box
    private void menuItemTestCasesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemTestCasesActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LexerTestCasesFrame().setVisible(true);
            }
        });
        this.setVisible(false);
        this.setEnabled(false);
    }//GEN-LAST:event_menuItemTestCasesActionPerformed

    // Opens the test cases menu
    private void buttonTestCasesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonTestCasesActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LexerTestCasesFrame().setVisible(true);
            }
        });
        this.setVisible(false);
        this.setEnabled(false);
    }//GEN-LAST:event_buttonTestCasesActionPerformed

    // Opens the helper test cases frame; Gives info on how to use Lexer;
    private void menutItemHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menutItemHelpActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LexerTestCasesHelpFrame().setVisible(true);
            }
        });
        this.setVisible(false);
        this.setEnabled(false);
    }//GEN-LAST:event_menutItemHelpActionPerformed

    private void buttonTestCases1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonTestCases1ActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LexerTestCasesFrame().setVisible(true);
            }
        });
        this.setVisible(false);
        this.setEnabled(false);
    }//GEN-LAST:event_buttonTestCases1ActionPerformed

    private void buttonQuit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonQuit1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_buttonQuit1ActionPerformed

    private void buttonClearAll2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonClearAll2ActionPerformed
        /* Create and display the new form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Lexer().setVisible(true);
            }
        });
        this.setVisible(false);
        this.setEnabled(false);
    }//GEN-LAST:event_buttonClearAll2ActionPerformed

    
    // ------------------------------------------------------------
    // --------------------[MAIN]----------------------------------
    // ------------------------------------------------------------
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Lexer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Lexer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Lexer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Lexer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Lexer().setVisible(true);
            }
        });
    }
    
    
    // ------------------------------------------------------------
    // ----------------[JFRAME VARIABLES]--------------------------
    // ------------------------------------------------------------

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonClearAll;
    private javax.swing.JButton buttonClearAll2;
    public javax.swing.JButton buttonLex;
    private javax.swing.JButton buttonQuit;
    private javax.swing.JButton buttonQuit1;
    private javax.swing.JButton buttonTestCases;
    private javax.swing.JButton buttonTestCases1;
    private javax.swing.JTextArea cstOutputArea;
    private javax.swing.JTextArea inputArea;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel labelInput;
    private javax.swing.JLabel labelInput1;
    private javax.swing.JLabel labelOutput;
    private javax.swing.JLabel labelOutput1;
    private javax.swing.JLabel labelTitle;
    private javax.swing.JLabel labelTitle1;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenu menuHelp;
    private javax.swing.JMenuItem menuItemQuit;
    private javax.swing.JMenuItem menuItemTestCases;
    private javax.swing.JMenuBar menuLexer;
    private javax.swing.JMenu menuTools;
    private javax.swing.JMenuItem menutItemHelp;
    private javax.swing.JTextArea outputArea;
    private javax.swing.JTextArea outputAreaParser;
    private javax.swing.JPanel panelLexer;
    private javax.swing.JPanel panelParser;
    private javax.swing.JScrollPane scrollPaneInput1;
    private javax.swing.JScrollPane scrollPaneOutput;
    private javax.swing.JScrollPane scrollPaneOutput1;
    // End of variables declaration//GEN-END:variables
}
