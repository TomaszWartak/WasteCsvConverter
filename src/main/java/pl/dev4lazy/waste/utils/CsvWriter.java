package pl.dev4lazy.waste.utils;

import java.io.*;

public class CsvWriter {

    private BufferedWriter writer;
    public CsvWriter( String csvFileName ) {
        openWriterForFile(csvFileName);
    }
    private void openWriterForFile(String csvFileName) {
        try {
            OutputStream outputStream = new FileOutputStream(csvFileName);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            writer = new BufferedWriter(outputStreamWriter);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void writeCsvLine( String csvLine ) {
        try {
            writer.write( csvLine );
            writer.newLine();
        } catch(IOException ex1) {
            ex1.printStackTrace();
            try {
                if(writer!= null) {
                    writer.close();
                }
            } catch (IOException ex2) {
                ex2.printStackTrace();
            }
        }
    }

    // TODO pamietaj o zamykaniu CsvWritera
    public void closeWriter() {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException ex2) {
                ex2.printStackTrace();
            }
        }
    }

}
