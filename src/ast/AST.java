/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

import ast.expresiones.Llama;
import ast.instrucciones.Break;
import ast.instrucciones.Continue;
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
            if (nodo instanceof Instruccion && !(nodo instanceof Funcion)) {
                Instruccion ins = (Instruccion)nodo;
                Object result = ins.ejecutar(Global);
                if (ins instanceof Break || result instanceof Break)
                {
                    Ventana.ggetVentana().listaError.add(new JError("Semantico", ((Break)ins).linea(), ((Break)ins).columna(),
                "BREAK en lugar inadecuado."));
                }                
                if (ins instanceof Continue || result instanceof Continue) 
                {
                    Ventana.ggetVentana().listaError.add(new JError("Semantico", ((Continue)ins).linea(), ((Continue)ins).columna(),
                "Continue en lugar inadecuado."));
                }                
            }
            else if (nodo instanceof Llama) {
                Expresion expe = (Expresion)nodo;
                expe.getValorImplicito(Global);
            }
        }
        //Global.Mostrar();
        //Global.MostrarFunciones();

        return null;
    }

    public LinkedList<NodoAST> getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(LinkedList<NodoAST> instrucciones) {
        this.instrucciones = instrucciones;
    }
    
    public String Graficar(){
        StringBuilder builder = new StringBuilder();
        int cont = 1;
        String root = "nodo" + cont;
        builder.append("digraph AST {\n");
        builder.append(root).append(" [label=\"AST_Raiz\"];\n");

        for (NodoAST instr : this.instrucciones) {
            String conString = instr.getNombre(builder, root, cont);
            if (conString != null) {
                cont = Integer.parseInt(conString);
            }
        }
        builder.append("}");
        
        return builder.toString();
        //System.out.println(builder.toString());
    }
    
}
