package pl.dev4lazy.waste;

import java.util.ArrayList;
import java.util.List;
public class StoreWasteDataConverter {

    private final String inputFileName = "dane-test1000v2.csv";

    private CsvReader csvReader;
    private CsvDecoder csvDecoder;
    private OutputCsvFile outputCsvFile;
    private ArrayList<String> fieldNamesList; //todo ok: z tego coś nie bardzo korzystasz...
    private ArrayList<StoreWasteInfo> storeWasteInfoList;

    public StoreWasteDataConverter() {
        csvReader = new CsvReader(inputFileName);
        csvDecoder = new CsvDecoder();
        storeWasteInfoList = new ArrayList<>();
        outputCsvFile = new OutputCsvFile(); // TODO ta metoda nie robi nic
    }

    private void createFieldList() {
        fieldNamesList = new ArrayList<>();
        fieldNamesList.add("store");
        fieldNamesList.add("article_code");
        fieldNamesList.add("article_name");
        fieldNamesList.add("article_store_price");
        fieldNamesList.add("article_ref_price");
        fieldNamesList.add("article_new_price");
        fieldNamesList.add("article_new_margin_percent");
        fieldNamesList.add("article_lm_price");
        fieldNamesList.add("article_obi_price");
        fieldNamesList.add("article_bricoman_price");
        fieldNamesList.add("article_local_competitor1_price");
        fieldNamesList.add("article_local_competitor2_price");
    }

    public List<StoreWasteInfo> makeStoreWasteInfoList() {
        ArrayList<String> values;
        /* TODO
            reader odczytuje porcję
            parser dzieli porcję na kawałki
            decoder interpretuje kawałek i zamienienia go na obiekt wejściowy
            converter zamienia obiekt wejściowy na obiekt wyjściowy
            coder zamienia obiekt wyjściowy na kawałek
            serializer składa kawałki w porcję
            writer zapisuje porcję
         */
        String csvLine = csvReader.readCsvLineWithCommaToPointConversion(); // "pusty odczyt" - wiersz nagłówków

        StoreWasteInfo storeWasteInfo;
        int rowCounter = 0;
        // reader odczytuje porcje w pętli
        while ((csvLine= csvReader.readCsvLineWithCommaToPointConversion())!=null) {
            // parser dzieli porcję na kawałki
            // decoder interpretuje kawałek i zamienienia go na obiekt wejściowy
            values = csvDecoder.getValuesFromCsvLine(csvLine); // TODO TEST
            // converter zamienia obiekt wejściowy na obiekt wyjściowy
            storeWasteInfo = makeStoreWasteInfo(values);
            storeWasteInfoList.add(storeWasteInfo);
            rowCounter++;
        }
        return storeWasteInfoList;
    }

    public StoreWasteInfo makeStoreWasteInfo(ArrayList<String> valuesFromCsvLine ) {
        StoreWasteInfo storeWasteInfo = new StoreWasteInfo.StoreWasteInfoBuilder()
                /* TODO */
                .store( csvDecoder.getIntegerOrNullFromString( valuesFromCsvLine.get(0)) )
                .analysisId( 0 ) // 0 - bo zostanie wpisany później
                .articleCode( csvDecoder.getIntegerOrNullFromString( valuesFromCsvLine.get(1)) )
                .articleName( valuesFromCsvLine.get(2) )
                .articleStorePrice( csvDecoder.getDoubleOrNullFromString(valuesFromCsvLine.get(3)) )
                .articleRefPrice( csvDecoder.getDoubleOrNullFromString(valuesFromCsvLine.get(4)) )
                .articleNewPrice( csvDecoder.getDoubleOrNullFromString(valuesFromCsvLine.get(5)) )
                .articleNewMarginPercent( csvDecoder.getDoubleOrNullFromString(valuesFromCsvLine.get(6)) )
                .articleLmPrice( csvDecoder.getDoubleOrNullFromString(valuesFromCsvLine.get(7)) )
                .artlcleObiPrice( csvDecoder.getDoubleOrNullFromString(valuesFromCsvLine.get(8)) )
                .artlcleBricomanPrice( csvDecoder.getDoubleOrNullFromString(valuesFromCsvLine.get(9)) )
                .articleLocalCompetitor1Price( csvDecoder.getDoubleOrNullFromString(valuesFromCsvLine.get(10)) )
                .articleLocalCompetitor2Price( csvDecoder.getDoubleOrNullFromString(valuesFromCsvLine.get(11)) )
                .department( valuesFromCsvLine.get(12) )
                .sector( valuesFromCsvLine.get(13) )

                .build();
        return storeWasteInfo;
    }

    public void insertAllStoreWasteInfos() {
        for (StoreWasteInfo storeWasteInfo : storeWasteInfoList) {
            insertStoreWasteInfo(storeWasteInfo);
        }
    }

    private void insertStoreWasteInfo( StoreWasteInfo storeWasteInfo) {
        outputCsvFile.insertStoreWasteInfo(storeWasteInfo);
    }

}


