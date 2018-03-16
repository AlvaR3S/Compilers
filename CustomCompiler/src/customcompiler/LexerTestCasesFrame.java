/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customcompiler;

import javax.swing.JTextArea;



/**
 *
 * @author reynaldoalvarez
 */
public class LexerTestCasesFrame extends javax.swing.JFrame {

    /**
     * Creates new form LexerTestCasesFrame
     */
    public LexerTestCasesFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelTitle1 = new javax.swing.JLabel();
        checkBox2 = new javax.swing.JCheckBox();
        labelInput = new javax.swing.JLabel();
        labelInput1 = new javax.swing.JLabel();
        checkBox4 = new javax.swing.JCheckBox();
        checkBox3 = new javax.swing.JCheckBox();
        checkBox1 = new javax.swing.JCheckBox();
        buttonConfirm = new javax.swing.JButton();
        checkBox5 = new javax.swing.JCheckBox();
        menuLexer = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuItemExit = new javax.swing.JMenuItem();
        menuHelp = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Custom Compiler: Lexer");
        setLocation(new java.awt.Point(20, 20));
        setName("LexerTestCasesFrame"); // NOI18N
        setPreferredSize(new java.awt.Dimension(643, 443));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelTitle1.setFont(new java.awt.Font("Helvetica Neue", 3, 36)); // NOI18N
        labelTitle1.setText("Test Cases");
        labelTitle1.setAlignmentX(45.0F);
        labelTitle1.setAlignmentY(15.0F);
        getContentPane().add(labelTitle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 50, 200, -1));

        checkBox2.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        checkBox2.setText("Chars and Quotes");
        checkBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBox2ActionPerformed(evt);
            }
        });
        getContentPane().add(checkBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 170, -1, -1));

        labelInput.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        labelInput.setText("Error Cases");
        getContentPane().add(labelInput, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 130, -1, -1));

        labelInput1.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        labelInput1.setText("Working Cases");
        getContentPane().add(labelInput1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 130, -1, -1));

        checkBox4.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        checkBox4.setText("Illegal Input");
        checkBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBox4ActionPerformed(evt);
            }
        });
        getContentPane().add(checkBox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 230, -1, -1));

        checkBox3.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        checkBox3.setText("Alan's Case");
        checkBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBox3ActionPerformed(evt);
            }
        });
        getContentPane().add(checkBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 200, -1, -1));

        checkBox1.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        checkBox1.setText("Empty Brackets");
        checkBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBox1ActionPerformed(evt);
            }
        });
        getContentPane().add(checkBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 170, -1, -1));

        buttonConfirm.setFont(new java.awt.Font("Helvetica", 0, 16)); // NOI18N
        buttonConfirm.setText("Confirm");
        buttonConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonConfirmActionPerformed(evt);
            }
        });
        getContentPane().add(buttonConfirm, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 360, 100, 30));

        checkBox5.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        checkBox5.setText("Random");
        checkBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBox5ActionPerformed(evt);
            }
        });
        getContentPane().add(checkBox5, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 200, -1, -1));

        menuLexer.setToolTipText("");

        menuFile.setText("File");

        menuItemExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        menuItemExit.setText("Exit");
        menuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemExitActionPerformed(evt);
            }
        });
        menuFile.add(menuItemExit);

        menuLexer.add(menuFile);

        menuHelp.setText("Help");
        menuLexer.add(menuHelp);

        setJMenuBar(menuLexer);

        getAccessibleContext().setAccessibleDescription("");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void menuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemExitActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Lexer().setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_menuItemExitActionPerformed

    private void checkBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkBox2ActionPerformed

    private void checkBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBox4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkBox4ActionPerformed

    private void checkBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkBox3ActionPerformed

    private void checkBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkBox1ActionPerformed

    private void buttonConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonConfirmActionPerformed
        
        Lexer lex = new Lexer();
        JTextArea inputArea = lex.getInputArea();
        
       // inputArea
        
        if(checkBox1.isSelected()) {
            inputArea.append("{}$");
        }
        
        if(checkBox2.isSelected()) {
            inputArea.append("print()&"
               + "if(a)$"
               + "while(true)$\n"
               + "{print(\"b\")}$");
        }
        
        if(checkBox3.isSelected()) {
            inputArea.append("{}$\n" +
            "{{{{{{} /* comments are ignored */ }}}}}$\n" +
            "{{{{{{}}}}}}}$\n" +
            "{ /* comments are ignored */ int @}$");
        }
        
        if(checkBox4.isSelected()) {
            inputArea.append("{int @}$"
                + "{\"$\"}$");
        }
        
        if(checkBox5.isSelected()) {
            inputArea.append("{\n"
                    + "\t int a = 4"
                    + "\n"
                    + "\n"
                    + "\t int b \n\n"
                    + "\t b = a + a"
                    + "\n"
                    + "}$");
        }
        
        lex.setVisible(true);
        lex.setEnabled(true);
        this.dispose();
    }//GEN-LAST:event_buttonConfirmActionPerformed

    private void checkBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBox5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkBox5ActionPerformed

//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(LexerTestCasesFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(LexerTestCasesFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(LexerTestCasesFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(LexerTestCasesFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new LexerTestCasesFrame().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonConfirm;
    private javax.swing.JCheckBox checkBox1;
    private javax.swing.JCheckBox checkBox2;
    private javax.swing.JCheckBox checkBox3;
    private javax.swing.JCheckBox checkBox4;
    private javax.swing.JCheckBox checkBox5;
    private javax.swing.JLabel labelInput;
    private javax.swing.JLabel labelInput1;
    private javax.swing.JLabel labelTitle1;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenu menuHelp;
    private javax.swing.JMenuItem menuItemExit;
    private javax.swing.JMenuBar menuLexer;
    // End of variables declaration//GEN-END:variables
}
