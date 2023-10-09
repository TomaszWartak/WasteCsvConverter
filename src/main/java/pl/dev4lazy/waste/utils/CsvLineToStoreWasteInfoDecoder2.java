package pl.dev4lazy.waste.utils;

import pl.dev4lazy.waste.interfaces.Decoder;
import pl.dev4lazy.waste.interfaces.Value;
import pl.dev4lazy.waste.model.CsvInfo;
import pl.dev4lazy.waste.model.DoubleValue;
import pl.dev4lazy.waste.model.StringValue;

import java.util.ArrayList;
import java.util.HashMap;

public class CsvLineToStoreWasteInfoDecoder2 implements Decoder< ArrayList<String>, HashMap<String, Value> > {

    private CsvInfo csvInfo;

    public CsvLineToStoreWasteInfoDecoder2(CsvInfo csvInfo) {
        this.csvInfo = csvInfo;
    }

    @Override
    public HashMap<String, Value> decode(ArrayList<String> parsedCsvLine) {
        ArrayList<String> parsedCsvHeaderRow = csvInfo.getParsedCsvHeaderRow();
        parsedCsvLine = CsvUtils.replaceCommasToPoints( parsedCsvLine );
        HashMap<String, Value> storeWasteInfo = new HashMap<>();
        for (int index = 0; index < parsedCsvLine.size(); index++) {
            String element = parsedCsvLine.get(index);
            Value value = null;
            switch (index) {
                case 0, 1, 2 : {
                    value = new StringValue();
                    value.setValue( element );
                    break;
                }
                default : {
                    if ((element!=null) && (element!="")) {
                        value = new DoubleValue();
                        value.setValue(CsvUtils.getDoubleOrNullFromString(element));
                    }
                }
            }
            if (value!=null) {
                storeWasteInfo.put( parsedCsvHeaderRow.get( index ), value );
            }
        }
        return storeWasteInfo;
    }

}
