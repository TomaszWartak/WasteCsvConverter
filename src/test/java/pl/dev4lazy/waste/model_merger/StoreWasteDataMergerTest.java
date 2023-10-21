package pl.dev4lazy.waste.model_merger;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.dev4lazy.waste.utils.CsvParser;
import pl.dev4lazy.waste.utils.CsvSerializer;

import java.util.ArrayList;

class StoreWasteDataMergerTest {

    private StoreWasteDataMerger storeWasteDataMerger;
    private ArrayList<String> parsedLine;

    @BeforeEach
    void prepare() {
        storeWasteDataMerger = new StoreWasteDataMerger( new CsvParser(), new CsvSerializer() );
        parsedLine = new ArrayList<>();
    }
    @Test
    void lineHasDataToAnalysis__Should_get_true__if_WasteDescription_is_Only_Given() {
        // given
        String lineToParse = "8033;Rybnik Obwiednia P�nocna 21;TEST 2;;";
        parsedLine = storeWasteDataMerger.parseLine( lineToParse );
        // when
        boolean result = storeWasteDataMerger.lineHasDataToAnalysis( parsedLine );
        // then
        Assertions.assertThat( result ).isTrue();
    }
    @Test
    void lineHasDataToAnalysis__Should_get_true__if_WasteCode_is_Only_Given() {
        // given
        String lineToParse = "8033;Rybnik Obwiednia P�nocna 21;;Inny;";
        parsedLine = storeWasteDataMerger.parseLine( lineToParse );
        // when
        boolean result = storeWasteDataMerger.lineHasDataToAnalysis( parsedLine );
        // then
        Assertions.assertThat( result ).isTrue();
    }

    @Test
    void lineHasDataToAnalysis__Should_get_true__if_OtherWasteCode_is_Only_Given() {
        String lineToParse = "8033;Rybnik Obwiednia P�nocna 21;;;07 02 13 - Odpady tworzyw sztucznych";
        parsedLine = storeWasteDataMerger.parseLine( lineToParse );
        // when
        boolean result = storeWasteDataMerger.lineHasDataToAnalysis( parsedLine );
        // then
        Assertions.assertThat( result ).isTrue();
    }
    @Test
    void lineHasDataToAnalysis__Should_get_true__If_WasteCode_and_OtherWasteCode_Are_Given() {
        String lineToParse = "8033;Rybnik Obwiednia P�nocna 21;;Inny;07 02 13 - Odpady tworzyw sztucznych";
        parsedLine = storeWasteDataMerger.parseLine( lineToParse );
        // when
        boolean result = storeWasteDataMerger.lineHasDataToAnalysis( parsedLine );
        // then
        Assertions.assertThat( result ).isTrue();
    }
    @Test
    void lineHasDataToAnalysis__Should_get_false__If_WasteDescription_and_WasteCode_and_OtherWasteCode_Are_Not_Given() {
        String lineToParse = "8033;Rybnik Obwiednia P�nocna 21;;;";
        parsedLine = storeWasteDataMerger.parseLine( lineToParse );
        // when
        boolean result = storeWasteDataMerger.lineHasDataToAnalysis( parsedLine );
        // then
        Assertions.assertThat( result ).isFalse();
    }

    @Test
    void isLineValid__Should_get_true__If_WasteDescription_and_WasteCode_are_proper() {
        // given
        String lineToParse =
                "8033;"+
                "Rybnik Obwiednia P�nocna 21;"+
                // WasteDescription
                "Ziemia, torf;"+
                // WasteCode
                "17 01 07 - Zmieszane odpady z betonu, gruzu ceglanego, odpadowych materia��w ceramicznych"+
                        " i element�w wyposa�enia inne ni� wymienione w 17 01 06;";
        parsedLine = storeWasteDataMerger.parseLine( lineToParse );
        // when
        boolean result = storeWasteDataMerger.isLineValid(parsedLine);
        // then
        Assertions.assertThat( result ).isTrue();
    }
    @Test
    void isLineValid__Should_get_true__If_WasteDescription_isnt_empty_and_WasteCode_equals_Inny_and_OtherWasteCode_are_proper() {
        // given
        String lineToParse =
                "8033;"+
                "Rybnik Obwiednia P�nocna 21;"+
                // WasteDescription
                "Opis odpadu opis odpdau;"+
                // WasteCode
                "Inny;"+
                // OtherWasteCode
                "17 07 01";
        parsedLine = storeWasteDataMerger.parseLine( lineToParse );
        // when
        boolean result = storeWasteDataMerger.isLineValid(parsedLine);
        // then
        Assertions.assertThat( result ).isTrue();
    }

    @Test
    void isLineValid__Should_get_false__If_WasteDescription_is_empty() {
        // given
        String lineToParse =
                "8033;"+
                "Rybnik Obwiednia P�nocna 21;"+
                // WasteDescription
                ";"+
                // WasteCode
                ";"+
                // OtherWasteCode
                "";
        parsedLine = storeWasteDataMerger.parseLine( lineToParse );
        // when
        boolean result = storeWasteDataMerger.isLineValid(parsedLine);
        // then
        Assertions.assertThat( result ).isFalse();
    }

    @Test
    void isLineValid__Should_get_false__If_WasteDescription_isnt_empty_and_WasteCode_is_improper_and_OtherWasteCode_is_improper() {
        // given
        String lineToParse =
                "8033;"+
                "Rybnik Obwiednia P�nocna 21;"+
                // WasteDescription
                "Opis odpadu opis odpadu;"+
                // WasteCode
                "I;"+
                // OtherWasteCode
                "01";
        parsedLine = storeWasteDataMerger.parseLine( lineToParse );
        // when
        boolean result = storeWasteDataMerger.isLineValid(parsedLine);
        // then
        Assertions.assertThat( result ).isFalse();
    }
   @Test
    void isLineValid__Should_get_false__If_WasteDescription_isnt_empty_and_WasteCode_equals_Inny_and_OtherWasteCode_are_improper() {
        // given
        String lineToParse =
                "8033;"+
                "Rybnik Obwiednia P�nocna 21;"+
                // WasteDescription
                "Opis odpadu opis odpdau;"+
                // WasteCode
                "Inny;"+
                // OtherWasteCode
                "01";
        parsedLine = storeWasteDataMerger.parseLine( lineToParse );
        // when
        boolean result = storeWasteDataMerger.isLineValid(parsedLine);
        // then
        Assertions.assertThat( result ).isFalse();
    }

}