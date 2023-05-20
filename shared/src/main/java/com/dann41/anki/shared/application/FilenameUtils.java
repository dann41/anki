package com.dann41.anki.shared.application;

public class FilenameUtils {

    public static String removeExtension(String filename) {
        if (filename != null && filename.contains(".")) {
            return filename.substring(0, filename.lastIndexOf('.'));
        } else {
            return filename;
        }
    }

}
