package pl.dev4lazy.waste.utils;

import java.io.*;

public class CsvReader {

    private BufferedReader reader;

    public CsvReader( String csvFileName ) {
        openReaderForFile(csvFileName);
    }

    private void openReaderForFile(String csvFileName) {
        try {
            InputStream inputStream = new FileInputStream(csvFileName);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

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
        return cleanZWNBSP(csvLine);
    }

    /**
     * ZWNBSP" jest znakiem o zerowej szerokości, więc nie jest widoczny, ale ma wpływ na sposób,
     * w jaki parser CSV traktuje ten wiersz - nie pozwala łamac wiersz.
     * A przede wszystkim, przy porównaniach napisów pomimo, że nie jest widoczny, to jest uwzględniany,
     * co powoduje różność napisów, które wyglądają tak samo...
     */
    private String cleanZWNBSP( String textToCleanZWNBSP ) {
        if (textToCleanZWNBSP!=null) {
            return textToCleanZWNBSP.replace("\uFEFF", "");
        } else {
            return null;
        }
    }

    // TODO pamietaj o zamykaniu CsvReadera
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