package pl.dev4lazy.waste.utils;

import pl.dev4lazy.waste.interfaces.Serializer;

import java.text.DecimalFormat;
import java.util.ArrayList;

/* TODO test
    gdy lista pieces jest pusta
 */

public class CsvSerializer implements Serializer {

    @Override
    public String serialize(ArrayList<String> pieces, String delimiter) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String piece : pieces) {
            stringBuilder
                    .append( piece )
                    .append( delimiter );
        }
        deleteLastDelimiter(stringBuilder);
        return stringBuilder.toString();
    }

    private static void deleteLastDelimiter(StringBuilder stringBuilder) {
        if (!stringBuilder.isEmpty()) {
            stringBuilder.deleteCharAt(stringBuilder.length()-1);
        }
    }

}
