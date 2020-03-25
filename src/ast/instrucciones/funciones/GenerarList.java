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

/**
 *
 * @author p_ab1
 */
public class GenerarList implements Expresion{
    private LinkedList<NodoAST> listaExp;
    private int linea;
    private int col;

    public GenerarList(LinkedList<NodoAST> listaExp, int linea, int col) {
        this.listaExp = listaExp;
        this.linea = linea;
        this.col = col;
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
    public String getNombre() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
