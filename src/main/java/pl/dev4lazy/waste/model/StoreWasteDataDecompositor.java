package pl.dev4lazy.waste.model;

import pl.dev4lazy.waste.interfaces.Decoder;
import pl.dev4lazy.waste.interfaces.Parser;
import pl.dev4lazy.waste.utils.CsvReader;

import java.util.ArrayList;
import java.util.List;
public class StoreWasteDataDecompositor {

    private final String inputFileName = "KPO_Report.csv";
    private CsvReader csvReader;
    private Parser csvParser;
    private Decoder csvDecoder;
    private CsvWriter csvWriter;
    private ArrayList<String> headerNamesList; //todo ok: z tego coś nie bardzo korzystasz...
    private ArrayList<StoreWasteInfo> storeWasteInfoList;

    /* TODO
    reader odczytuje porcję
    parser dzieli porcję na kawałki
    decoder interpretuje kawałek i zamienienia go na obiekt wejściowy
    converter zamienia obiekt wejściowy na obiekt wyjściowy
    coder zamienia obiekt wyjściowy na kawałek
    serializer składa kawałki w porcję
    writer zapisuje porcję
 */
    public StoreWasteDataDecompositor(Parser parser, Decoder decoder) {
        csvReader = new CsvReader(inputFileName);
        csvParser = parser;
        csvDecoder = decoder;
        storeWasteInfoList = new ArrayList<>();
        csvWriter = new CsvWriter(); // TODO ta metoda nie robi nic
    }

    public List<StoreWasteInfo> makeStoreWasteInfoList() {
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
        csvWriter.insertStoreWasteInfo(storeWasteInfo);
    }

}


