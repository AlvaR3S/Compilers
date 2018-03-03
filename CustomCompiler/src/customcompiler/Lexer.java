/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customcompiler;

import static customcompiler.Lexer.TokenType.EOP;
import static customcompiler.Lexer.TokenType.unrecognized;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTextArea;
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
        
    public JTextArea getInputArea() {
        return inputArea;
    }
    
    private void buttonChange() {
        // Starts with buttons turned off
        buttonClearInput.setEnabled(false);
        buttonClearOutput.setEnabled(false);
        buttonClearAll.setEnabled(false);
        
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
                    buttonClearOutput.setEnabled(false);   
                    buttonClearAll.setEnabled(false);
                } else {
                    buttonClearOutput.setEnabled(true);
                    buttonClearAll.setEnabled(true);
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
                    buttonClearInput.setEnabled(false);
                    buttonClearAll.setEnabled(false);
                } else {
                    buttonClearInput.setEnabled(true);
                    buttonClearAll.setEnabled(true);
                }
            }
        });
    }
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
        ID("[a-z]"), 
       
        // Numbers
        digit("[0-9]"), 
        
        // --------------------|Symbols|--------------------------- \\
        // Arithmetic Operator
        intopAddition("[+]"),
        
        // Unary Operator
        boolopNotEqualTo("!="),
        boolopEqualTo("=="),
        assignmentStatement("="),
        
        // Brackets
        openBracket("[{]"),
        closeBracket("[}]"),
        
        // Parenthesis
        openParenthesis("[(]"),
        closeParenthesis("[)]"),
        
        // Whitespace
        whtieSpace("[ \t\f\r\n]+"),
        
        // Letters in between quotes are chars
        CHAR("\"[a-z]\""), //get first letter in string makes it a char rest are ID
        intCHAR("\"int\""),
        unrecognized("[A-Z|~|!|@|#|%|^||&|*|_|:|<|>|?|;|'|,|.|/]");
        
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPaneInput = new javax.swing.JScrollPane();
        inputArea = new javax.swing.JTextArea();
        scrollPaneOutput = new javax.swing.JScrollPane();
        outputArea = new javax.swing.JTextArea();
        buttonParser = new javax.swing.JButton();
        labelNavigateInfo = new javax.swing.JLabel();
        buttonRun = new javax.swing.JButton();
        buttonProject4 = new javax.swing.JButton();
        buttonProject3 = new javax.swing.JButton();
        buttonQuit = new javax.swing.JButton();
        labelTitle = new javax.swing.JLabel();
        labelInput = new javax.swing.JLabel();
        labelOutput = new javax.swing.JLabel();
        buttonMain = new javax.swing.JButton();
        buttonClearAll = new javax.swing.JButton();
        buttonClearInput = new javax.swing.JButton();
        buttonClearOutput = new javax.swing.JButton();
        buttonTestCases = new javax.swing.JButton();
        menuLexer = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuItemQuit = new javax.swing.JMenuItem();
        menuTools = new javax.swing.JMenu();
        menuItemTestCases = new javax.swing.JMenuItem();
        menuHelp = new javax.swing.JMenu();
        menuItemHelp = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Custom Compiler: Lexer");
        setBounds(new java.awt.Rectangle(20, 20, 0, 0));
        setLocation(new java.awt.Point(20, 20));
        setName("frameLexer"); // NOI18N
        setPreferredSize(new java.awt.Dimension(1156, 805));
        setSize(new java.awt.Dimension(0, 0));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        inputArea.setColumns(20);
        inputArea.setFont(new java.awt.Font("Helvetica Neue", 0, 20)); // NOI18N
        inputArea.setRows(5);
        inputArea.setTabSize(2);
        inputArea.setToolTipText("");
        inputArea.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        inputArea.setVerifyInputWhenFocusTarget(false);
        scrollPaneInput.setViewportView(inputArea);

        getContentPane().add(scrollPaneInput, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 200, 460, 380));

        outputArea.setEditable(false);
        outputArea.setColumns(20);
        outputArea.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        outputArea.setRows(5);
        outputArea.setTabSize(2);
        outputArea.setToolTipText("");
        outputArea.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        outputArea.setVerifyInputWhenFocusTarget(false);
        scrollPaneOutput.setViewportView(outputArea);

        getContentPane().add(scrollPaneOutput, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 200, 460, 380));

        buttonParser.setFont(new java.awt.Font("Helvetica", 0, 16)); // NOI18N
        buttonParser.setText("PARSER");
        buttonParser.setToolTipText("Opens parser window");
        buttonParser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonParserActionPerformed(evt);
            }
        });
        getContentPane().add(buttonParser, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 710, 120, 30));

        labelNavigateInfo.setFont(new java.awt.Font("Helvetica Neue", 1, 12)); // NOI18N
        labelNavigateInfo.setText("Navigate through the Custom Compiler with the buttons down below:");
        getContentPane().add(labelNavigateInfo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 680, -1, 20));

        buttonRun.setFont(new java.awt.Font("Helvetica", 0, 16)); // NOI18N
        buttonRun.setText("Run");
        buttonRun.setToolTipText("Execute program");
        buttonRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRunActionPerformed(evt);
            }
        });
        getContentPane().add(buttonRun, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 640, 90, 30));

        buttonProject4.setFont(new java.awt.Font("Helvetica", 0, 16)); // NOI18N
        buttonProject4.setText("Unavailable");
        buttonProject4.setToolTipText("Project 4 coming soon...");
        buttonProject4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonProject4ActionPerformed(evt);
            }
        });
        getContentPane().add(buttonProject4, new org.netbeans.lib.awtextra.AbsoluteConstraints(459, 710, 140, 30));

        buttonProject3.setFont(new java.awt.Font("Helvetica", 0, 16)); // NOI18N
        buttonProject3.setText("Unavailable");
        buttonProject3.setToolTipText("Project 3 coming soon...");
        buttonProject3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonProject3ActionPerformed(evt);
            }
        });
        getContentPane().add(buttonProject3, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 710, 140, 30));

        buttonQuit.setFont(new java.awt.Font("Helvetica", 0, 16)); // NOI18N
        buttonQuit.setText("Quit");
        buttonQuit.setToolTipText("Exits Program");
        buttonQuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonQuitActionPerformed(evt);
            }
        });
        getContentPane().add(buttonQuit, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 710, 90, 30));

        labelTitle.setFont(new java.awt.Font("Helvetica Neue", 3, 36)); // NOI18N
        labelTitle.setText("Custom Compiler: Lexer");
        labelTitle.setAlignmentX(45.0F);
        labelTitle.setAlignmentY(15.0F);
        getContentPane().add(labelTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 70, -1, -1));

        labelInput.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        labelInput.setText("Input");
        getContentPane().add(labelInput, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 150, -1, -1));

        labelOutput.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        labelOutput.setText("Output");
        getContentPane().add(labelOutput, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 150, -1, -1));

        buttonMain.setFont(new java.awt.Font("Helvetica", 0, 16)); // NOI18N
        buttonMain.setText("HOME");
        buttonMain.setToolTipText("Goes to main menu window");
        buttonMain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonMainActionPerformed(evt);
            }
        });
        getContentPane().add(buttonMain, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 710, 90, 30));

        buttonClearAll.setFont(new java.awt.Font("Helvetica", 0, 16)); // NOI18N
        buttonClearAll.setText("Clear All");
        buttonClearAll.setToolTipText("Removes text from input and output fields");
        buttonClearAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonClearAllActionPerformed(evt);
            }
        });
        getContentPane().add(buttonClearAll, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 640, 90, 30));

        buttonClearInput.setFont(new java.awt.Font("Helvetica", 0, 16)); // NOI18N
        buttonClearInput.setText("Clear Input");
        buttonClearInput.setToolTipText("Removes text from input field");
        buttonClearInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonClearInputActionPerformed(evt);
            }
        });
        getContentPane().add(buttonClearInput, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 590, -1, 30));

        buttonClearOutput.setFont(new java.awt.Font("Helvetica", 0, 16)); // NOI18N
        buttonClearOutput.setText("Clear Output");
        buttonClearOutput.setToolTipText("Removes text from Output field");
        buttonClearOutput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonClearOutputActionPerformed(evt);
            }
        });
        getContentPane().add(buttonClearOutput, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 590, -1, 30));

        buttonTestCases.setFont(new java.awt.Font("Helvetica", 0, 16)); // NOI18N
        buttonTestCases.setText("Test Cases");
        buttonTestCases.setToolTipText("Opens the test case menu");
        buttonTestCases.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonTestCasesActionPerformed(evt);
            }
        });
        getContentPane().add(buttonTestCases, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 710, -1, 30));

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

        menuItemHelp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        menuItemHelp.setText("User help");
        menuItemHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemHelpActionPerformed(evt);
            }
        });
        menuHelp.add(menuItemHelp);

        menuLexer.add(menuHelp);

        setJMenuBar(menuLexer);

        getAccessibleContext().setAccessibleParent(this);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void buttonParserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonParserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonParserActionPerformed

    private void buttonRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRunActionPerformed
        int i = 1;
        int errorCount = 0;
        int warningCount = 0;
        
        String input = inputArea.getText();
        String output = outputArea.getText();
        boolean errorToken = false;
        
        
    
        // Lexer takes the input, finds the patterns and places them into token format
        StringBuffer tokenPatternsBuffer = new StringBuffer();
        for (TokenType tokenType : TokenType.values()) 
            tokenPatternsBuffer.append(String.format("|(?<%s>%s)", tokenType.name(), tokenType.pattern));
   
        Pattern tokenPatterns = Pattern.compile(tokenPatternsBuffer.substring(1), Pattern.CASE_INSENSITIVE);

        // Lexer Matches the patterns and if they are valid, they will be added to the new tokens array for output
        Matcher tokenMatcher = tokenPatterns.matcher(input);
           
        // Returns tokens using the stored and formatted token information
        ArrayList<Token> tokens = new ArrayList<Token>(); 
            
        // Loops through the input and finds valid tokens
        while(tokenMatcher.find()) {     
            if(tokenMatcher.group(TokenType.whtieSpace.name()) != null) { 
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
            } else if(tokenMatcher.group(TokenType.ID.name()) != null) {
                tokens.add(new Token(TokenType.ID, tokenMatcher.group(TokenType.ID.name()))); 
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
            } else if(tokenMatcher.group(TokenType.CHAR.name()) != null) {
                tokens.add(new Token(TokenType.CHAR, tokenMatcher.group(TokenType.CHAR.name())));
            } else if(tokenMatcher.group(TokenType.intCHAR.name()) != null) {
                tokens.add(new Token(TokenType.CHAR, tokenMatcher.group(TokenType.intCHAR.name())));
                // Needs to print individual letters
            } else if(tokenMatcher.group(TokenType.unrecognized.name()) != null) {
                tokens.add(new Token(TokenType.unrecognized, tokenMatcher.group(TokenType.unrecognized.name())));
                errorCount++;
            } else {
                System.out.println("Unrecognized token found.");
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
        outputArea.append("\nLEXER: Lexing program " + i + "...\n");
           
        // Outputs a stream of tokens from the given input
        for(Token token : tokens) {
            int index = token.data.indexOf("$");
            boolean moreThanOnce = index != -1 && index != input.lastIndexOf("$");
            
            if(token.type == unrecognized) {
                outputArea.append("Unrecognized token:" + token + "\n");
            } else {
                outputArea.append("LEXER:" + token + "\n"); // Prints out tokens
            }
            
            // If no errors or warnings have been found then lexer has succeeded
            if(token.type == EOP) {
                outputArea.append("LEXER: Lex completed successfully\n\n");
                
                // If there is more than one $ there is more than one lexeing program
                if(moreThanOnce) {
                    i++;
                   outputArea.append("\nLEXER: Lexing program " + i + "...\n");  
                }  
            }
            
        }
        // Spits out a warning when input string does not end with a $ symbol
        if(!input.endsWith("$")) {
            warningCount++;
        }
        
        // Spits out an error when input contains ("&") OR ('$')
        if(input.contains("\"$\"") || input.contains("\'$\'")) {
            errorCount++;
        }
        
        // Ignoring comments (NOT FINISHED YET)
        if(input.contains("//")) {
            System.out.print("ignore");
        }
        
        
        outputArea.append("Lexer crashed with:\n [" + warningCount + "] Warning(s) "
                            + "and [" + errorCount + "] Error(s).\n\n");  
    }//GEN-LAST:event_buttonRunActionPerformed

    private void buttonProject4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonProject4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonProject4ActionPerformed

    private void buttonProject3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonProject3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonProject3ActionPerformed

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
        inputArea.setText(null);
        outputArea.setText(null);  
    }//GEN-LAST:event_buttonClearAllActionPerformed

    // Button that deletes input data
    private void buttonClearInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonClearInputActionPerformed
        inputArea.setText(null);    
    }//GEN-LAST:event_buttonClearInputActionPerformed

    // Button that deletes output data
    private void buttonClearOutputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonClearOutputActionPerformed
        outputArea.setText(null); 
    }//GEN-LAST:event_buttonClearOutputActionPerformed

    private void buttonMainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonMainActionPerformed
        runCC main = new runCC();
        main.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_buttonMainActionPerformed

    private void menuItemTestCasesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemTestCasesActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable() {
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

    private void menuItemHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemHelpActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LexerTestCasesHelpFrame().setVisible(true);
            }
        });
        this.setVisible(false);
        this.setEnabled(false);
    }//GEN-LAST:event_menuItemHelpActionPerformed

    /**
     * @param args the command line arguments
     */
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
            public void run() {
                new Lexer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonClearAll;
    private javax.swing.JButton buttonClearInput;
    private javax.swing.JButton buttonClearOutput;
    private javax.swing.JButton buttonMain;
    private javax.swing.JButton buttonParser;
    private javax.swing.JButton buttonProject3;
    private javax.swing.JButton buttonProject4;
    private javax.swing.JButton buttonQuit;
    private javax.swing.JButton buttonRun;
    private javax.swing.JButton buttonTestCases;
    private javax.swing.JTextArea inputArea;
    private javax.swing.JLabel labelInput;
    private javax.swing.JLabel labelNavigateInfo;
    private javax.swing.JLabel labelOutput;
    private javax.swing.JLabel labelTitle;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenu menuHelp;
    private javax.swing.JMenuItem menuItemHelp;
    private javax.swing.JMenuItem menuItemQuit;
    private javax.swing.JMenuItem menuItemTestCases;
    private javax.swing.JMenuBar menuLexer;
    private javax.swing.JMenu menuTools;
    private javax.swing.JTextArea outputArea;
    private javax.swing.JScrollPane scrollPaneInput;
    private javax.swing.JScrollPane scrollPaneOutput;
    // End of variables declaration//GEN-END:variables
}
