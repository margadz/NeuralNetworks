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
public class LogisticFunction implements ActivationFunction{
    
    @Override
    public double function(double input) {
        return (1.0/(1 + Math.exp(-input)));
    }

    @Override
    public double derivative(double x) {
        return function(x) * (1.0 - function(x));
    }
    
    
    
}
