/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast.instrucciones.funciones;

import ast.Expresion;
import ast.Instruccion;

import entorno.Entorno;
import java.util.Arrays;
import olc2.p1_201504242.JError;
import olc2.p1_201504242.Ventana;

/**
 *
 * @author p_ab1
 */
public class Print implements Instruccion{

    private Expresion valor;
    private int linea;
    private int col;
    
    public Print(Expresion valor, int linea, int col) {
        this.valor = valor;
        this.linea = linea;
        this.col = col;
    }
        
    @Override
    public Object ejecutar(Entorno ent) {
        //Ventana pp = (Ventana)olc2.p1_201504242.OLC2P1_201504242.ven.ggetVentana();
        try {
            Object o = valor.getValorImplicito(ent);
            if (o instanceof ListVar) 
            {
                ListVar s = (ListVar)o;
                String cad = "";
                for (int i = 0; i < s.size(); i++) {
                    if (s.get(i) instanceof Object[]) {
                        cad = cad + Arrays.toString((Object[])s.get(i));
                    }
                    else{
                        cad = i ==0 ? ""+s.get(i) : cad + (","+s.get(i));
                    }
                    
                }
                //System.out.println("");
                Ventana.ggetVentana().agregarConsola("["+cad+"]");
                //pp.agregarConsola("["+cad+"]");
            }
            else if (o instanceof Object[]) {
                Object[] s = (Object[])o;
                
               // pp.agregarConsola(Arrays.toString(s));
                Ventana.ggetVentana().agregarConsola(Arrays.toString(s));
                //System.out.println(Arrays.toString(s));
            }
            else if (o != null) {
                //pp.agregarConsola(o.toString());
                Ventana.ggetVentana().agregarConsola(o.toString());
                
             //  System.out.println(o.toString());
            }else{
                Ventana.ggetVentana().agregarConsola("Null");
                
//                pp.agregarConsola("Null");
               // System.out.println("Null");
            }
        } catch (Exception e) {
            Ventana.ggetVentana().listaError.add(new JError("Ejecucion", linea(), columna(), "error en Clase print "+e.getMessage()));
           //System.err.println("error en Clase print "+e.getMessage());
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
        builder.append(nodo).append(" [label=\"Imprimir\"];\n");
        builder.append(parent).append(" -> ").append(nodo).append(";\n");

        //cont = valor.getNombre(builder, nodo, cont);
        return ""+valor.getNombre(builder, nodo, cont);
    }
    
}
