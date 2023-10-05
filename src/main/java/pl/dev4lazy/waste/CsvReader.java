package pl.dev4lazy.waste;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CsvReader {

    private BufferedReader reader;

    public void openReaderFromAssets(String csvFileName) {
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        try { // TODO zrób try with resources
            // TODO inputStream = assetManager.open( csvFileName );
            inputStreamReader = new InputStreamReader( inputStream );
            reader = new BufferedReader( inputStreamReader );
        } catch (Exception ex0) {
            ex0.printStackTrace();
            try {
                if (inputStreamReader != null)
                    inputStreamReader.close();
                if (inputStream != null)
                    inputStream.close();
                if (reader != null)
                    reader.close();
            } catch (Exception ex2) {
                ex2.printStackTrace();
            }
        }
    }

    public void openReaderFromExternalStorage(String csvFileName) {
        try {
            File directory = getDocumentDirectory();
            FileReader fileReader = new FileReader(directory + File.separator + csvFileName);
            reader = new BufferedReader(fileReader);
        } catch (FileNotFoundException ex0) {
            ex0.printStackTrace();
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex2) {
                ex2.printStackTrace();
            }
        }
    }

    private File getDocumentDirectory() {
        // TODO
        return null;
    }

    public String readCsvLine() {
        String csvLine = null;
        try {
            csvLine = reader.readLine();
            // jeżeli część dziesiętną oddziela ",", to zamiana na "."
            // żeby konwersja ze String na Double przeszła
            if (csvLine!=null) {
                csvLine = csvLine.replace(",", ".");
            }
        } catch(IOException ex1) {
            ex1.printStackTrace();
            try {
                if(reader!= null) {
                    reader.close();
                }
            } catch (IOException ex2) {
                ex2.printStackTrace();
            }
        }
        return csvLine;
    }

    public void closeReader() {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException ex2) {
                ex2.printStackTrace();
            }
        }
    }

}