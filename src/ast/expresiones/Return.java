/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast.expresiones;

import ast.Expresion;
import entorno.Entorno;
import entorno.Tipo;

/**
 *
 * @author p_ab1
 */
public class Return implements Expresion{
    private boolean retornoVoid;
    private Expresion valorDeRetorno;
    private int linea;
    private int col;
    
     public Return(Expresion valorDeRetorno,int linea,int col)
        {
          this.linea = linea;
          this.col = col;
            this.valorDeRetorno = valorDeRetorno;
            retornoVoid = false;
        }
     
    public Return(int linea,int col)
        {
           this.linea = linea;
          this.col = col;
            retornoVoid = true;
        }
     public boolean isRetornoVoid()
        {
            return retornoVoid;
        }
     
    @Override
    public Tipo getTipo(Entorno ent) {
        return valorDeRetorno != null ? valorDeRetorno.getTipo(ent) : null;
    }

    @Override
    public Object getValorImplicito(Entorno ent) {
        return valorDeRetorno != null ? valorDeRetorno.getValorImplicito(ent) : null;
        //return valorDeRetorno.getValorImplicito(ent);
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
        if (isRetornoVoid()) {
            String nodo = "nodo" + ++cont;
            builder.append(nodo).append(" [label=\"Rerun Void\"];\n");
            builder.append(parent).append(" -> ").append(nodo).append(";\n");
            return ""+cont;
        }
        else{
            String nodo = "nodo" + ++cont;
            builder.append(nodo).append(" [label=\"Return Exp\"];\n");
            builder.append(parent).append(" -> ").append(nodo).append(";\n");
            
            cont = Integer.parseInt(this.valorDeRetorno.getNombre(builder, nodo, cont));
            
            return ""+cont;
        }
    }
    
}
