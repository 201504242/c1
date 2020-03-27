/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast.expresiones.operaciones;

import ast.Expresion;
import entorno.Entorno;
import entorno.Tipo;

/**
 *
 * @author p_ab1
 */
public class Ternaria implements Expresion{

    private Expresion ex1;
    private Expresion ex2;
    private Expresion ex3;
    private int linea;
    private int col;

    public Ternaria(Expresion ex1, Expresion ex2, Expresion ex3, int linea, int col) {
        this.ex1 = ex1;
        this.ex2 = ex2;
        this.ex3 = ex3;
        this.linea = linea;
        this.col = col;
    }
    
    @Override
    public Tipo getTipo(Entorno ent) {
        if (ex1.getTipo(ent).isBoolean()) {
            Object r1 = ex1.getValorImplicito(ent);
            if ((boolean)r1)
            {
                return ex2.getTipo(ent);
            }
            return ex3.getTipo(ent);
        }
        return null;
    }

    @Override
    public Object getValorImplicito(Entorno ent) {
        Object r1 = ex1.getValorImplicito(ent);
        Tipo t1 = ex1.getTipo(ent);
        if (r1 != null && t1.isBoolean()) 
        {               
            if ((boolean)r1) 
            {
                return ex2.getValorImplicito(ent);
            }
            else
            {
                return ex3.getValorImplicito(ent);
            }
        }
        else{
            System.out.println("No puede ejecutar valores nulos en operacion ternaria 1");
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
        builder.append(nodo).append(" [label=\"Ternaria\"];\n");
        builder.append(parent).append(" -> ").append(nodo).append(";\n");

        cont = Integer.parseInt(ex1.getNombre(builder, nodo, cont));

        String nodoOp = "nodo" + ++cont;
        builder.append(nodoOp).append(" [label=\"" + "?" + "\"];\n");
        builder.append(nodo).append(" -> ").append(nodoOp).append(";\n");

        cont = Integer.parseInt(ex2.getNombre(builder, nodo, cont));
        
        String nodotres = "nodo" + ++cont;
        builder.append(nodotres).append(" [label=\"" + ":" + "\"];\n");
        builder.append(nodo).append(" -> ").append(nodotres).append(";\n");

        cont = Integer.parseInt(ex3.getNombre(builder, nodo, cont));
        
        return ""+cont;
    }
    
}
