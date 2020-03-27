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
public class GenerarList implements Expresion{
    private LinkedList<NodoAST> listaExp;
    private int linea;
    private int col;
    private ListVar ArrayR; 
    public GenerarList(LinkedList<NodoAST> listaExp, int linea, int col) {
        this.listaExp = listaExp;
        this.linea = linea;
        this.col = col;
        this.ArrayR = null;
    }    
   
    @Override
    public Tipo getTipo(Entorno ent) {
//        Tipo []tp = new Tipo[listaExp.size()];
//        int c = 0;
//        for (NodoAST nodo : listaExp) 
//        {
//            if (nodo instanceof Expresion)
//            {
//                Expresion a = (Expresion)nodo;
//                Tipo ti = a.getTipo(ent);
//                tp[c] = ti;
//                c++;
//            }                        
//        }
//        return tipoDominante(tp);
        return new Tipo(Tipo.Tipos.LIST);
    }

    @Override
    public Object getValorImplicito(Entorno ent) {
        ListVar Array = new ListVar();
        for (NodoAST nodo : listaExp) {
            if (nodo instanceof Expresion) 
            {
                Expresion a = (Expresion)nodo;
                Object valor = a.getValorImplicito(ent);
//                if (valor instanceof Object[]) 
//                {
//                    Object [] aa = (Object[]) valor;
//                    for (Object ob : aa) {
//                        Array.add(ob);
//                    }
//                }
//                else
//                {
                    Array.add(valor);
//                }                   
            }
        }
        this.ArrayR = Array;
        return Array;        
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
        builder.append(nodo).append(" [label=\"List\"];\n");
        builder.append(parent).append(" -> ").append(nodo).append(";\n");

        String nodoOp1 = "nodo" + ++cont;
        builder.append(nodoOp1).append(" [label=\""+ Arrays.toString(this.ArrayR.toArray()) + "\"];\n");
        builder.append(nodo).append(" -> ").append(nodoOp1).append(";\n");

        return ""+ cont;
    }
    
    private Tipo tipoDominante(Tipo[] tp) {
        for (Tipo tipo : tp) {
            if (tipo.isString()) {
                return new Tipo(Tipo.Tipos.STRING);
            }
        }
        for (Tipo tipo : tp) {
            if (tipo.isDouble()) {
                return new Tipo(Tipo.Tipos.DOUBLE);
            }
        }        
        for (Tipo tipo : tp) {
            if (tipo.isInt()) {
                return new Tipo(Tipo.Tipos.INT);
            }
        }
        for (Tipo tipo : tp) {
            if (tipo.isBoolean()) {
                return new Tipo(Tipo.Tipos.BOOL);
            }
        }
        return null;
    }

    private ListVar gen(int i, Entorno ent, LinkedList<Expresion> listaDimensiones, ListVar nuevo) {
        int c = (int) listaDimensiones.get(i).getValorImplicito(ent);
        if (i == 0) 
        {
            ListVar aux = new ListVar();
            for (int j = 0; j < c; j++) 
            {
                aux.add(nuevo);
            }
            return aux;
        }
        else
        {
            ListVar aux = new ListVar();
            for (int j = 0; j < c; j++) 
            {
                aux.add(nuevo);
            }
            i--;
            return gen(i,ent,listaDimensiones,aux);
        }
    }
}
