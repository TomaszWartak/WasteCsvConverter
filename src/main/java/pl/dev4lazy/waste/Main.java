package pl.dev4lazy.waste;

import pl.dev4lazy.waste.model.StoreWasteDataDecompositor;
import pl.dev4lazy.waste.utils.CsvLineToStoreWasteInfoDecoder;
import pl.dev4lazy.waste.utils.CsvParser;

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
                new CsvLineToStoreWasteInfoDecoder()
        );
    }

    private static void convertData() {
        storeWasteDataDecompositor.makeStoreWasteInfoList( );
        storeWasteDataDecompositor.insertAllStoreWasteInfos();
    }
}