/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast.instrucciones.graficas;

import ast.Expresion;
import ast.Instruccion;
import ast.NodoAST;
import entorno.Entorno;
import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.JOptionPane;
import olc2.p1_201504242.JError;
import olc2.p1_201504242.Ventana;

/**
 *
 * @author p_ab1
 */
public class grafDispersion implements Instruccion{
    private LinkedList<NodoAST> lista;
    private int linea;
    private int col;
    private String nombr;

    public grafDispersion(LinkedList<NodoAST> lista, int linea, int col) {
        this.lista = lista;
        this.linea = linea;
        this.col = col;
    }
    
    @Override
    public Object ejecutar(Entorno ent) {
        //plot(mat,main, xlab, ylab, ylim
         if (lista.size() == 5) 
        {
            try {
                Object[]MAT = (Object[]) ((Expresion)lista.get(0)).getValorImplicito(ent);
                Object main = ((Expresion)lista.get(1)).getValorImplicito(ent);
                Object xlab =  ((Expresion)lista.get(2)).getValorImplicito(ent);
                Object ylab =  ((Expresion)lista.get(3)).getValorImplicito(ent);
                Object ylim =  ((Expresion)lista.get(4)).getValorImplicito(ent);
                
//                if (v !=null && main != null && nombre != null) 
//                {
                    this.nombr = (String) main;
//                    if (datos.length != titulos.length){
//                        Ventana.ggetVentana().listaError.add(new JError("Semantico",linea(), columna(),
//                        "Grafica de Lineas datos: "+datos.length+" titulos: " +titulos.length)); 
//                    }
//                    String n = getN();
//                    if(Generar(n,(String)nombre,datos,titulos))
//                    {
//                          Ventana.ggetVentana().agregarConsola("GrafDispersion "+this.nombr+" Generada, nombre: "+n);
//                        int input = JOptionPane.showConfirmDialog(null, "Desea Abrir la Grafica "+nombre);
//                        if (input == 0) 
//                        {
//                            abrirGrafica(n);
//                        }
//                    }
//                }
//                else
//                {
//                    Ventana.ggetVentana().listaError.add(new JError("Semantico",linea(), columna(),
//                    "Grafica de Lineas tiene datos nulos")); 
//                }
            } catch (Exception e) {
                Ventana.ggetVentana().listaError.add(new JError("Ejecucion",linea(), columna(),
                    "Grafica de Dispersion tiene error "+e.getMessage())); 
            }
        }
        else
        {
            Ventana.ggetVentana().listaError.add(new JError("Semantico",linea(), columna(),
                "Grafica de Dispersion necesita unicamente 5 Expresiones tiene:"+lista.size())); 
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
        builder.append(nodo).append(" [label=\"Grafica\"];\n");
        builder.append(parent).append(" -> ").append(nodo).append(";\n");
        
        String nodoIf = "nodo" + ++cont;
        builder.append(nodoIf).append(" [label=\"Dispercion\"];\n");
        builder.append(nodo).append(" -> ").append(nodoIf).append(";\n");

        String nodoOp = "nodo" + ++cont;
        builder.append(nodoOp).append(" [label=\"" + this.nombr + "\"];\n");
        builder.append(nodo).append(" -> ").append(nodoOp).append(";\n");
        
        return String.valueOf(cont);
    }
    
      private boolean Generar(String archivo,String nombre, Object[] datos,Object[] titulos) {
         
        FileWriter filewriter = null;
        PrintWriter printw = null;
        try{
            filewriter = new FileWriter("Graficas\\"+archivo+".html");//declarar el archivo
            printw = new PrintWriter(filewriter);//declarar un impresor

            printw.println("<head>");
            printw.println("<script src=\"plotly-latest.min.js\"></script>");     
            printw.println("</head>");
            
            printw.println("<body>");
            printw.println("<center><div id='myDiv'></div></center>");
            printw.println("</body>");
            
            printw.println("<script>");
            printw.println("var dataPie = [{");
            printw.println("title:'"+nombre+"',");
            printw.print("values:[");            
            for (int i = 0; i < datos.length; i++) {
                String cad = i != datos.length-1 ? "'"+datos[i]+"',":"'"+datos[i]+"'";
                printw.print(cad);
            }
            printw.println("],");
            printw.print("labels:[");     
            for (int i = 0; i < datos.length; i++) {
                String cad;
                if (i < titulos.length) {
                    cad = i != titulos.length-1 ? "'"+titulos[i]+"',":"'"+titulos[i]+"'";
                }
                else{
                    cad = i != datos.length-1 ? ",'Des"+i+"'":",'Des"+i+"'";
                }
                
                printw.print(cad);
            }
            printw.println("],");
            printw.println("type: 'pie'");            
            printw.println("}];");           
            printw.println("var layoutPie = { height: 400,width: 500};");           
            printw.println("Plotly.newPlot('myDiv', dataPie, layoutPie);");           
               
            printw.println("</script>");    
            
            printw.close();
            return true;
            
        }   catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "ERROR al generar archivo "+ex.getMessage());        
            return false;
        }
    }

    private void abrirGrafica(String nombre){
        try {
            File objetofile = new File ("Graficas\\"+nombre+".html");
            Desktop.getDesktop().open(objetofile);
        } catch (IOException exx) {
            JOptionPane.showMessageDialog(null, "No existe "+nombre);
        }
    }
 
    private String getN(){
        char n;
        Random rnd = new Random();
        String cadena = new String();
        for (int i=0; i < 4 ; i++) {
        n = (char)(rnd.nextDouble() * 26.0 + 65.0 );
        cadena += n; }
        return cadena;
    }
}
