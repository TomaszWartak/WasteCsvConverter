package pl.dev4lazy.waste.model_merger;

import pl.dev4lazy.waste.interfaces.Parser;
import pl.dev4lazy.waste.interfaces.Serializer;
import pl.dev4lazy.waste.interfaces.Validator;
import pl.dev4lazy.waste.utils.*;
import pl.dev4lazy.waste.validators.WasteCodeValidator;

import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class StoreWasteDataMerger {

    private final int STORE_NR_POSITION = 0;
    private final int STORE_ADDRESS_POSITION = 1;
    private final int WASTE_DESCRIPTION_POSITION = 2;
    private final int WASTE_CODE_POSITION = 3;
    private final int OTHER_WASTE_CODE_POSITION = 4;
    private final String OTHER_WASTE = "Inny";
    private final String inputDirectoryName = "./files";
    private final String outputDirectoryName = "./files/result";
    private final String outputFileName = "all.csv";

    String inputFileCharsetName;
    private CsvWriter csvWriter;
    private Parser csvParser;
    private Serializer csvSerializer;
    private ArrayList<String> linesFromInputFile;
    private WasteDescriptionNotEmptyValidator wasteDescriptionNotEmptyValidator;
    private MainWasteCodeValidator mainWasteCodeValidator;
    private OtherWasteCodeValidator otherWasteCodeValidator;

    public StoreWasteDataMerger(Parser csvParser, Serializer csvSerializer) {
        csvWriter = new CsvWriter(outputFileName, StandardCharsets.ISO_8859_1.displayName() );
        this.csvParser = csvParser;
        this.csvSerializer = csvSerializer;
    }

    public void merge() {
        ArrayList<String> filesNames = null;
        try {
            filesNames = DirectoryUtils.getFilesNamesOnlyFromDirectory( inputDirectoryName );
        } catch ( FileNotFoundException ex ) {
            System.out.println( ex.getMessage() );
            ex.printStackTrace();
        }
        if (filesNames!=null) {
            createOutputFile(outputDirectoryName + "/" + outputFileName);
            addHeaderToOutputFile();
            filesNames.forEach(this::mergeFromFile);
            closeOutputFile();
        }
    }
    private void createOutputFile(String outputDirectoryName) {
        csvWriter = new CsvWriter(outputDirectoryName, StandardCharsets.ISO_8859_1.displayName());
    }

    private void addHeaderToOutputFile() {
        String outputFileHeader = "Nr sklepu;Sklep;Odpad;Kod odpadu pojemnika;Inny kod";
        csvWriter.writeCsvLine( outputFileHeader );
    }

    private void closeOutputFile() {
        csvWriter.closeWriter();
    }

    private void mergeFromFile(String fileName ) {
        readAllLinesFromFile(fileName);
        removeHeaderLine();
        for (String lineFromInputFile : linesFromInputFile) {
            ArrayList<String> parsedLine = parseLine( lineFromInputFile );
            if (lineHasDataToAnalysis(parsedLine)) {
                // linia kodu poniżej bezwarunkowo zapisuje wiersz do pliku WY, jesli tylko nie jest pusty
                // Zastępuje kod zakomentowan /**/ w którym przedobrzyłem z walidacją
/*                String lineForOutputFile = csvSerializer.serialize(parsedLine, CsvUtils.CSV_SEPARATOR);
                lineForOutputFile = TextEncodingTool.getTextWithCharset( lineForOutputFile, inputFileCharsetName );*/
                csvWriter.writeCsvLine(csvSerializer.serialize(parsedLine, CsvUtils.CSV_SEPARATOR));
                /*if (isLineValid(parsedLine)) {
                    if (isOtherWasteCodeUsed(parsedLine)) {
                        setOtherCodeAsMainCode(parsedLine);
                    }
                    extractCodeWithoutDescription(parsedLine);
                    removeNoLongerNeededOtherCode(parsedLine);
                    csvWriter.writeCsvLine(csvSerializer.serialize(parsedLine, CsvUtils.CSV_SEPARATOR));
                } else {
                    if (wasteDescriptionNotEmptyValidator.isNotValid()) {
                        parsedLine.set(WASTE_DESCRIPTION_POSITION, wasteDescriptionNotEmptyValidator.getNegativeValidationDescription());
                    }
                    if (mainWasteCodeValidator.isNotValid()) {
                        parsedLine.set(WASTE_CODE_POSITION, mainWasteCodeValidator.getNegativeValidationDescription());
                    }
                    if (otherWasteCodeValidator.isNotValid()) {
                        parsedLine.set(OTHER_WASTE_CODE_POSITION, otherWasteCodeValidator.getNegativeValidationDescription());
                    }
                }*/
            }
        }
    }

/* TODO- Zrób log -> sklep, kod, komentarz*/
    private void readAllLinesFromFile(String fileName) {
        CsvReader csvReader = new CsvReader(inputDirectoryName+"/"+ fileName, StandardCharsets.ISO_8859_1.displayName());
        linesFromInputFile = csvReader.readAllCsvLines();
        inputFileCharsetName = csvReader.getCharsetNameFromText();
        csvReader.closeReader();
    }

    private void removeHeaderLine() {
        if (!linesFromInputFile.isEmpty()) {
            linesFromInputFile.remove(0);
        }
    }

    public ArrayList<String> parseLine( String lineToParse) {
        return csvParser.parse( lineToParse );
    }

    public boolean lineHasDataToAnalysis(ArrayList<String> parsedLine) {
        return
            !(
                parsedLine.get(WASTE_DESCRIPTION_POSITION).isEmpty() &&
                parsedLine.get(WASTE_CODE_POSITION).isEmpty() &&
                parsedLine.get(OTHER_WASTE_CODE_POSITION).isEmpty()
            );
    }

    public boolean isLineValid(ArrayList<String> parsedLine ) {
        wasteDescriptionNotEmptyValidator = new WasteDescriptionNotEmptyValidator();
        wasteDescriptionNotEmptyValidator.doValidation( parsedLine );
        mainWasteCodeValidator = new MainWasteCodeValidator(new WasteCodeValidator());
        mainWasteCodeValidator.doValidation( parsedLine );
        otherWasteCodeValidator = new OtherWasteCodeValidator( new WasteCodeValidator() );
        otherWasteCodeValidator.doValidation( parsedLine );
        if (wasteDescriptionNotEmptyValidator.isValid()) {
            if (parsedLine.get(WASTE_CODE_POSITION).equals(OTHER_WASTE)) {
                return otherWasteCodeValidator.isValid();
            } else {
                return mainWasteCodeValidator.isValid();
            }
        } else {
            return false;
        }
    }

    private boolean isOtherWasteCodeUsed(ArrayList<String> parsedLine) {
        return parsedLine.get(WASTE_CODE_POSITION).equals(OTHER_WASTE);
    }

    private void setOtherCodeAsMainCode(ArrayList<String> parsedLine) {
        parsedLine.set(WASTE_CODE_POSITION, parsedLine.get(OTHER_WASTE_CODE_POSITION) );
        parsedLine.set(OTHER_WASTE_CODE_POSITION,"");
    }

    private void extractCodeWithoutDescription(ArrayList<String> parsedLine) {
        parsedLine.set(WASTE_CODE_POSITION, getWasteCode(parsedLine) );
    }

    private String getWasteCode(ArrayList<String> parsedLine) {
        return clearTrailingSpaceFromCode( getWasteCodeWithoutDescription(parsedLine) );
    }

    private String clearTrailingSpaceFromCode(String wasteCode) {
        if ((!wasteCode.equals(OTHER_WASTE))&&(!wasteCode.isEmpty())&&(wasteCode.charAt(8)==' ')) {
            wasteCode = wasteCode.substring(0,8);
        }
        return wasteCode;
    }

    private String getWasteCodeWithoutDescription(ArrayList<String> parsedLine) {
        String wasteCode = parsedLine.get(WASTE_CODE_POSITION);
        if (wasteCode.equals(OTHER_WASTE)) {
            return wasteCode;
        }
        if (wasteCode.length()>8) {
            return parsedLine.get(WASTE_CODE_POSITION).substring(0, 9);
        } else {
            return "";
        }
    }
    private String getOtherWasteCode(ArrayList<String> parsedLine) {
        return parsedLine.get(OTHER_WASTE_CODE_POSITION);
    }

    private void removeNoLongerNeededOtherCode(ArrayList<String> parsedLine) {
        parsedLine.remove(OTHER_WASTE_CODE_POSITION);
    }

    private class WasteDataValidator {
        protected boolean valid = false;
        protected String storeNumber;
        protected String wasteCode;

        protected void setSignature( String storeNumber, String wasteCode) {
            this.storeNumber = storeNumber;
            this.wasteCode = wasteCode;
        }

        protected String getSignature() {
            return storeNumber+", "+wasteCode;
        }
    }

    private class WasteDescriptionNotEmptyValidator extends WasteDataValidator implements Validator<ArrayList<String>> {

        @Override
        public void doValidation(ArrayList<String> value) {
            valid  = !value.get(WASTE_DESCRIPTION_POSITION).isEmpty();
            if (!valid) {
                // ustawienie danych do komunikatu o braku walidacji
                setSignature( value.get( STORE_NR_POSITION ), getWasteCode(value) );
            }
        }

        @Override
        public boolean isValid() {
            return valid;
        }

        @Override
        public String getNegativeValidationDescription() {
            return getSignature()+", ERR: Nie podano opisu innego odpadu";
        }
    }

    private class MainWasteCodeValidator extends WasteDataValidator implements Validator<ArrayList<String>> {

        private WasteCodeValidator wasteCodeValidator;

        public MainWasteCodeValidator(WasteCodeValidator wasteCodeValidator) {
            this.wasteCodeValidator = wasteCodeValidator;
        }

        @Override
        public void doValidation(ArrayList<String> value) {
            String codeWithoutDescription = getWasteCode(value);
            wasteCodeValidator.doValidation( codeWithoutDescription );
            valid = codeWithoutDescription.equals(OTHER_WASTE) || wasteCodeValidator.isValid();
            if (!valid) {
                setSignature(value.get(STORE_NR_POSITION), codeWithoutDescription);
            }
        }

        @Override
        public boolean isValid() {
            return valid;
        }

        @Override
        public String getNegativeValidationDescription() {
            return getSignature()+", ERR: "+wasteCodeValidator.getNegativeValidationDescription();
        }
    }

    private class OtherWasteCodeValidator extends WasteDataValidator implements Validator<ArrayList<String>> {

        private WasteCodeValidator wasteCodeValidator;

        public OtherWasteCodeValidator(WasteCodeValidator wasteCodeValidator) {
            this.wasteCodeValidator = wasteCodeValidator;
        }

        @Override
        public void doValidation(ArrayList<String> value) {
            String otherWasteCode = getOtherWasteCode(value);
            wasteCodeValidator.doValidation( otherWasteCode );
            valid = wasteCodeValidator.isValid( );
            if (!valid) {
                setSignature( value.get( STORE_NR_POSITION ), otherWasteCode );
            }
        }

        @Override
        public boolean isValid() {
            return valid;
        }

        @Override
        public String getNegativeValidationDescription() {
            return getSignature()+", ERR: "+wasteCodeValidator.getNegativeValidationDescription();
        }
    }
    
}
