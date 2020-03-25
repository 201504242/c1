/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entorno;

/**
 *
 * @author p_ab1
 */
public class Tipo {
    private Tipos tipoPrimitivo;   

    //CONSTRUCTOR QUE ASIGNA EL TIPO PRIMITIVO
    public Tipo(Tipos tp) {
        this.tipoPrimitivo = tp;
    }

    //RETORNAR EL TIPO PRIMITIVO 
    public Tipos getTipoPrimitivo() {
        return tipoPrimitivo;
    }
    
    public enum Tipos
    {
            STRING,
            INT,
            DOUBLE,
            BOOL,
            VOID,
            DEFAULT,
            LIST
    }
 
    public boolean isNumeric(){
        if (tipoPrimitivo == Tipos.INT || tipoPrimitivo == Tipos.DOUBLE) 
        {
            return true;
        }
        return false;
    }
    
    public boolean isBoolean(){
        return tipoPrimitivo == Tipos.BOOL;
    }
    
    public boolean isDouble(){
        return (tipoPrimitivo == Tipos.DOUBLE);
    }
    
    public boolean isInt(){
        return (tipoPrimitivo == Tipos.INT);
    }
    
    public boolean isString(){
        return (tipoPrimitivo == Tipos.STRING);
    }
    
}
