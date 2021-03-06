/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast.instrucciones;

import ast.Expresion;
import ast.Instruccion;
import ast.NodoAST;
import ast.expresiones.Identificador;
import ast.instrucciones.funciones.ListVar;
import entorno.Entorno;
import entorno.Simbolo;
import java.util.LinkedList;
import olc2.p1_201504242.JError;
import olc2.p1_201504242.Ventana;

/**
 *
 * @author p_ab1
 */
public class For extends Condicion implements Instruccion{
    private int linea;
    private int col;
    private Identificador id;
    
    public For(Identificador id, LinkedList<NodoAST> bloque, Expresion cond,int linea,int col ) {
        super(bloque, cond);
        this.col = col;
        this.linea = linea;
        this.id = id;
    }    
    
    @Override
    public Object ejecutar(Entorno ent) {
        Entorno local = new Entorno(ent);
        try {
            //inicializacion existe
            if (ent.existe(id.getVal())) 
            {
                Simbolo sim = ent.get(id.getVal());
                
            }
            else
            {
                local.agregar(id.getVal(), new Simbolo(id.getVal(), Simbolo.Rol.VECTOR));
            }
            Expresion condicion = getCond();
            Object ob = condicion.getValorImplicito(local);
            if (ob instanceof ListVar) 
            {
                ListVar lista = (ListVar)ob;
                for (Object c : lista) {
                    Entorno entFor = new Entorno(local); 
                    String variable = id.getVal();
                    Simbolo s = entFor.get(variable);
                    s.setValor(c);
                    entFor.agregar(variable, s);
                    for (NodoAST in : getIns()) 
                    {
                        Object result = ((Instruccion)in).ejecutar(entFor);
                        if (in instanceof Break) {
                            return new Break(in.linea(),in.columna());
                        }
                        if (in instanceof Continue || result instanceof Continue) 
                        {
                            break;
                        }
                        if (result != null)
                        {
                            return result;
                        }
                    }
                }
            }
            else if (ob instanceof Object []) 
            {
                Object [] vector = (Object[]) ob;
                for (Object c : vector) 
                {
                    Entorno entFor = new Entorno(local); 
                    String variable = id.getVal();
                    Simbolo s = entFor.get(variable);
                    s.setValor(c);
                    entFor.agregar(variable, s);
                    for (NodoAST in : getIns()) 
                    {
                        Object result = ((Instruccion)in).ejecutar(entFor);
                        if (in instanceof Break) {
                            return new Break(in.linea(),in.columna());
                        }
                        if (in instanceof Continue || result instanceof Continue) 
                        {
                            break;
                        }
                        if (result != null)
                        {
                            return result;
                        }
                    }
                }
            }
            else
            {
                Entorno entFor = new Entorno(local); 
                String variable = id.getVal();
                Simbolo s = entFor.get(variable);
                s.setValor(ob);
                entFor.agregar(variable, s);
                for (NodoAST in : getIns()) 
                {
                    Object result = ((Instruccion)in).ejecutar(entFor);
                    if (in instanceof Break) {
                        return new Break(in.linea(),in.columna());
                    }
                    if (in instanceof Continue || result instanceof Continue) 
                    {
                        break;
                    }
                    if (result != null)
                    {
                        return result;
                    }
                }
            }
            
            
        } catch (Exception e) {
            Ventana.ggetVentana().listaError.add(new JError("Ejecucion", linea(), columna(), "error en Clase For "+e.getMessage()));
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
        builder.append(nodo).append(" [label=\"For\"];\n");
        builder.append(parent).append(" -> ").append(nodo).append(";\n");
        
        for (NodoAST instr : getIns()) {
            cont = Integer.parseInt(instr.getNombre(builder, nodo, cont));
        }
        
        return ""+cont;
    }
    
}
