/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entorno;

import entorno.Tipo.Tipos;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 *
 * @author p_ab1
 */
public class Entorno {    
    private Hashtable tabla;
    private Hashtable funciones;
    private Entorno anterior;
    
    public Entorno(Entorno anterior)
    {
        this.tabla = new Hashtable();
        this.funciones = new Hashtable();

        this.anterior = anterior;
    }
    

    public void agregar(String id, Simbolo simbolo)
    {
        tabla.put(id, simbolo);
    }
    
    public void agregarFuncion(String id, Simbolo simbolo)
    {
        funciones.put(id, simbolo);
    }
    
    public Simbolo get(String id)
    {
        for (Entorno e = this; e != null; e = e.anterior)
        {
            Simbolo encontrado = (Simbolo)(e.tabla.get(id));
            if (encontrado != null)
                {
                return encontrado;
            }
        }
        System.out.println("El simbolo \"" + id + "\" no ha sido declarado en el entorno actual ni en alguno externo");
        return null;
    }
    
    public Simbolo getFuncion(String id)
    {
        for (Entorno e = this; e != null; e = e.anterior)
        {
            Simbolo encontrado = (Simbolo)(e.funciones.get(id));
            if (encontrado != null)
                {
                return encontrado;
            }
        }
        System.err.println("La Funcion \"" + id + "\" no ha sido declarado");
        return null;
    }
    
    public void reemplazar(String id, Simbolo nuevoValor)
        {
            for (Entorno e = this; e != null; e = e.getAnterior())
            {
                Simbolo encontrado = (Simbolo)(e.tabla.get(id.toLowerCase()));
                if (encontrado != null)
                {
                    e.tabla.put(id.toLowerCase(), nuevoValor);
                }
            }
        }

    public void reemplazarFun(String id, Simbolo nuevoValor)
        {
            for (Entorno e = this; e != null; e = e.getAnterior())
            {
                Simbolo encontrado = (Simbolo)(e.funciones.get(id.toLowerCase()));
                if (encontrado != null)
                {
                    e.funciones.put(id.toLowerCase(), nuevoValor);
                }
            }
        }
    
    public void Mostrar(){
        System.out.println("***************************Tabla de Simbolos ******************************");
        int c= 0;
        for (Entorno e = this; e != null; e = e.anterior)
        {
            Enumeration<Simbolo> enumeration = e.tabla.elements();
            System.out.println("Entorno "+c);
            while (enumeration.hasMoreElements())
            {
                Simbolo sim = (Simbolo)enumeration.nextElement();
                Tipo t = sim.getTipo();
                Object val = sim.getValor();
                if (val instanceof Object[]) {
                    Object[] s = (Object[])val;
                    val = Arrays.toString(s);
                }
                System.out.println(
                        "Variable:" + sim.getIdentificador()+
                        "\tTipo:"+((t==null) ? "null" : t.getTipoPrimitivo())+
//                        "\tTipo:"+t+
                        "\tRol:"+sim.getRol()+
                        "\tValor:"+((val==null) ? "null" : val.toString()));
            }
            c++;
        } 
    }
     public boolean existe(String id)
        {
            for (Entorno e = this; e != null; e = e.anterior)
            {
                if (e.tabla.containsKey(id))
                {
                    return true;
                }
            }
            return false;
        }

     public boolean existeFuncion(String id)
        {
            for (Entorno e = this; e != null; e = e.anterior)
            {
                if (e.funciones.containsKey(id))
                {
                    return true;
                }
            }
            return false;
        }
     
    public boolean existeEnActual(String id)
    {
        Simbolo encontrado = (Simbolo)(tabla.get(id));
        return encontrado != null;
    }
    
    public Hashtable getTabla() {
        return tabla;
    }

    public void setTabla(Hashtable tabla) {
        this.tabla = tabla;
    }

    public Entorno getAnterior() {
        return anterior;
    }

    public void setAnterior(Entorno anterior) {
        this.anterior = anterior;
    }

    public void MostrarFunciones() {
        System.out.println("***************************Tabla de FUNCIONES ******************************");
        int c= 0;
        for (Entorno e = this; e != null; e = e.anterior)
        {
            Enumeration<Simbolo> enumeration = e.funciones.elements();
            System.out.println("Entorno "+c);
            while (enumeration.hasMoreElements())
            {
                Simbolo sim = (Simbolo)enumeration.nextElement();
                //Tipos t = sim.getTipo().getTipoPrimitivo();
                Object val = sim.getValor();
                if (val instanceof Object[]) {
                    Object[] s = (Object[])val;
                    val = Arrays.toString(s);
                }
                System.out.println(
                        "Variable:" + sim.getIdentificador()+
                        "\tTipo:"+((sim.getTipo()==null) ? "null" : sim.getTipo().getTipoPrimitivo())+
                        "\tRol:"+sim.getRol()+
                        "\tValor:"+((val==null) ? "null" : val.toString()));
            }
            c++;
        }
    }

    public Hashtable getFunciones() {
        return funciones;
    }

    
}
