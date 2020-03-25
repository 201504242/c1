/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast.expresiones.operaciones;

import ast.Expresion;
import entorno.Entorno;
import entorno.Tipo;
import olc2.p1_201504242.JError;
import olc2.p1_201504242.Ventana;

/**
 *
 * @author p_ab1
 */
public class Aritmetica extends Operacion{

    public Aritmetica(Expresion op1, Expresion op2, Operador operador, int col, int linea) {
        super(op1, op2, operador, col, linea);
        
    }

    public Aritmetica(Expresion opU,Operador operador,int col,int linea) {
        super(opU, operador, col, linea);
    }   
    
    @Override
    public Tipo tipoDominante(Tipo t1, Tipo t2) {
        if (operador == Operador.POTENCIA) {
             return new Tipo(Tipo.Tipos.DOUBLE);
        }
        else
        {
            if (t1.getTipoPrimitivo() == Tipo.Tipos.STRING || t2.getTipoPrimitivo() == Tipo.Tipos.STRING) 
            {
                return new Tipo(Tipo.Tipos.STRING);
            }
            else if (t1.getTipoPrimitivo() == Tipo.Tipos.BOOL || t2.getTipoPrimitivo() == Tipo.Tipos.BOOL) {
                System.out.println("ERROR tipos bool no se puede operar");
                return null;
            }
            else if(t1.getTipoPrimitivo() == Tipo.Tipos.DOUBLE || t2.getTipoPrimitivo() == Tipo.Tipos.DOUBLE)
            {
                return new Tipo(Tipo.Tipos.DOUBLE);
            }
            else if (t1.getTipoPrimitivo() == Tipo.Tipos.INT || t2.getTipoPrimitivo() == Tipo.Tipos.INT) 
            {
                return new Tipo(Tipo.Tipos.INT);
            }
            else
            {
                System.out.println("Clase Aritemetica error en tipos :"+t1 + " , "+t2);
                return null;
            }
        }
    }

    @Override
    public Tipo getTipo(Entorno ent) {
        if (opU == null) {
            return tipoDominante(op1.getTipo(ent), op2.getTipo(ent));
        }
            return opU.getTipo(ent);
            
    }

    @Override
    public Object getValorImplicito(Entorno ent) {
        Tipo t1=null,t2=null,tipoResultado=null;
        Object resU = null, res1=null,res2=null;
        if (opU == null) 
        {
//            System.out.println(operador);
            res1 = op1.getValorImplicito(ent);
            res2 = op2.getValorImplicito(ent);
            t1 = op1.getTipo(ent);
            t2 = op2.getTipo(ent);
            tipoResultado = tipoDominante(t1, t2);
        }
        else
        {
            tipoResultado = opU.getTipo(ent);
            resU = opU.getValorImplicito(ent);
        }
        boolean r1 = (res1 instanceof Object[]);
        boolean r2 = (res2 instanceof Object[]);
        Object [] arr1 = r1==true ? (Object[])res1 : null;
        Object [] arr2 = r2==true ? (Object[])res2 : null;
        Object [] total;
        int grande = 0;
        if (r1 && r2) {
            if (arr1.length != arr2.length && !(arr1.length == 1 || arr2.length == 1)) 
            {
                Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(),
                        "Aritmetica: Quiere operar 2 datos con difernte tamaño "+
                                "Tam1: "+((Object[])res1).length+" Tam2: "+((Object[])res2).length));
                return null;
            }
            grande = arr1.length > arr2.length ? arr1.length : arr2.length;
        }       
        
        if (tipoResultado != null)
        {         
            switch(operador)
            {
                case SUMA:
                    switch (tipoResultado.getTipoPrimitivo()) 
                    {
                        case STRING:
                            // <editor-fold defaultstate="collapsed" desc="Vectores">
                            if (r1 && r2){
                                total = new Object[grande];
                                for (int i = 0; i < grande; i++) {
                                    if (arr1.length == 1) {
                                        total[i] = (arr1[0].toString()) + (arr2[i].toString());
                                    }
                                    else if (arr2.length == 1) {
                                        total[i] = (arr1[i].toString()) + (arr2[0].toString());
                                    }
                                    else{
                                        total[i] = new Double(arr1[i].toString()) + new Double(arr2[i].toString());
                                    }
                                }
                                return total;
                            }
                            else if (r1) {
                                total = new Object[arr1.length];
                                for (int i = 0; i < arr1.length; i++) {
                                    total[i] = arr1[i].toString()+(res2.toString());
                                }
                                return total;
                            }
                            else if (r2) {
                                total = new Object[arr2.length];
                                for (int i = 0; i < arr2.length; i++) {
                                    total[i] = (arr2[i].toString())+(res1.toString());
                                }
                                return total;
                            }
                            // </editor-fold>
                            return res1.toString()+res2.toString();
                        case DOUBLE:
                            // <editor-fold defaultstate="collapsed" desc="Vectores">
                            if (r1 && r2){
                                total = new Object[grande];
                                for (int i = 0; i < grande; i++) {
                                    if (arr1.length == 1) {
                                        total[i] = new Double(arr1[0].toString()) + new Double(arr2[i].toString());
                                    }
                                    else if (arr2.length == 1) {
                                        total[i] = new Double(arr1[i].toString()) + new Double(arr2[0].toString());
                                    }
                                    else{
                                        total[i] = new Double(arr1[i].toString()) + new Double(arr2[i].toString());
                                    }
                                }
                                return total;
                            }
                            else if (r1) {
                                total = new Object[arr1.length];
                                for (int i = 0; i < arr1.length; i++) {
                                    total[i] = new Double(arr1[i].toString())+new Double(res2.toString());
                                }
                                return total;
                            }
                            else if (r2) {
                                total = new Object[arr2.length];
                                for (int i = 0; i < arr2.length; i++) {
                                    total[i] = new Double(arr2[i].toString())+new Double(res1.toString());
                                }
                                return total;
                            }
                            // </editor-fold>
                            return new Double(res1.toString()) +new Double(res2.toString());
                        case INT:
                            // <editor-fold defaultstate="collapsed" desc="Vectores">
                            if (r1 && r2){
                                total = new Object[grande];
                                for (int i = 0; i < grande; i++) {
                                    if (arr1.length == 1) {
                                        total[i] = (int)arr1[0] + (int)arr2[i];
                                    }
                                    else if (arr2.length == 1) {
                                        total[i] = (int)arr1[i] + (int)arr2[0];
                                    }
                                    else{
                                        total[i] = (int)arr1[i] + (int)arr2[i];
                                    }
                                }
                                return total;
                            }
                            else if (r1) {
                                total = new Object[arr1.length];
                                for (int i = 0; i < arr1.length; i++) {
                                    total[i] = (int)arr1[i]+(int)res2;
                                }
                                return total;
                            }
                            else if (r2) {
                                total = new Object[arr2.length];
                                for (int i = 0; i < arr2.length; i++) {
                                    total[i] = (int)arr2[i]+(int)res1;
                                }
                                return total;
                            }
                            // </editor-fold>
                            return (int)res1 + (int)res2;
                        default:
                            Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(),
                            "Aritmetica: en suma no int,double,string "));
                            //System.out.println("ERROR en case int case suma");
                            return null;                            
                    }
                case RESTA:
                    switch (tipoResultado.getTipoPrimitivo()) 
                    {
                        case STRING:
                            Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(),
                            "Aritmetica: no se puede RESTAR tipo string"));
                            //System.out.println("ERROR no se puede restar tipo string");
                            return null;
                        case DOUBLE:
                            // <editor-fold defaultstate="collapsed" desc="Vectores">
                            if (r1 && r2){
                                total = new Object[grande];
                                for (int i = 0; i < grande; i++) {
                                    if (arr1.length == 1) {
                                        total[i] = new Double(arr1[0].toString()) - new Double(arr2[i].toString());
                                    }
                                    else if (arr2.length == 1) {
                                        total[i] = new Double(arr1[i].toString()) - new Double(arr2[0].toString());
                                    }
                                    else{
                                        total[i] = new Double(arr1[i].toString()) - new Double(arr2[i].toString());
                                    }
                                }
                                return total;
                            }
                            else if (r1) {
                                total = new Object[arr1.length];
                                for (int i = 0; i < arr1.length; i++) {
                                    total[i] = new Double(arr1[i].toString()) - new Double(res2.toString());
                                }
                                return total;
                            }
                            else if (r2) {
                                total = new Object[arr2.length];
                                for (int i = 0; i < arr2.length; i++) {
                                    total[i] = new Double(res1.toString()) - new Double(arr2[i].toString());
                                }
                                return total;
                            }
                            // </editor-fold>
                            return new Double(res1.toString()) - new Double(res2.toString());
                        case INT:
                            // <editor-fold defaultstate="collapsed" desc="Vectores">
                            if (r1 && r2){
                                total = new Object[grande];
                                for (int i = 0; i < grande; i++) {
                                    if (arr1.length == 1) {
                                        total[i] = (int)arr1[0] - (int)arr2[i];
                                    }
                                    else if (arr2.length == 1) {
                                        total[i] = (int)arr1[i] - (int)arr2[0];
                                    }
                                    else{
                                        total[i] = (int)arr1[i] - (int)arr2[i];
                                    }
                                }
                                return total;
                            }
                            else if (r1) {
                                total = new Object[arr1.length];
                                for (int i = 0; i < arr1.length; i++) {
                                    total[i] = (int)arr1[i]-(int)res2;
                                }
                                return total;
                            }
                            else if (r2) {
                                total = new Object[arr2.length];
                                for (int i = 0; i < arr2.length; i++) {
                                    total[i] = (int)res1- (int)arr2[i];
                                }
                                return total;
                            }
                            // </editor-fold>
                            return (int)res1 - (int)res2;
                        default:
                            Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(),
                            "Aritmetica: en suma no int,double"));
                            //System.out.println("ERROR en case int case resta");
                            return null;
                    }
                case MULTIPLICACION:
                    switch (tipoResultado.getTipoPrimitivo()) 
                    {
                        case STRING:
                             Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(),
                            "Aritmetica: no se puede MULTIPLICAR tipo string"));
                            return null;
                        case DOUBLE:
                            // <editor-fold defaultstate="collapsed" desc="Vectores">
                            if (r1 && r2){
                                total = new Object[grande];
                                for (int i = 0; i < grande; i++) {
                                    if (arr1.length == 1) {
                                        total[i] = new Double(arr1[0].toString()) * new Double(arr2[i].toString());
                                    }
                                    else if (arr2.length == 1) {
                                        total[i] = new Double(arr1[i].toString()) * new Double(arr2[0].toString());
                                    }
                                    else{
                                        total[i] = new Double(arr1[i].toString()) * new Double(arr2[i].toString());
                                    }
                                }
                                return total;
                            }
                            else if (r1) {
                                total = new Object[arr1.length];
                                for (int i = 0; i < arr1.length; i++) {
                                    total[i] = new Double(arr1[i].toString()) * new Double(res2.toString());
                                }
                                return total;
                            }
                            else if (r2) {
                                total = new Object[arr2.length];
                                for (int i = 0; i < arr2.length; i++) {
                                    total[i] = new Double(res1.toString()) * new Double(arr2[i].toString());
                                }
                                return total;
                            }
                            // </editor-fold>
                            return new Double(res1.toString()) * new Double(res2.toString());
                        case INT:
                            // <editor-fold defaultstate="collapsed" desc="Vectores">
                            if (r1 && r2){
                                total = new Object[grande];
                                for (int i = 0; i < grande; i++) {
                                    if (arr1.length == 1) {
                                        total[i] = (int)arr1[0] * (int)arr2[i];
                                    }
                                    else if (arr2.length == 1) {
                                        total[i] = (int)arr1[i] * (int)arr2[0];
                                    }
                                    else{
                                        total[i] = (int)arr1[i] * (int)arr2[i];
                                    }
                                }
                                return total;
                            }
                            else if (r1) {
                                total = new Object[arr1.length];
                                for (int i = 0; i < arr1.length; i++) {
                                    total[i] = (int)arr1[i]*(int)res2;
                                }
                                return total;
                            }
                            else if (r2) {
                                total = new Object[arr2.length];
                                for (int i = 0; i < arr2.length; i++) {
                                    total[i] = (int)res1*(int)arr2[i];
                                }
                                return total;
                            }
                            // </editor-fold>
                            return (int)res1 * (int)res2;
                        default:
                            Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(),
                            "Aritmetica: en multiplicacion no int,double"));
                            //System.out.println("ERROR en case multiplicacion");
                            return null;
                    }
                case DIVISION:
                    switch (tipoResultado.getTipoPrimitivo()) 
                    {
                        case STRING:
                            Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(),
                            "Aritmetica: no se puede DIVIDIR tipo string"));
                            //System.out.println("ERROR no se puede dividir tipo string");
                            return null;
                        case DOUBLE:
                            try {
                                // <editor-fold defaultstate="collapsed" desc="Vectores">
                                if (r1 && r2){
                                    total = new Object[grande];
                                for (int i = 0; i < grande; i++) {
                                    if (arr1.length == 1) {
                                        total[i] = new Double(arr1[0].toString()) / new Double(arr2[i].toString());
                                    }
                                    else if (arr2.length == 1) {
                                        total[i] = new Double(arr1[i].toString()) / new Double(arr2[0].toString());
                                    }
                                    else{
                                        total[i] = new Double(arr1[i].toString()) / new Double(arr2[i].toString());
                                    }
                                }
                                return total;
                                }
                                else if (r1) {
                                    total = new Object[arr1.length];
                                    for (int i = 0; i < arr1.length; i++) {
                                        total[i] = new Double(arr1[i].toString()) / new Double(res2.toString());
                                    }
                                    return total;
                                }
                                else if (r2) {
                                    total = new Object[arr2.length];
                                    for (int i = 0; i < arr2.length; i++) {
                                        total[i] = new Double(res1.toString()) / new Double(arr2[i].toString()) ;
                                    }
                                    return total;
                                }
                                // </editor-fold>
                                Double a1 = new Double(res1.toString());
                                Double a2 = new Double(res2.toString());
                                if (a1 == 0.0 && a2 == 0.0) 
                                {
                                    return new Double(0.0);
                                }
                                return a1 / a2; 
                            } catch (Exception e) {
                                Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(),
                                "Aritmetica: Division con cero "+e.getMessage()));
                                return null;
                            }
                        case INT:
                            try {
                                // <editor-fold defaultstate="collapsed" desc="Vectores">
                                if (r1 && r2){
                                    total = new Object[grande];
                                for (int i = 0; i < grande; i++) {
                                    if (arr1.length == 1) {
                                        total[i] = (int)arr1[0] / (int)arr2[i];
                                    }
                                    else if (arr2.length == 1) {
                                        total[i] = (int)arr1[i] / (int)arr2[0];
                                    }
                                    else{
                                        total[i] = (int)arr1[i] / (int)arr2[i];
                                    }
                                }
                                return total;
                                }
                                else if (r1) {
                                    total = new Object[arr1.length];
                                    for (int i = 0; i < arr1.length; i++) {
                                        total[i] = (int)arr1[i]/(int)res2;
                                    }
                                    return total;
                                }
                                else if (r2) {
                                    total = new Object[arr2.length];
                                    for (int i = 0; i < arr2.length; i++) {
                                        total[i] = (int)res1/ (int)arr2[i];
                                    }
                                    return total;
                                }
                                // </editor-fold>
                                return (int)res1 / (int)res2;
                            } catch (Exception e) {
                                Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(),
                                "Aritmetica: Division con cero "+e.getMessage()));
                                return null;
                            } 
                        default:
                            Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(),
                            "Aritmetica: en division no int,double"));
                            return null;
                    }
                case POTENCIA:
                    switch (tipoResultado.getTipoPrimitivo()) 
                    {
                        case STRING:
                            Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(),
                            "Aritmetica: no se puede hacer potencia tipo string"));
                            return null;
                        case DOUBLE:
                            try {
                                 // <editor-fold defaultstate="collapsed" desc="Vectores">
                                if (r1 && r2){
                                    total = new Object[grande];
                                    for (int i = 0; i < grande; i++) {
                                        if (arr1.length == 1) {
                                            total[i] = Math.pow(new Double(arr1[0].toString()),new Double(arr2[i].toString()));
                                        }
                                        else if (arr2.length == 1) {
                                            total[i] = Math.pow(new Double(arr1[i].toString()),new Double(arr2[0].toString()));
                                        }
                                        else{
                                            total[i] = Math.pow(new Double(arr1[i].toString()),new Double(arr2[i].toString()));
                                        }
                                    }
                                    return total;
                                }
                                else if (r1) {
                                    total = new Object[arr1.length];
                                    for (int i = 0; i < arr1.length; i++) {
                                        total[i] = Math.pow(new Double(arr1[i].toString()) , new Double(res2.toString()));
                                    }
                                    return total;
                                }
                                else if (r2) {
                                    total = new Object[arr2.length];
                                    for (int i = 0; i < arr2.length; i++) {
                                        total[i] = Math.pow(new Double(res1.toString()),new Double(arr2[i].toString()));
                                    }
                                    return total;
                                }
                                // </editor-fold>
                                return Math.pow(new Double(res1.toString()) , new Double(res2.toString()));
                            } catch (Exception e) {
                                Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(),
                                "Aritmetica: no se ejecuto potencia "+e.getMessage()));
                                return null;
                            }
                        case INT:
                            try {
                                // <editor-fold defaultstate="collapsed" desc="Vectores">
                                if (r1 && r2){
                                    total = new Object[grande];
                                    for (int i = 0; i < grande; i++) {
                                        if (arr1.length == 1) {
                                            total[i] = Math.pow((int)arr1[0] , (int)arr2[i]);
                                        }
                                        else if (arr2.length == 1) {
                                            total[i] = Math.pow((int)arr1[i] , (int)arr2[0]);
                                        }
                                        else{
                                            total[i] = Math.pow((int)arr1[i] , (int)arr2[i]);
                                        }
                                    }
                                    return total;
                                }
                                else if (r1) {
                                    total = new Object[arr1.length];
                                    for (int i = 0; i < arr1.length; i++) {
                                        total[i] = Math.pow((int)arr1[i],(int)res2);
                                    }
                                    return total;
                                }
                                else if (r2) {
                                    total = new Object[arr2.length];
                                    for (int i = 0; i < arr2.length; i++) {
                                        total[i] = Math.pow((int)res1,(int)arr2[i]);
                                    }
                                    return total;
                                }
                                // </editor-fold>
                                return Math.pow((int)res1 , (int)res2);
                            } catch (Exception e) {
                                Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(),
                                "Aritmetica: no se ejecuto potencia "+e.getMessage()));
                                return null;
                            }
                        default:
                            Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(),
                            "Aritmetica: en potencia no int,double"));
                            return null;
                    }
                case MODULO:
//                    if (!"0".equals(String.valueOf(res2))) {
                        switch (tipoResultado.getTipoPrimitivo()) 
                        {
                            case STRING:
                                Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(),
                                "Aritmetica: no se puede hacer modulo tipo string"));
                                return null;
                            case DOUBLE:
                                // <editor-fold defaultstate="collapsed" desc="Vectores">
                                if (r1 && r2){
                                    total = new Object[grande];
                                    for (int i = 0; i < grande; i++) {
                                        if (arr1.length == 1) {
                                            total[i] = new Double(arr1[0].toString()) % new Double(arr2[i].toString());
                                        }
                                        else if (arr2.length == 1) {
                                            total[i] = new Double(arr1[i].toString()) % new Double(arr2[0].toString());
                                        }
                                        else{
                                            total[i] = new Double(arr1[i].toString()) % new Double(arr2[i].toString());
                                        }
                                    }
                                    return total;
                                }
                                else if (r1) {
                                    total = new Object[arr1.length];
                                    for (int i = 0; i < arr1.length; i++) {
                                        total[i] = new Double(arr1[i].toString()) % new Double(res2.toString());
                                    }
                                    return total;
                                }
                                else if (r2) {
                                    total = new Object[arr2.length];
                                    for (int i = 0; i < arr2.length; i++) {
                                        total[i] = new Double(res1.toString()) % new Double(arr2[i].toString());
                                    }
                                    return total;
                                }
                                // </editor-fold>
                                return new Double(res1.toString()) % new Double(res2.toString());
                            case INT:
                                 // <editor-fold defaultstate="collapsed" desc="Vectores">
                                if (r1 && r2){
                                    total = new Object[grande];
                                    for (int i = 0; i < grande; i++) {
                                        if (arr1.length == 1) {
                                            total[i] = (int)arr1[0] % (int)arr2[i];
                                        }
                                        else if (arr2.length == 1) {
                                            total[i] = (int)arr1[i] % (int)arr2[0];
                                        }
                                        else{
                                            total[i] = (int)arr1[i] % (int)arr2[i];
                                        }
                                    }
                                    return total;
                                }
                                else if (r1) {
                                    total = new Object[arr1.length];
                                    for (int i = 0; i < arr1.length; i++) {
                                        total[i] = (int)arr1[i]%(int)res2;
                                    }
                                    return total;
                                }
                                else if (r2) {
                                    total = new Object[arr2.length];
                                    for (int i = 0; i < arr2.length; i++) {
                                        total[i] = (int)res1%(int)arr2[i];
                                    }
                                    return total;
                                }
                                // </editor-fold>
                                return (int)res1 % (int)res2;
                            default:
                                Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(),
                                "Aritmetica: no double,int"));
                                return null;
                        }
                case MENOS_UNARIO:
                {
                    boolean rU = (resU instanceof Object[]);
                    Object [] arrU = rU==true ? (Object[])resU : null;
                    if (resU instanceof Double)
                    {
                        if (rU) 
                        {
                            total = new Object[arrU.length];
                            for (int i = 0; i < arrU.length; i++) {
                                total[i] = 0.0 - (double)arrU[i];
                            }
                            return total;
                        }
                        return 0.0 - (double)resU;
                    }
                    else if (resU instanceof Integer)
                    {
                        if (rU) 
                        {
                            total = new Object[arrU.length];
                            for (int i = 0; i < arrU.length; i++) {
                                total[i] = 0.0 - (int)arrU[i];
                            }
                            return total;
                        }
                        return 0 - (int)resU;
                    }
                    else
                    {
                        Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(),
                                "Aritmetica: no es ni double,int (Menos unario)"));
                        return null;
                    }   
                }
                default:
                {
                    Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(),
                                "Aritmetica: ningun caso en esta clase"));
                }
            }
        //}
        }
        return null;
    }

    
    @Override
    public String getNombre() {
        return "Arit";
    }

}
