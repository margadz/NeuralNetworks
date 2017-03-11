/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad.data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author glabg
 */
public class Entity {

    private List<Double> params;
    private String klass;

    public Entity(List<Double> params, String klass) {
        this.params = params;
        this.klass = klass;
    }

    public List<Double> getParams() {
        return params;
    }

    public void setParams(ArrayList<Double> params) {
        this.params = params;
    }

    public String getKlass() {
        return klass;
    }

    public void setKlass(String klass) {
        this.klass = klass;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append(" class: ");
        ret.append(klass);
        ret.append(" params: ");
        for (double param : params) {
            ret.append(" ");
            ret.append(param);
        }
        return ret.toString();
    }
}
