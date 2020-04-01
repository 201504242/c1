/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast.instrucciones.funciones;

import ast.Expresion;
import ast.Instruccion;
import ast.NodoAST;

import entorno.Entorno;
import entorno.Simbolo;
import entorno.Tipo;
import java.util.Arrays;
import java.util.LinkedList;
import olc2.p1_201504242.JError;
import olc2.p1_201504242.Ventana;

/**
 *
 * @author p_ab1
 */
public class Print implements Instruccion{

    private LinkedList<NodoAST> valor;
    private int linea;
    private int col;
    
    public Print(LinkedList<NodoAST>  valor, int linea, int col) {
        this.valor = valor;
        this.linea = linea;
        this.col = col;
    }
        
    @Override
    public Object ejecutar(Entorno ent) {
        //Ventana pp = (Ventana)olc2.p1_201504242.OLC2P1_201504242.ven.ggetVentana();
        try {
            Expresion exp = (Expresion) valor.get(0);
            Object o = exp.getValorImplicito(ent);
            if (o instanceof ListVar) 
            {
                ListVar s = (ListVar)o;
                String cad = "";
                for (int i = 0; i < s.size(); i++) {
                    if (s.get(i) instanceof Object[]) {
                        cad = cad + Arrays.toString((Object[])s.get(i)) +"\n";
                    }
                    else{
                        cad = i ==0 ? ""+s.get(i) : cad + (","+s.get(i));
                    }
                    
                }
                //System.out.println("");
                Ventana.ggetVentana().agregarConsola("["+cad+"]");
                //pp.agregarConsola("["+cad+"]");
            }
             //Matrix
            else if(o instanceof Object[][])
            {
                Object [][] m = (Object[][]) o;
                Ventana.ggetVentana().agregarConsola("--- x: "+m.length+" y:"+m[0].length+" -----");
                int cont = 1;
                for (Object[] row : m){
                    //System.out.println(Arrays.toString(row)); 
                    Ventana.ggetVentana().agregarConsola("["+cont+",] "+Arrays.toString(row));
                    cont++;
                } 
            }
            else if (o instanceof Object[]) {
                Object[] s = (Object[])o;
                
               // pp.agregarConsola(Arrays.toString(s));
                Ventana.ggetVentana().agregarConsola(Arrays.toString(s));
                System.out.println(Arrays.toString(s));
            }
           
            else if (o != null) {
                //pp.agregarConsola(o.toString());
                if (o instanceof Simbolo) {
                    if (((Simbolo) o).getValor().toString().equals("E1") ) {
                        Ventana.ggetVentana().listaError.add(new JError("Sintactico", linea(), columna(),"Dato: "+((Simbolo) o).getIdentificador()+" No Encontrada"));
                    }
                    
                }else{
                    Ventana.ggetVentana().agregarConsola(o.toString());
                }
               System.out.println(o.toString());
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

        Expresion exp = (Expresion) valor.get(0);
        return ""+exp.getNombre(builder, nodo, cont);
    }
    
    
}
