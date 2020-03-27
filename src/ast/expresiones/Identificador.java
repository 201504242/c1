
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast.expresiones;

import ast.Expresion;
import ast.instrucciones.funciones.FuncionMatrix;
import ast.instrucciones.funciones.ListVar;
import ast.instrucciones.funciones.ModificadorMatrix;
import entorno.Entorno;
import entorno.Simbolo;
import entorno.Simbolo.Acc;
import entorno.Simbolo.Rol;
import entorno.Tipo;
import entorno.nodoExp;
import java.util.LinkedList;
import olc2.p1_201504242.JError;
import olc2.p1_201504242.Ventana;

/**
 *
 * @author p_ab1
 */
public class Identificador implements Expresion{

    private String val;
    private LinkedList<nodoExp> lista;
    private int linea;
    private int col;
    private boolean isAssign;

    // solo variable b
    
    public Identificador(String val, int linea, int col, boolean isAssign) {
        this.val = val;
        this.linea = linea;
        this.col = col;
        this.isAssign = isAssign;
    }

    
    //[E] | [[E]] | [E,E] | [E,] | [,E]
    public Identificador(String val,LinkedList<nodoExp> lista,boolean isAssign,int linea,int col) {
        this.col = col;
        this.lista = lista;
        this.linea = linea;
        this.val = val;
        this.isAssign = isAssign;
    }
    
    
    @Override
    public Tipo getTipo(Entorno ent) {
        if (ent.get(val) != null) {
            return ent.get(val).getTipo();
        }
        else{
            System.err.println("Error Semantico linea:"+linea()+" col:"+columna() + "variable "+val+" no existe en el entorno actual");
                //Ventana.getVentana().listaError.add(new JError("Semantico", linea(), columana(), 
                //        "ERROR variable "+val+" no existe en el entorno actual"));
            return null;
        }
    }

    @Override
    public Object getValorImplicito(Entorno ent) {
        if (ent.existe(val)) 
        {
            Simbolo sim = ent.get(val);
            //si encontro que ya se declaro
            if (sim != null) 
            {
                // tiene asignacion
                if (isAssign) 
                {
                    //NO tiene una lista id[E][E]
                    if (lista == null) 
                    {
                        return sim;
                    }
                    // tiene una lista 
                    else
                    {
                        // Es una matrix asignandose 
                        if(sim.getTipo().getTipoPrimitivo() == Tipo.Tipos.MATRIZ)
                        {
                            try{
                                if(lista.size()>0)
                                {
                                    nodoExp nodoexpresion = lista.get(0);
                                     
                                    return new ModificadorMatrix(nodoexpresion, (Object[][])sim.getValor(), ent , sim);
                                }
                            }
                            catch(Exception e){
                                 System.err.println("Error acceso matriz");
                            }
                        }
                        //puede ser acceso a vector 
                        if (sim.getRol() == Rol.VECTOR) 
                        {
                            if (sim.getValor() instanceof Object[]) {
                                Simbolo aux = traerSimbolos(sim, ent);
                                if (aux != null) {
                                    return aux;
                                }
                            }
                            else if(sim.getValor() instanceof Object)
                            {
                                Simbolo aux = traerSimbolo(sim, ent);
                                if (aux != null) {
                                    return aux;
                                }
                            }
                        }
                        else if (sim.getRol() == Rol.LISTA) 
                        {
                            //varios valores en la lista
                            if (sim.getValor() instanceof ListVar) {
                                ListVar list = (ListVar) sim.getValor();
                                Simbolo obj = traerSimbolLista(sim,ent);
                                return obj; 
                            }
                            //un solo valor en la lsita
                            else if (sim.getValor() instanceof Object) {
                                ListVar listaVar = new ListVar();
                                Simbolo ss = new Simbolo(val, listaVar.add(sim.getValor()), new Tipo(Tipo.Tipos.LIST), Rol.LISTA, 0);
                                return ss;
                            }
                        }
                        
                            return sim.getValor();
                        
                    }
                }
                //no tiene asignacion
                else
                {
                    //NO tiene una lista id[E][E]
                    if (lista == null) 
                    {
                        if (sim.getValor() instanceof Object[]) 
                        {
                            return (Object[])sim.getValor();                         
                        }
                        return sim.getValor();
                    }
                    // tiene una lista 
                    else
                    {
                        // GET MATRIZ
                        if(sim.getTipo().getTipoPrimitivo() == Tipo.Tipos.MATRIZ)
                        {
                            try{
                                if(lista.size()>0)
                                {
                                    nodoExp nodoexpresion = lista.get(0);
                                    Object res =  FuncionMatrix.getValoresMatriz(nodoexpresion,(Object[][])sim.getValor(), ent);
                                    return res;
                                }
                            }
                            catch(Exception e){
                                 System.err.println("Error acceso matriz");
                            }
                        }
                        // arreglo de valores
                        if (sim.getRol() == Rol.VECTOR && sim.getValor() instanceof Object[])
                        {
                            Object [] aux =traerValores(sim,ent);
                            return aux;                            
                        }   
                        //un valor
                        else if(sim.getRol() == Rol.VECTOR && sim.getValor() instanceof Object)
                        {
                            Object aux = traerValor(sim,ent);
                            return aux;
                        }
                        //listas
                        else if (sim.getRol() == Rol.LISTA && sim.getValor() instanceof ListVar) 
                        {
                            //LinkedList aux = new LinkedList();
                            ListVar list = (ListVar) sim.getValor();
                            Object ob = getValorLista(lista,0,ent,list,null);
                            return ob; 
                        }
                       
                                   
//                            ListVar list = (ListVar) sim.getValor();
//                            Object ob = getValorLista(lista,0,ent,list,null);
//                            return ob; 
                        
                            
//                            int tama_list = list.size() ;
//                            //recorro la lista de dimensiones
//                            for (int i = 0; i < lista.size(); i++) 
//                            {
//                                nodoExp expLista = lista.get(i);
//                                int posicion = (int) expLista.getExp1().getValorImplicito(ent);
//                                if (posicion <= tama_list && posicion > 0) 
//                                {
//                                    //Acceso [E] tipo1 
//                                    if (expLista.getTipoAcceso() == Acc.tipo1) {
//                                       // ListVar listaRetornar = new ListVar();
//                                        Object valor = list.get(posicion-1);
//                                       // listaRetornar.add(valor);
//                                        aux.add(valor);
//                                    }
//                                    //Acceso [[E]] tipo2
//                                    else if(expLista.getTipoAcceso() == Acc.tipo2)
//                                    {
//                                        //Object [] Array = new Object[1];
//                                        //Array[0] = list.get(posicion);
//                                        aux.add(lista.get(posicion-1));
//                                    }
//                                }
//                                else
//                                {
//                                    Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(),
//                                    "Indentificador: "+val+" Indice fuera de rango List: "+tama_list+" Acceso: "+posicion)); 
//                                    return null;
//                                }
//                            }
                            
//                            return aux.toArray();
                        
                    }
                }
            }
        }        
        else
        {
            
            Simbolo sim2 = new Simbolo(val, Rol.VECTOR);
            ent.agregar(val, sim2);
            return sim2;
        }
        //simbolo no existe 
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
    public String getNombre() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public LinkedList<nodoExp> getLista() {
        return lista;
    }

    public void setLista(LinkedList<nodoExp> lista) {
        this.lista = lista;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public boolean isIsAssign() {
        return isAssign;
    }

    public void setIsAssign(boolean isAssign) {
        this.isAssign = isAssign;
    }

    private Object[] traerValores(Simbolo sim,Entorno ent) {
        Object [] aux = new Object[1];
        Object []valor_vector  = (Object[])sim.getValor();
        //recorro la lista
        int tama_vector = valor_vector.length;                                                        
        for (int i = 0; i < lista.size(); i++) 
        {
            nodoExp expLista = lista.get(i);
            int numLista = (int)expLista.getExp1().getValorImplicito(ent);
            if (numLista <= tama_vector && numLista != 0) 
            {
                // [E]
                if(expLista.getTipoAcceso() == Acc.tipo1 && numLista > 0)
                {   
                    if (i == 0) {
                        aux[0] = valor_vector[numLista-1] ;                                                                                    
                    }
                    else{
                        aux[0] = aux[0];
                    }
                }
                else
                {

                }
            }
            else
            {
                System.err.println("Indentificador: "+val+" Indice fuera de rango "
                        + "Vector: "+tama_vector+" Acceso: "+numLista
                        + " lin: "+linea()+" Col: "+columna());  
                Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(),
                "Indentificador: "+val+" Indice fuera de rango Vector: "+tama_vector+" Acceso: "+numLista)); 
                return null;
            }                                    
        }
        return aux;
    }

    private Simbolo traerSimbolo(Simbolo sim, Entorno ent){
        Object valor_vector  = (Object)sim.getValor();
        LinkedList<Object> aux = new LinkedList();
        for (int i = 0; i < lista.size(); i++) {
            nodoExp expLista = lista.get(i);
            int numLista = (int)expLista.getExp1().getValorImplicito(ent);
            if (numLista > 0)
            {
                for (int j = 0; j < numLista; j++) 
                {
                    if (j == 0) {
                        aux.add(valor_vector);
                    //sim.setValor(valor_vector);
                    }
                    else
                    {
                        sim.lugar = numLista;
                        aux.add(null);
                    }
                }
            }
            else
            {
                System.err.println("Indentificador: "+val+" Indice fuera de rango "
                        + "Vector: 1 Acceso: "+numLista
                        + " lin: "+linea()+" Col: "+columna());  
                Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(),
                "Indentificador: "+val+" Indice fuera de rango Vector: 1 Acceso: "+numLista)); 
                return null;
            }  
        }
        sim.setValor(aux.toArray());
        return sim; 
    }
    
    private Object traerValor(Simbolo sim, Entorno ent) {
        Object  aux = new Object[1];
        Object valor_vector  = (Object)sim.getValor();
        for (int i = 0; i < lista.size(); i++) {
            nodoExp expLista = lista.get(i);
            int numLista = (int)expLista.getExp1().getValorImplicito(ent);
            if (numLista != 0 && numLista ==1) 
            {
                 if (i == 0) {
                    aux = valor_vector;                                                                                    
                }
                else{
                    aux = aux;
                }

            }
            else
            {
                System.err.println("Indentificador: "+val+" Indice fuera de rango "
                        + "Vector: 1 Acceso: "+numLista
                        + " lin: "+linea()+" Col: "+columna());  
                Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(),
                "Indentificador: "+val+" Indice fuera de rango Vector: 1 Acceso: "+numLista)); 
                return null;
            }  
        }
        return aux;                  
    }

    private Simbolo traerSimbolos(Simbolo sim, Entorno ent) {
        Object []valor_vector  = (Object[])sim.getValor();
        LinkedList<Object> aux = new LinkedList();
        int tamalista = lista.size();
        for (int i = 0; i < tamalista; i++) {
            nodoExp expLista = lista.get(i);
            int numLista = (int)expLista.getExp1().getValorImplicito(ent);
            if (numLista > 0)
            {
                int grande = numLista > valor_vector.length ? numLista :  valor_vector.length;
                for (int j = 0; j < valor_vector.length; j++) 
                {
                    if (j == 0) {
                        aux.add(valor_vector[j]);
                    }
                    else
                    {
                        sim.lugar = numLista;
                        aux.add(valor_vector[j]);
                    }
                }
                for (int j = valor_vector.length; j < grande; j++) 
                {
                    aux.add(null);
                }
                
            }
            else
            {
                System.err.println("Indentificador: "+val+" Indice fuera de rango "
                        + "Vector: 1 Acceso: "+numLista
                        + " lin: "+linea()+" Col: "+columna());  
                Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(),
                "Indentificador: "+val+" Indice fuera de rango Vector: 1 Acceso: "+numLista)); 
                return null;
            } 
        
        }
        sim.setValor(aux.toArray());
        return sim; 
    }

    private Object getValorLista(LinkedList<nodoExp> l, int indiceAcutal, Entorno ent, ListVar list,Object[] vector) {
        try {
            nodoExp exp =l.get(indiceAcutal);
            int index = (int) exp.getExp1().getValorImplicito(ent);
            if (indiceAcutal == l.size()-1) 
            {
                //[E]
                if (exp.getTipoAcceso() == Acc.tipo1) 
                {
                    if (vector != null && list == null) 
                    {
                        Object ob = vector[index-1];
                        return ob;
                    }
                    Object ob = list.get(index-1);
                    ListVar ll = new ListVar();
                    ll.add(ob);
                    return ll;                    
                }
                //[[E]]
                else if(exp.getTipoAcceso() == Acc.tipo2)
                {
                    Object ob = list.get(index-1);
                    return ob;
                }                
            }
            else
            {
                //[E]
                if (exp.getTipoAcceso() == Acc.tipo1) 
                {
                    if (list.get(index-1) instanceof ListVar) {
                        list = (ListVar) list.get(index-1);
                    }else{
                        Object[] extraAux = (Object[]) list.get(index-1);
                        ListVar ll = new ListVar();
                        for (Object obje : extraAux) {
                            ll.add(obje);
                        }
                        return getValorLista(l, indiceAcutal+1, ent, ll,null);

                    }
                    return getValorLista(l, indiceAcutal+1, ent, list,null);
                }
                //[[E]]
                else if(exp.getTipoAcceso() == Acc.tipo2)
                {
                    Object ob = list.get(index-1);
//                    if (ob instanceof Object ) {
//                        Object [] valorR = new Object[1];
//                        valorR[0]=ob;
//                        return getValorLista(l, indiceAcutal+1, ent, null,valorR);
//                    }
                    if (ob instanceof Object[]) {
                        return getValorLista(l, indiceAcutal+1, ent, null,(Object[])ob);
                    }
                    //list = (ListVar) list.get(index-1);
                    return getValorLista(l, indiceAcutal+1, ent, (ListVar) ob,null);
                    
                    
                    //return ob;
                }      
                list = (ListVar) list.get(index-1);
                return getValorLista(l, indiceAcutal+1, ent, list,null);
            }
        } catch (Exception e) {
            Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(),
                "Indentificador: "+val+" Indice fuera de rango. "+e.getMessage())); 
            return null;
        }
        return null;
    }

    private Simbolo traerSimbolLista(Simbolo sim, Entorno ent) {
        ListVar valor_vector  = (ListVar)sim.getValor();
        LinkedList<Object> aux = new LinkedList();
        for (int i = 0; i < lista.size(); i++) {
            nodoExp expLista = lista.get(i);
            int numLista = (int)expLista.getExp1().getValorImplicito(ent);
            if (numLista > 0)
            {
                for (int j = 0; j < numLista; j++) 
                {
                    if (j == 0) {
                        aux.add(valor_vector.get(0));
                    //sim.setValor(valor_vector);
                    }
                    else
                    {
                        sim.lugar = numLista;
                        aux.add(null);
                    }
                }
            }
            else
            {
                System.err.println("Indentificador: "+val+" Indice fuera de rango "
                        + "Vector: 1 Acceso: "+numLista
                        + " lin: "+linea()+" Col: "+columna());  
                Ventana.ggetVentana().listaError.add(new JError("Semantico", linea(), columna(),
                "Indentificador: "+val+" Indice fuera de rango Vector: 1 Acceso: "+numLista)); 
                return null;
            }  
        }
        sim.setValor(aux.toArray());
        return sim; 
    }
    
    
}

