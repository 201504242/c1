/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast.instrucciones;

import ast.Expresion;
import ast.Instruccion;
import ast.expresiones.Return;
import entorno.Entorno;
import java.util.LinkedList;

/**
 *
 * @author p_ab1
 */
public class Switch implements Instruccion{
    private Expresion exp;
    private LinkedList<Instruccion> listCasos;
    private int linea;
    private int col;
    private Instruccion defecto = null;
    private Boolean EjecutarDefecto = true;
    
    public Switch(Expresion exp, LinkedList<Instruccion> listCasos, int linea, int col) {
        this.exp = exp;
        this.listCasos = listCasos;
        this.linea = linea;
        this.col = col;
    }
    
    
    
    @Override
    public Object ejecutar(Entorno ent) {
        Object o  = exp.getValorImplicito(ent);
        for (Instruccion l : listCasos) {
            if (l instanceof casoDefecto) 
            {
                this.defecto = l;
                break;
            }
        }
        for (Instruccion item : listCasos) 
        {
            if (item instanceof Caso) 
            {
                Object expCaso = ((Caso) item).getExpresion().getValorImplicito(ent);
                if (o.equals(expCaso))
                {
                    EjecutarDefecto = false;
                    Object ret = item.ejecutar(ent);
                    if (ret instanceof Return) {
                        return ret;
                    }
                }
            }
        }
        if (EjecutarDefecto && this.defecto != null) {
            Object ret = defecto.ejecutar(ent);
            if (ret instanceof Return) {
                return ret;
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
        builder.append(nodo).append(" [label=\"Switch\"];\n");
        builder.append(parent).append(" -> ").append(nodo).append(";\n");
        
        return ""+cont;
    }
    
}
