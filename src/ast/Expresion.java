/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

import entorno.Entorno;
import entorno.Tipo;

/**
 *
 * @author p_ab1
 */
public interface Expresion extends NodoAST{
    Tipo getTipo(Entorno ent);
    Object getValorImplicito(Entorno ent);

}
