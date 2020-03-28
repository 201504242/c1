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
public class Fstring implements Expresion{
    private LinkedList<NodoAST> valor;
    private int linea;
    private int col;
    private int tipo;//StringLength = 1 | remove = 2 | toLowerCase = 3 | toUpperCase = 4

    public Fstring(int tipo,LinkedList<NodoAST> valor, int linea, int col) {
        this.valor = valor;
        this.linea = linea;
        this.col = col;
        this.tipo = tipo;
    }
    
    
    @Override
    public Tipo getTipo(Entorno ent) {
        switch(tipo){
            case 1://StringLength
                return new Tipo(Tipo.Tipos.INT);
            case 2://remove
                return new Tipo(Tipo.Tipos.STRING);
            case 3://toLowerCase
                return new Tipo(Tipo.Tipos.STRING);
            case 4://toUpperCase
                return new Tipo(Tipo.Tipos.STRING);
        }
        return null;
    }

    @Override
    public Object getValorImplicito(Entorno ent) {
         try{
            Expresion exp = (Expresion) valor.get(0);
            Object o = exp.getValorImplicito(ent);
            switch(tipo){
                case 1://StringLength
                    if (o instanceof Object[]) {
                        Object[] cad = (Object[]) o;
                        if (cad.length == 1) {
                            String val = String.valueOf(cad[0]);                
                            return val.length();
                        }else{
                            Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(), 
                                    "Vector tiene mas de 1 dato"));
                            return null;
                        }
                    }
                    String val = String.valueOf(o);                
                    return val.length();
                case 2://remove
                    val = "";
                    String val2 ="";
                    //Primer Dato
                    if (o instanceof Object[]) {
                        Object[] cad = (Object[]) o;
                        if (cad.length == 1) {
                            val = String.valueOf(cad[0]);                
                        }else{
                            Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(), 
                                    "Vector 1 tiene mas de 1 dato"));
                            return null;
                        }
                    }
                    else{
                        val = String.valueOf(o);                
                    }
                    //Segundo dato
                    Object o2 = ((Expresion)valor.get(1)).getValorImplicito(ent);
                    if (o instanceof Object[]) {
                        Object[] cad = (Object[]) o2;
                        if (cad.length == 1) {
                            val2 = String.valueOf(cad[0]);                
                        }else{
                            Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(), 
                                    "Vector 2 tiene mas de 1 dato"));
                            return null;
                        }
                    }
                    else{
                        val2 = String.valueOf(o2);                
                    }
                    
                    for (char c : val2.toCharArray()) {
                        val = val.replaceAll(String.valueOf(c), "");
                    }                    
                    return val;
                case 3://toLowerCase
                    if (o instanceof Object[]) {
                        Object[] cad = (Object[]) o;
                        if (cad.length == 1) {
                            val = String.valueOf(cad[0]).toLowerCase();                
                            return val;
                        }else{
                            Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(), 
                                    "Vector tiene mas de 1 dato"));
                            return null;
                        }
                    }
                    val = String.valueOf(o).toLowerCase();                
                    return val;
                case 4://toUpperCase
                    if (o instanceof Object[]) {
                        Object[] cad = (Object[]) o;
                        if (cad.length == 1) {
                            val = String.valueOf(cad[0]).toUpperCase();                
                            return val;
                        }else{
                            Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(), 
                                    "Vector tiene mas de 1 dato"));
                            return null;
                        }
                    }
                    val = String.valueOf(o).toUpperCase();  
                    return val;
            }
            return null;
        } catch (Exception e) {
            Ventana.ggetVentana().listaError.add(new JError("Ejecucion", linea(), columna(), "Error en Clase Fstring: "+e.getMessage()));
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
        switch(tipo){
            case 1:
                builder.append(nodo).append(" [label=\"StringLength\"];\n");
                break;
            case 2:
                builder.append(nodo).append(" [label=\"remove\"];\n");
                break;
            case 3:
                builder.append(nodo).append(" [label=\"toLowerCase\"];\n");
                break;
            case 4:
                builder.append(nodo).append(" [label=\"toUpperCase\"];\n");
                break;
        }
        builder.append(parent).append(" -> ").append(nodo).append(";\n");

        Expresion exp = (Expresion) valor.get(0);
        return ""+exp.getNombre(builder, nodo, cont);
    }
    
}
