/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast.instrucciones;

import ast.Expresion;
import ast.Instruccion;
import entorno.Entorno;
import entorno.Simbolo;
import entorno.Simbolo.Rol;
import entorno.Tipo;
import entorno.Var;

/**
 *
 * @author p_ab1
 */
public class Declaracion implements Instruccion{
    private Var var;
    private Expresion derecha;
    private int linea;
    private int col;

    public Declaracion(Var var, Expresion derecha, int linea, int col) {
        this.var = var;
        this.derecha = derecha;
        this.linea = linea;
        this.col = col;
    }
    
    
    @Override
    public Object ejecutar(Entorno ent) {
        String nombreIzq = var.getId();
        if (var.getDim() > 1 ) 
        {
            //arreglos
        }
        else
        {
            if (ent.existeEnActual(nombreIzq)) 
            {
            //        Ventana.getVentana().listaError.add(new JError("Semantico", linea(), columana(),"ERROR la variable "+nombreIzq+" ya existe"));
//                    JOptionPane.showMessageDialog(null, "ERROR la variable ya existe");
            }
            else
            {
                Tipo tipoIngresado = derecha.getTipo(ent);
                Object derechaIngresado = derecha.getValorImplicito(ent);
                Simbolo sim = new Simbolo(nombreIzq,derechaIngresado,tipoIngresado,Rol.VECTOR,var.getDim());
                ent.agregar(nombreIzq, sim);
            }
        }
        return null;
    }

    @Override
    public int linea() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int columna() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNombre() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
