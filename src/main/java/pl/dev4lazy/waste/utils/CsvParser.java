package pl.dev4lazy.waste.utils;

import pl.dev4lazy.waste.interfaces.Parser;

import java.util.ArrayList;

public class CsvParser implements Parser {

    public ArrayList<String> parse(String csvLine ) {
        ArrayList<String> values = new ArrayList<>();
        if (!csvLine.isEmpty()) {
            /*
             Jeżeli w linii csv po ostatnim separatorze jest pusty napis (np. ";"),
             to nie zostanie on dodany do values.
             Wynika to z tego, że jeśli cutCsvValueFromCsvLine() usunie ostatni separator
             (jako efekt uboczny) z csvLineStringBuilder, to csvLineStringBuilder
             przyjmie wartość "" i pętla odczytu wartości z csvLineStringBuilder
             zostanie zakończona.
             Dlatego, zamiast tego ostaniego pustego napisu dodaję spację (np. "; ").
             Spacja w cutCsvValueFromCsvLine() zosytanie zamieniona na pusty napis,
             który dzięki temu zostanie dodany do values.
            */
            if (lastCharIsSeparator(csvLine)) {
                csvLine=csvLine+" ";
            }
            StringBuilder csvLineStringBuilder = new StringBuilder(csvLine);
            String value;
            while (csvLineStringBuilder.length()>0) {
                value = cutCsvValueFromCsvLine(csvLineStringBuilder);
                if (value!=null)
                    values.add(value);
            }
        }
        return values;
    }

    private boolean lastCharIsSeparator(String csvLine) {
        if (csvLine.isEmpty()) {
            return false;
        } else {
            return (csvLine.charAt(csvLine.length()-1)== CsvUtils.CSV_SEPARATOR.charAt(0));
        }
    }


    /**
     Działanie: wycina wartość z początku linii csv
     Argumenty: csvLine - linia csv
     Rezultat: wartość wyciętą z początku linii csv
     Efekty uboczne: csvLine jest pomniejszoną o wyciętą wartość
     */
    private String cutCsvValueFromCsvLine( StringBuilder csvLine ) {
        if (!csvLine.toString().isEmpty()) {
            StringBuilder result = new StringBuilder();
            int delimiterPosition = csvLine.indexOf(CsvUtils.CSV_SEPARATOR);
            if (delimiterPosition > -1) {
                result.append(csvLine.substring(0, delimiterPosition).trim());
                result.toString().replace(CsvUtils.CSV_SEPARATOR, "");
                csvLine.delete(0, delimiterPosition + 1);
            } else {
                // W csvLine została ostatnia wartość
                result.append(csvLine.toString().trim());
                csvLine.delete(0,csvLine.length());
            }
            return result.toString();
        } else {
            return "";
        }
    }

}
