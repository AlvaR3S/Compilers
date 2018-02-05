/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customcompiler;


import static customcompiler.Lexer.TokenType.EOP;
import static customcompiler.Lexer.TokenType.openBracket;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

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
        
    }
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
        
        // Semicolon and colon
        SEMICOLON(";"),
        COLON(":"),
        
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
        jTextArea2 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

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

        jPanel1.setPreferredSize(new java.awt.Dimension(500, 500));

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        jButton1.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jButton1.setText("Run");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel2.setText("Output");

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel1.setText("Input");

        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jScrollPane3.setViewportView(jTextArea3);

        jButton2.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jButton2.setText("Clear Output");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jButton3.setText("Clear Input");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jButton4.setText("Clear All");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Helvetica Neue", 1, 36)); // NOI18N
        jLabel3.setText("Custom Java Lexer");
        jLabel3.setSize(new java.awt.Dimension(45, 15));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(156, 156, 156)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(149, 149, 149))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 88, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(366, 366, 366)
                        .addComponent(jButton1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(356, 356, 356)
                        .addComponent(jButton4)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(202, 202, 202)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(184, 184, 184))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(208, 208, 208))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(34, 34, 34)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4)
                .addGap(61, 61, 61))
        );

        jButton1.getAccessibleContext().setAccessibleName("Lex");
        jButton1.getAccessibleContext().setAccessibleDescription("");

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

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 808, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 710, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>                        

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {                                       
        // TODO add your handling code here:
    }                                      

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        jTextArea2.setText(null);
        jTextArea3.setText(null);        // TODO add your handling code here:
    }                                        

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        jTextArea2.setText(null);        // TODO add your handling code here:
    }                                        

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        jTextArea3.setText(null);        // TODO add your handling code here:
    }                                        

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         

        int i = 1;

        //the input that will be tested

        // Outputs a stream of tokens from the given input
        ArrayList<Token> tokens = lex(jTextArea2.getText());
        for(Token token : tokens) {
            if(token.type == openBracket) {
                jTextArea3.append("LEXER: Lexing program " + i + "...\n");
                i = i + 1;
            }
            if(token.type == EOP){ // Lex ends program when "$" is found
                jTextArea3.append("LEXER:" + token + "\n");
                jTextArea3.append("LEXER: Lex completed successfully\n\n");

            } else {
                jTextArea3.append("LEXER:" + token + "\n"); // Prints out tokens
            }
        }
        // TODO add your handling code here:

    }                                        

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
        System.exit(0);
    }                                          

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Lexer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
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
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JToggleButton jToggleButton1;
    // End of variables declaration                   
}
