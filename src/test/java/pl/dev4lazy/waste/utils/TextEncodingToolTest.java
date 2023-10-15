package pl.dev4lazy.waste.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class TextEncodingToolTest {

    TextEncodingTool textEncodingTool;

    @BeforeEach
    void prepare() {
        textEncodingTool = new TextEncodingTool();
    }

    @Test
    void encoding() {
        String charset = textEncodingTool.getCharsetForText( "S�upsk Hubalczyk�w");
        Assertions.assertEquals(StandardCharsets.UTF_8.displayName(), charset );
    }

    @Test
    void coding() {
        String result = textEncodingTool.getTextWithCharset( "S�upsk Hubalczyk�w", "Windows-1251");
        Assertions.assertEquals( "Słupsk Hubalczyków", result );
    }
}