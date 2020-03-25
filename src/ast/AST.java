/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

import ast.expresiones.Llama;
import ast.instrucciones.Funcion;
import entorno.Entorno;
import java.util.LinkedList;
import olc2.p1_201504242.JError;
import olc2.p1_201504242.Ventana;

/**
 *
 * @author p_ab1
 */
public class AST {
    private LinkedList<NodoAST> instrucciones;
    private Entorno Global = new Entorno(null);

    public AST(LinkedList<NodoAST> instrucciones) {
        this.instrucciones = instrucciones;
    }

    public Entorno getGlobal() {
        return Global;
    }    
    
    public Object ejecutar(){
        
        
        for (NodoAST nodo : instrucciones) {
            //codigo para llenar tabla de simbolos con funciones
            if (nodo instanceof Funcion) 
            {
                Funcion instacia = (Funcion)nodo;
                if (Global.existeFuncion(instacia.getIdentificador())) {
                    System.err.println("La funcion: "+instacia.getIdentificador()+" ya existe"+
                            " Parametros: "+instacia.getParametrosFormales().size());
                    Ventana.ggetVentana().listaError.add(new JError("Semantico", instacia.linea(), instacia.columna(),
                "La funcion: "+instacia.getIdentificador()+" ya existe"+
                            " Parametros: "+instacia.getParametrosFormales().size())); 
                }
                else{
                    Global.agregarFuncion(instacia.getIdentificador(),instacia);                
                }
            }
        }
        //ejecutar todas las instrucciones menos las funciones ya que fueron agregadas
        for (NodoAST nodo : instrucciones) {
            try {
                
            } catch (Exception e) {
            }
            if (nodo instanceof Instruccion && !(nodo instanceof Funcion)) {
                Instruccion ins = (Instruccion)nodo;
                ins.ejecutar(Global);
            }
            else if (nodo instanceof Llama) {
                Expresion expe = (Expresion)nodo;
                expe.getValorImplicito(Global);
            }
        }
        Global.Mostrar();
        Global.MostrarFunciones();

        return null;
    }

    public LinkedList<NodoAST> getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(LinkedList<NodoAST> instrucciones) {
        this.instrucciones = instrucciones;
    }
    
}
