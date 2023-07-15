package com.nju.boot.utils;

import java.io.File;
import java.nio.file.Paths;

public class PathUtils {
    public final static String absolutePath = new File("").getAbsolutePath();
    public static final String FILEPATH = Paths.get(absolutePath,"file").toString();
}
