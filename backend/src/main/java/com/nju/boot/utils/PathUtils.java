package com.nju.boot.utils;

import java.io.File;
import java.nio.file.Paths;

public class PathUtils {
    public final static String absolutePath = new File("").getAbsolutePath();
    //public static final String FILEPATH = Paths.get(absolutePath,"file").toString();

    public static final String FILE_SRC_PATH = Paths.get(absolutePath,"file", "src").toString();

    public static final String FILE_AST_PATH = Paths.get(absolutePath,"file", "output", "ast").toString();

    public static final String FILE_CG_PATH = Paths.get(absolutePath,"file", "output", "cg").toString();

    public static final String FILE_CFG_PATH = Paths.get(absolutePath,"file", "output", "cfg").toString();

    public static final String FILE_PDG_PATH = Paths.get(absolutePath,"file", "output", "pdg").toString();
}
