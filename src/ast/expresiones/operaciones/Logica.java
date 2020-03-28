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
public class Logica extends Operacion{

    public Logica(Expresion op1, Expresion op2, Operador operador, int col, int linea) {
        super(op1, op2, operador, col, linea);
    }

    public Logica(Expresion opU, Operador operador, int col, int linea) {
        super(opU, operador, col, linea);
    }

    @Override
    public Tipo tipoDominante(Tipo t1, Tipo t2) {
        if (t1.isBoolean() && t2.isBoolean()) {
            return new Tipo(Tipo.Tipos.BOOL);
        }
        return null;
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
        Object res1=null, res2=null,resU=null;
        if (opU == null) 
        {
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
        boolean r1=false,r2=false; Object[]arr1= null, arr2=null,total=null;
        Object[][]m1=null,m2=null,mtotal=null;
        int x = 0,y=0,isMat=0;
        //matriz
        if (res1 instanceof Object[][] || res2 instanceof Object[][]) 
        {
            r1 = (res1 instanceof Object[][]);
            r2 = (res2 instanceof Object[][]);
            m1 = r1==true ? (Object[][])res1 : null;
            m2 = r2==true ? (Object[][])res2 : null;
            x = 0;
            y = 0;
            isMat = 1;
            if (r1 && r2) {
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
            }else if (r1) {
                x = m1.length;
                y = m1[0].length;
            }else if (r2) {
                x = m2.length;
                y = m2[0].length;
            }
        }
        //vector
        else
        {
            r1 = (res1 instanceof Object[]);
            r2 = (res2 instanceof Object[]);
            arr1 = r1==true ? (Object[])res1 : null;
            arr2 = r2==true ? (Object[])res2 : null;
            x = 0;isMat=0;
            if (r1 && r2) {
                if (arr1.length != arr2.length && !(arr1.length == 1 || arr2.length == 1)) 
                {
                    Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(),
                            "Logica: Quiere operar 2 datos con difernte tamaño "+
                                    "Tam1: "+((Object[])res1).length+" Tam2: "+((Object[])res2).length));
                    return null;
                }
                x = arr1.length > arr2.length ? arr1.length : arr2.length;
            }
        }
        
        if (tipoResultado != null)
        {            
            try {
                switch(operador){
                    case OR:
                        if (isMat == 1) {
                            // <editor-fold defaultstate="collapsed" desc="Matrices">
                            if (r1 && r2){
                                mtotal = new Object[x][y];
                                for (int i=0; i < m1.length; i++) {
                                    for (int j=0; j < m1[i].length; j++) {
                                        mtotal[i][j] = (boolean)m1[i][j] || (boolean)m2[i][j];
                                    }
                                }
                                return mtotal;
                            }
                            else if (r1) {
                                mtotal = new Object[x][y];
                                for (int i=0; i < m1.length; i++) {
                                    for (int j=0; j < m1[i].length; j++) {
                                        mtotal[i][j] = (boolean)m1[i][j] || (boolean)res2;
                                    }
                                }
                                return mtotal;
                            }
                            else if (r2) {
                                mtotal = new Object[m2.length][m2[0].length];
                                for (int i=0; i < m2.length; i++) {
                                    for (int j=0; j < m2[i].length; j++) {
                                        mtotal[i][j] = (boolean)res1 || (boolean)m2[i][j];
                                    }
                                }
                                return mtotal;
                            }
                            return null;
                            // </editor-fold>
                        }else{
                            // <editor-fold defaultstate="collapsed" desc="Vectores">
                            if (r1 && r2){
                                total = new Object[x];
                                for (int i = 0; i < x; i++) {
                                    if (arr1.length == 1) {
                                        total[i] = (boolean)arr1[0] || (boolean)arr2[i];
                                    }
                                    else if (arr2.length == 1) {
                                        total[i] = (boolean)arr1[i] || (boolean)arr2[0];
                                    }
                                    else{
                                        total[i] = (boolean)arr1[i] || (boolean)arr2[i];
                                    }
                                }
                                return total;
                            }
                            else if (r1) {
                                total = new Object[arr1.length];
                                for (int i = 0; i < arr1.length; i++) {
                                    total[i] = (boolean)arr1[i] || (boolean)res2;
                                }
                                return total;
                            }
                            else if (r2) {
                                total = new Object[arr2.length];
                                for (int i = 0; i < arr2.length; i++) {
                                    total[i] = (boolean)arr2[i] || (boolean)res1;
                                }
                                return total;
                            }
                            // </editor-fold>
                            return (boolean)res1 || (boolean)res2;
                        }
                    case AND:
                        if (isMat == 1) {
                            // <editor-fold defaultstate="collapsed" desc="Matrices">
                            if (r1 && r2){
                                mtotal = new Object[x][y];
                                for (int i=0; i < m1.length; i++) {
                                    for (int j=0; j < m1[i].length; j++) {
                                        mtotal[i][j] = (boolean)m1[i][j] && (boolean)m2[i][j];
                                    }
                                }
                                return mtotal;
                            }
                            else if (r1) {
                                mtotal = new Object[x][y];
                                for (int i=0; i < m1.length; i++) {
                                    for (int j=0; j < m1[i].length; j++) {
                                        mtotal[i][j] = (boolean)m1[i][j] && (boolean)res2;
                                    }
                                }
                                return mtotal;
                            }
                            else if (r2) {
                                mtotal = new Object[m2.length][m2[0].length];
                                for (int i=0; i < m2.length; i++) {
                                    for (int j=0; j < m2[i].length; j++) {
                                        mtotal[i][j] = (boolean)res1 && (boolean)m2[i][j];
                                    }
                                }
                                return mtotal;
                            }
                            return null;
                            // </editor-fold>
                        }else{
                            // <editor-fold defaultstate="collapsed" desc="Vectores">
                            if (r1 && r2){
                                total = new Object[x];
                                for (int i = 0; i < x; i++) {
                                    if (arr1.length == 1) {
                                        total[i] = (boolean)arr1[0] && (boolean)arr2[i];
                                    }
                                    else if (arr2.length == 1) {
                                        total[i] = (boolean)arr1[i] && (boolean)arr2[0];
                                    }
                                    else{
                                        total[i] = (boolean)arr1[i] && (boolean)arr2[i];
                                    }
                                }
                                return total;
                            }
                            else if (r1) {
                                total = new Object[arr1.length];
                                for (int i = 0; i < arr1.length; i++) {
                                    total[i] = (boolean)arr1[i] && (boolean)res2;
                                }
                                return total;
                            }
                            else if (r2) {
                                total = new Object[arr2.length];
                                for (int i = 0; i < arr2.length; i++) {
                                    total[i] = (boolean)arr2[i] && (boolean)res1;
                                }
                                return total;
                            }
                            // </editor-fold>
                            return (boolean)res1 && (boolean)res2;
                        }
                    case NOT:
                        int rU = 0;
                        //matriz
                        if (resU instanceof Object[][]) {
                            rU = 2;                        
                        }
                        else if(resU instanceof Object[]){
                            rU = 1;
                        }

                        Object [] arrU = rU==1 ? (Object[])resU : null;
                        Object [][] mU = rU==2 ? (Object[][])resU : null;
                        boolean rr;
                        //Matriz
                        if (rU == 2) {
                            // <editor-fold defaultstate="collapsed" desc="Matrices">
                            x = mU.length;
                            y = mU[0].length;
                            mtotal = new Object[x][y];
                            for (int i=0; i < x; i++) {
                                for (int j=0; j < y; j++) {
                                    rr = (boolean)mU[i][j];
                                    mtotal[i][j] = !rr ;
                                }
                            }
                            return mtotal;
                            // </editor-fold>
                        }
                        //vector
                        else if (rU == 1) 
                        {
                            // <editor-fold defaultstate="collapsed" desc="Matrices">                                
                            total = new Object[arrU.length];
                            for (int i = 0; i < arrU.length; i++) {
                                rr = (boolean) arrU[i];
                                total[i] = !rr;
                            }
                            return total;                            
                            // </editor-fold>
                        }
                        rr = (boolean) resU;
                        return !rr;
                    default:
                        Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(),
                        "Logica: El operador no pertenece a la clase logica"));
                        return null;
                }
            } catch (Exception e) {
                Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(),
                        "Logica: Exception clase logica "+e.getMessage()));
            }
        }
        return null;
        
    }

    @Override
    public String getNombre(StringBuilder builder, String parent, int cont) {
        String nodo = "nodo" + ++cont;
        builder.append(nodo).append(" [label=\"Logica\"];\n");
        builder.append(parent).append(" -> ").append(nodo).append(";\n");

        cont = Integer.parseInt(op1.getNombre(builder, nodo, cont));

        String nodoOp = "nodo" + ++cont;
        builder.append(nodoOp).append(" [label=\"" + operador + "\"];\n");
        builder.append(nodo).append(" -> ").append(nodoOp).append(";\n");

        cont = Integer.parseInt(op2.getNombre(builder, nodo, cont));
        return ""+cont;
    }
    
}
