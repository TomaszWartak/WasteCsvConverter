package pl.dev4lazy.waste;

import java.io.*;

public class CsvReader {

    private BufferedReader reader;

    public CsvReader( String csvFileName ) {
        openReaderForFile3(csvFileName);
    }

    public void openReaderForFile1(String csvFileName) {
        try ( FileReader fileReader = new FileReader(csvFileName);
              BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            reader = bufferedReader;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void openReaderForFile3(String csvFileName) {
        try  {
            FileReader fileReader = new FileReader(csvFileName);
            reader = new BufferedReader(fileReader);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void openReaderForFile2(String csvFileName) {
        try ( InputStream inputStream = new FileInputStream(csvFileName);
              InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
              BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            reader = bufferedReader;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /*
    public void openReaderFromAssets(String csvFileName) {
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        try ( InputStream inputStream = new FileInputStream(csvFileName);
              InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
              BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            reader = bufferedReader;
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

*/

    public String readCsvLine() {
        String csvLine = null;
        try {
            csvLine = reader.readLine();
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

    public String readCsvLineWithCommaToPointConversion() {
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


    // TODO pamietaj o zamknięciu CsvReadera
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