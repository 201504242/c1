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
public class Break implements Instruccion{
    private int linea;
    private int col;

    public Break(int linea,int col) {
        this.linea = linea;
        this.col = col;
    }

    
    @Override
    public Object ejecutar(Entorno ent) {
        return null;
    }

    @Override
    public int linea() {
        return this.linea;
    }

    @Override
    public int columna() {
        return this.col;
    }

    @Override
    public String getNombre(StringBuilder builder, String parent, int cont) {
        String nodo = "nodo" + ++cont;
        builder.append(nodo).append(" [label=\"Break\"];\n");
        builder.append(parent).append(" -> ").append(nodo).append(";\n");
        return ""+cont;
    }
    
}
