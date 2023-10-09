package pl.dev4lazy.waste.model;

import java.util.ArrayList;

public class CsvInfo {

    private ArrayList<String> parsedCsvHeaderRow;

    private static CsvInfo instance;

    private CsvInfo() {
        // Prywatny konstruktor, aby uniemożliwić tworzenie wielu instancji.
    }

    public static CsvInfo getInstance() {
        if (instance == null) {
            instance = new CsvInfo();
        }
        return instance;
    }

    public ArrayList<String> getParsedCsvHeaderRow() {
        return parsedCsvHeaderRow;
    }

    public void setParsedCsvHeaderRow(ArrayList<String> parsedCsvHeaderRow) {
        this.parsedCsvHeaderRow = parsedCsvHeaderRow;
    }

}

