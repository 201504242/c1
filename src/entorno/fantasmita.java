/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entorno;

import ast.Instruccion;
import ast.NodoAST;

/**
 *
 * @author p_ab1
 */
public class fantasmita implements Instruccion{

    public fantasmita() {
    }

    @Override
    public Object ejecutar(Entorno ent) {
        System.out.println("entro a FANTASMA");
        return null;
    }

    @Override
    public int linea() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int columna() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNombre(StringBuilder builder, String parent, int cont) {
        return null;
    }
    
}
