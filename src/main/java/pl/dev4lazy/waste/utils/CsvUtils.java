package pl.dev4lazy.waste.utils;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class CsvUtils {
    public static final String CSV_SEPARATOR = ";";


    public static String replaceCommasToPoints(String csvLine) {
        if (csvLine !=null) {
            csvLine = csvLine.replace(",", ".");
        }
        return csvLine;
    }

    public static ArrayList<String> replaceCommasToPoints(ArrayList<String> strings) {
        return strings
                .stream()
                .map( string -> string.replace(",", ".") )
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static Integer getIntegerOrNullFromString(String value) {
        if (value.isEmpty()) {
            return null;
        } else {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException ex) {
                return null;
            }
        }
    }

    public static Double getDoubleOrNullFromString(String value) {
        if (value.isEmpty()) {
            return null;
        } else {
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException ex) {
                return null;
            }
        }
    }
}
