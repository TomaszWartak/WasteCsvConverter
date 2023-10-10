package pl.dev4lazy.waste;

import pl.dev4lazy.waste.utils.CsvInfo;
import pl.dev4lazy.waste.model.StoreWasteDataConverter;
import pl.dev4lazy.waste.model.WasteCodeInfoToCsvLineCoder;
import pl.dev4lazy.waste.model.CsvLineToStoreWasteInfoDecoder;
import pl.dev4lazy.waste.utils.CsvParser;
import pl.dev4lazy.waste.utils.CsvSerializer;

public class Main {

    private static StoreWasteDataConverter storeWasteDataConverter;

    public static void main(String[] args) {
        convertWasteData( );
    }

    private static void convertWasteData( ) {
        prepareConverter();
        convertData();
    }

    private static void prepareConverter() {
        storeWasteDataConverter = new StoreWasteDataConverter(
                new CsvParser(),
                new CsvLineToStoreWasteInfoDecoder( CsvInfo.getInstance() ),
                new WasteCodeInfoToCsvLineCoder(),
                new CsvSerializer()
        );
    }

    private static void convertData() {
        storeWasteDataConverter.readCsvFile();
        storeWasteDataConverter.makeStoreWasteInfoList( );
        storeWasteDataConverter.convertStoreInfosToWasteCodeInfos( );
        storeWasteDataConverter.makeOutputCsvLineList();
        storeWasteDataConverter.saveCsvFile();
    }
}