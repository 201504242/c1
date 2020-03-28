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
import java.util.LinkedList;
import olc2.p1_201504242.JError;
import olc2.p1_201504242.Ventana;

/**
 *
 * @author p_ab1
 */
public class Length implements Expresion{
    private LinkedList<NodoAST> valor;
    private int linea;
    private int col;

    public Length(LinkedList<NodoAST> valor, int linea, int col) {
        this.valor = valor;
        this.linea = linea;
        this.col = col;
    }
    
    
    @Override
    public Tipo getTipo(Entorno ent) {
        return new Tipo(Tipo.Tipos.INT);
    }

    @Override
    public Object getValorImplicito(Entorno ent) {
        try{
            Expresion exp = (Expresion) valor.get(0);
            Object o = exp.getValorImplicito(ent);
            if (o instanceof ListVar) {
                return ((ListVar) o).size();
            }else if (o instanceof Object[][]) {
                return ((Object[][]) o).length;
            }else if (o instanceof Object[]) {
                return ((Object[]) o).length;
            }else if (o instanceof Object) {
                String val = String.valueOf(o);                
                return val.length();
            }
            
        } catch (Exception e) {
            Ventana.ggetVentana().listaError.add(new JError("Ejecucion", linea(), columna(), "Error en Clase Lenght: "+e.getMessage()));
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
        builder.append(nodo).append(" [label=\"Length\"];\n");
        builder.append(parent).append(" -> ").append(nodo).append(";\n");

        Expresion exp = (Expresion) valor.get(0);
        return ""+exp.getNombre(builder, nodo, cont);
    }
    
}
