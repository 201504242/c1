/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast.instrucciones;

import ast.Expresion;
import ast.Instruccion;
import ast.instrucciones.funciones.ListVar;
import entorno.Entorno;
import entorno.Simbolo;
import entorno.Tipo;
import olc2.p1_201504242.JError;
import olc2.p1_201504242.Ventana;

/**
 *
 * @author p_ab1
 */
public class Asignacion implements Instruccion{
    //private Var variable;
    private Expresion variable;
    private Expresion der;
    private int linea;
    private int col;    

    //id = E
//    public Asignacion(Var var, Expresion der, int linea, int col) {
//        this.variable = var;
//        this.der = der;
//        this.linea = linea;
//        this.col = col;
//    }
//    

    public Asignacion(Expresion variable, Expresion der, int linea, int col) {
        this.variable = variable;
        this.der = der;
        this.linea = linea;
        this.col = col;
    }
    
    
    @Override
    public Object ejecutar(Entorno ent) {
        try
        {
            Simbolo SimboloViejo = (Simbolo)variable.getValorImplicito(ent);
            Object valorNuevo = der.getValorImplicito(ent);
            //asigno tipo o lista
            if (valorNuevo instanceof ListVar) {
                SimboloViejo.setRol(Simbolo.Rol.LISTA);
                SimboloViejo.setTipo(new Tipo(Tipo.Tipos.LIST));
            }
            else{
                SimboloViejo.setRol(Simbolo.Rol.VECTOR);
                Tipo tipoNuevo = der.getTipo(ent);
                SimboloViejo.setTipo(tipoNuevo);
            }
            
            if (SimboloViejo.lugar == 0) {                
                SimboloViejo.setValor(valorNuevo);  
            }
            else{
                Object [] prueba = (Object[])SimboloViejo.getValor();
                if (valorNuevo instanceof Object[]) {
                    Object [] p = (Object[]) valorNuevo;
                    if (p.length !=1 ) {
                        Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(),
                        "Tratando de asignar más de un valor a una posición"));
                        return null;
                    }
                    valorNuevo = p[0];
                }
                prueba[SimboloViejo.lugar-1] = valorNuevo;                
                SimboloViejo.setValor(prueba);  
            }
            SimboloViejo.lugar = 0;
            
            
        }
        catch(Exception e)
        {
            System.err.println("Semantico Fatal"+ linea()+" "+columna()+
                    " Error en asignacion casteo en Asignacion " + e.getMessage());
            Ventana.ggetVentana().listaError.add(new JError("Semantico Fatal", linea(), columna(),
            //System.err.println(
                   "Error en asignacion casteo en Asignacion " + e.getMessage()));
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
    
}