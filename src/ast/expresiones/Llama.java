/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast.expresiones;

import ast.Expresion;
import ast.NodoAST;
import ast.instrucciones.Funcion;
import ast.instrucciones.funciones.ListVar;
import entorno.Entorno;
import entorno.Simbolo;
import entorno.Simbolo.Rol;
import entorno.Tipo;
import entorno.Tipo.Tipos;
import java.util.LinkedList;
import olc2.p1_201504242.JError;
import olc2.p1_201504242.Ventana;

/**
 *
 * @author p_ab1
 */
public class Llama implements Expresion{
    private String id;
    private LinkedList<NodoAST> valores;
    private int linea;
    private int col;

    //Llamada a funcion normal con parametros
    public Llama(String id, LinkedList<NodoAST> valores, int linea, int col) {
        this.id = id;
        this.valores = valores;
        this.linea = linea;
        this.col = col;
    }
    
    //Llamada a funcion normal sin parametros
    public Llama(String id, int linea, int col) {
        this.id = id;
        this.valores = new LinkedList();
        this.linea = linea;
        this.col = col;
    }
    
    @Override
    public Tipo getTipo(Entorno ent) {
        if (ent.getFuncion(id+"_"+valores.size()) != null) 
        {
            return ent.getFuncion(id+"_"+valores.size()).getTipo();
        }
        else
        {
            Ventana.ggetVentana().listaError.add(new JError("Sintactico", linea(), columna(), "Error en la funcion "+id+" no existe"));

            //System.err.println("ERROR variable "+id+" no existe en el entorno actual en tipo");
            return null;
        }       
    }

    @Override
    public Object getValorImplicito(Entorno ent) {
        try {
            if (ent.existeFuncion(id+"_"+valores.size())) 
            {
                Entorno local = new Entorno(ent);
                Funcion funcion = (Funcion)ent.getFuncion(id+"_"+valores.size());           
                LinkedList<Simbolo> formales = funcion.getParametrosFormales();
                if (formales.size() == valores.size()) 
                {
                    Object a = resolverFuncion(formales,ent,local,funcion);
                    Tipo nuevoTipo = colocarTipo(a);
                    funcion.setTipo(nuevoTipo);
                    ent.reemplazarFun(id, funcion);
                    return a;
                }
                else
                {
                    Ventana.ggetVentana().listaError.add(new JError("Sintactico", linea(), columna(), "Error en los parametros, cantidad de parametros"));

//                    System.out.println("ERROR parametros formales ");
                }
            }
            else
            {
                //Ventana.getVentana().listaError.add(new JError("Semantico", linea(), columana(),
                //System.err.println("ERROR la funcion con nombre: "+id+" no existe en ningun entorno linea:"+linea());
                Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(),
                "la funcion con nombre: "+id+" no existe en ningun entorno"));        
            }
        } catch (Exception e) {
            //WVentana.getVentana().listaError.add(new JError("Semantico Fatal", linea(), columana(),
            //System.out.println(
          //  System.err.println("ERROR la funcion con nombre: "+id+" tiene un error linea:"+linea()+" "+e.getMessage());
            Ventana.ggetVentana().listaError.add(new JError("Ejecucion", linea(), columna(),
            "la funcion con nombre: "+id+" tiene un error")); 
        }
        return null;
    }

    @Override
    public int linea() {
        return this.linea;
    }

    @Override
    public int columna() {
        return this.col;
    }

    @Override
    public String getNombre(StringBuilder builder, String parent, int cont) {
        String nodo = "nodo" + ++cont;
        builder.append(nodo).append(" [label=\"Llamada\"];\n");
        builder.append(parent).append(" -> ").append(nodo).append(";\n");

        String nodoOp1 = "nodo" + ++cont;
        builder.append(nodoOp1).append(" [label=\""+this.id+ "\"];\n");
        builder.append(nodo).append(" -> ").append(nodoOp1).append(";\n");
        
        String nodoO = "nodo" + ++cont;
        builder.append(nodoO).append(" [label=\"Parametros: "+this.valores.size()+ "\"];\n");
        builder.append(nodo).append(" -> ").append(nodoO).append(";\n");
        
        return ""+ cont;
    }

    private Tipo tipoDominante(Tipo[] tp) {
        for (Tipo tipo : tp) {
            if (tipo.isString()) {
                return new Tipo(Tipos.STRING);
            }
        }
        for (Tipo tipo : tp) {
            if (tipo.isDouble()) {
                return new Tipo(Tipos.DOUBLE);
            }
        }        
        for (Tipo tipo : tp) {
            if (tipo.isInt()) {
                return new Tipo(Tipos.INT);
            }
        }
        for (Tipo tipo : tp) {
            if (tipo.isBoolean()) {
                return new Tipo(Tipos.BOOL);
            }
        }
        return null;
    }

    private Object resolverFuncion(LinkedList<Simbolo> formales, Entorno ent, Entorno local, Funcion funcion) {
        Simbolo sim_aux;
        String id_aux;
        Expresion exp_aux;                
        Object valor = null;
        Tipos tipoPar_aux = null;

        LinkedList<Simbolo> datos = new LinkedList();
        for (int i = 0; i < valores.size(); i++)
        {
            //traigo todo el simbolo
            sim_aux = formales.get(i);                            
            id_aux = sim_aux.getIdentificador();
            Object def = "";
            //traigo el valor que tenga en la llamada
            if (valores.get(i) instanceof Literal) {
                Literal ll = (Literal) valores.get(i);
                def = ll.getValorImplicito(ent);
            }
            if (def.equals("default")) 
            {
                exp_aux = (Expresion)sim_aux.getValor(); 
                if (exp_aux == null) 
                {
                    //exp_aux = (Expresion)valores.get(i);
                    System.err.println("ERROR la funcion con nombre: "+id+" tiene un error linea: "+linea()+" columna: "+columna()
                            +"Parametro: "+(i+1)+" no tiene valor default"
                    );
                    Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(),
                    "la funcion con nombre: "+id+" tiene un error"+" Parametro: "+(i+1)+" no tiene valor default")); 
                }
            }
            else
            {
                exp_aux = (Expresion)valores.get(i);
            }
            valor = exp_aux.getValorImplicito(ent); 
            //vecotor 
            if (valor instanceof Object[]) 
            {
                tipoPar_aux = colocarTipoVec((Object[])valor).getTipoPrimitivo();
            }
            else{
                tipoPar_aux = colocarTipo(valor).getTipoPrimitivo();
            }
            
            
            //ACA TIENE QUE MODIFICARSE CUANDO YA HAYAN VECTORES 
            //EL ROL Y LAS DIMENSIONES CAMBIARAN
            Simbolo s = new Simbolo(id_aux, valor, new Tipo(tipoPar_aux),Rol.VECTOR,0);
            datos.addLast(s);
        }
        //Agrego todos los datos al local
        for (int i = 0; i < datos.size(); i++) {
            Simbolo loca = datos.get(i);
            local.agregar(loca.getIdentificador(), loca);
        }
        Object a = funcion.ejecutar(local);
        return a;
    }

    private Tipo colocarTipo(Object r) {
        if (r instanceof Integer) {
            return new Tipo(Tipos.INT);
        }else if(r instanceof Boolean){
            return new Tipo(Tipos.BOOL);
        }else if(r instanceof String){
            return new Tipo(Tipos.STRING);
        }else if(r instanceof Double){
            return new Tipo(Tipos.DOUBLE);
        }else{
            return new Tipo(Tipos.VOID);
        }
        
    }
    
     private Tipo colocarTipoVec(Object[] r) {
       Tipo []tp = new Tipo[r.length];
        int c = 0;
        for (Object nodo : r) 
        {
            Tipo ti = tipo(nodo);
            tp[c] = ti;
            c++;
                                
        }
        return tipoDominante(tp);
        
    }
    
      private Tipo tipo(Object r) {
        if (r instanceof Integer) {
            return new Tipo(Tipo.Tipos.INT);
        }else if(r instanceof Boolean){
            return new Tipo(Tipo.Tipos.BOOL);
        }else if(r instanceof String){
            return new Tipo(Tipo.Tipos.STRING);
        }else if(r instanceof Double){
            return new Tipo(Tipo.Tipos.DOUBLE);
        }else{
            return new Tipo(Tipo.Tipos.VOID);
        }
    }
      
    private Tipo colocarTipo(ListVar r) {
       Tipo []tp = new Tipo[r.size()];
        int c = 0;
        for (Object nodo : r) 
        {
            Tipo ti = tipo(nodo);
            tp[c] = ti;
            c++;
                                
        }
        return tipoDominante(tp);
        
    }
    
    
}
