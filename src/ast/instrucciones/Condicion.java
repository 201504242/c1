/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast.instrucciones;

import ast.Expresion;
import ast.NodoAST;
import java.util.LinkedList;

/**
 *
 * @author p_ab1
 */
public class Condicion {
    private LinkedList<NodoAST> ins;
    private Expresion cond;

    public Condicion(LinkedList<NodoAST> ins, Expresion cond) {
        this.ins = ins;
        this.cond = cond;
    }

    public LinkedList<NodoAST> getIns() {
        return ins;
    }

    public void setIns(LinkedList<NodoAST> ins) {
        this.ins = ins;
    }

    public Expresion getCond() {
        return cond;
    }

    public void setCond(Expresion cond) {
        this.cond = cond;
    }
}
