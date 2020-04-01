/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olc2.p1_201504242;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

/**
 *
 * @author Jorge
 */
public class Editor extends RTextScrollPane {
    private RSyntaxTextArea textArea;
    private  File archivo = null;
    private  RTextScrollPane sp;
    public Editor ( File archivo , RSyntaxTextArea text) throws FileNotFoundException, IOException
    {
        super(text);
        this.archivo = archivo;
        this.textArea = text;
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        textArea.setCodeFoldingEnabled(true);
        FileReader fr = null;
        BufferedReader br = null;
        fr = new FileReader (archivo);
         br = new BufferedReader(fr);
         String linea;
         String total = "";
         while((linea=br.readLine())!=null){
             total += linea + "\r\n";
            textArea.setText(total);
         }
        textArea.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent ce) {
                 int linenum = 1;
                int columnnum = 1;
                try {
                    int caretpos = textArea.getCaretPosition();
                    linenum = textArea.getLineOfOffset(caretpos);
                    columnnum = caretpos - textArea.getLineStartOffset(linenum);
                    linenum += 1;
                }
                catch(Exception ex) { }
                updateStatus(linenum, columnnum);
            }
        });
        
         //DOM.setRecursos(archivo.getParent());
    }

    private void updateStatus(int linenumber, int columnnumber) {
        Ventana.ggetVentana().agregarLabel("Line: " + linenumber + " Column: " + columnnumber);
    }

     public Editor ( RSyntaxTextArea text) throws FileNotFoundException, IOException
    {
        super(text);
        this.textArea = text;
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        textArea.setCodeFoldingEnabled(true);
        textArea.setText("");
        textArea.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent ce) {
                 int linenum = 1;
                int columnnum = 1;
                try {
                    int caretpos = textArea.getCaretPosition();
                    linenum = textArea.getLineOfOffset(caretpos);
                    columnnum = caretpos - textArea.getLineStartOffset(linenum);
                    linenum += 1;
                }
                catch(Exception ex) { }
                updateStatus(linenum, columnnum);
            }
        });
         //DOM.setRecursos(archivo.getParent());
    }
   
    
    public String getRecurso()
    {
       return archivo != null ? archivo.getParent()+"\\" : "";
    }

    public String getTitulo() {
        return archivo != null ? archivo.getName() : "Nuevo";
    }
            
    public String getCodigo()
    {
        return textArea != null ? textArea.getText() : "";
    }
    
    public void setCodigo(String codigo)
    {
        textArea.setText(codigo);
    }     
}
