/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olc2.p1_201504242;

/**
 *
 * @author p_ab1
 */
public class JError {
    private String tipoError;
    private int linea;
    private int columna;
    private String desc;

    public JError(String tipoError, int linea, int columna, String desc) {
        this.tipoError = tipoError;
        this.linea = linea;
        this.columna = columna;
        this.desc = desc;
    }

    public String getTipoError() {
        return tipoError;
    }

    public void setTipoError(String tipoError) {
        this.tipoError = tipoError;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    
    
}
