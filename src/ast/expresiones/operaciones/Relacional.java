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
        Object res2 = op2.getValorImplicito(ent);
        
        Tipo t1 = op1.getTipo(ent);
        Tipo t2 = op2.getTipo(ent);
        Tipo tipoResultado = tipoDominante(t1, t2);
        
         //variables para operar
        boolean rr1=false,rr2=false; Object[]arr1= null, arr2=null,total=null;
        Object[][]m1=null,m2=null,mtotal=null;
        int x = 0,y=0,isMat=0;
        //matriz
        if (res1 instanceof Object[][] || res2 instanceof Object[][]) 
        {
            rr1 = (res1 instanceof Object[][]);
            rr2 = (res2 instanceof Object[][]);
            m1 = rr1==true ? (Object[][])res1 : null;
            m2 = rr2==true ? (Object[][])res2 : null;
            x = 0;
            y = 0;
            isMat = 1;
            if (rr1 && rr2) {
                if ((m1.length != m2.length) && (m1[0].length != m2[0].length)) 
                {
                    Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(),
                            "Aritmetica: Quiere operar 2 matrices con difernte tamaño "+
                                    "M1= x:"+((Object[][])res1).length+" y: "+((Object[][])res1)[0].length+
                                    " M2= x:"+((Object[][])res2).length+" y: "+((Object[][])res2)[0].length ));
                    return null;
                }
                x = m1.length;
                y = m1[0].length;
            }else if (rr1) {
                x = m1.length;
                y = m1[0].length;
            }else if (rr2) {
                x = m2.length;
                y = m2[0].length;
            }
        }
        //vector
        else
        {
            rr1 = (res1 instanceof Object[]);
            rr2 = (res2 instanceof Object[]);
            arr1 = rr1==true ? (Object[])res1 : null;
            arr2 = rr2==true ? (Object[])res2 : null;
            x = 0;isMat=0;
            if (rr1 && rr2) {
                if (arr1.length != arr2.length && !(arr1.length == 1 || arr2.length == 1)) 
                {
                    Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(),
                            "Relacional: Quiere operar 2 datos con difernte tamaño ")); 
                    return null;
                }
                x = arr1.length > arr2.length ? arr1.length : arr2.length;
            
            }else if(rr1){
                x = arr1.length;
            }else if (rr2) {
                x = arr2.length;
            }
        }
        
        if (tipoResultado != null)
        {
            //solo string
            if (t1.isString() && t2.isString()){
                int a = 0;
                switch(operador){
                    case IGUAL_IGUAL:
                        if (isMat == 1) {
                            // <editor-fold defaultstate="collapsed" desc="Matrices">
                            if (rr1 && rr2){
                                mtotal = new Object[x][y];
                                for (int i=0; i < m1.length; i++) {
                                    for (int j=0; j < m1[i].length; j++) {
                                        a = m1[i][j].toString().compareTo(m2[i][j].toString());
                                        mtotal[i][j] = a == 0;
                                    }
                                }
                                return mtotal;
                            }
                            else if (rr1) {
                                mtotal = new Object[x][y];
                                for (int i=0; i < m1.length; i++) {
                                    for (int j=0; j < m1[i].length; j++) {
                                        a = m1[i][j].toString().compareTo(res2.toString());
                                        mtotal[i][j] = a == 0;
                                    }
                                }
                                return mtotal;
                            }
                            else if (rr2) {
                                mtotal = new Object[m2.length][m2[0].length];
                                for (int i=0; i < m2.length; i++) {
                                    for (int j=0; j < m2[i].length; j++) {
                                        a = res1.toString().compareTo(m2[i][j].toString());
                                        mtotal[i][j] = a == 0;
                                    }
                                }
                                return mtotal;
                            }
                            return null;
                            // </editor-fold>
                        }else{
                            // <editor-fold defaultstate="collapsed" desc="Vectores">
                            if (rr1 && rr2){
                                total = new Object[x];                            
                                for (int i = 0; i < x; i++) {
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
                                total = new Object[x];
                                for (int i = 0; i < x; i++) {
                                    a= arr1[i].toString().compareTo(res2.toString());
                                    total[i] = a==0;
                                }
                                return total;
                            }
                            else if (rr2) {
                                total = new Object[x];
                                for (int i = 0; i < x; i++) {
                                    a= res1.toString().compareTo(arr2[i].toString());
                                    total[i] = a==0;
                                }
                                return total;
                            }
                            // </editor-fold>
                            a = res1.toString().compareTo(res2.toString());
                            return a == 0;
                        }
                    case DIFERENTE_QUE:
                        if (isMat == 1) {
                            // <editor-fold defaultstate="collapsed" desc="Matrices">
                            if (rr1 && rr2){
                                mtotal = new Object[x][y];
                                for (int i=0; i < m1.length; i++) {
                                    for (int j=0; j < m1[i].length; j++) {
                                        a = m1[i][j].toString().compareTo(m2[i][j].toString());
                                        mtotal[i][j] = a != 0;
                                    }
                                }
                                return mtotal;
                            }
                            else if (rr1) {
                                mtotal = new Object[x][y];
                                for (int i=0; i < m1.length; i++) {
                                    for (int j=0; j < m1[i].length; j++) {
                                        a = m1[i][j].toString().compareTo(res2.toString());
                                        mtotal[i][j] = a != 0;
                                    }
                                }
                                return mtotal;
                            }
                            else if (rr2) {
                                mtotal = new Object[m2.length][m2[0].length];
                                for (int i=0; i < m2.length; i++) {
                                    for (int j=0; j < m2[i].length; j++) {
                                        a = res1.toString().compareTo(m2[i][j].toString());
                                        mtotal[i][j] = a != 0;
                                    }
                                }
                                return mtotal;
                            }
                            return null;
                            // </editor-fold>
                        }else{    
                            // <editor-fold defaultstate="collapsed" desc="Vectores">
                            if (rr1 && rr2){
                                total = new Object[x];                            
                                for (int i = 0; i < x; i++) {
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
                                total = new Object[x];
                                for (int i = 0; i < x; i++) {
                                    a= arr1[i].toString().compareTo(res2.toString());
                                    total[i] = a!=0;
                                }
                                return total;
                            }
                            else if (rr2) {
                                total = new Object[x];
                                for (int i = 0; i < x; i++) {
                                    a= res1.toString().compareTo(arr2[i].toString());
                                    total[i] = a!=0;
                                }
                                return total;
                            }
                            // </editor-fold>
                            a = res1.toString().compareTo(res2.toString());
                            return a != 0;
                        }
                    case MAYOR_QUE:
                        if (isMat == 1) {
                            // <editor-fold defaultstate="collapsed" desc="Matrices">
                            if (rr1 && rr2){
                                mtotal = new Object[x][y];
                                for (int i=0; i < m1.length; i++) {
                                    for (int j=0; j < m1[i].length; j++) {
                                        a = m1[i][j].toString().compareTo(m2[i][j].toString());
                                        mtotal[i][j] = a > 0;
                                    }
                                }
                                return mtotal;
                            }
                            else if (rr1) {
                                mtotal = new Object[x][y];
                                for (int i=0; i < m1.length; i++) {
                                    for (int j=0; j < m1[i].length; j++) {
                                        a = m1[i][j].toString().compareTo(res2.toString());
                                        mtotal[i][j] = a > 0;
                                    }
                                }
                                return mtotal;
                            }
                            else if (rr2) {
                                mtotal = new Object[m2.length][m2[0].length];
                                for (int i=0; i < m2.length; i++) {
                                    for (int j=0; j < m2[i].length; j++) {
                                        a = res1.toString().compareTo(m2[i][j].toString());
                                        mtotal[i][j] = a > 0;
                                    }
                                }
                                return mtotal;
                            }
                            return null;
                            // </editor-fold>
                        }else{    
                            // <editor-fold defaultstate="collapsed" desc="Vectores">
                        if (rr1 && rr2){
                            total = new Object[x];                            
                            for (int i = 0; i < x; i++) {
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
                            total = new Object[x];
                            for (int i = 0; i < x; i++) {
                                a= arr1[i].toString().compareTo(res2.toString());
                                total[i] = a>=0;
                            }
                            return total;
                        }
                        else if (rr2) {
                            total = new Object[x];
                            for (int i = 0; i < x; i++) {
                                a= res1.toString().compareTo(arr2[i].toString());
                                total[i] = a>=0;
                            }
                            return total;
                        }
                        // </editor-fold>
                            a = res1.toString().compareTo(res2.toString());
                            return a > 0;
                        }
                    case MENOR_QUE:
                        if (isMat == 1) {
                            // <editor-fold defaultstate="collapsed" desc="Matrices">
                            if (rr1 && rr2){
                                mtotal = new Object[x][y];
                                for (int i=0; i < m1.length; i++) {
                                    for (int j=0; j < m1[i].length; j++) {
                                        a = m1[i][j].toString().compareTo(m2[i][j].toString());
                                        mtotal[i][j] = a < 0;
                                    }
                                }
                                return mtotal;
                            }
                            else if (rr1) {
                                mtotal = new Object[x][y];
                                for (int i=0; i < m1.length; i++) {
                                    for (int j=0; j < m1[i].length; j++) {
                                        a = m1[i][j].toString().compareTo(res2.toString());
                                        mtotal[i][j] = a < 0;
                                    }
                                }
                                return mtotal;
                            }
                            else if (rr2) {
                                mtotal = new Object[m2.length][m2[0].length];
                                for (int i=0; i < m2.length; i++) {
                                    for (int j=0; j < m2[i].length; j++) {
                                        a = res1.toString().compareTo(m2[i][j].toString());
                                        mtotal[i][j] = a < 0;
                                    }
                                }
                                return mtotal;
                            }
                            return null;
                            // </editor-fold>
                        }else{
                            // <editor-fold defaultstate="collapsed" desc="Vectores">
                            if (rr1 && rr2){
                                total = new Object[x];                            
                                for (int i = 0; i < x; i++) {
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
                                total = new Object[x];
                                for (int i = 0; i < x; i++) {
                                    a= arr1[i].toString().compareTo(res2.toString());
                                    total[i] = a<0;
                                }
                                return total;
                            }
                            else if (rr2) {
                                total = new Object[x];
                                for (int i = 0; i < x; i++) {
                                    a= res1.toString().compareTo(arr2[i].toString());
                                    total[i] = a<0;
                                }
                                return total;
                            }
                            // </editor-fold>
                            a = res1.toString().compareTo(res2.toString());
                            return a < 0;
                        }
                    case MENOR_IGUAL:
                        if (isMat == 1) {
                            // <editor-fold defaultstate="collapsed" desc="Matrices">
                            if (rr1 && rr2){
                                mtotal = new Object[x][y];
                                for (int i=0; i < m1.length; i++) {
                                    for (int j=0; j < m1[i].length; j++) {
                                        a = m1[i][j].toString().compareTo(m2[i][j].toString());
                                        mtotal[i][j] = a <= 0;
                                    }
                                }
                                return mtotal;
                            }
                            else if (rr1) {
                                mtotal = new Object[x][y];
                                for (int i=0; i < m1.length; i++) {
                                    for (int j=0; j < m1[i].length; j++) {
                                        a = m1[i][j].toString().compareTo(res2.toString());
                                        mtotal[i][j] = a <= 0;
                                    }
                                }
                                return mtotal;
                            }
                            else if (rr2) {
                                mtotal = new Object[m2.length][m2[0].length];
                                for (int i=0; i < m2.length; i++) {
                                    for (int j=0; j < m2[i].length; j++) {
                                        a = res1.toString().compareTo(m2[i][j].toString());
                                        mtotal[i][j] = a <= 0;
                                    }
                                }
                                return mtotal;
                            }
                            return null;
                            // </editor-fold>
                        }else{
                            // <editor-fold defaultstate="collapsed" desc="Vectores">
                            if (rr1 && rr2){
                                total = new Object[x];                            
                                for (int i = 0; i < x; i++) {
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
                                total = new Object[x];
                                for (int i = 0; i < x; i++) {
                                    a= arr1[i].toString().compareTo(res2.toString());
                                    total[i] = a<=0;
                                }
                                return total;
                            }
                            else if (rr2) {
                                total = new Object[x];
                                for (int i = 0; i < x; i++) {
                                    a= res1.toString().compareTo(arr2[i].toString());
                                    total[i] = a<=0;
                                }
                                return total;
                            }
                            // </editor-fold>
                            a = res1.toString().compareTo(res2.toString());
                            return a <= 0;
                        }
                    case MAYOR_IGUAL:
                        if (isMat == 1) {
                            // <editor-fold defaultstate="collapsed" desc="Matrices">
                            if (rr1 && rr2){
                                mtotal = new Object[x][y];
                                for (int i=0; i < m1.length; i++) {
                                    for (int j=0; j < m1[i].length; j++) {
                                        a = m1[i][j].toString().compareTo(m2[i][j].toString());
                                        mtotal[i][j] = a > 0;
                                    }
                                }
                                return mtotal;
                            }
                            else if (rr1) {
                                mtotal = new Object[x][y];
                                for (int i=0; i < m1.length; i++) {
                                    for (int j=0; j < m1[i].length; j++) {
                                        a = m1[i][j].toString().compareTo(res2.toString());
                                        mtotal[i][j] = a >= 0;
                                    }
                                }
                                return mtotal;
                            }
                            else if (rr2) {
                                mtotal = new Object[m2.length][m2[0].length];
                                for (int i=0; i < m2.length; i++) {
                                    for (int j=0; j < m2[i].length; j++) {
                                        a = res1.toString().compareTo(m2[i][j].toString());
                                        mtotal[i][j] = a >= 0;
                                    }
                                }
                                return mtotal;
                            }
                            return null;
                            // </editor-fold>
                        }else{
                            // <editor-fold defaultstate="collapsed" desc="Vectores">
                            if (rr1 && rr2){
                                total = new Object[x];                            
                                for (int i = 0; i < x; i++) {
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
                                total = new Object[x];
                                for (int i = 0; i < x; i++) {
                                    a= arr1[i].toString().compareTo(res2.toString());
                                    total[i] = a>=0;
                                }
                                return total;
                            }
                            else if (rr2) {
                                total = new Object[x];
                                for (int i = 0; i < x; i++) {
                                    a= res1.toString().compareTo(arr2[i].toString());
                                    total[i] = a>=0;
                                }
                                return total;
                            }
                            // </editor-fold>
                            a = res1.toString().compareTo(res2.toString());
                            return a >=0;
                        }
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
                        if (isMat == 1) {
                            // <editor-fold defaultstate="collapsed" desc="Matrices">
                            if (rr1 && rr2){
                                mtotal = new Object[x][y];
                                for (int i=0; i < m1.length; i++) {
                                    for (int j=0; j < m1[i].length; j++) {
                                        mtotal[i][j] = (boolean)(new Double(m1[i][j].toString()) > new Double(m2[i][j].toString()));;
                                    }
                                }
                                return mtotal;
                            }
                            else if (rr1) {
                                mtotal = new Object[x][y];
                                for (int i=0; i < m1.length; i++) {
                                    for (int j=0; j < m1[i].length; j++) {
                                        mtotal[i][j] = (boolean)(new Double(m1[i][j].toString()) > new Double(res2.toString()));;;
                                    }
                                }
                                return mtotal;
                            }
                            else if (rr2) {
                                mtotal = new Object[m2.length][m2[0].length];
                                for (int i=0; i < m2.length; i++) {
                                    for (int j=0; j < m2[i].length; j++) {
                                        mtotal[i][j] = (boolean)(new Double(res1.toString()) > new Double(m2[i][j].toString()));;;
                                    }
                                }
                                return mtotal;
                            }
                            return null;
                            // </editor-fold>
                        }else{
                            // <editor-fold defaultstate="collapsed" desc="Vectores">
                                if (rr1 && rr2){
                                    arr1 = booltoInt(arr1);
                                    arr2 = booltoInt(arr2);
                                    total = new Object[x];
                                    for (int i = 0; i < x; i++) {
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
                                    arr1 = booltoInt(arr1);
                                    total = new Object[x];
                                    for (int i = 0; i < x; i++) {
                                        total[i] = (boolean)(new Double(arr1[i].toString()) > new Double(res2.toString()));
                                    }
                                    return total;
                                }
                                else if (rr2) {
                                    arr2 = booltoInt(arr2);
                                    total = new Object[x];
                                    for (int i = 0; i < x; i++) {
                                        total[i] = (boolean)(new Double(res1.toString()) > new Double(arr2[i].toString()));
                                    }
                                    return total;
                                }
                                // </editor-fold>
                            return (boolean)(new Double(res1.toString()) > new Double(res2.toString()));
                        }
                    case MENOR_QUE:
                        if (isMat == 1) {
                            // <editor-fold defaultstate="collapsed" desc="Matrices">
                            if (rr1 && rr2){
                                mtotal = new Object[x][y];
                                for (int i=0; i < m1.length; i++) {
                                    for (int j=0; j < m1[i].length; j++) {
                                        mtotal[i][j] = (boolean)(new Double(m1[i][j].toString()) < new Double(m2[i][j].toString()));;
                                    }
                                }
                                return mtotal;
                            }
                            else if (rr1) {
                                mtotal = new Object[x][y];
                                for (int i=0; i < m1.length; i++) {
                                    for (int j=0; j < m1[i].length; j++) {
                                        mtotal[i][j] = (boolean)(new Double(m1[i][j].toString()) < new Double(res2.toString()));;;
                                    }
                                }
                                return mtotal;
                            }
                            else if (rr2) {
                                mtotal = new Object[m2.length][m2[0].length];
                                for (int i=0; i < m2.length; i++) {
                                    for (int j=0; j < m2[i].length; j++) {
                                        mtotal[i][j] = (boolean)(new Double(res1.toString()) < new Double(m2[i][j].toString()));;;
                                    }
                                }
                                return mtotal;
                            }
                            return null;
                            // </editor-fold>
                        }else{
                            // <editor-fold defaultstate="collapsed" desc="Vectores">
                            if (rr1 && rr2){
                                arr1 = booltoInt(arr1);
                                arr2 = booltoInt(arr2);
                                total = new Object[x];
                                for (int i = 0; i < x; i++) {
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
                                arr1 = booltoInt(arr1);
                                total = new Object[x];
                                for (int i = 0; i < x; i++) {
                                    total[i] = (boolean)(new Double(arr1[i].toString()) < new Double(res2.toString()));
                                }
                                return total;
                            }
                            else if (rr2) {
                                arr2 = booltoInt(arr2);
                                total = new Object[x];
                                for (int i = 0; i < x; i++) {
                                    total[i] = (boolean)(new Double(res1.toString()) < new Double(arr2[i].toString()));
                                }
                                return total;
                            }
                            // </editor-fold>
                            return (boolean)(new Double(res1.toString()) < new Double(res2.toString()));
                        }
                    case MAYOR_IGUAL:
                        if (isMat == 1) {
                            // <editor-fold defaultstate="collapsed" desc="Matrices">
                            if (rr1 && rr2){
                                mtotal = new Object[x][y];
                                for (int i=0; i < m1.length; i++) {
                                    for (int j=0; j < m1[i].length; j++) {
                                        mtotal[i][j] = (boolean)(new Double(m1[i][j].toString()) >= new Double(m2[i][j].toString()));;
                                    }
                                }
                                return mtotal;
                            }
                            else if (rr1) {
                                mtotal = new Object[x][y];
                                for (int i=0; i < m1.length; i++) {
                                    for (int j=0; j < m1[i].length; j++) {
                                        mtotal[i][j] = (boolean)(new Double(m1[i][j].toString()) >= new Double(res2.toString()));;;
                                    }
                                }
                                return mtotal;
                            }
                            else if (rr2) {
                                mtotal = new Object[m2.length][m2[0].length];
                                for (int i=0; i < m2.length; i++) {
                                    for (int j=0; j < m2[i].length; j++) {
                                        mtotal[i][j] = (boolean)(new Double(res1.toString()) >= new Double(m2[i][j].toString()));;;
                                    }
                                }
                                return mtotal;
                            }
                            return null;
                            // </editor-fold>
                        }else{
                            // <editor-fold defaultstate="collapsed" desc="Vectores">
                            if (rr1 && rr2){
                                arr1 = booltoInt(arr1);
                                arr2 = booltoInt(arr2);
                                total = new Object[x];
                                    for (int i = 0; i < x; i++) {
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
                                arr1 = booltoInt(arr1);
                                total = new Object[x];
                                for (int i = 0; i < x; i++) {
                                    total[i] = (boolean)(new Double(arr1[i].toString()) >= new Double(res2.toString()));
                                }
                                return total;
                            }
                            else if (rr2) {
                                arr2 = booltoInt(arr2);
                                total = new Object[x];
                                for (int i = 0; i < x; i++) {
                                    total[i] = (boolean)(new Double(res1.toString()) >= new Double(arr2[i].toString()));
                                }
                                return total;
                            }
                            // </editor-fold>
                            return (boolean)(new Double(res1.toString()) >= new Double(res2.toString()));
                        }
                    case MENOR_IGUAL:
                        if (isMat == 1) {
                            // <editor-fold defaultstate="collapsed" desc="Matrices">
                            if (rr1 && rr2){
                                mtotal = new Object[x][y];
                                for (int i=0; i < m1.length; i++) {
                                    for (int j=0; j < m1[i].length; j++) {
                                        mtotal[i][j] = (boolean)(new Double(m1[i][j].toString()) <= new Double(m2[i][j].toString()));;
                                    }
                                }
                                return mtotal;
                            }
                            else if (rr1) {
                                mtotal = new Object[x][y];
                                for (int i=0; i < m1.length; i++) {
                                    for (int j=0; j < m1[i].length; j++) {
                                        mtotal[i][j] = (boolean)(new Double(m1[i][j].toString()) <= new Double(res2.toString()));;;
                                    }
                                }
                                return mtotal;
                            }
                            else if (rr2) {
                                mtotal = new Object[m2.length][m2[0].length];
                                for (int i=0; i < m2.length; i++) {
                                    for (int j=0; j < m2[i].length; j++) {
                                        mtotal[i][j] = (boolean)(new Double(res1.toString()) <= new Double(m2[i][j].toString()));;;
                                    }
                                }
                                return mtotal;
                            }
                            return null;
                            // </editor-fold>
                        }else{
                            // <editor-fold defaultstate="collapsed" desc="Vectores">
                            if (rr1 && rr2){
                                arr1 = booltoInt(arr1);
                                arr2 = booltoInt(arr2);
                                total = new Object[x];
                                    for (int i = 0; i < x; i++) {
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
                                arr1 = booltoInt(arr1);
                                total = new Object[x];
                                for (int i = 0; i < x; i++) {
                                    total[i] = (boolean)(new Double(arr1[i].toString()) <= new Double(res2.toString()));
                                }
                                return total;
                            }
                            else if (rr2) {
                                arr2 = booltoInt(arr2);
                                total = new Object[x];
                                for (int i = 0; i < x; i++) {
                                    total[i] = (boolean)(new Double(res1.toString()) <= new Double(arr2[i].toString()));
                                }
                                return total;
                            }
                            // </editor-fold>
                            boolean resp = (boolean)(new Double(res1.toString()) <= new Double(res2.toString())); 
                            return resp;
                        }                          
                    case IGUAL_IGUAL:
                        if (isMat == 1) {
                            // <editor-fold defaultstate="collapsed" desc="Matrices">
                            if (rr1 && rr2){
                                mtotal = new Object[x][y];
                                for (int i=0; i < m1.length; i++) {
                                    for (int j=0; j < m1[i].length; j++) {
                                        mtotal[i][j] = (boolean)(Objects.equals(new Double(m1[i][j].toString()),new Double(m2[i][j].toString())));
                                    }
                                }
                                return mtotal;
                            }
                            else if (rr1) {
                                mtotal = new Object[x][y];
                                for (int i=0; i < m1.length; i++) {
                                    for (int j=0; j < m1[i].length; j++) {
                                        mtotal[i][j] = (boolean)(Objects.equals(new Double(m1[i][j].toString()),new Double(res2.toString())));
                                    }
                                }
                                return mtotal;
                            }
                            else if (rr2) {
                                mtotal = new Object[m2.length][m2[0].length];
                                for (int i=0; i < m2.length; i++) {
                                    for (int j=0; j < m2[i].length; j++) {
                                        mtotal[i][j] = (boolean)(Objects.equals(new Double(res1.toString()),new Double(m2[i][j].toString())));
                                    }
                                }
                                return mtotal;
                            }
                            return null;
                            // </editor-fold>
                        }else{
                            // <editor-fold defaultstate="collapsed" desc="Vectores">
                            if (rr1 && rr2){
                                arr1 = booltoInt(arr1);
                                arr2 = booltoInt(arr2);
                                total = new Object[x];
                                for (int i = 0; i < x; i++) {
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
                                arr1 = booltoInt(arr1);
                                total = new Object[x];
                                for (int i = 0; i < x; i++) {
                                    total[i] = (boolean)(Objects.equals(new Double(arr1[i].toString()), new Double(res2.toString())));
                                }
                                return total;
                            }
                            else if (rr2) {
                                arr2 = booltoInt(arr2);
                                total = new Object[x];
                                for (int i = 0; i < x; i++) {
                                    total[i] = (boolean)(Objects.equals(new Double(res1.toString()),new Double(arr2[i].toString())));
                                }
                                return total;
                            }
                            // </editor-fold>
                            return (boolean)(Objects.equals(new Double(res1.toString()), new Double(res2.toString())));
                        }                        
                    case DIFERENTE_QUE:
                        if (isMat == 1) {
                            // <editor-fold defaultstate="collapsed" desc="Matrices">
                            if (rr1 && rr2){
                                mtotal = new Object[x][y];
                                for (int i=0; i < m1.length; i++) {
                                    for (int j=0; j < m1[i].length; j++) {
                                        mtotal[i][j] = (boolean)(Double.valueOf(m1[i][j].toString()) != Double.valueOf(m2[i][j].toString()));
                                    }
                                }
                                return mtotal;
                            }
                            else if (rr1) {
                                mtotal = new Object[x][y];
                                for (int i=0; i < m1.length; i++) {
                                    for (int j=0; j < m1[i].length; j++) {
                                        mtotal[i][j] = (boolean)(Double.valueOf(m1[i][j].toString()) != Double.valueOf(res2.toString()));
                                    }
                                }
                                return mtotal;
                            }
                            else if (rr2) {
                                mtotal = new Object[m2.length][m2[0].length];
                                for (int i=0; i < m2.length; i++) {
                                    for (int j=0; j < m2[i].length; j++) {
                                        mtotal[i][j] = (boolean)(Double.valueOf(res1.toString()) != Double.valueOf(m2[i][j].toString()));
                                    }
                                }
                                return mtotal;
                            }
                            return null;
                            // </editor-fold>
                        }else{
                            // <editor-fold defaultstate="collapsed" desc="Vectores">
                            if (rr1 && rr2){
                                arr1 = booltoInt(arr1);
                                arr2 = booltoInt(arr2);
                                total = new Object[x];
                                for (int i = 0; i < x; i++) {
                                    if (arr1.length == 1) {
                                        double r1 = Double.valueOf(arr1[0].toString());
                                        double r2 = Double.valueOf(arr2[i].toString());    
                                        boolean res = r1 != r2;
                                        total[i] = res;
                                    }
                                    else if (arr2.length == 1) {
                                        double r1 = Double.valueOf(arr1[i].toString());
                                        double r2 = Double.valueOf(arr2[0].toString());    
                                        boolean res = r1 != r2;
                                        total[i] = res;
                                    }
                                    else{
                                        double r1 = Double.valueOf(arr1[i].toString());
                                        double r2 = Double.valueOf(arr2[i].toString());    
                                        boolean res = r1 != r2;
                                        total[i] = res;
                                    }
                                }
                                return total;
                            }
                            else if (rr1) {
                                arr1 = booltoInt(arr1);
                                total = new Object[x];
                                for (int i = 0; i < x; i++) {                                    
                                    double r1 = Double.valueOf(arr1[i].toString());
                                    double r2 = Double.valueOf(res2.toString());    
                                    boolean res = r1 != r2;
                                    total[i] = res;
                                }
                                return total;
                            }
                            else if (rr2) {
                                arr2 = booltoInt(arr2);
                                total = new Object[x];
                                for (int i = 0; i < x; i++) {
                                    double r1 = Double.valueOf(res1.toString());
                                    double r2 = Double.valueOf(arr2[i].toString());    
                                    boolean res = r1 != r2;
                                    total[i] = res;
                                }
                                return total;
                            }
                            // </editor-fold>
                            double r1 = Double.valueOf(res1.toString());
                            double r2 = Double.valueOf(res2.toString());    
                            boolean res = r1 != r2;
                            return res;
                        }  
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
                        if (isMat == 1) {
                            // <editor-fold defaultstate="collapsed" desc="Matrices">
                            if (rr1 && rr2){
                                mtotal = new Object[x][y];
                                for (int i=0; i < m1.length; i++) {
                                    for (int j=0; j < m1[i].length; j++) {
                                        mtotal[i][j] = (boolean)m1[i][j] == (boolean)m2[i][j];
                                    }
                                }
                                return mtotal;
                            }
                            else if (rr1) {
                                mtotal = new Object[x][y];
                                for (int i=0; i < m1.length; i++) {
                                    for (int j=0; j < m1[i].length; j++) {
                                        mtotal[i][j] = (boolean)m1[i][j] == (boolean)res2;
                                    }
                                }
                                return mtotal;
                            }
                            else if (rr2) {
                                mtotal = new Object[m2.length][m2[0].length];
                                for (int i=0; i < m2.length; i++) {
                                    for (int j=0; j < m2[i].length; j++) {
                                        mtotal[i][j] = (boolean)res1 == (boolean)m2[i][j];
                                    }
                                }
                                return mtotal;
                            }
                            return null;
                            // </editor-fold>
                        }else{
                            // <editor-fold defaultstate="collapsed" desc="Vectores">
                            if (rr1 && rr2){
                                total = new Object[x];
                                for (int i = 0; i < x; i++) {
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
                                total = new Object[x];
                                for (int i = 0; i < x; i++) {
                                    total[i] = (boolean)(arr1[i]) ==  (boolean)(res2);
                                }
                                return total;
                            }
                            else if (rr2) {
                                total = new Object[x];
                                for (int i = 0; i < x; i++) {
                                    total[i] = (boolean)(res1) ==  (boolean)(arr2[i]);
                                }
                                return total;
                            }
                            // </editor-fold>
                            return (boolean)((boolean)res1 == (boolean)res2);
                        }
                    case DIFERENTE_QUE:
                        if (isMat == 1) {
                            // <editor-fold defaultstate="collapsed" desc="Matrices">
                            if (rr1 && rr2){
                                mtotal = new Object[x][y];
                                for (int i=0; i < m1.length; i++) {
                                    for (int j=0; j < m1[i].length; j++) {
                                        mtotal[i][j] = (boolean)m1[i][j] != (boolean)m2[i][j];
                                    }
                                }
                                return mtotal;
                            }
                            else if (rr1) {
                                mtotal = new Object[x][y];
                                for (int i=0; i < m1.length; i++) {
                                    for (int j=0; j < m1[i].length; j++) {
                                        mtotal[i][j] = (boolean)m1[i][j] != (boolean)res2;
                                    }
                                }
                                return mtotal;
                            }
                            else if (rr2) {
                                mtotal = new Object[m2.length][m2[0].length];
                                for (int i=0; i < m2.length; i++) {
                                    for (int j=0; j < m2[i].length; j++) {
                                        mtotal[i][j] = (boolean)res1 != (boolean)m2[i][j];
                                    }
                                }
                                return mtotal;
                            }
                            return null;
                            // </editor-fold>
                        }else{
                            // <editor-fold defaultstate="collapsed" desc="Vectores">
                            if (rr1 && rr2){
                                total = new Object[x];
                                for (int i = 0; i < x; i++) {
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
                                total = new Object[x];
                                for (int i = 0; i < x; i++) {
                                    total[i] = (boolean)(arr1[i]) !=  (boolean)(res2);
                                }
                                return total;
                            }
                            else if (rr2) {
                                total = new Object[x];
                                for (int i = 0; i < x; i++) {
                                    total[i] = (boolean)(res1) !=  (boolean)(arr2[i]);
                                }
                                return total;
                            }
                            // </editor-fold>
                            return (boolean)res1 != (boolean)res2;
                        }
                    default:    
                        Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(),
                        "Relacional: operador realacional en bool "));
                        return null;
                }
            }
            else{
                Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(),
                        "Relacional: no se puede operar si no es numero: RELACIONAL " + res1 + " "+res2));
                return null;
            }
            
        }
        return null;
    }


    @Override
    public String getNombre(StringBuilder builder, String parent, int cont) {
        String nodo = "nodo" + ++cont;
        builder.append(nodo).append(" [label=\"Relacional\"];\n");
        builder.append(parent).append(" -> ").append(nodo).append(";\n");

        cont = Integer.parseInt(op1.getNombre(builder, nodo, cont));

        String nodoOp = "nodo" + ++cont;
        builder.append(nodoOp).append(" [label=\"" + operador + "\"];\n");
        builder.append(nodo).append(" -> ").append(nodoOp).append(";\n");

        cont = Integer.parseInt(op2.getNombre(builder, nodo, cont));
        return ""+cont;
    }

    private Object[] booltoInt(Object[] arr1) {
        try {
            Object [] t = new Object[arr1.length];
            for (int i = 0; i < t.length; i++) {
                if (arr1[i] instanceof Boolean) {
                    t[i] = Boolean.compare((boolean) arr1[i], false); 
                }else{
                    t[i] = arr1[i];
                }
            }
            return t;
        } catch (Exception e) {
           return  arr1;
        }        
    }
    
}
