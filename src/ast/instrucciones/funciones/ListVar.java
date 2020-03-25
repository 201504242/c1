/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast.instrucciones.funciones;

import entorno.Simbolo;
import java.util.LinkedList;

/**
 *
 * @author p_ab1
 */
public class ListVar extends LinkedList{
    
    @Override
    public void add(int i,Object e) {
        super.add(i, e); //To change body of generated methods, choose Tools | Templates.
    }

//    @Override
//    public boolean add(Object e) {
//        Simbolo nuevoSimbolo = new Simbolo("", null, e);
//        return super.add(nuevoSimbolo); //To change body of generated methods, choose Tools | Templates.
//    }

    
//    @Override
//    public Object get(int i) {
//        
//        Simbolo getSimbolo =  (Simbolo) super.get(i); //To change body of generated methods, choose Tools | Templates.
//        return getSimbolo.getValor();
//    }
//    
    public Object getAssig(int i) {        
        Simbolo getSimbolo =  (Simbolo) super.get(i); //To change body of generated methods, choose Tools | Templates.
        return getSimbolo;
    }

    
}
