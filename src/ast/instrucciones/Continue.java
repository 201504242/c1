/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast.instrucciones;

import ast.Instruccion;
import entorno.Entorno;

/**
 *
 * @author p_ab1
 */
public class Continue implements Instruccion{
    int linea;
    int col;

    public Continue() {
//        this.linea = linea;
//        this.col = col;
    }
    
   
    @Override
    public Object ejecutar(Entorno ent) {
        return null;
    }

    @Override
    public int linea() {
        return linea;
    }

    @Override
    public String getNombre() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int columna() {
        return this.col;
    }
    
    
}
