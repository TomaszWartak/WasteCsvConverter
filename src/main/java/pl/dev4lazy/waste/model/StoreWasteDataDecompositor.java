package pl.dev4lazy.waste.model;

import pl.dev4lazy.waste.interfaces.*;
import pl.dev4lazy.waste.utils.CsvReader;
import pl.dev4lazy.waste.utils.CsvWriter;

import java.util.ArrayList;
import java.util.HashMap;

public class StoreWasteDataDecompositor {

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

    private ArrayList<HashMap<String, Value>> storeWasteInfoList2;

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
    public StoreWasteDataDecompositor(
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
        storeWasteInfoList2 = new ArrayList<>();
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
        HashMap<String, Value> storeWasteInfo;
        for (String portion : inputCsvLineList) {
            pieces = getPiecesFromPortion(portion);
            storeWasteInfo = makeStoreWasteInfo(pieces);
            storeWasteInfoList2.add(storeWasteInfo);
        }
        csvReader.closeReader();
    }

    public String getPortion() {
        return csvReader.readCsvLine();
    }


    public ArrayList<String> getPiecesFromPortion( String portion ) {
        return csvParser.parse( portion );
    }

    public HashMap<String, Value>  makeStoreWasteInfo(ArrayList<String> pieces ) {
        return (HashMap<String, Value> )csvDecoder.decode( pieces );
    }

    public void makeWasteCodeInfoList() {
        for (HashMap<String, Value> storeWasteInfo : storeWasteInfoList2) {
            WasteCodeInfo.Builder wasteCodeInfoBuilder = initBuilder(storeWasteInfo);
            removeNoLongerNecessaryDataFromStoreWastInfo(storeWasteInfo);
            addWasteInfoToWasteCodeInfo(storeWasteInfo, wasteCodeInfoBuilder);
        }
    }

    private void addWasteInfoToWasteCodeInfo(HashMap<String, Value> storeWasteInfo, WasteCodeInfo.Builder wasteCodeInfoBuilder) {
        for ( String key: storeWasteInfo.keySet() ) {
            WasteCodeInfo wasteCodeInfo = wasteCodeInfoBuilder.build();
            Value value = storeWasteInfo.get( key );
            wasteCodeInfo.setWasteCode( key );
            wasteCodeInfo.setAmountOfWaste( (Double) value.getValue() );
            wasteCodeInfoList.add( wasteCodeInfo );
        }
    }

    private WasteCodeInfo.Builder initBuilder(HashMap<String, Value> storeWasteInfo) {
        WasteCodeInfo.Builder wasteCodeInfoBuilder = new WasteCodeInfo.Builder()
                .withStore( (String) storeWasteInfo.get( "Store").getValue() )
                .withName( (String) storeWasteInfo.get( "Name").getValue() )
                .withRegion( (String) storeWasteInfo.get( "Region").getValue() );
        return wasteCodeInfoBuilder;
    }

    private void removeNoLongerNecessaryDataFromStoreWastInfo(HashMap<String, Value> storeWasteInfo) {
        storeWasteInfo.remove( "Store" );
        storeWasteInfo.remove( "Name" );
        storeWasteInfo.remove( "Region" );
    }

    public void makeOutputCsvLineList() {
        for ( WasteCodeInfo wasteCodeInfo : wasteCodeInfoList ) {
            ArrayList<String> element = (ArrayList<String>)csvCoder.code( wasteCodeInfo );
            outputCsvLineList.add( csvSerializer.serialize(element) );
        }
    }

    public void saveCsvFile() {
        for (String csvLine: outputCsvLineList) {
            csvWriter.writeCsvLine(csvLine);
        }
        csvWriter.closeWriter();
    }

}


