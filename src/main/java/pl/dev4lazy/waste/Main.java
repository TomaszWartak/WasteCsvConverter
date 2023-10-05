package pl.dev4lazy.waste;

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
        storeWasteDataConverter = new StoreWasteDataConverter();
    }

    private static void convertData() {
        storeWasteDataConverter.makeStoreWasteInfoList( );
        storeWasteDataConverter.insertAllStoreWasteInfos();
    }
}