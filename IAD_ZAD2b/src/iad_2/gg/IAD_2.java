/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad_2.gg;

import iad_2.gg.net.Network;
import iad_2_gg.gui.MainWindow;
import iad_2_gg.services.NetworkFactory;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author glabg
 */
public class IAD_2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainWindow().setVisible(true);
            }
        });

//        List<Integer> layers = new ArrayList<>();
//        layers.add(4);
//        layers.add(2);
//        layers.add(4);
//        Network net = NetworkFactory.GetNetwork(layers, 1);
//
//        double[] data1 = {1, 0, 0, 0};
//        double[] data2 = {0, 1, 0, 0};
//        double[] data4 = {0, 0, 0, 1};
//        double[] data3 = {0, 0, 1, 0};
//        List<double[]> input = new ArrayList<>();
//        for (int i = 0; i < 1; i++) {
//            input.add(data1);
//            input.add(data2);
//            input.add(data3);
//            input.add(data4);
//
//        }
//        System.out.println(net.trainNetwork(input, input, 0.6, 0, 1000, 0.00, true));
//        double[] result = net.activate(data1);
//        for (double i : result) {
//            System.out.println(i + " ");
//        }
//        System.out.println();
//        result = net.activate(data2);
//        for (double i : result) {
//            System.out.println(i + " ");
//        }
//        System.out.println();
//        result = net.activate(data3);
//        for (double i : result) {
//            System.out.println(i + " ");
//        }
//        System.out.println();
//        result = net.activate(data4);
//        for (double i : result) {
//            System.out.println(i + " ");
//        }
//        for (Double i : net.getLog()) {
//             System.out.println(i);
//  }        
        
       // GUIHelper.importPattern(new File("F:\\Biblioteka\\Studia\\iad\\IAD\\iad_SVN\\IAD_ZAD2\\trainingData.csv"));

//        Neuron n = new Neuron(f, 0, 1);
//        File f = new File("d:/net.xml");
//        XMLNetworkService.marshaling(net, f);
//        
//        Network net1 = XMLNetworkService.unmarshaling(f);
//        XMLNetworkService.marshaling(net1, new File("d:/net1.xml"));
    }

}
