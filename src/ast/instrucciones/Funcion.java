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
import entorno.Simbolo;
import java.util.LinkedList;

/**
 *
 * @author p_ab1
 */
public class Funcion extends Simbolo implements Instruccion{
    private LinkedList<Simbolo> parametrosFormales;
    private LinkedList<NodoAST> sentencias;
    private int linea;
    private int col;

    //constructor para funciones con parametros
    public Funcion(String id,Rol rol,LinkedList<Simbolo> parametrosFormales, LinkedList<NodoAST> sentencias,int linea,int col) {
        super(id,rol);
        this.parametrosFormales = parametrosFormales;
        this.sentencias = sentencias;
        this.linea = linea;
        this.col = col;
    }

    //constructor para funciones sin parametros
    public Funcion(String id, Rol rol, LinkedList<NodoAST> sentencias, int linea, int col) {
        super(id,rol);
        this.sentencias = sentencias;
        this.linea = linea;
        this.col = col;
        this.parametrosFormales = new LinkedList();
    }

    
    
    @Override
    public Object ejecutar(Entorno ent) {
        for (NodoAST s : sentencias) {
            if (s instanceof Instruccion)
            {
                Instruccion instru = (Instruccion) s;
                Object result = instru.ejecutar(ent);
                 if (result != null)
                {
                    return result;
                }
            }
            else if (s instanceof Expresion)
            {
                Object result = ((Expresion)s).getValorImplicito(ent);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    @Override
    public int linea() {
        return this.linea;
    }

    public LinkedList<Simbolo> getParametrosFormales() {
        return parametrosFormales;
    }

    public void setParametrosFormales(LinkedList<Simbolo> parametrosFormales) {
        this.parametrosFormales = parametrosFormales;
    }

    public LinkedList<NodoAST> getSentencias() {
        return sentencias;
    }

    public void setSentencias(LinkedList<NodoAST> sentencias) {
        this.sentencias = sentencias;
    }

    @Override
    public int columna() {
        return this.col;
    }

    @Override
    public String getNombre(StringBuilder builder, String parent, int cont) {
        String nodo = "nodo" + ++cont;
        builder.append(nodo).append(" [label=\"FUNCION\"];\n");
        builder.append(parent).append(" -> ").append(nodo).append(";\n");
        
        String nodoIf = "nodo" + ++cont;
        builder.append(nodoIf).append(" [label=\""+getIdentificador()+"\"];\n");
        builder.append(nodo).append(" -> ").append(nodoIf).append(";\n");
        
        String nodoP = "nodo" + ++cont;
        builder.append(nodoP).append(" [label=\"Parametros: "+getParametrosFormales().size()+"\"];\n");
        builder.append(nodo).append(" -> ").append(nodoP).append(";\n");
        
        for (NodoAST instr : sentencias) {
            cont = Integer.parseInt(instr.getNombre(builder, nodoIf, cont));
        }
        
        return String.valueOf(cont);
    }

       
    
    
}
