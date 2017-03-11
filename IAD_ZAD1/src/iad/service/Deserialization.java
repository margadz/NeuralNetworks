/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad.service;

import iad.data.Container;
import iad.data.Entity;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author glabg
 */
public class Deserialization {

    private static String SEPARATOR;

    void start(File input) {
        setSeparator(input);
        try (Scanner scanner = new Scanner(input)) {
            while (scanner.hasNext()) {
                parseEntity(scanner.nextLine());
            }
        } catch (IOException | CSVFormatException e) {
            e.printStackTrace();
        }
    }

    void parseEntity(String line) throws CSVFormatException {
        String[] split = line.split(SEPARATOR);
        List<Double> params = new ArrayList<>();
        if (split.length > 1) {
            for (int i = 0; i < split.length - 1; i++) {
                params.add(Double.parseDouble(split[i]));
            }
            Container.getInstance().addEntity(new Entity((List<Double>) params, split[split.length - 1]));
        } else {
            throw new CSVFormatException();
        }
    }

    private void setSeparator(File input) {
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
}

class CSVFormatException extends Exception {
}
