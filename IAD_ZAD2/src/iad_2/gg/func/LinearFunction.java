/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad_2.gg.func;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author marr
 */
@XmlRootElement
public class LinearFunction implements ActivationFunction {

    @Override
    public double function(double input) {
        return input;
    }

    @Override
    public double derivative(double x) {
        return 0.0;
    }
    
}
