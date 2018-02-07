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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author reynaldoalvarez
 */
public class Lexer extends javax.swing.JFrame {

    /**
     * Creates new form Lexer
     */
    public Lexer() {
        initComponents();
        buttonChange();
          
    }

    private void buttonChange() {
        // Starts with buttons turned off
        clearInput.setEnabled(false);
        clearOutput.setEnabled(false);
        clearAll.setEnabled(false);
        
        // Checks to see if outputArea is empty or not, then changes button
        outputArea.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                outputChanged();
            }
            public void removeUpdate(DocumentEvent e) {
                outputChanged();
            }
            public void insertUpdate(DocumentEvent e) {
                outputChanged();
            }
            public void outputChanged() {
                if(outputArea.getText().isEmpty()) {
                    clearOutput.setEnabled(false);   
                    clearAll.setEnabled(false);
                } else {
                    clearOutput.setEnabled(true);
                    clearAll.setEnabled(true);
                }
            }
        });
        
        // Checks to see if inputArea is empty or not, then changes button
        inputArea.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                inputChanged();
            }
            public void removeUpdate(DocumentEvent e) {
                inputChanged();
            }
            public void insertUpdate(DocumentEvent e) {
                inputChanged();
            }
            public void inputChanged() {
                if(inputArea.getText().isEmpty()) {
                    clearInput.setEnabled(false);
                    clearAll.setEnabled(false);
                } else {
                    clearInput.setEnabled(true);
                    clearAll.setEnabled(true);
                }
            }
        });
    }

   
    
    // Defining our token keywordS with their corresponding expression names 
    public static enum TokenType {
        
        
        
        // -----------------|End of Program Marker|--------------------- \\
        EOP("[$]"),
        
        
        // -------------|Keywords|Numbers|Letters|Booleans|---------------- \\
        // Keywords
        keywordInt("int"),
        keywordString("String"),
        keywordBoolean("boolean"),
        keywordAbstract("abstract"),
        keywordContinue("continue"),
        keywordFor("for"),
        keywordNew("new"),
        keywordSwitch("switch"),
        keywordAssert("assert"),
        keywordDefault("default"),
        keywordGoto("goto"),
        keywordPackage("package"),
        keywordSynchronized("synchronized"),
        keywordDo("do"),
        keywordIf("if"),
        keywordPrivate("private"),
        keywordThis("this"),
        keywordBreak("break"),
        keywordDouble("double"),
        keywordImplements("implements"),
        keywordProtected("protected"),
        keywordThrow("throw"),
        keywordByte("byte"),
        keywordElse("else"),
        keywordImport("import"),
        keywordPublic("public"),
        keywordThrows("throws"),
        keywordCase("case"),
        keywordEnum("enum"),
        keywordInstanceof("instanceof"),
        keywordReturn("return"),
        keywordTransient("transient"),
        keywordCatch("catch"),
        keywordExtends("extends"),
        keywordShort("short"),
        keywordTry("try"),
        keywordChar("char"),
        keywordFinal("final"),
        keywordInterface("interface"),
        keywordStatic("static"),
        keywordVoid("void"),
        keywordClass("class"),
        keywordFinally("finally"),
        keywordLong("long"),
        keywordStrictfp("strictfp"),
        keywordVolatile("volatile"),
        keywordConst("const"),
        keywordFloat("float"),
        keywordNative("native"),
        keywordSuper("super"),
        keywordWhile("while"),
        
        // Identifiers
        identifier("[a-z][a-z|A-Z|0-9|_]+"),
        
        // Booleans
        boolvalFalse("false"),
        boolvalTrue("true"),
        
        // Letters
        CHAR("[a-z|A-Z]"), 
        
        
        // Numbers
        digit("[0-9]"), 
        
        
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
        
        // Semicolon and colon
        SEMICOLON(";"),
        COLON(":"),
        
        // Comments
        
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
    
    // Allows a string to be placed before a desired string
    public static String before(String value, String a) {
        // Return substring containing all characters before a string.
        int posA = value.indexOf(a);
        if (posA == -1) {
            return "";
        }
        return value.substring(0, posA);
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
            else if(matcher.group(TokenType.keywordInt.name()) != null) {
                tokens.add(new Token(TokenType.keywordInt, matcher.group(TokenType.keywordInt.name()))); 
            } else if(matcher.group(TokenType.keywordString.name()) != null) {
                tokens.add(new Token(TokenType.keywordString, matcher.group(TokenType.keywordString.name()))); 
            } else if(matcher.group(TokenType.keywordBoolean.name()) != null) {
                tokens.add(new Token(TokenType.keywordBoolean, matcher.group(TokenType.keywordBoolean.name()))); 
            } else if(matcher.group(TokenType.keywordAbstract.name()) != null) {
                tokens.add(new Token(TokenType.keywordAbstract, matcher.group(TokenType.keywordAbstract.name()))); 
            } else if(matcher.group(TokenType.keywordContinue.name()) != null) {
                tokens.add(new Token(TokenType.keywordContinue, matcher.group(TokenType.keywordContinue.name()))); 
            } else if(matcher.group(TokenType.keywordFor.name()) != null) {
                tokens.add(new Token(TokenType.keywordFor, matcher.group(TokenType.keywordFor.name()))); 
            } else if(matcher.group(TokenType.keywordNew.name()) != null) {
                tokens.add(new Token(TokenType.keywordNew, matcher.group(TokenType.keywordNew.name()))); 
            } else if(matcher.group(TokenType.keywordSwitch.name()) != null) {
                tokens.add(new Token(TokenType.keywordSwitch, matcher.group(TokenType.keywordSwitch.name()))); 
            } else if(matcher.group(TokenType.keywordAssert.name()) != null) {
                tokens.add(new Token(TokenType.keywordAssert, matcher.group(TokenType.keywordAssert.name()))); 
            } else if(matcher.group(TokenType.keywordDefault.name()) != null) {
                tokens.add(new Token(TokenType.keywordDefault, matcher.group(TokenType.keywordDefault.name()))); 
            } else if(matcher.group(TokenType.keywordGoto.name()) != null) {
                tokens.add(new Token(TokenType.keywordGoto, matcher.group(TokenType.keywordGoto.name()))); 
            } else if(matcher.group(TokenType.keywordPackage.name()) != null) {
                tokens.add(new Token(TokenType.keywordPackage, matcher.group(TokenType.keywordPackage.name()))); 
            } else if(matcher.group(TokenType.keywordSynchronized.name()) != null) {
                tokens.add(new Token(TokenType.keywordSynchronized, matcher.group(TokenType.keywordSynchronized.name()))); 
            } else if(matcher.group(TokenType.keywordDo.name()) != null) {
                tokens.add(new Token(TokenType.keywordDo, matcher.group(TokenType.keywordDo.name()))); 
            } else if(matcher.group(TokenType.keywordIf.name()) != null) {
                tokens.add(new Token(TokenType.keywordIf, matcher.group(TokenType.keywordIf.name()))); 
            } else if(matcher.group(TokenType.keywordPrivate.name()) != null) {
                tokens.add(new Token(TokenType.keywordPrivate, matcher.group(TokenType.keywordPrivate.name()))); 
            } else if(matcher.group(TokenType.keywordThis.name()) != null) {
                tokens.add(new Token(TokenType.keywordThis, matcher.group(TokenType.keywordThis.name()))); 
            } else if(matcher.group(TokenType.keywordBreak.name()) != null) {
                tokens.add(new Token(TokenType.keywordBreak, matcher.group(TokenType.keywordBreak.name()))); 
            } else if(matcher.group(TokenType.keywordDouble.name()) != null) {
                tokens.add(new Token(TokenType.keywordDouble, matcher.group(TokenType.keywordDouble.name()))); 
            } else if(matcher.group(TokenType.keywordImplements.name()) != null) {
                tokens.add(new Token(TokenType.keywordImplements, matcher.group(TokenType.keywordImplements.name()))); 
            } else if(matcher.group(TokenType.keywordProtected.name()) != null) {
                tokens.add(new Token(TokenType.keywordProtected, matcher.group(TokenType.keywordProtected.name()))); 
            } else if(matcher.group(TokenType.keywordThrow.name()) != null) {
                tokens.add(new Token(TokenType.keywordThrow, matcher.group(TokenType.keywordThrow.name()))); 
            } else if(matcher.group(TokenType.keywordByte.name()) != null) {
                tokens.add(new Token(TokenType.keywordByte, matcher.group(TokenType.keywordByte.name()))); 
            } else if(matcher.group(TokenType.keywordElse.name()) != null) {
                tokens.add(new Token(TokenType.keywordElse, matcher.group(TokenType.keywordElse.name()))); 
            } else if(matcher.group(TokenType.keywordImport.name()) != null) {
                tokens.add(new Token(TokenType.keywordImport, matcher.group(TokenType.keywordImport.name()))); 
            } else if(matcher.group(TokenType.keywordPublic.name()) != null) {
                tokens.add(new Token(TokenType.keywordPublic, matcher.group(TokenType.keywordPublic.name()))); 
            } else if(matcher.group(TokenType.keywordThrows.name()) != null) {
                tokens.add(new Token(TokenType.keywordThrows, matcher.group(TokenType.keywordThrows.name()))); 
            } else if(matcher.group(TokenType.keywordCase.name()) != null) {
                tokens.add(new Token(TokenType.keywordCase, matcher.group(TokenType.keywordCase.name()))); 
            } else if(matcher.group(TokenType.keywordEnum.name()) != null) {
                tokens.add(new Token(TokenType.keywordEnum, matcher.group(TokenType.keywordEnum.name()))); 
            } else if(matcher.group(TokenType.keywordInstanceof.name()) != null) {
                tokens.add(new Token(TokenType.keywordInstanceof, matcher.group(TokenType.keywordInstanceof.name()))); 
            } else if(matcher.group(TokenType.keywordReturn.name()) != null) {
                tokens.add(new Token(TokenType.keywordReturn, matcher.group(TokenType.keywordReturn.name()))); 
            } else if(matcher.group(TokenType.keywordTransient.name()) != null) {
                tokens.add(new Token(TokenType.keywordTransient, matcher.group(TokenType.keywordTransient.name()))); 
            } else if(matcher.group(TokenType.keywordCatch.name()) != null) {
                tokens.add(new Token(TokenType.keywordCatch, matcher.group(TokenType.keywordCatch.name()))); 
            } else if(matcher.group(TokenType.keywordExtends.name()) != null) {
                tokens.add(new Token(TokenType.keywordExtends, matcher.group(TokenType.keywordExtends.name()))); 
            } else if(matcher.group(TokenType.keywordShort.name()) != null) {
                tokens.add(new Token(TokenType.keywordShort, matcher.group(TokenType.keywordShort.name()))); 
            } else if(matcher.group(TokenType.keywordTry.name()) != null) {
                tokens.add(new Token(TokenType.keywordTry, matcher.group(TokenType.keywordTry.name()))); 
            } else if(matcher.group(TokenType.keywordChar.name()) != null) {
                tokens.add(new Token(TokenType.keywordChar, matcher.group(TokenType.keywordChar.name()))); 
            } else if(matcher.group(TokenType.keywordFinal.name()) != null) {
                tokens.add(new Token(TokenType.keywordFinal, matcher.group(TokenType.keywordFinal.name()))); 
            } else if(matcher.group(TokenType.keywordInterface.name()) != null) {
                tokens.add(new Token(TokenType.keywordInterface, matcher.group(TokenType.keywordInterface.name()))); 
            } else if(matcher.group(TokenType.keywordStatic.name()) != null) {
                tokens.add(new Token(TokenType.keywordStatic, matcher.group(TokenType.keywordStatic.name()))); 
            } else if(matcher.group(TokenType.keywordVoid.name()) != null) {
                tokens.add(new Token(TokenType.keywordVoid, matcher.group(TokenType.keywordVoid.name()))); 
            } else if(matcher.group(TokenType.keywordClass.name()) != null) {
                tokens.add(new Token(TokenType.keywordClass, matcher.group(TokenType.keywordClass.name()))); 
            } else if(matcher.group(TokenType.keywordFinally.name()) != null) {
                tokens.add(new Token(TokenType.keywordFinally, matcher.group(TokenType.keywordFinally.name()))); 
            } else if(matcher.group(TokenType.keywordLong.name()) != null) {
                tokens.add(new Token(TokenType.keywordLong, matcher.group(TokenType.keywordLong.name()))); 
            } else if(matcher.group(TokenType.keywordStrictfp.name()) != null) {
                tokens.add(new Token(TokenType.keywordStrictfp, matcher.group(TokenType.keywordStrictfp.name()))); 
            } else if(matcher.group(TokenType.keywordVolatile.name()) != null) {
                tokens.add(new Token(TokenType.keywordVolatile, matcher.group(TokenType.keywordVolatile.name()))); 
            } else if(matcher.group(TokenType.keywordConst.name()) != null) {
                tokens.add(new Token(TokenType.keywordConst, matcher.group(TokenType.keywordConst.name()))); 
            } else if(matcher.group(TokenType.keywordFloat.name()) != null) {
                tokens.add(new Token(TokenType.keywordFloat, matcher.group(TokenType.keywordFloat.name()))); 
            } else if(matcher.group(TokenType.keywordNative.name()) != null) {
                tokens.add(new Token(TokenType.keywordNative, matcher.group(TokenType.keywordNative.name()))); 
            } else if(matcher.group(TokenType.keywordSuper.name()) != null) {
                tokens.add(new Token(TokenType.keywordSuper, matcher.group(TokenType.keywordSuper.name()))); 
            } else if(matcher.group(TokenType.keywordWhile.name()) != null) {
                tokens.add(new Token(TokenType.keywordWhile, matcher.group(TokenType.keywordWhile.name()))); 
            } else if(matcher.group(TokenType.boolvalFalse.name()) != null) {
                tokens.add(new Token(TokenType.boolvalFalse, matcher.group(TokenType.boolvalFalse.name()))); 
            } else if(matcher.group(TokenType.boolvalTrue.name()) != null) {
                tokens.add(new Token(TokenType.boolvalTrue, matcher.group(TokenType.boolvalTrue.name()))); 
            } else if(matcher.group(TokenType.identifier.name()) != null) {
                tokens.add(new Token(TokenType.identifier, matcher.group(TokenType.identifier.name()))); 
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
            } else if(matcher.group(TokenType.SEMICOLON.name()) != null) {
                tokens.add(new Token(TokenType.SEMICOLON, matcher.group(TokenType.SEMICOLON.name())));
            } else if(matcher.group(TokenType.COLON.name()) != null) {
                tokens.add(new Token(TokenType.COLON, matcher.group(TokenType.COLON.name())));
            } else if(matcher.group(TokenType.QUOTE.name()) != null) {
                tokens.add(new Token(TokenType.QUOTE, matcher.group(TokenType.QUOTE.name()))); 
            } else if(matcher.group(TokenType.EOP.name()) != null) {
                tokens.add(new Token(TokenType.EOP, matcher.group(TokenType.EOP.name()))); 
            }
        }
   
        return tokens;
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jInternalFrame1 = new javax.swing.JInternalFrame();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jFrame1 = new javax.swing.JFrame();
        jFrame2 = new javax.swing.JFrame();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jToggleButton1 = new javax.swing.JToggleButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        inputArea = new javax.swing.JTextArea();
        runCode = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        outputArea = new javax.swing.JTextArea();
        clearOutput = new javax.swing.JButton();
        clearInput = new javax.swing.JButton();
        clearAll = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        jInternalFrame1.setVisible(true);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame2Layout = new javax.swing.GroupLayout(jFrame2.getContentPane());
        jFrame2.getContentPane().setLayout(jFrame2Layout);
        jFrame2Layout.setHorizontalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame2Layout.setVerticalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jMenu3.setText("File");
        jMenuBar2.add(jMenu3);

        jMenu4.setText("Edit");
        jMenuBar2.add(jMenu4);

        jToggleButton1.setText("jToggleButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);
        setLocation(new java.awt.Point(300, 23));

        jPanel1.setPreferredSize(new java.awt.Dimension(500, 500));

        inputArea.setColumns(20);
        inputArea.setRows(5);
        jScrollPane2.setViewportView(inputArea);

        runCode.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        runCode.setText("Run");
        runCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runCodeActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel2.setText("Output");

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel1.setText("Input");

        outputArea.setColumns(20);
        outputArea.setRows(5);
        jScrollPane3.setViewportView(outputArea);

        clearOutput.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        clearOutput.setText("Clear Output");
        clearOutput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearOutputActionPerformed(evt);
            }
        });

        clearInput.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        clearInput.setText("Clear Input");
        clearInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearInputActionPerformed(evt);
            }
        });

        clearAll.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        clearAll.setText("Clear All");
        clearAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearAllActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Helvetica Neue", 1, 36)); // NOI18N
        jLabel3.setText("Custom Java Lexer");
        jLabel3.setSize(new java.awt.Dimension(45, 15));

        jButton5.setText("Empty brackets");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel4.setText("Test Cases: ");

        jButton6.setText("Alan's code");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Advanced code");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("Simple code");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(202, 202, 202)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(166, 166, 166))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(254, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(208, 208, 208))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(156, 156, 156)
                .addComponent(clearInput)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(clearOutput)
                .addGap(149, 149, 149))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton8)))
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(runCode))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(clearAll)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clearOutput)
                    .addComponent(clearInput))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(runCode)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clearAll))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton5)
                            .addComponent(jButton6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton7)
                            .addComponent(jButton8))))
                .addGap(19, 19, 19))
        );

        runCode.getAccessibleContext().setAccessibleName("Lex");
        runCode.getAccessibleContext().setAccessibleDescription("");
        clearOutput.getAccessibleContext().setAccessibleName("ClearOutput");

        jMenu1.setText("File");
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.META_MASK));
        jMenuItem1.setText("Exit");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 808, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 643, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>                        

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {                                       
        // TODO add your handling code here:
    }                                      

    private void clearAllActionPerformed(java.awt.event.ActionEvent evt) {                                         
        inputArea.setText(null);
        outputArea.setText(null);       
    }                                        

    private void clearInputActionPerformed(java.awt.event.ActionEvent evt) {                                           
        inputArea.setText(null);       
    }                                          

    private void clearOutputActionPerformed(java.awt.event.ActionEvent evt) {                                            
        outputArea.setText(null);       
    }                                           

    private void runCodeActionPerformed(java.awt.event.ActionEvent evt) {                                        

        int i = 1;
        
        String input = inputArea.getText();
        String output = outputArea.getText();
        
        int errorCount = 0;
        int warningCount = 0;
        
        boolean lexComplete = false;
        //boolean endToken = false;
        boolean errorToken = false;
      
        if((input.isEmpty())) { //Error if there is no input
            outputArea.append("~ERROR: No input found~\n");
            errorCount++;
        } else {
            
                
            outputArea.append("\nLEXER: Lexing program " + i + "...\n");
            

            // Outputs a stream of tokens from the given input
            ArrayList<Token> tokens = lex(input);
            for(Token token : tokens) {
                int index = token.data.indexOf("$");
                boolean moreThanOnce = index != -1 && index != input.lastIndexOf("$");
                
                outputArea.append("LEXER:" + token + "\n"); // Prints out tokens
              
                if((warningCount == 0) && (errorCount == 0)) {
                        if(token.type == EOP) {
                            outputArea.append("LEXER: Lex completed successfully\n\n");
                        }
                    }
               
                if(moreThanOnce == true){
                    i++;
                    outputArea.append("\nLEXER: Lexing program " + i + "...\n");
                }
                
               
            }
            
            
        } 
        if(errorToken == true) {
                outputArea.append("\nLEXER Error: Unrecognized Token.\n");
               
            }
        outputArea.append("Lexer crashed with:\n [" + warningCount + "] Warning(s) "
                    + "and [" + errorCount + "] Error(s).\n\n"); 
    }                                       

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        System.exit(0);
    }                                          

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        inputArea.append("{}$");
    }                                        

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        inputArea.append("{}$\n" +
            "{{{{{{}}}}}}$\n" +
            "{{{{{{}}}}}}}$\n" +
            "{int @}$");
    }                                        

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        inputArea.append("public static String toBinary(int base10Num){\n" +
       "        boolean isNeg = base10Num < 0;\n" +
       "        base10Num = Math.abs(base10Num);        \n" +
       "        String result = \"\";\n" +
       "        \n" +
       "        while(base10Num > 1){\n" +
       "            result = (base10Num % 2) + result;\n" +
       "            base10Num /= 2;\n" +
       "        }\n" +
       "        assert base10Num == 0 || base10Num == 1 : \"value is not <= 1: \" + base10Num;\n" +
       "        \n" +
       "        result = base10Num + result;\n" +
       "        assert all0sAnd1s(result);\n" +
       "        \n" +
       "        if( isNeg )\n" +
       "            result = \"-\" + result;\n" +
       "        return result;\n" +
       "    }$");
    }                                        

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {                                         
       inputArea.append("short num;\n" +
            "    	\n" +
            "    	num = 150;\n" +
            "    }\n" +
            "}$");
    }                                        

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Lexer().setVisible(true);
                
            }
        });
       
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton clearAll;
    private javax.swing.JButton clearInput;
    private javax.swing.JButton clearOutput;
    private javax.swing.JTextArea inputArea;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JTextArea outputArea;
    private javax.swing.JButton runCode;
    // End of variables declaration                   
}
