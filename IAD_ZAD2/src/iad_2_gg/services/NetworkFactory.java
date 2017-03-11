/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad_2_gg.services;

import iad_2.gg.net.Network;
import java.util.List;

/**
 *
 * @author marr
 */
public class NetworkFactory {
 
    
    public static Network GetNetwork(List<Integer>layers, int bias) throws Exception{
        Network net = new Network();
        net.prepareNetwork(layers, bias);
        return net;
    }
}
