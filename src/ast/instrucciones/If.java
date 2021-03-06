/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast.instrucciones;

import ast.Expresion;
import ast.Instruccion;
import ast.NodoAST;
import ast.expresiones.Return;
import entorno.Entorno;
import java.util.LinkedList;

/**
 *
 * @author p_ab1
 */
public class If  extends Condicion implements Instruccion{

    private LinkedList<NodoAST> insElse;
    private Instruccion elseIf;
    private int linea;
    private int col;

    public If(LinkedList<NodoAST> ins, Expresion cond) {
        super(ins, cond);
    }
    
    public If(LinkedList<NodoAST> ins, Expresion cond,LinkedList<NodoAST>insElse) {
        super(ins, cond);
        this.insElse = insElse;
    }
    
    public If(LinkedList<NodoAST> ins, Expresion cond,Instruccion elseIf) {
        super(ins, cond);
        this.elseIf = elseIf;
    }
    @Override
    public Object ejecutar(Entorno ent) {
        //verifico si traer un arreglo
        Object verificacion = (Object)getCond().getValorImplicito(ent);
        boolean valorCondicion = true;
        if (verificacion instanceof Object[]) {
            valorCondicion = (boolean)((Object[])verificacion)[0];
        }
        else{
            valorCondicion = (boolean)verificacion;
        }
        
        if (valorCondicion) 
        {
            Entorno local = new Entorno(ent);
            for (NodoAST nodo : getIns()) {
                if (nodo instanceof Instruccion) 
                {
                    Instruccion ins = (Instruccion)nodo;
                    Object result = ins.ejecutar(local);
                    if (ins instanceof  Break || result instanceof Break) {
                        return new Break(ins.linea(),ins.columna());
                    }
                    if (ins instanceof Continue) 
                    {
                        return new Continue();
                    }

                    if (result != null) {
                        return result;
                    }                    
                }
                else if (nodo instanceof Expresion) 
                {
                    Expresion exp = (Expresion)nodo;
                    if (exp instanceof Return) {
                        return exp.getValorImplicito(local);
                    }
                    exp.getValorImplicito(local);
                }
            }
//            local.setNombre("If");
//            Ventana.getArray().add(local);
        }
        else
        {
            Entorno local = new Entorno(ent);
            //ejecuto el else if
            if (elseIf != null) 
            {
                Object result = elseIf.ejecutar(local);
                if (result != null)
                {
                    return result;
                }
            }
            //ejecuto el else
            else
            {
                if (getInsElse() != null) {
                    for(NodoAST nodo : getInsElse())
                    {
                        if (nodo instanceof Break) 
                        {
                            return new Break(nodo.linea(),nodo.columna());
                        }
                        if (nodo instanceof Instruccion)
                        {
                            Instruccion ins = (Instruccion)nodo;
                            Object result = ins.ejecutar(local);
                            if (result != null)
                            {
                                return result;
                            }
                        }
                        else if (nodo instanceof Expresion)
                        {
                            Expresion expr = (Expresion)nodo;
                            return expr.getValorImplicito(local);
                        }
                    }
                }                
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
    public String getNombre(StringBuilder builder, String parent, int cont) {
        String nodo = "nodo" + ++cont;
        builder.append(nodo).append(" [label=\"If-Else\"];\n");
        builder.append(parent).append(" -> ").append(nodo).append(";\n");

        String nodoIf = "nodo" + ++cont;
        builder.append(nodoIf).append(" [label=\"If\"];\n");
        builder.append(nodo).append(" -> ").append(nodoIf).append(";\n");

        for (NodoAST instr : getIns()) {
            cont = Integer.parseInt(instr.getNombre(builder, nodoIf, cont));
        }

        if (getInsElse() != null) {
            String nodoElse = "nodo" + ++cont;
            builder.append(nodoElse).append(" [label=\"Else\"];\n");
            builder.append(nodo).append(" -> ").append(nodoElse).append(";\n");

            for (NodoAST instr : getInsElse()) {
                cont = Integer.parseInt(instr.getNombre(builder, nodoElse, cont));
            }
        }
        

        return ""+cont;
    }
    
    public LinkedList<NodoAST> getInsElse() {
        return insElse;
        
    }
}
