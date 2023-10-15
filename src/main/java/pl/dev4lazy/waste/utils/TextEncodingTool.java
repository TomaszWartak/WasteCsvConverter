package pl.dev4lazy.waste.utils;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.spi.CharsetProvider;

public class TextEncodingTool {
	  public void test(String text) {
          try {
              byte[] bytes = text.getBytes();
              CharsetDetector detector = new CharsetDetector();
              detector.setText(bytes);
              CharsetMatch match = detector.detect();
              String detectedCharset = match.getName();
              System.out.println("Detected charset: " + detectedCharset);
          } catch (Exception e) {
              e.printStackTrace();
          }
	  }

      public String getCharsetForText( String text  ) {
          CharsetDetector detector = new CharsetDetector();
          detector.setText( text.getBytes() );
          return detector.detect().getName();
      }

     /* public String ______setCharsetForText( String text, String charsetName ) {
          try {

              // Uzyskaj dostawcę kodowania
              CharsetProvider charsetProvider = new CharsetProvider();

              // Uzyskaj kodowanie dla określonego zestawu znaków
              CharsetEncoder encoder = charsetProvider.charsetForName(charsetName).newEncoder();

              // Konwertuj łańcuch znaków na tablicę bajtów
              CharBuffer charBuffer = CharBuffer.wrap(text);
              ByteBuffer byteBuffer = ByteBuffer.allocate(text.length() * 2); // Załóżmy maksymalny rozmiar
              encoder.encode(charBuffer, byteBuffer, true);

              // Przygotuj tablicę bajtów do odczytu
              byteBuffer.flip();

              // Odczytaj zakodowany tekst jako tablicę bajtów
              byte[] encodedBytes = new byte[byteBuffer.limit()];
              byteBuffer.get(encodedBytes);

              // Wyświetl zakodowany tekst
              System.out.println("Zakodowany tekst: " + new String(encodedBytes, charsetName));
          } catch (Exception e) {
              e.printStackTrace();
          }
      }*/

      public String getTextWithCharset( String text, String charsetName) {
          String result = text;
          try {
              result = new String( text.getBytes(charsetName) );
          } catch (UnsupportedEncodingException ex) {
              ex.printStackTrace();
          }
          return result;
      }
}

