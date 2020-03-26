/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

/**
 *
 * @author p_ab1
 */
public interface NodoAST {
    int linea();
    int columna();
    String getNombre(StringBuilder builder, String parent, int cont);
}
