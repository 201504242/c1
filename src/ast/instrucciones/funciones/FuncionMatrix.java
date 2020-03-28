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
import entorno.nodoExp;
import java.util.LinkedList;
import olc2.p1_201504242.JError;
import olc2.p1_201504242.Ventana;

/**
 *
 * @author hp
 */
public class FuncionMatrix implements Expresion{
    private LinkedList<NodoAST> listaExp;
    private int linea;
    private int col;

    public FuncionMatrix(LinkedList<NodoAST> listaExp, int linea, int col) {
        this.listaExp = listaExp;
        this.linea = linea;
        this.col = col;
    }    
   
    @Override
    public Tipo getTipo(Entorno ent) {
        Tipo []tp = new Tipo[1];
        int c = 0;
        NodoAST arreglo = listaExp.get(0);
        if (arreglo instanceof Expresion)
        {
            Expresion a = (Expresion)arreglo;
            Tipo ti = a.getTipo(ent);
            tp[c] = ti;
            c++;
        }
        return tipoDominante(tp);
    }

    @Override
    public Object getValorImplicito(Entorno ent) {
        if(listaExp.size() >= 3)
        {
            NodoAST nodo_row = listaExp.get(1);
            NodoAST nodo_col = listaExp.get(2);
            NodoAST nodo_data = listaExp.get(0);
            try{

                int nrow = (nodo_row instanceof Expresion) ? (Integer)((Expresion)nodo_row).getValorImplicito(ent): 0;
                int ncol = (nodo_col instanceof Expresion) ? (Integer)((Expresion)nodo_col).getValorImplicito(ent): 0;
                Object crearData = (nodo_data instanceof Expresion) ? ((Expresion)nodo_data).getValorImplicito(ent): null;
                Object[][] matrizNueva = new Object[nrow][ncol];
                if(crearData!=null)
                    if(crearData instanceof Object[])
                    {
                        Object[] vec = (Object[])crearData; 
                        for(int i = 0 ; i < ncol ; i++)
                        {
                            for(int y=0 ; y < nrow ; y++)
                            {
                                matrizNueva[y][i] = vec[y%vec.length] ;
                            }
                        }
                    }else{
                         for(int i = 0 ; i < ncol ; i++)
                        {
                            for(int y=0 ; y < nrow ; y++)
                            {
                                matrizNueva[y][i] = crearData ;
                            }
                        }
                    }
                return matrizNueva;
                
            }
            catch(Exception e)
            {
                Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(),
                        "Error creando la matriz "));
                //System.err.println("Error escribiendo la matriz");
            }
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

    public static Object  getValoresMatriz(nodoExp e ,Object[][] mat, Entorno ent)
    {
        // Acceso a la matriz 
        switch(e.getTipoAcceso())
        {
            case m1:
                   int val1 =  (Integer)e.getExp1().getValorImplicito(ent) -1;
                   int val2 = (Integer)e.getExp2().getValorImplicito(ent) -1;
                   return mat[val1][val2];
            case m2:
                    int val1_m2 =  (Integer)e.getExp1().getValorImplicito(ent)-1;
                    Object[] result = new Object[mat[val1_m2].length];
                    for(int i= 0 ; i < result.length ; i++){
                        result[i] = mat[val1_m2][i];
                    }
                    return result;
            case m3:
                    int val1_m3 =  (Integer)e.getExp1().getValorImplicito(ent)-1;
                    Object[] result_m3 = new Object[mat.length];
                    for(int i=0 ; i < result_m3.length ;i++)
                    {
                        result_m3[i] = mat[i][val1_m3];
                    }
                    return result_m3;
            //[<Expresión>]
            case tipo1:
                int val1_tipo1 =  (Integer)e.getExp1().getValorImplicito(ent)-1;
                Object resit_tipo1 = new Object();
                int pos = 0;
                for (int i = 0; i < mat[0].length; i++){		// El primer índice recorre las columnas.
                    for (int j = 0; j < mat.length; j++){	// El segundo índice recorre las filas.
                            // Procesamos cada elemento de la matriz.
                            if(pos == val1_tipo1){
                                resit_tipo1 = mat[j][i];
                                return resit_tipo1;
                            }
                        pos++;
                    }
                }
                return resit_tipo1;
                    
        }
           return null;
    }

    @Override
    public String getNombre(StringBuilder builder, String parent, int cont) {
        String nodo = "nodo" + ++cont;
        builder.append(nodo).append(" [label=\"Matrix\"];\n");
        builder.append(parent).append(" -> ").append(nodo).append(";\n");

        return ""+cont;
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
   
}
 