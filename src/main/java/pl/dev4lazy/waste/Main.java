package pl.dev4lazy.waste;

import pl.dev4lazy.waste.model.CsvInfo;
import pl.dev4lazy.waste.model.StoreWasteDataDecompositor;
import pl.dev4lazy.waste.utils.CsvCoder;
import pl.dev4lazy.waste.utils.CsvLineToStoreWasteInfoDecoder2;
import pl.dev4lazy.waste.utils.CsvParser;
import pl.dev4lazy.waste.utils.CsvSerializer;

public class Main {

    private static StoreWasteDataDecompositor storeWasteDataDecompositor;

    public static void main(String[] args) {
        convertWasteData( );
    }

    private static void convertWasteData( ) {
        prepareConverter();
        convertData();
    }

    private static void prepareConverter() {
        storeWasteDataDecompositor = new StoreWasteDataDecompositor(
                new CsvParser(),
                new CsvLineToStoreWasteInfoDecoder2( CsvInfo.getInstance() ),
                new CsvCoder(),
                new CsvSerializer()
        );
    }

    private static void convertData() {
        storeWasteDataDecompositor.readCsvFile();
        storeWasteDataDecompositor.makeStoreWasteInfoList( );
        storeWasteDataDecompositor.makeWasteCodeInfoList( );
        storeWasteDataDecompositor.makeOutputCsvLineList();
        storeWasteDataDecompositor.saveCsvFile();
    }
}