package pl.dev4lazy.waste;

import java.util.ArrayList;
import java.util.List;
public class StoreWasteDataConverter {

    private final String inputFileName = "KPO_Report.csv";
    private CsvReader csvReader;
    private Parser csvParser;
    private Decoder csvDecoder;
    private OutputCsvFile outputCsvFile;
    private ArrayList<String> headerNamesList; //todo ok: z tego coś nie bardzo korzystasz...
    private ArrayList<StoreWasteInfo> storeWasteInfoList;

    public StoreWasteDataConverter(Parser parser, Decoder decoder) {
        csvReader = new CsvReader(inputFileName);
        this.csvParser = parser;
        this.csvDecoder = decoder;
        storeWasteInfoList = new ArrayList<>();
        outputCsvFile = new OutputCsvFile(); // TODO ta metoda nie robi nic
    }

    public List<StoreWasteInfo> makeStoreWasteInfoList() {
        /* TODO
            reader odczytuje porcję
            parser dzieli porcję na kawałki
            decoder interpretuje kawałek i zamienienia go na obiekt wejściowy
            converter zamienia obiekt wejściowy na obiekt wyjściowy
            coder zamienia obiekt wyjściowy na kawałek
            serializer składa kawałki w porcję
            writer zapisuje porcję
         */
        ArrayList<String> pieces;
        StoreWasteInfo storeWasteInfo;
        String portion = getPortion(); // "pusty odczyt" - wiersz nagłówków
        createHeaderNamesList( portion );
        int rowCounter = 0;
        // reader odczytuje porcje w pętli
        while ((portion=getPortion()) != null) {
            // parser dzieli porcję na kawałki
            pieces = getPiecesFromPortion(portion); // TODO TEST
            // decoder interpretuje kawałek po kawałku i zamienienia je na obiekty wejściowye
            storeWasteInfo = makeStoreWasteInfo(pieces);
            storeWasteInfoList.add(storeWasteInfo);
            rowCounter++;
        }
        csvReader.closeReader();
        return storeWasteInfoList;
    }

    private void createHeaderNamesList( String header ) {
        headerNamesList = getPiecesFromPortion(header);
        /* TODO USUŃ
        headerNamesList = new ArrayList<>();
        headerNamesList.add("store");
        headerNamesList.add("article_code");
        headerNamesList.add("article_name");
        headerNamesList.add("article_store_price");
        headerNamesList.add("article_ref_price");
        headerNamesList.add("article_new_price");
        headerNamesList.add("article_new_margin_percent");
        headerNamesList.add("article_lm_price");
        headerNamesList.add("article_obi_price");
        headerNamesList.add("article_bricoman_price");
        headerNamesList.add("article_local_competitor1_price");
        headerNamesList.add("article_local_competitor2_price");

         */
    }

    public String getPortion() {
        String portion = csvReader.readCsvLine();
        return portion;
    }


    public ArrayList<String> getPiecesFromPortion( String portion ) {
        return csvParser.parse( portion );
    }

    public StoreWasteInfo makeStoreWasteInfo(ArrayList<String> pieces ) {
        return (StoreWasteInfo)csvDecoder.decode( pieces );
    }

    public void insertAllStoreWasteInfos() {
        // converter zamienia obiekt wejściowy na obiekt wyjściowy
        // coder zamienia obiekt wyjściowy na kawałek
//        serializer składa kawałki w porcję
//        writer zapisuje porcję
        for (StoreWasteInfo storeWasteInfo : storeWasteInfoList) {
            insertStoreWasteInfo(storeWasteInfo);
        }
    }

    private void insertStoreWasteInfo( StoreWasteInfo storeWasteInfo) {
        outputCsvFile.insertStoreWasteInfo(storeWasteInfo);
    }

}


