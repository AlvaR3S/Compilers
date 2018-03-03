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
public class LexerTestCasesHelpFrame extends javax.swing.JFrame {

    /**
     * Creates new form LexerTestCasesHelpFrame
     */
    public LexerTestCasesHelpFrame() {
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
        buttonReturn = new javax.swing.JButton();
        menuLexer = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuItemExit = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Custom Compiler: Lexer");
        setBounds(new java.awt.Rectangle(20, 20, 0, 0));
        setPreferredSize(new java.awt.Dimension(570, 502));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelTitle1.setFont(new java.awt.Font("Helvetica Neue", 1, 36)); // NOI18N
        labelTitle1.setText("Test Case Help");
        labelTitle1.setAlignmentX(45.0F);
        labelTitle1.setAlignmentY(15.0F);
        getContentPane().add(labelTitle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, -1, -1));

        buttonReturn.setFont(new java.awt.Font("Helvetica", 0, 16)); // NOI18N
        buttonReturn.setText("Return");
        buttonReturn.setToolTipText("Goes back to main menu");
        buttonReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonReturnActionPerformed(evt);
            }
        });
        getContentPane().add(buttonReturn, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 400, 130, 40));

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

        setJMenuBar(menuLexer);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void menuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemExitActionPerformed
        Lexer lex = new Lexer();
        lex.setEnabled(true);
        lex.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menuItemExitActionPerformed

    private void buttonReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonReturnActionPerformed
        Lexer lex = new Lexer();
        lex.setEnabled(true);
        lex.setVisible(true);
        this.dispose();
        
    }//GEN-LAST:event_buttonReturnActionPerformed

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
            java.util.logging.Logger.getLogger(LexerTestCasesHelpFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LexerTestCasesHelpFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LexerTestCasesHelpFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LexerTestCasesHelpFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LexerTestCasesHelpFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonReturn;
    private javax.swing.JLabel labelTitle1;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenuItem menuItemExit;
    private javax.swing.JMenuBar menuLexer;
    // End of variables declaration//GEN-END:variables
}
