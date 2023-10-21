package pl.dev4lazy.waste;

import pl.dev4lazy.waste.model_merger.StoreWasteDataMerger;
import pl.dev4lazy.waste.model_converter.StoreWasteDataConverter;
import pl.dev4lazy.waste.model_converter.WasteCodeInfoToCsvLineCoder;
import pl.dev4lazy.waste.model_converter.CsvLineToStoreWasteInfoDecoder;
import pl.dev4lazy.waste.utils.CsvInfo;
import pl.dev4lazy.waste.utils.CsvParser;
import pl.dev4lazy.waste.utils.CsvSerializer;

import java.util.Scanner;

public class WasteCsvTool {

    private static Scanner scanner;
    private static StoreWasteDataConverter storeWasteDataConverter;
    private static StoreWasteDataMerger storeWasteDataMerger;

    public static void main(String[] args) {
        mainLoop();
    }

    private static void mainLoop() {
        scanner = new Scanner(System.in);
        Option option = null;
        do {
            printMenu();
            try {
                option = chooseOption();
                executeOption(option);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (option != Option.EXIT);
    }

    private static void printMenu() {
        System.out.println("Wybierz opcję:");
        for (Option option : Option.values()) {
            System.out.println(option);
        }
    }
    private static Option chooseOption() {
        int option = scanner.nextInt();
        scanner.nextLine();
        return Option.fromInt(option);
    }

    private static void executeOption(Option option) {
        switch (option) {
            case REVERT_DATA -> revertWasteData();
            case MERGE_DATA -> mergeData();
            case EXIT -> close();
        }
    }

    private static void close() {
        scanner.close();
        System.out.println("Bye Bye!");
    }

    private enum Option {
        REVERT_DATA(1, "Konwertuj dane z KPO_Report.csv"),
        MERGE_DATA(2, "Połącz dane z plików sklepowych"),
        EXIT(3, "Zakończ");
        private final int optionNumber;
        private final String description;
        Option(int optionNumber, String description) {
            this.optionNumber = optionNumber;
            this.description = description;
        }
        static Option fromInt(int option) {
            if (option < 0 || option > values().length) {
                throw new IllegalArgumentException("Opcja o takim numerze nie istnieje");
            }
            return values()[option - 1];
        }
        @Override
        public String toString() {
            return String.format("%d - %s", optionNumber, description);
        }
    }
    private static void revertWasteData() {
        storeWasteDataConverter = new StoreWasteDataConverter(
                new CsvParser(),
                new CsvLineToStoreWasteInfoDecoder(CsvInfo.getInstance()),
                new WasteCodeInfoToCsvLineCoder(),
                new CsvSerializer()
        );
        storeWasteDataConverter.readCsvFile();
        storeWasteDataConverter.makeStoreWasteInfoList();
        storeWasteDataConverter.convertStoreInfosToWasteCodeInfos();
        storeWasteDataConverter.makeOutputCsvLineList();
        storeWasteDataConverter.saveCsvFile();
        System.out.println("Konwersja danych zakończona.");
    }

    private static void mergeData() {
        storeWasteDataMerger = new StoreWasteDataMerger(
                new CsvParser(),
                new CsvSerializer());
        storeWasteDataMerger.merge();
        System.out.println("Łączenie danych zakończone.");
    }


}