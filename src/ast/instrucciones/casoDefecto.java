/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast.instrucciones;

import ast.Expresion;
import ast.Instruccion;
import ast.NodoAST;
import entorno.Entorno;
import java.util.LinkedList;

/**
 *
 * @author p_ab1
 */
public class casoDefecto implements Instruccion{
    private LinkedList<NodoAST> instrucciones;
    private int linea;
    private int col;

    public casoDefecto(LinkedList<NodoAST> instrucciones, int linea, int col) {
        this.instrucciones = instrucciones;
        this.linea = linea;
        this.col = col;
    }
    
    
    @Override
    public Object ejecutar(Entorno ent) {
        eti:
        for (NodoAST ins : instrucciones) {
            if (ins instanceof Instruccion) {
                Instruccion i = (Instruccion) ins;
                Object result = i.ejecutar(ent);
                if (ins instanceof Break || result instanceof Break)
                {
                    return result;
                }
                if (ins instanceof Continue || result instanceof Continue) 
                {
                    continue eti;
                }

                if (result != null)
                {
                    return result;
                } 
            }else if(ins instanceof Expresion){
                Expresion e = (Expresion) ins;
                e.getValorImplicito(ent);
            }
        }
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
        builder.append(nodo).append(" [label=\"-default\"];\n");
        builder.append(parent).append(" -> ").append(nodo).append(";\n");
        
        return ""+cont;
    }
    
}
