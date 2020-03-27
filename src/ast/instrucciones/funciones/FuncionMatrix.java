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
        return new Tipo(Tipo.Tipos.MATRIZ);
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
                System.err.println("Error escribiendo la matriz");
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

    @Override
    public String getNombre() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        }
           return null;
    }
    

   
}
 