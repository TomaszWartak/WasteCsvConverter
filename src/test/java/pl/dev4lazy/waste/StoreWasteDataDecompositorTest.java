package pl.dev4lazy.waste;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.dev4lazy.waste.model.CsvInfo;
import pl.dev4lazy.waste.model.StoreWasteDataDecompositor;
import pl.dev4lazy.waste.utils.CsvCoder;
import pl.dev4lazy.waste.utils.CsvLineToStoreWasteInfoDecoder2;
import pl.dev4lazy.waste.utils.CsvParser;
import pl.dev4lazy.waste.utils.CsvSerializer;

class StoreWasteDataDecompositorTest {

    private final String HEADER_ROW =
            "Store;Name;Region;17 09 04;15 01 03;15 01 02;15 01 01;"+
            "17 04 05;16 02 13*;16 02 14;16 02 16;07 04 80*;16 06 05;"+
            "16 03 06;16 03 05*;07 04 81;16 06 04;02 01 08*;20 03 07;"+
            "20 01 36;17 09 03*;13 02 08*;17 01 07;02 01 03;03 01 05;"+
            "17 01 03;17 01 02;17 01 01;15 01 11*;15 01 10*;15 01 06;"+
            "17 04 07;16 03 03*;17 02 03;08 01 12;15 02 02*;16 03 04;"+
            "16 02 11*;15 02 03;08 01 11*;20 02 01;13 07 03*;17 06 04;"+
            "16 01 03;16 01 19;20 01 21*;13 02 05*;20 03 99;07 04 04*;"+
            "08 03 18;15 01 04;17 04 02;17 02 01;16 06 01*;13 03 07*;"+
            "07 02 13;20 01 29*;17 04 01;20 01 34;17 03 80;15 01 05;"+
            "15 01 07;20 01 02;20 01 38;16 02 15*;03 01 04*;14 06 03*;"+
            "20 01 35*;20 02 03;19 12 01;08 04 10;08 04 09*;20 03 01;"+
            "15 01 09;20 01 39;06 13 01*;17 01 82;17 02 02;20 01 19*;17 08 02;20 01 01";
    private final String FIRST_ROW =
            "8016;Białystok Narodowych Sił Zbrojnych 3;R08;99,32;55,812;6,38;22,7;2,159;"+
            "0,04;;0,09;0,17;;1,27;;0,105;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;";
    private StoreWasteDataDecompositor storeWasteDataDecompositor;

    @BeforeEach
    public void initAll() {
        storeWasteDataDecompositor = new StoreWasteDataDecompositor(
                new CsvParser(),
                new CsvLineToStoreWasteInfoDecoder2( CsvInfo.getInstance() ),
                new CsvCoder(),
                new CsvSerializer()
        );
    }

    @Test
    public void getPortion__Should_return_header_row() {
        // given - dane wejściowe

        // when - dane wyjściowe (badane)
        String headerRow = storeWasteDataDecompositor.getPortion();

        // then - badanie poprawności danych wyjściowych
        Assertions.assertThat( headerRow ).isEqualTo( HEADER_ROW );
    }

    @Test
    public void getPortion__Should_return_first_row__When_second_portion_is_red() {
        // given - dane wejściowe

        // when - dane wyjściowe (badane)
        String portion = storeWasteDataDecompositor.getPortion();
        portion = storeWasteDataDecompositor.getPortion();

        // then - badanie poprawności danych wyjściowych
        Assertions.assertThat( portion ).isEqualTo( FIRST_ROW );
    }
}