/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad_2_gg.services;

import iad_2.gg.net.Network;
import iad_2_gg.gui.MainWindow;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.bind.JAXBException;

/**
 *
 * @author glabg
 */
public class GUIHelper {

    private static String SEPARATOR;

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
        setSeparator(file);
        List<double[]> data = new ArrayList<>();
        List<double[]> expected = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;
            while ((line = br.readLine()) != null) {
                parsePatternFile(line, data, expected);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        DataParser tData = new DataParser(data, expected);
        MainWindow.getInstance().isTrainingEnabled(tData);
    }

    private static void parsePatternFile(String line, List<double[]> data, List<double[]> expected) {
        String[] split = line.split(SEPARATOR);
        double[] tmpData = new double[split.length - 1];
        double[] patterData = parseName(split[split.length - 1]);
        for (int i = 0; i < split.length - 1; i++) {
            tmpData[i] = Double.parseDouble(split[i]);
        }
        data.add(tmpData);
        expected.add(patterData);
    }

    public static double[] parseValues(String stringvalues) {
        String[] split = stringvalues.split(",");
        double[] result = new double[split.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = Double.parseDouble(split[i]);
        }
        return result;
    }

    private static void setSeparator(File input) {
        Pattern pattern = Pattern.compile("([a-zA-Z0-9._\\-]+)(.)([a-zA-Z0-9._\\-]+)");
        try {
            Scanner scanner = new Scanner(input);
            Matcher matcher = pattern.matcher(scanner.nextLine());
            if (matcher.find()) {
                SEPARATOR = matcher.group(2);
            }
        } catch (FileNotFoundException ex) {
            //TODO :)
        }
    }

    private static double[] parseName(String name) {
        double[] pattern = new double[3];
        switch (name) {
            case "Iris-setosa":
                pattern[0] = 1.0;
                pattern[1] = 0.0;
                pattern[2] = 0.0;
                break;
            case "Iris-versicolor":
                pattern[0] = 0.0;
                pattern[1] = 1.0;
                pattern[2] = 0.0;
                break;
            case "Iris-virginica":
                pattern[0] = 0.0;
                pattern[1] = 0.0;
                pattern[2] = 1.0;
                break;
        }
        return pattern;
    }
}
