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
public class Literal implements Expresion{
    private Object valor;
    private Tipo tipo;
    private int linea;
    private int col;

    public Literal(Object valor, Tipo tipo, int linea, int col) {
        this.valor = valor;
        this.tipo = tipo;
        this.linea = linea;
        this.col = col;
    }
    
    
    @Override
    public Tipo getTipo(Entorno ent) {
        return this.tipo;
    }

    @Override
    public Object getValorImplicito(Entorno ent) {
        return this.valor;
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
