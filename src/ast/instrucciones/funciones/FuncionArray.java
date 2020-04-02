/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast.instrucciones.funciones;

import ast.Expresion;
import ast.NodoAST;
import entorno.Entorno;
import entorno.Tipo;
import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author p_ab1
 */
public class FuncionArray implements Expresion{
    private LinkedList<NodoAST> listaExp;
    private int linea;
    private int col;

    public FuncionArray(LinkedList<NodoAST> listaExp, int linea, int col) {
        this.listaExp = listaExp;
        this.linea = linea;
        this.col = col;
    }
    
    
    @Override
    public Tipo getTipo(Entorno ent) {
        return new Tipo(Tipo.Tipos.ARRAY);
    }

    @Override
    public Object getValorImplicito(Entorno ent) {
        if (listaExp.size() == 2)
        {
            Object dato = listaExp.get(0);
            Object dimensiones = listaExp.get(1);
            
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
        builder.append(nodo).append(" [label=\"Array\"];\n");
        builder.append(parent).append(" -> ").append(nodo).append(";\n");

        return ""+ cont;
    }
    
}
