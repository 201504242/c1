/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entorno;

/**
 *
 * @author p_ab1
 */
public class Var {
    
    private String id;
    private int dim;

    public Var(String id, int dim) {
        this.id = id;
        this.dim = dim;
    }

    public Var(String id) {
        this.id = id;
        this.dim = 1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDim() {
        return dim;
    }

    public void setDim(int dim) {
        this.dim = dim;
    }
}
