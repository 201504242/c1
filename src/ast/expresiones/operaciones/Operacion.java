/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast.expresiones.operaciones;

import ast.Expresion;
import entorno.Entorno;
import entorno.Tipo;

/**
 *
 * @author p_ab1
 */
public abstract class Operacion implements Expresion{

    Expresion op1;
    Expresion op2;
    Expresion opU;
    Operador operador;
    int linea;
    int col;
    
    public Operacion(Expresion op1, Expresion op2, Operador operador,int col,int fila) {
        this.op1 = op1;
        this.op2 = op2;
        this.operador = operador;
        this.linea = fila;
        this.col =col;
   }
    
    public Operacion(Expresion opU, Operador operador,int col, int fila) {
        this.opU = opU;
        this.operador = operador;
        this.linea = fila;
        this.col = col;
    }
    
    public abstract Tipo tipoDominante(Tipo t1,Tipo t2);
    
    
    @Override
    public int linea() {
        return this.linea;
    }

    @Override
    public int columna() {
        return this.col;
    }

    
    public enum Operador
        {
            SUMA,
            RESTA,
            MULTIPLICACION,
            DIVISION,
            POTENCIA,
            MODULO,
            MENOS_UNARIO,
            MAYOR_QUE,
            MAYOR_IGUAL,
            MENOR_QUE,
            MENOR_IGUAL,
            IGUAL_IGUAL,
            DIFERENTE_QUE,
            OR,
            XOR,
            AND,
            NOT,
            TERNARIA,
            DESCONOCIDO
        }

    
}
