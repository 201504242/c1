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
public class Fnum implements Expresion{
    private LinkedList<NodoAST> valor;
    private int linea;
    private int col;
    private int tipo;//Trunk = 1 | Round = 2 | Mean, = 3 | Median = 4 | Mode=5

    public Fnum(int tipo,LinkedList<NodoAST> valor, int linea, int col) {
        this.valor = valor;
        this.linea = linea;
        this.col = col;
        this.tipo = tipo;
    }

    
    
    @Override
    public Tipo getTipo(Entorno ent) {
        switch(tipo){
            case 1://Trunk
                return new Tipo(Tipo.Tipos.INT);
            case 2://Round
                return new Tipo(Tipo.Tipos.INT);
            case 3://Mean
            case 4://Median
            case 5://Mode
                return new Tipo(Tipo.Tipos.DOUBLE);
        }
        return null;
    }

    @Override
    public Object getValorImplicito(Entorno ent) {
        try{
            Expresion exp = (Expresion) valor.get(0);
            Object o = exp.getValorImplicito(ent);
            switch(tipo){
                case 1://Trunk
                    if (o instanceof Object[]) {
                        Object[] cad = (Object[]) o;
                        if (cad.length == 1) {
                            String[] val = String.valueOf(cad[0]).split("\\.");                
                            return val[0];
                        }else{
                            Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(), 
                                    "Vector tiene mas de 1 dato en Trunk"));
                            return null;
                        }
                    }
                    String[] val = String.valueOf(o).split("\\.");
                    return Integer.parseInt(val[0]);
                case 2://Round
                    String dato = "";
                    if (o instanceof Object[]) {
                        Object[] cad = (Object[]) o;
                        if (cad.length == 1) {
                            dato = String.valueOf(cad[0]);                
                        }else{
                            Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(), 
                                    "Vector 1 tiene mas de 1 dato"));
                            return null;
                        }
                    }
                    else{
                        dato = String.valueOf(o);                
                    }                    
                    return Math.round(new Double(dato));
                case 3://mean
                    //sin trim
                    int trim = 0;
                    double sum = 0;
                    if (valor.size() > 1) {
                        trim = (int)((Expresion) valor.get(1)).getValorImplicito(ent);
                    }
                    if (o instanceof Object[]) {
                        Object[] cad = (Object[]) o;  
                        int contador = 0;
                        for (int i = 0; i < cad.length; i++) {
                            double valor = new Double(cad[i].toString());
                            if (trim == 0) {
                                sum += valor;
                                contador++;
                            }else if(trim < valor){
                                sum += valor;
                                contador++;
                            }
                        }
                        return sum/contador;
                    }
                    return null;
////                    val = String.valueOf(o).toLowerCase();                
//                    return val;
                case 4://Median
                    if (o instanceof Object[]) {
                        Object[] cad = (Object[]) o;
                        if (cad.length == 1) {
//                            val = String.valueOf(cad[0]).toUpperCase();                
//                            return val;
                        }else{
                            Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(), 
                                    "Vector tiene mas de 1 dato"));
                            return null;
                        }
                    }
////                    val = String.valueOf(o).toUpperCase();  
//                    return val;
                case 5://Mode
                        break;
            }
            return null;
        } catch (Exception e) {
            Ventana.ggetVentana().listaError.add(new JError("Ejecucion", linea(), columna(), "Error en Clase Fnum: "+e.getMessage()));
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
        switch(tipo){//Trunk = 1 | Round = 2 | Mean, = 3 | Median = 4 | Mode=5
            case 1:
                builder.append(nodo).append(" [label=\"Trunk\"];\n");
                break;
            case 2:
                builder.append(nodo).append(" [label=\"Round\"];\n");
                break;
            case 3:
                builder.append(nodo).append(" [label=\"Mean\"];\n");
                break;
            case 4:
                builder.append(nodo).append(" [label=\"Median\"];\n");
                break;
             case 5:
                builder.append(nodo).append(" [label=\"Mode\"];\n");
                break;
        }
        builder.append(parent).append(" -> ").append(nodo).append(";\n");

        Expresion exp = (Expresion) valor.get(0);
        return ""+exp.getNombre(builder, nodo, cont);
    }
    
}
