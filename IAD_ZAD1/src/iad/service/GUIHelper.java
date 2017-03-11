/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad.service;

import iad.data.Container;
import iad.gui.Zadanie1;
import java.io.File;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author glabg
 */
public class GUIHelper {
    
    private static final Class helperClass = GUIHelper.class;

    public static void actionLocator(String action, File file){
        try {
            Method actionMethod = helperClass.getMethod(action, File.class);
            actionMethod.invoke(null, file);
        } catch (Exception ex) {
            Logger.getLogger(GUIHelper.class.getName()).log(Level.SEVERE, null, ex);
            Zadanie1.showError(ex);
        } 
    }
    
    public static void importFromCSV(File file){
        Container.getInstance().clear();
        new Deserialization().start(file);
    }
    
}
