/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad_2_gg.services;

import iad_2.gg.net.Network;
import iad_2.gg.net.TrainingData;
import iad_2_gg.gui.MainWindow;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.xml.bind.JAXBException;

/**
 *
 * @author glabg
 */
public class GUIHelper {

    private static final Class helperClass = GUIHelper.class;

    public static void actionLocator(String action, File file) {
        System.out.println(action);
        try {
            Method actionMethod = helperClass.getMethod(action, File.class);
            actionMethod.invoke(null, file);
        } catch (Exception ex) {
            Logger.getLogger(GUIHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void importFromXML(File file) {
        Network net;
        try {
            net = XMLNetworkService.unmarshaling(file);
            MainWindow.getInstance().setNetwork(net);
            MainWindow.showInfo("Poprawnie wczytano sieć z pliku XML!", "Wczytano XML");
        } catch (JAXBException ex) {
            Logger.getLogger(GUIHelper.class.getName()).log(Level.SEVERE, null, ex);
            MainWindow.showError(ex, "Błąd wczytywania sieci z pliku XML", "błąd wczytywania XML");
        }
    }

    public static void exportToXML(File file) {
        Network net = MainWindow.getInstance().getNetwork();
        try {
            XMLNetworkService.marshaling(net, file);
            MainWindow.showInfo("Poprawnie zapisano sieć do pliku XML!", "Zapisano do XML");
        } catch (JAXBException ex) {
            Logger.getLogger(GUIHelper.class.getName()).log(Level.SEVERE, null, ex);
            MainWindow.showError(ex, "Błąd zapisywania sieci do pliku XML", "błąd zapisu do XML");
        }
    }

    public static void importPattern(File file) {
        List<double[]> data = new ArrayList<double[]>();
        List<double[]> expected = new ArrayList<double[]>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;
            while ((line = br.readLine()) != null) {
                parsePatternFile(line, data, expected);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        TrainingData.data = data;
        TrainingData.expected = expected;
        MainWindow.getInstance().isTrainingEnabled(true);
    }

    private static void parsePatternFile(String line, List<double[]> data, List<double[]> expected) {
        String[] split = line.split(Pattern.quote("),("));
        String tmpdata = split[0].replaceAll("\\(|\\)", "");
        data.add(parseValues(tmpdata));
        tmpdata = split[1].replaceAll("\\(|\\)", "");
        expected.add(parseValues(tmpdata));
    }
    public static double[] parseValues(String stringvalues){
        String[] split = stringvalues.split(",");
        double[] result = new double[split.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = Double.parseDouble(split[i]);
        }
        return result;
    }
}
