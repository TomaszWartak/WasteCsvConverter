package pl.dev4lazy.waste.model;

import pl.dev4lazy.waste.interfaces.Decoder;
import pl.dev4lazy.waste.interfaces.Value;
import pl.dev4lazy.waste.utils.CsvInfo;
import pl.dev4lazy.waste.utils.DoubleValue;
import pl.dev4lazy.waste.utils.CsvUtils;
import pl.dev4lazy.waste.utils.StringValue;

import java.util.ArrayList;

public class CsvLineToStoreWasteInfoDecoder implements Decoder< ArrayList<String>, StoreWasteInfo > {

    private CsvInfo csvInfo;

    public CsvLineToStoreWasteInfoDecoder(CsvInfo csvInfo) {
        this.csvInfo = csvInfo;
    }

    @Override
    public StoreWasteInfo decode(ArrayList<String> parsedCsvLine) {
        ArrayList<String> parsedCsvHeaderRow = csvInfo.getParsedCsvHeaderRow();
        parsedCsvLine = CsvUtils.replaceCommasToPoints( parsedCsvLine );
        StoreWasteInfo storeWasteInfo = new StoreWasteInfo();//HashMap<>();
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
