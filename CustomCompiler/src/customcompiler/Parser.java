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

import customcompiler.Lexer.Token;
import java.util.ArrayList;


public class Parser {
    private final ArrayList<Token> tokens;
    private int currentToken = 0;
    
    Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }
}



