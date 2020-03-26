/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast.instrucciones;

import ast.Expresion;
import ast.Instruccion;
import ast.NodoAST;
import entorno.Entorno;
import java.util.LinkedList;

/**
 *
 * @author p_ab1
 */
public class DoWhile extends Condicion implements Instruccion{
    private int linea;
    private int col;

    public DoWhile(LinkedList<NodoAST> ins, Expresion cond,int lin,int col) {
        super(ins, cond);
        this.col = col;
        this.linea = lin;
    }
    
    @Override
    public Object ejecutar(Entorno ent) {
        Entorno doW = new Entorno(ent);  
        for (NodoAST ins : getIns())
        {
            if (ins instanceof Instruccion) 
            {        
                Instruccion aux = (Instruccion)ins;
                Object result = aux.ejecutar(doW);
                if (ins instanceof Break || result instanceof Break)
                {
                    return null;
                }
                if (ins instanceof Continue || result instanceof Continue) 
                {
                    continue;
                }

                if (result != null)
                {
                    return result;
                }                          
            }
            else if (ins instanceof Expresion) 
            {
                Expresion aux = (Expresion) ins;
                aux.getValorImplicito(doW);
            }                   
        }
        eti: 
        while (verificacion((boolean)getCond().getValorImplicito(ent)))
        {
            Entorno local = new Entorno(ent);            
            for (NodoAST ins : getIns())
            {
                if (ins instanceof Instruccion) 
                {        
                    Instruccion aux = (Instruccion)ins;
                    Object result = aux.ejecutar(local);
                    if (ins instanceof Break || result instanceof Break)
                    {
                        return null;
                    }
                    if (ins instanceof Continue || result instanceof Continue) 
                    {
                        continue eti;
                    }

                    if (result != null)
                    {
                        return result;
                    }                          
                }
                else if (ins instanceof Expresion) 
                {
                    Expresion aux = (Expresion) ins;
                    aux.getValorImplicito(local);
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
        builder.append(nodo).append(" [label=\"Do-While\"];\n");
        builder.append(parent).append(" -> ").append(nodo).append(";\n");

        for (NodoAST instr : getIns()) {
            cont = Integer.parseInt(instr.getNombre(builder, nodo, cont));
        }
        return ""+cont;
    }

    private boolean verificacion(boolean b) {
        Object verificacion = (Object)b;
        boolean valorCondicion;
        if (verificacion instanceof Object[]) {
            valorCondicion = (boolean)((Object[])verificacion)[0];
        }
        else{
            valorCondicion = (boolean)verificacion;
        }
        return valorCondicion;
    }
    
}
