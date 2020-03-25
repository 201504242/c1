/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast.expresiones.operaciones;

import ast.Expresion;
import entorno.Entorno;
import entorno.Tipo;
import java.util.Objects;
import olc2.p1_201504242.JError;
import olc2.p1_201504242.Ventana;

/**
 *
 * @author p_ab1
 */
public class Relacional extends Operacion{

    public Relacional(Expresion op1, Expresion op2, Operador operador, int col, int linea) {
        super(op1, op2, operador, col, linea);
    }

    @Override
    public Tipo tipoDominante(Tipo t1, Tipo t2) {
        if (t1.getTipoPrimitivo() == Tipo.Tipos.DOUBLE || t2.getTipoPrimitivo() == Tipo.Tipos.DOUBLE) 
        {
            return new Tipo(Tipo.Tipos.BOOL);
        } 
        else if (t1.getTipoPrimitivo() == Tipo.Tipos.INT || t2.getTipoPrimitivo() == Tipo.Tipos.INT){
            return new Tipo(Tipo.Tipos.BOOL);
        }
        else if (t1.getTipoPrimitivo() == Tipo.Tipos.STRING && t2.getTipoPrimitivo() == Tipo.Tipos.STRING) {
            return new Tipo(Tipo.Tipos.BOOL);
        }
        else if (t1.getTipoPrimitivo() == Tipo.Tipos.BOOL && t2.getTipoPrimitivo() == Tipo.Tipos.BOOL) {
            return new Tipo(Tipo.Tipos.BOOL);
        }
        else if (t1.getTipoPrimitivo() == null ) {
            return new Tipo(Tipo.Tipos.BOOL);
        }
        else
        {
            System.out.println("ALGO MALO tipo dominante relacional t1:"+t1.getTipoPrimitivo() + " ,t2: "+t2.getTipoPrimitivo());
            return null;
        }
    }

    @Override
    public Tipo getTipo(Entorno ent) {
        return tipoDominante(op1.getTipo(ent), op2.getTipo(ent));
    }

    @Override
    public Object getValorImplicito(Entorno ent) {
        Object res1 = op1.getValorImplicito(ent);
//        if (res1 instanceof Object[]) {
//            res1 = ((Object[])res1)[0];
//        }
        Object res2 = op2.getValorImplicito(ent);
//        if (res2 instanceof Object[]) {
//            res2 = ((Object[])res2)[0];
//        }
        Tipo t1 = op1.getTipo(ent);
        Tipo t2 = op2.getTipo(ent);
        Tipo tipoResultado = tipoDominante(t1, t2);
        
        boolean rr1 = (res1 instanceof Object[]);
        boolean rr2 = (res2 instanceof Object[]);
        Object [] arr1 = rr1==true ? (Object[])res1 : null;
        Object [] arr2 = rr2==true ? (Object[])res2 : null;
        Object [] total;
        int grande = 0;
        if (rr1 && rr2) {
            if (arr1.length != arr2.length && !(arr1.length == 1 || arr2.length == 1)) 
            {
                Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(),
                        "Relacional: Quiere operar 2 datos con difernte tamaño ")); 
                return null;
            }
            grande = arr1.length > arr2.length ? arr1.length : arr2.length;
        }
        
        if (tipoResultado != null)
        {
            //solo string
            if (t1.isString() && t2.isString()){
                int a = 0;
                switch(operador){
                    case IGUAL_IGUAL:  
                        // <editor-fold defaultstate="collapsed" desc="Vectores">
                        if (rr1 && rr2){
                            total = new Object[grande];                            
                            for (int i = 0; i < arr1.length; i++) {
                                if (arr1.length == 1) {
                                    a= arr1[0].toString().compareTo(arr2[i].toString());
                                }
                                else if (arr2.length == 1) {
                                    a= arr1[i].toString().compareTo(arr2[0].toString());
                                }
                                else{
                                    a= arr1[i].toString().compareTo(arr2[i].toString());
                                }                                
                                total[i] = a==0;
                            }
                            return total;
                        }
                        else if (rr1) {
                            total = new Object[arr1.length];
                            for (int i = 0; i < arr1.length; i++) {
                                a= arr1[i].toString().compareTo(res2.toString());
                                total[i] = a==0;
                            }
                            return total;
                        }
                        else if (rr2) {
                            total = new Object[arr2.length];
                            for (int i = 0; i < arr2.length; i++) {
                                a= res1.toString().compareTo(arr2[i].toString());
                                total[i] = a==0;
                            }
                            return total;
                        }
                        // </editor-fold>
                        a = res1.toString().compareTo(res2.toString());
                        return a == 0;
                    case DIFERENTE_QUE:
                        // <editor-fold defaultstate="collapsed" desc="Vectores">
                        if (rr1 && rr2){
                            total = new Object[grande];                            
                            for (int i = 0; i < arr1.length; i++) {
                                if (arr1.length == 1) {
                                    a= arr1[0].toString().compareTo(arr2[i].toString());
                                }
                                else if (arr2.length == 1) {
                                    a= arr1[i].toString().compareTo(arr2[0].toString());
                                }
                                else{
                                    a= arr1[i].toString().compareTo(arr2[i].toString());
                                }                                
                                total[i] = a != 0;
                            }
                            return total;
                        }
                        else if (rr1) {
                            total = new Object[arr1.length];
                            for (int i = 0; i < arr1.length; i++) {
                                a= arr1[i].toString().compareTo(res2.toString());
                                total[i] = a!=0;
                            }
                            return total;
                        }
                        else if (rr2) {
                            total = new Object[arr2.length];
                            for (int i = 0; i < arr2.length; i++) {
                                a= res1.toString().compareTo(arr2[i].toString());
                                total[i] = a!=0;
                            }
                            return total;
                        }
                        // </editor-fold>
                        a = res1.toString().compareTo(res2.toString());
                        return a != 0;
                    case MAYOR_QUE:
                        // <editor-fold defaultstate="collapsed" desc="Vectores">
                        if (rr1 && rr2){
                            total = new Object[grande];                            
                            for (int i = 0; i < arr1.length; i++) {
                                if (arr1.length == 1) {
                                    a= arr1[0].toString().compareTo(arr2[i].toString());
                                }
                                else if (arr2.length == 1) {
                                    a= arr1[i].toString().compareTo(arr2[0].toString());
                                }
                                else{
                                    a= arr1[i].toString().compareTo(arr2[i].toString());
                                }                                
                                total[i] = a > 0;
                            }
                            return total;
                        }
                        else if (rr1) {
                            total = new Object[arr1.length];
                            for (int i = 0; i < arr1.length; i++) {
                                a= arr1[i].toString().compareTo(res2.toString());
                                total[i] = a>=0;
                            }
                            return total;
                        }
                        else if (rr2) {
                            total = new Object[arr2.length];
                            for (int i = 0; i < arr2.length; i++) {
                                a= res1.toString().compareTo(arr2[i].toString());
                                total[i] = a>=0;
                            }
                            return total;
                        }
                        // </editor-fold>
                        a = res1.toString().compareTo(res2.toString());
                        return a > 0;
                    case MENOR_QUE:
                        // <editor-fold defaultstate="collapsed" desc="Vectores">
                        if (rr1 && rr2){
                            total = new Object[grande];                            
                            for (int i = 0; i < arr1.length; i++) {
                                if (arr1.length == 1) {
                                    a= arr1[0].toString().compareTo(arr2[i].toString());
                                }
                                else if (arr2.length == 1) {
                                    a= arr1[i].toString().compareTo(arr2[0].toString());
                                }
                                else{
                                    a= arr1[i].toString().compareTo(arr2[i].toString());
                                }                                
                                total[i] = a < 0;
                            }
                            return total;
                        }
                        else if (rr1) {
                            total = new Object[arr1.length];
                            for (int i = 0; i < arr1.length; i++) {
                                a= arr1[i].toString().compareTo(res2.toString());
                                total[i] = a<0;
                            }
                            return total;
                        }
                        else if (rr2) {
                            total = new Object[arr2.length];
                            for (int i = 0; i < arr2.length; i++) {
                                a= res1.toString().compareTo(arr2[i].toString());
                                total[i] = a<0;
                            }
                            return total;
                        }
                        // </editor-fold>
                        a = res1.toString().compareTo(res2.toString());
                        return a < 0;
                    case MENOR_IGUAL:
                        // <editor-fold defaultstate="collapsed" desc="Vectores">
                        if (rr1 && rr2){
                            total = new Object[grande];                            
                            for (int i = 0; i < arr1.length; i++) {
                                if (arr1.length == 1) {
                                    a= arr1[0].toString().compareTo(arr2[i].toString());
                                }
                                else if (arr2.length == 1) {
                                    a= arr1[i].toString().compareTo(arr2[0].toString());
                                }
                                else{
                                    a= arr1[i].toString().compareTo(arr2[i].toString());
                                }                                
                                total[i] = a <= 0;
                            }
                            return total;
                        }
                        else if (rr1) {
                            total = new Object[arr1.length];
                            for (int i = 0; i < arr1.length; i++) {
                                a= arr1[i].toString().compareTo(res2.toString());
                                total[i] = a<=0;
                            }
                            return total;
                        }
                        else if (rr2) {
                            total = new Object[arr2.length];
                            for (int i = 0; i < arr2.length; i++) {
                                a= res1.toString().compareTo(arr2[i].toString());
                                total[i] = a<=0;
                            }
                            return total;
                        }
                        // </editor-fold>
                        a = res1.toString().compareTo(res2.toString());
                        return a <= 0;
                    case MAYOR_IGUAL:
                        // <editor-fold defaultstate="collapsed" desc="Vectores">
                        if (rr1 && rr2){
                            total = new Object[grande];                            
                            for (int i = 0; i < arr1.length; i++) {
                                if (arr1.length == 1) {
                                    a= arr1[0].toString().compareTo(arr2[i].toString());
                                }
                                else if (arr2.length == 1) {
                                    a= arr1[i].toString().compareTo(arr2[0].toString());
                                }
                                else{
                                    a= arr1[i].toString().compareTo(arr2[i].toString());
                                }                                
                                total[i] = a >= 0;
                            }
                            return total;
                        }
                        else if (rr1) {
                            total = new Object[arr1.length];
                            for (int i = 0; i < arr1.length; i++) {
                                a= arr1[i].toString().compareTo(res2.toString());
                                total[i] = a>=0;
                            }
                            return total;
                        }
                        else if (rr2) {
                            total = new Object[arr2.length];
                            for (int i = 0; i < arr2.length; i++) {
                                a= res1.toString().compareTo(arr2[i].toString());
                                total[i] = a>=0;
                            }
                            return total;
                        }
                        // </editor-fold>
                        a = res1.toString().compareTo(res2.toString());
                        return a >=0;                        
                    default:    
                        Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(),
                        "Relacional: no se puede hacer otra operacion entre string que no sea >,<,>=,<=,==,!="));
                        return null;
                }
            }
            //solo numero
            else if (t1.isNumeric() && t2.isNumeric())
            {
                switch(operador)
                {
                    case MAYOR_QUE:
                        // <editor-fold defaultstate="collapsed" desc="Vectores">
                            if (rr1 && rr2){
                                total = new Object[grande];
                                for (int i = 0; i < grande; i++) {
                                    if (arr1.length == 1) {
                                        total[i] = (boolean)(new Double(arr1[0].toString()) > new Double(arr2[i].toString()));
                                    }
                                    else if (arr2.length == 1) {
                                        total[i] = (boolean)(new Double(arr1[i].toString()) > new Double(arr2[0].toString()));
                                    }
                                    else{
                                        total[i] = (boolean)(new Double(arr1[i].toString()) > new Double(arr2[i].toString()));
                                    }
                                }
                                return total;
                            }
                            else if (rr1) {
                                total = new Object[arr1.length];
                                for (int i = 0; i < arr1.length; i++) {
                                    total[i] = (boolean)(new Double(arr1[i].toString()) > new Double(res2.toString()));
                                }
                                return total;
                            }
                            else if (rr2) {
                                total = new Object[arr2.length];
                                for (int i = 0; i < arr2.length; i++) {
                                    total[i] = (boolean)(new Double(res1.toString()) > new Double(arr2[i].toString()));
                                }
                                return total;
                            }
                            // </editor-fold>
                        return (boolean)(new Double(res1.toString()) > new Double(res2.toString()));
                    case MENOR_QUE:
                        // <editor-fold defaultstate="collapsed" desc="Vectores">
                        if (rr1 && rr2){
                            total = new Object[grande];
                            for (int i = 0; i < grande; i++) {
                                if (arr1.length == 1) {
                                    total[i] = (boolean)(new Double(arr1[0].toString()) < new Double(arr2[i].toString()));
                                }
                                else if (arr2.length == 1) {
                                    total[i] = (boolean)(new Double(arr1[i].toString()) < new Double(arr2[0].toString()));
                                }
                                else{
                                    total[i] = (boolean)(new Double(arr1[i].toString()) < new Double(arr2[i].toString()));
                                }
                            }
                            return total;
                        }
                        else if (rr1) {
                            total = new Object[arr1.length];
                            for (int i = 0; i < arr1.length; i++) {
                                total[i] = (boolean)(new Double(arr1[i].toString()) < new Double(res2.toString()));
                            }
                            return total;
                        }
                        else if (rr2) {
                            total = new Object[arr2.length];
                            for (int i = 0; i < arr2.length; i++) {
                                total[i] = (boolean)(new Double(res1.toString()) < new Double(arr2[i].toString()));
                            }
                            return total;
                        }
                        // </editor-fold>
                        return (boolean)(new Double(res1.toString()) < new Double(res2.toString()));
                    case MAYOR_IGUAL:
                        // <editor-fold defaultstate="collapsed" desc="Vectores">
                        if (rr1 && rr2){
                            total = new Object[grande];
                                for (int i = 0; i < grande; i++) {
                                    if (arr1.length == 1) {
                                        total[i] = (boolean)(new Double(arr1[0].toString()) >= new Double(arr2[i].toString()));
                                    }
                                    else if (arr2.length == 1) {
                                        total[i] = (boolean)(new Double(arr1[i].toString()) >= new Double(arr2[0].toString()));
                                    }
                                    else{
                                        total[i] = (boolean)(new Double(arr1[i].toString()) >= new Double(arr2[i].toString()));
                                    }
                                }
                                return total;
                        }
                        else if (rr1) {
                            total = new Object[arr1.length];
                            for (int i = 0; i < arr1.length; i++) {
                                total[i] = (boolean)(new Double(arr1[i].toString()) >= new Double(res2.toString()));
                            }
                            return total;
                        }
                        else if (rr2) {
                            total = new Object[arr2.length];
                            for (int i = 0; i < arr2.length; i++) {
                                total[i] = (boolean)(new Double(res1.toString()) >= new Double(arr2[i].toString()));
                            }
                            return total;
                        }
                        // </editor-fold>
                        return (boolean)(new Double(res1.toString()) >= new Double(res2.toString()));
                    case MENOR_IGUAL:
                        // <editor-fold defaultstate="collapsed" desc="Vectores">
                        if (rr1 && rr2){
                            total = new Object[grande];
                                for (int i = 0; i < grande; i++) {
                                    if (arr1.length == 1) {
                                        total[i] = (boolean)(new Double(arr1[0].toString()) <= new Double(arr2[i].toString()));
                                    }
                                    else if (arr2.length == 1) {
                                        total[i] = (boolean)(new Double(arr1[i].toString()) <= new Double(arr2[0].toString()));
                                    }
                                    else{
                                        total[i] = (boolean)(new Double(arr1[i].toString()) <= new Double(arr2[i].toString()));
                                    }
                                }
                                return total;
                        }
                        else if (rr1) {
                            total = new Object[arr1.length];
                            for (int i = 0; i < arr1.length; i++) {
                                total[i] = (boolean)(new Double(arr1[i].toString()) <= new Double(res2.toString()));
                            }
                            return total;
                        }
                        else if (rr2) {
                            total = new Object[arr2.length];
                            for (int i = 0; i < arr2.length; i++) {
                                total[i] = (boolean)(new Double(res1.toString()) <= new Double(arr2[i].toString()));
                            }
                            return total;
                        }
                        // </editor-fold>
                        boolean resp = (boolean)(new Double(res1.toString()) <= new Double(res2.toString())); 
                        return resp;
                          
                    case IGUAL_IGUAL:
                        // <editor-fold defaultstate="collapsed" desc="Vectores">
                        if (rr1 && rr2){
                            total = new Object[grande];
                            for (int i = 0; i < grande; i++) {
                                if (arr1.length == 1) {
                                    total[i] = (boolean)(Objects.equals(new Double(arr1[0].toString()), new Double(arr2[i].toString())));
                                }
                                else if (arr2.length == 1) {
                                    total[i] = (boolean)(Objects.equals(new Double(arr1[i].toString()), new Double(arr2[0].toString())));
                                }
                                else{
                                    total[i] = (boolean)(Objects.equals(new Double(arr1[0].toString()), new Double(arr2[i].toString())));
                                }
                            }
                            return total;
                        }
                        else if (rr1) {
                            total = new Object[arr1.length];
                            for (int i = 0; i < arr1.length; i++) {
                                total[i] = (boolean)(Objects.equals(new Double(arr1[i].toString()), new Double(res2.toString())));
                            }
                            return total;
                        }
                        else if (rr2) {
                            total = new Object[arr2.length];
                            for (int i = 0; i < arr2.length; i++) {
                                total[i] = (boolean)(Objects.equals(new Double(res1.toString()),new Double(arr2[i].toString())));
                            }
                            return total;
                        }
                        // </editor-fold>
                        return (boolean)(Objects.equals(new Double(res1.toString()), new Double(res2.toString())));
                        
                    case DIFERENTE_QUE:
                        // <editor-fold defaultstate="collapsed" desc="Vectores">
                        if (rr1 && rr2){
                            total = new Object[grande];
                            for (int i = 0; i < grande; i++) {
                                if (arr1.length == 1) {
                                    total[i] = (boolean)(Double.valueOf(arr1[0].toString()) !=  Double.valueOf(arr2[i].toString()));
                                }
                                else if (arr2.length == 1) {
                                    total[i] = (boolean)(Double.valueOf(arr1[i].toString()) !=  Double.valueOf(arr2[0].toString()));
                                }
                                else{
                                    total[i] = (boolean)(Double.valueOf(arr1[i].toString()) !=  Double.valueOf(arr2[i].toString()));
                                }
                            }
                            return total;
                        }
                        else if (rr1) {
                            total = new Object[arr1.length];
                            for (int i = 0; i < arr1.length; i++) {
                                total[i] = (boolean)(Double.valueOf(arr1[i].toString()) !=  Double.valueOf(res2.toString()));
                            }
                            return total;
                        }
                        else if (rr2) {
                            total = new Object[arr2.length];
                            for (int i = 0; i < arr2.length; i++) {
                                total[i] = (boolean)(Double.valueOf(res1.toString()) !=  Double.valueOf(arr2[i].toString()));
                            }
                            return total;
                        }
                        // </editor-fold>
                        double r1 = Double.valueOf(res1.toString());
                        double r2 = Double.valueOf(res2.toString());    
                        boolean res = r1 != r2;
                        return res;
                           
                    default:    
                        Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(),
                        "Relacional"));
                        return null;
                }
            }
            else if (t1.isBoolean() && t2.isBoolean()) 
            {
               // boolean r1 = (boolean)res1;
                //boolean r2 = (boolean)res2;    
                switch(operador)
                {
                    case IGUAL_IGUAL:
                        // <editor-fold defaultstate="collapsed" desc="Vectores">
                        if (rr1 && rr2){
                            total = new Object[grande];
                            for (int i = 0; i < grande; i++) {
                                if (arr1.length == 1) {
                                    total[i] = (boolean)(arr1[0]) ==  (boolean)(arr2[i]);
                                }
                                else if (arr2.length == 1) {
                                    total[i] = (boolean)(arr1[i]) ==  (boolean)(arr2[0]);
                                }
                                else{
                                    total[i] = (boolean)(arr1[i]) ==  (boolean)(arr2[i]);
                                }
                            }
                            return total;
                        }
                        else if (rr1) {
                            total = new Object[arr1.length];
                            for (int i = 0; i < arr1.length; i++) {
                                total[i] = (boolean)(arr1[i]) ==  (boolean)(res2);
                            }
                            return total;
                        }
                        else if (rr2) {
                            total = new Object[arr2.length];
                            for (int i = 0; i < arr2.length; i++) {
                                total[i] = (boolean)(res1) ==  (boolean)(arr2[i]);
                            }
                            return total;
                        }
                        // </editor-fold>
                        return (boolean)((boolean)res1 == (boolean)res2);
                        
                    case DIFERENTE_QUE:
                        // <editor-fold defaultstate="collapsed" desc="Vectores">
                        if (rr1 && rr2){
                            total = new Object[grande];
                            for (int i = 0; i < grande; i++) {
                                if (arr1.length == 1) {
                                    total[i] = (boolean)(arr1[0]) !=  (boolean)(arr2[i]);
                                }
                                else if (arr2.length == 1) {
                                    total[i] = (boolean)(arr1[i]) !=  (boolean)(arr2[0]);
                                }
                                else{
                                    total[i] = (boolean)(arr1[i]) !=  (boolean)(arr2[i]);
                                }
                            }
                            return total;
                        }
                        else if (rr1) {
                            total = new Object[arr1.length];
                            for (int i = 0; i < arr1.length; i++) {
                                total[i] = (boolean)(arr1[i]) !=  (boolean)(res2);
                            }
                            return total;
                        }
                        else if (rr2) {
                            total = new Object[arr2.length];
                            for (int i = 0; i < arr2.length; i++) {
                                total[i] = (boolean)(res1) !=  (boolean)(arr2[i]);
                            }
                            return total;
                        }
                        // </editor-fold>
                        return (boolean)res1 != (boolean)res2;
                    default:    
                        Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(),
                        "Relacional: operador realacional en bool "));
                        return null;
                }
            }
            else{
                Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(),
                        "Relacional: no se puede operar si no es numero: RELACIONAL" + res1 + " "+res2));
                return null;
            }
            
        }
        return null;
    }


    @Override
    public String getNombre() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
