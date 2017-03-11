/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad_2.gg.func;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author marr
 */
@XmlRootElement
@XmlSeeAlso({LinearFunction.class, LogisticFunction.class})
public interface ActivationFunction {
    public double function(double input);
    public double derivative(double x);
}
