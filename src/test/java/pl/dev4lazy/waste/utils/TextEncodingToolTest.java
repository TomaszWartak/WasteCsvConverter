package pl.dev4lazy.waste.utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

class TextEncodingToolTest {

    TextEncodingTool textEncodingTool;

    @BeforeEach
    void prepare() {
        textEncodingTool = new TextEncodingTool();
    }

    @Test
    void encoding1() {
        String charset = textEncodingTool.getCharsetFromText( "Bielsko-Biała");
        String charsetUTF8 = StandardCharsets.UTF_8.displayName();
        Assertions.assertThat( charset ).isEqualTo( charsetUTF8 );
    }
    @Test
    void checkForUTF8() {
        String charset = textEncodingTool.getCharsetFromText( "Jelenia Góra");
        String charsetUTF8 = StandardCharsets.UTF_8.displayName();
        Assertions.assertThat( charset ).isEqualTo( charsetUTF8 );
    }

    @Test
    void coding() {
        String result = textEncodingTool.getTextWithCharset( "S�upsk Hubalczyk�w", "Windows-1250");
        Assertions.assertThat( result).isEqualTo("Słupsk Hubalczyków" );
    }
    @Test
    void coding2() {
        String result = textEncodingTool.getTextWithCharset( "Słupsk Hubalczyków", "Windows-1250");
        Assertions.assertThat( result).isEqualTo("Słupsk Hubalczyków" );
    }

    @Test
    void name() {
        try {
            // Tekst w kodowaniu UTF-8
            String textUTF8 = "Żółć";

            // Konwersja z UTF-8 na ISO-8859-1
            byte[] utf8Bytes = textUTF8.getBytes("UTF-8");
            String textISO8859 = new String(utf8Bytes, "ISO-8859-1");

            System.out.println("Tekst w UTF-8: " + textUTF8);
            System.out.println("Tekst w ISO-8859-1: " + textISO8859);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}