/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast.instrucciones.funciones;

import entorno.Entorno;
import entorno.Simbolo;
import entorno.nodoExp;

/**
 *
 * @author hp
 */
  public  class ModificadorMatrix{
       
        private nodoExp e;
        private Object[][] mat;
        private Entorno ent ;
        private Simbolo sim ;
        
        public ModificadorMatrix(nodoExp e, Object[][] mat, Entorno ent, Simbolo sim) {
            this.e = e;
            this.mat = mat;
            this.ent = ent;
            this.sim = sim;
        }

    public void cambiarValor(Object valorNuevo) {
     // Acceso a la matriz 
     try{
     
    if(valorNuevo instanceof Object[])
        switch(e.getTipoAcceso()) // Modificar si es un vector lo que se tiene que asignar 
        {
            case m1:
                   System.err.println("Error no se puede asignar un vector a una casilla");
                   break;
            case m2:
                    Object[] vector = (Object[])valorNuevo;
                    int val1_m2 =  (Integer)e.getExp1().getValorImplicito(ent)-1;
                    for(int i= 0 ; i < mat[val1_m2].length ; i++)
                    {
                         mat[val1_m2][i] = vector[i%vector.length];
                    }
                    break;
            case m3:
                    Object[] vectorN = (Object[])valorNuevo;
                    int val1_m3 =  (Integer)e.getExp1().getValorImplicito(ent)-1;
                    Object[] result_m3 = new Object[mat.length];
                    for(int i=0 ; i < mat.length ;i++)
                    {
                        mat[i][val1_m3] = vectorN[i%vectorN.length];
                    }
                    break;
        }
     else
        switch(e.getTipoAcceso())
        {
            case m1:
                   int val1 =  (Integer)e.getExp1().getValorImplicito(ent) -1;
                   int val2 = (Integer)e.getExp2().getValorImplicito(ent) -1;
                   mat[val1][val2] = valorNuevo;
                   break;
            case m2:
                    int val1_m2 =  (Integer)e.getExp1().getValorImplicito(ent)-1;
                    for(int i= 0 ; i < mat[val1_m2].length ; i++){
                         mat[val1_m2][i] = valorNuevo;
                    }
                    break;
            case m3:
                    int val1_m3 =  (Integer)e.getExp1().getValorImplicito(ent)-1;
                    Object[] result_m3 = new Object[mat.length];
                    for(int i=0 ; i < mat.length ;i++)
                    {
                        mat[i][val1_m3] = valorNuevo;
                    }
                    break;
        }
    }
     catch(Exception error)
        {
            System.err.println("Error en indices modificacion matriz");
            System.err.println(error.getMessage());
        }
    }
    
        
        
        
        
        
   }

