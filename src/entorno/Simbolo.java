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
public class Simbolo {
    private String identificador;
    private Object valor;
    private Tipo tipo; 
    private Rol rol;
    private int dim;
    public int lugar;
    
    public Simbolo(String identificador, Object valor, Tipo tipo, Rol rol,int dim) {
        this.identificador = identificador;
        this.valor = valor;
        this.tipo = tipo;
        this.rol = rol;
        this.lugar = 0;
        this.dim = dim;
    }

    public Simbolo(String id, Rol rol) {
        this.identificador = id;
        this.rol = rol;   
        this.lugar = 0;
    }

    
    public Simbolo(String id, Rol rol, Object valor) {
        this.identificador = id;
        this.rol = rol;    
        this.valor = valor;
        this.lugar = 0;
    }
    
   
    
    public enum Acc{
        tipo1,//CORIZQ EXP CORDER
        tipo2,//CORIZQ CORIZQ EXP CORDER CORDER  
        m1,//CORIZQ EXP COMA EXP CORDER  
        m2,//CORIZQ EXP COMA CORDER 
        m3//CORIZQ COMA EXP CORDER 
    }
    
    public enum Rol{
        VECTOR,
        LISTA,
        MATRIZ,
        ARREGLO,
        FUNCION        
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
    

//    @Override
//    public String toString() {
//        if (this.valor instanceof Object[]) 
//        {
//            Object[] s = (Object[])this.valor;
//            for (Object object : s) {
//                
//            }
//            return Arrays.toString(s);
//        }
//        if (this.valor instanceof Object) {
//            return this.valor.toString();
//        }
//        return this.toString(); //To change body of generated methods, choose Tools | Templates.
//    }
    
    
}
