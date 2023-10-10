package pl.dev4lazy.waste.model;

import pl.dev4lazy.waste.interfaces.*;
import pl.dev4lazy.waste.utils.CsvInfo;
import pl.dev4lazy.waste.utils.CsvReader;
import pl.dev4lazy.waste.utils.CsvUtils;
import pl.dev4lazy.waste.utils.CsvWriter;

import java.util.ArrayList;

/* TODO można zrobić klasę CsvData, która zawierała by sparsowane (lub nie) nagłowek i listę wierszy.
    Ustawiało by się, czy ma wiersz nagłowka i ew. jakies inne tematy.
    Pytanie, czy to jest efektywne, takie tworzenie rozbudowanych struktur, zamiast w locie odczytywac parsować, i dekodować,
    a później w drugą stronę...
 */

public class StoreWasteDataConverter {

    private final String inputFileName = "KPO_Report.csv";
    private final String outputFileName = "KPO_Report_new.csv";
    private CsvInfo csvInfo;
    private CsvReader csvReader;
    private Parser csvParser;
    private Decoder csvDecoder;
    private Coder csvCoder;
    private Serializer csvSerializer;
    private CsvWriter csvWriter;

    private ArrayList<String> inputCsvLineList;

    private ArrayList<StoreWasteInfo> storeWasteInfoList;

    private ArrayList<WasteCodeInfo> wasteCodeInfoList;

    private ArrayList<String> outputCsvLineList;

    /* TODO
    reader odczytuje porcję (np. linię z pliku csv)
    parser dzieli porcję na kawałki
    decoder interpretuje kawałek i zamienienia go na obiekt wejściowy
    converter zamienia obiekt wejściowy na obiekt wyjściowy
    coder zamienia obiekt wyjściowy na kawałek
    serializer składa kawałki w porcję
    writer zapisuje porcję
    */
    public StoreWasteDataConverter(
            Parser parser,
            Decoder decoder,
            Coder coder,
            Serializer serializer ) {
        csvInfo = CsvInfo.getInstance();
        csvReader = new CsvReader(inputFileName);
        csvParser = parser;
        csvDecoder = decoder;
        csvCoder = coder;
        csvSerializer = serializer;
        csvWriter = new CsvWriter(outputFileName);
        inputCsvLineList = new ArrayList<>();
        storeWasteInfoList = new ArrayList<>();
        wasteCodeInfoList = new ArrayList<>();
        outputCsvLineList = new ArrayList<>();
    }

    public void readCsvFile() {
        csvInfo.setParsedCsvHeaderRow( csvParser.parse( csvReader.readCsvLine() ));
        String inputCsvLine;
        while ((inputCsvLine=getPortion()) != null) {
            inputCsvLineList.add(inputCsvLine);
        }
        csvReader.closeReader();
    }

    public void makeStoreWasteInfoList() {
        ArrayList<String> pieces;
        StoreWasteInfo storeWasteInfo;
        for (String portion : inputCsvLineList) {
            pieces = getPiecesFromPortion(portion);
            storeWasteInfo = makeStoreWasteInfo(pieces);
            storeWasteInfoList.add(storeWasteInfo);
        }
        csvReader.closeReader();
    }

    public String getPortion() {
        return csvReader.readCsvLine();
    }


    // public na potrzeby testów
    public ArrayList<String> getPiecesFromPortion( String portion ) {
        return csvParser.parse( portion );
    }

    // public na potrzeby testów
    public StoreWasteInfo makeStoreWasteInfo(ArrayList<String> pieces ) {
        return (StoreWasteInfo)csvDecoder.decode( pieces );
    }

    public void convertStoreInfosToWasteCodeInfos() {
        for (StoreWasteInfo storeWasteInfo : storeWasteInfoList) {
            WasteCodeInfo.Builder wasteCodeInfoBuilder = initBuilder(storeWasteInfo);
            removeNoLongerNecessaryDataFromStoreWastInfo(storeWasteInfo);
            addWasteInfoToWasteCodeInfo(storeWasteInfo, wasteCodeInfoBuilder);
        }
    }

    private WasteCodeInfo.Builder initBuilder(StoreWasteInfo storeWasteInfo) {
        WasteCodeInfo.Builder wasteCodeInfoBuilder = new WasteCodeInfo.Builder()
                .withStore( (String) storeWasteInfo.get( "Store").getValue() )
                .withName( (String) storeWasteInfo.get( "Name").getValue() )
                .withRegion( (String) storeWasteInfo.get( "Region").getValue() );
        return wasteCodeInfoBuilder;
    }

    private void removeNoLongerNecessaryDataFromStoreWastInfo(StoreWasteInfo storeWasteInfo) {
        storeWasteInfo.remove( "Store" );
        storeWasteInfo.remove( "Name" );
        storeWasteInfo.remove( "Region" );
    }

    private void addWasteInfoToWasteCodeInfo(StoreWasteInfo storeWasteInfo, WasteCodeInfo.Builder wasteCodeInfoBuilder) {
        for ( String key: storeWasteInfo.keySet() ) {
            WasteCodeInfo wasteCodeInfo = wasteCodeInfoBuilder.build();
            Value value = storeWasteInfo.get( key );
            wasteCodeInfo.setWasteCode( key );
            wasteCodeInfo.setAmountOfWaste( (Double) value.getValue() );
            wasteCodeInfoList.add( wasteCodeInfo );
        }
    }

    public void makeOutputCsvLineList() {
        for ( WasteCodeInfo wasteCodeInfo : wasteCodeInfoList ) {
            ArrayList<String> element = (ArrayList<String>)csvCoder.code( wasteCodeInfo );
            outputCsvLineList.add( csvSerializer.serialize(element, CsvUtils.CSV_SEPARATOR) );
        }
    }

    public void saveCsvFile() {
        for (String csvLine: outputCsvLineList) {
            csvWriter.writeCsvLine(csvLine);
        }
        csvWriter.closeWriter();
    }

}


