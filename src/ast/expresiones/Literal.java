/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast.expresiones;

import ast.Expresion;
import entorno.Entorno;
import entorno.Tipo;

/**
 *
 * @author p_ab1
 */
public class Literal implements Expresion{
    private Object valor;
    private Tipo tipo;
    private int linea;
    private int col;

    public Literal(Object valor, Tipo tipo, int linea, int col) {
        this.valor = valor;
        this.tipo = tipo;
        this.linea = linea;
        this.col = col;
    }
    
    
    @Override
    public Tipo getTipo(Entorno ent) {
        return this.tipo;
    }

    @Override
    public Object getValorImplicito(Entorno ent) {
        return this.valor;
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
        builder.append(nodo).append(" [label=\"Literal\"];\n");
        builder.append(parent).append(" -> ").append(nodo).append(";\n");

        String nodoOp1 = "nodo" + ++cont;
        builder.append(nodoOp1).append(" [label=\""+ valor + "\"];\n");
        builder.append(nodo).append(" -> ").append(nodoOp1).append(";\n");

        return ""+cont;
    }
    
}
