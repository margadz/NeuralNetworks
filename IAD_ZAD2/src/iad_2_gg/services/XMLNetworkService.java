/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad_2_gg.services;

import iad_2.gg.func.LinearFunction;
import iad_2.gg.func.LogisticFunction;
import iad_2.gg.net.Network;
import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author glabg
 */
public class XMLNetworkService {

    public static void marshaling(Network net, File file) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Network.class, LogisticFunction.class, LinearFunction.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(net, file);
    }

    public static Network unmarshaling(File file) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Network.class, LogisticFunction.class, LinearFunction.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Network net = (Network) jaxbUnmarshaller.unmarshal(file);
        return net;
    }

}
