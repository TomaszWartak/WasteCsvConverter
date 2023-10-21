package pl.dev4lazy.waste.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class DirectoryUtils {

    public static ArrayList<String> getFilesNamesOnlyFromDirectory(String pathName ) throws FileNotFoundException{
        File directory = new File( pathName );
        if (directory.isDirectory()) {
            return new ArrayList<>(Arrays.stream(directory.listFiles())
                    .filter( file -> file.isFile() )
                    .map( file -> file.getName() )
                    .toList());
        } else {
            throw new FileNotFoundException( "Ścieżka "+pathName+" nie jest katalogiem.");
        }
    }

}
