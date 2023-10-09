package pl.dev4lazy.waste.utils;

import pl.dev4lazy.waste.interfaces.Serializer;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CsvSerializer implements Serializer {

    @Override
    public String serialize(ArrayList<String> pieces) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        return new StringBuilder()
                .append( pieces.get(0) )
                .append( CsvUtils.CSV_SEPARATOR )
                .append( pieces.get(1) )
                .append( CsvUtils.CSV_SEPARATOR )
                .append( pieces.get(2) )
                .append( CsvUtils.CSV_SEPARATOR )
                .append( pieces.get(3) )
                .append( CsvUtils.CSV_SEPARATOR )
                .append( pieces.get(4) )
                .toString();
    }

}
