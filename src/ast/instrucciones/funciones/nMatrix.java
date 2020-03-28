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
public class nMatrix implements Expresion{
    private LinkedList<NodoAST> valor;
    private int linea;
    private int col;
    private String id;
    public nMatrix(String id,LinkedList<NodoAST> valor, int linea, int col) {
        this.valor = valor;
        this.id = id;
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
            if (o instanceof Object[][]) {
                Object[][] re = (Object[][]) o;
                if (id.equalsIgnoreCase("nCol")) {
                    return re[0].length;
                }else{
                    return re.length;   
                }
            }
            else{
                Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(), "Solo Acepta matrices en Expresion"));

            }
            return exp.getTipo(ent).getTipoPrimitivo();
        } catch (Exception e) {
            Ventana.ggetVentana().listaError.add(new JError("Ejecucion", linea(), columna(), "Error en Clase nMatrix: "+e.getMessage()));
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
        builder.append(nodo).append(" [label=\"nMatrix\"];\n");
        builder.append(parent).append(" -> ").append(nodo).append(";\n");

        Expresion exp = (Expresion) valor.get(0);
        return ""+exp.getNombre(builder, nodo, cont);
    }
    
}
