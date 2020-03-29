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
import entorno.Tipo;
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
public class grafLineas implements Instruccion{
    private LinkedList<NodoAST> lista;
    private int linea;
    private int col;
    private String nombr;
    
    public grafLineas(LinkedList<NodoAST> lista, int linea, int col) {
        this.lista = lista;
        this.linea = linea;
        this.col = col;
        this.nombr = "";
    }
    
    @Override
    public Object ejecutar(Entorno ent) {
        //LINEAS -> plot( v, type, xlab, ylab, main)
        //DISPERSION -> plot(mat,main, xlab, ylab, ylim)
        if (lista.size() == 5) 
        {
            Expresion exp = (Expresion) lista.get(1);
            Object o = exp.getValorImplicito(ent);
            o = convertirAString(o);
            if (o.toString().equalsIgnoreCase("P") || o.toString().equalsIgnoreCase("I") || o.toString().equalsIgnoreCase("O")) 
            {
                graficaLineas(ent);
            }
            else{
                graficaDispersion(ent);
            }            
        }
        else
        {
            Ventana.ggetVentana().listaError.add(new JError("Semantico",linea(), columna(),
                "Grafica necesita unicamente 5 Expresiones tiene:"+lista.size())); 
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
        builder.append(nodoIf).append(" [label=\"Lineas-Dispersion\"];\n");
        builder.append(nodo).append(" -> ").append(nodoIf).append(";\n");

        String nodoOp = "nodo" + ++cont;
        builder.append(nodoOp).append(" [label=\"" + this.nombr + "\"];\n");
        builder.append(nodo).append(" -> ").append(nodoOp).append(";\n");
        
        return String.valueOf(cont);
    }
    
     
    private boolean Generar(String archivo,String nombre, Object[] datos,String xlab,String ylab, String type) {
         
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
            printw.println("var data = [{");   
            
            printw.print("y: [");   
            for (int i = 0; i < datos.length; i++) {
                String cad = i != datos.length-1 ? "'"+datos[i]+"',":"'"+datos[i]+"'";
                printw.print(cad);
            }
            printw.println("],");
            
            printw.print("x: [");   
            for (int i = 1; i < datos.length+1; i++) {
                String cad = i != datos.length ? "'"+i+"',":"'"+i+"'";
                printw.print(cad);
            }
            printw.println("],");
            
            switch(type){
                case "P"://puntos
                    printw.println("mode: 'markers',");    
                    break;
                case "I"://lineas
                    printw.println("mode: 'lines',");    
                    break;
                case "O"://lineas y puntos
                    printw.println("mode: 'lines+markers',");    
                    break;
                default:
                    return false;
            }
            printw.println("marker: {\n" +
            "            color: 'rgb(219, 64, 82)',\n" +
            "            size: 15\n" +
            "        },\n" +
            "        line: {\n" +
            "            color: 'rgb(128, 0, 128)',\n" +
            "            width: 1\n" +
            "        }");            
            printw.println("}];");           
            printw.println("var layout = {title: '"+nombre+"',xaxis: {title:'"+xlab+"'},yaxis: {title:'"+ylab+"'} };");           
            printw.println("Plotly.newPlot('myDiv', data, layout);");           
               
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
        for (int i=0; i < 5 ; i++) {
        n = (char)(rnd.nextDouble() * 26.0 + 65.0 );
        cadena += n; }
        return cadena;
    }

    private Object convertirAString(Object type) {
        if (type instanceof Object[]) {
            return ((Object[])type)[0];
        }
        return type;
    }

    private void graficaDispersion(Entorno ent) {
        try {
            Object[][]MAT = (Object[][]) ((Expresion)lista.get(0)).getValorImplicito(ent);
            Object main = ((Expresion)lista.get(1)).getValorImplicito(ent);
            Object xlab =  ((Expresion)lista.get(2)).getValorImplicito(ent);
            Object ylab =  ((Expresion)lista.get(3)).getValorImplicito(ent);
            Object[] ylim =  (Object[])((Expresion)lista.get(4)).getValorImplicito(ent);
            xlab = convertirAString(xlab);
            ylab = convertirAString(ylab);
            main = convertirAString(main);

            if (MAT !=null && main != null && xlab != null && ylab != null && ylim !=null) 
            {
                this.nombr = (String) main;
                String n = getN();
                //Matriz a lineal
                int c=0;
                Object[][] matriz = (Object[][])MAT;
                Object[] v = new Object[matriz.length*matriz[0].length];
                for (int i = 0; i < matriz[0].length; i++){	
                    for (int j = 0; j < matriz.length; j++){
                        v[c] = matriz[j][i];
                        c++;
                    }
                } 
                //valor minimo y maximo
                double min = new Double(ylim[0].toString());
                double max = new Double(ylim[1].toString());
                if (min > max) {
                    max = min;
                    min = max;
                }
                if(GenerarDis(n,(String)main,v,(String)xlab,(String)ylab,min,max))
                {
                    Ventana.ggetVentana().agregarConsola("Grafica Dispersion: "+this.nombr+" Generada, nombre: "+n);
                    int input = JOptionPane.showConfirmDialog(null, "Desea Abrir la Grafica "+main);
                    if (input == 0) 
                    {
                        abrirGrafica(n);
                    }
                }
            }
            else
            {
                Ventana.ggetVentana().listaError.add(new JError("Semantico",linea(), columna(),
                "Grafica de Dispersion tiene datos nulos")); 
            }
        } catch (Exception e) {
            Ventana.ggetVentana().listaError.add(new JError("Ejecucion",linea(), columna(),
                "Grafica de Dispersion tiene error "+e.getMessage())); 
        }
    }

    private void graficaLineas(Entorno ent) {
        try {
            Object type = ((Expresion)lista.get(1)).getValorImplicito(ent);
            Object xlab =  ((Expresion)lista.get(2)).getValorImplicito(ent);
            Object ylab =  ((Expresion)lista.get(3)).getValorImplicito(ent);
            Object main =  ((Expresion)lista.get(4)).getValorImplicito(ent);
            type = convertirAString(type);
            xlab = convertirAString(xlab);
            ylab = convertirAString(ylab);
            main = convertirAString(main);

            Object valor1 = (Object) ((Expresion)lista.get(0)).getValorImplicito(ent);

            if (type !=null && xlab != null && ylab !=null && valor1 != null && main != null) {
                this.nombr = (String) main;
            //v = Matriz
                if (valor1 instanceof Object[][]) {
                    int c=0;
                    Object[][] matriz = (Object[][])valor1;
                    Object[] v = new Object[matriz.length*matriz[0].length];
                    for (int i = 0; i < matriz[0].length; i++){		// El primer índice recorre las columnas.
                        for (int j = 0; j < matriz.length; j++){	// El segundo índice recorre las filas.
                            v[c] = matriz[j][i];
                            c++;
                        }
                    }
                    valor1 = v;
                }
            //ya con el vector arreglado lo graficamos
                String n = getN();
                if(Generar(n, (String)main, (Object[])valor1,(String)xlab,(String)ylab,((String)type).toUpperCase())){
                   Ventana.ggetVentana().agregarConsola("Grafica Lineas: "+this.nombr+" Generada, nombre: "+n);
                int input = JOptionPane.showConfirmDialog(null, "Desea Abrir la Grafica "+this.nombr);
                    if (input == 0) 
                    {
                        abrirGrafica(n);
                    } 
                }
            }
            else
            {
                Ventana.ggetVentana().listaError.add(new JError("Semantico",linea(), columna(),
                "Grafica de Lineas tiene datos nulos")); 
            }
        } catch (Exception e) {
            Ventana.ggetVentana().listaError.add(new JError("Ejecucion",linea(), columna(),
                "Grafica de Lineas tiene error "+e.getMessage())); 
        }
    }
    
    private boolean GenerarDis(String archivo,String nombre, Object[] datos,String xlab,String ylab,double min,double max) {
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
            printw.println("var data = [{");   
            
            printw.print("y: [");   
            for (int i = 0; i < datos.length; i++) {
                double valorExp = new Double(datos[i].toString());
                //valor permitidos en Y
                if (valorExp >= min && valorExp <= max) {
                    String cad = i != datos.length-1 ? "'"+datos[i]+"',":"'"+datos[i]+"'";
                    printw.print(cad);  
                }                
            }
            printw.println("],");
            
            printw.print("x: [");   
            for (int i = 1; i < datos.length+1; i++) {
                String cad = i != datos.length ? "'"+i+"',":"'"+i+"'";
                printw.print(cad);
            }
            printw.println("],");
            printw.println("mode: 'markers',");
            printw.println("marker: {\n" +
            "            color: 'rgb(255, 217, 102)',\n" +
            "            size: 15\n" +
            "        },\n" +
            "        line: {\n" +
            "            color: 'rgb(128, 0, 128)',\n" +
            "            width: 1\n" +
            "        }");            
            printw.println("}];");           
            printw.println("var layout = {title: '"+nombre+"',xaxis: {title:'"+xlab+"'},yaxis: {title:'"+ylab+"'} };");           
            printw.println("Plotly.newPlot('myDiv', data, layout);");           
               
            printw.println("</script>");    
            
            printw.close();
            return true;
            
        }   catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "ERROR al generar archivo "+ex.getMessage());        
            return false;
        }
    }

}
