/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.i18n;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Klasse für die Internationalisierung.
 * I18n = Internationalzation
 *         |18 Buchstaben  |
 * @author Steffen Brauer
 *
 */
public class I18n {

    private static I18n resource = null;
    /**
     * Diese Methode setzt die Sprache und das Land.
     * @param Sprache
     * @param Land
     * @return nichts
     */
    public static void setLanguage(String language){
        resource.setCurrentLanguage(language);
    }

    public static List<String> getLanguages(){
        return resource.getAllLanguages();
    }

    /**
     * Diese Methode gibt den aktuellen Pfad der L18ndateien zurück.
     * @return Pfad
     */
    public static String getPath(){
        if(resource != null){
            return resource.getPath();
        }else{
            return "";
        }
    }

    /**
     * Diese Methode setzt Pfad der L18ndateien.
     * @param neuer Pfad
     */
    public static void setPath(String newPath){
        resource = new I18n(newPath);
    }

    /**
     * Dient zur uebersetzung von Feldern.
     * @param Feld, das uebersetzt werden soll.
     * @return Die passende Übersetzung
     */
    public static String get(String str){
        if(resource == null){
            throw new RuntimeException("No Path set.");
        }
        return resource.getTranslation(str);
    }

    private String path;
    private List<String> languages;
    private String currentLanguage;
    private Map<String,String> translation;


    private I18n(String path){
        this.path = path;
        languages = new ArrayList<String>();
        currentLanguage = "en";
        loadLanguages();
        loadFile();
    }

    private List<String> getAllLanguages(){
        return languages;
    }


    private String getLanguage(){
        return currentLanguage;
    }

    private void setCurrentLanguage(String str){
        currentLanguage = str;
        loadFile();
    }

    private String bulidPath(){
        return path + "/I18n_" + currentLanguage + ".txt";
    }

    private void loadLanguages(){
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        for(int i = 0; i < listOfFiles.length ; i++){
            String raw = listOfFiles[i].getName();
            String language = raw.substring(5,7);
            System.out.println(language);
            languages.add(language);
        }
    }

    private void loadFile(){
        BufferedReader reader;
        Map<String,String> map = new HashMap<String,String>();
        String zeile;
        try {
            reader = new BufferedReader(new FileReader(bulidPath()));
            while ((zeile = reader.readLine()) != null) {
                String[]values = zeile.split(" = ");
                map.put(values[0], values[1]);
            }
        } catch (IOException e) {
            System.err.println(e.toString());
        }
        translation = map;
    }

    private String getTranslation(String str){
        String result = translation.get(str);
        return result == null ? str : result;
    }





}
