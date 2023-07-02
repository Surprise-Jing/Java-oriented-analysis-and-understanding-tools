package com.nju.boot.util;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathUtils {
    static String absolutePath = new File("").getAbsolutePath();

    public static String PROGRAMS_FOLDER =  Paths.get(absolutePath,"staticSlicer","src","test","graph").toString();

    public static String PROGRAMS_OUT_FOLDER = Paths.get(absolutePath,"staticSlicer","src","test","res").toString();
}
