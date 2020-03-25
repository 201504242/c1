/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entorno;

import ast.Expresion;

/**
 *
 * @author p_ab1
 */
public class nodoExp {
    private Expresion exp1;
    private Expresion exp2;
    private entorno.Simbolo.Acc tipoAcceso;

    //nodo para [E] | [[E]] | [E,] | [,E]
    public nodoExp(Expresion exp1, Simbolo.Acc tipoAcceso) {
        this.exp1 = exp1;
        this.tipoAcceso = tipoAcceso;
    }
    
    // nodo para [E,E]
    public nodoExp(Expresion exp1,Expresion exp2, Simbolo.Acc tipoAcceso) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.tipoAcceso = tipoAcceso;
    }
    
    public Expresion getExp1() {
        return exp1;
    }

    public void setExp1(Expresion exp) {
        this.exp1 = exp;
    }

    public Simbolo.Acc getTipoAcceso() {
        return tipoAcceso;
    }

    public void setTipoAcceso(Simbolo.Acc tipoAcceso) {
        this.tipoAcceso = tipoAcceso;
    }    

    public Expresion getExp2() {
        return exp2;
    }

    public void setExp2(Expresion exp2) {
        this.exp2 = exp2;
    }
    
}
