package com.nju.boot.utils;

import java.io.File;
import java.nio.file.Paths;

public class PathUtils {

    /** 当前最外层父程序的路径，用于保存文件信息，配置到服务器可正常使用 */
    public final static String absolutePath = new File("").getAbsolutePath();
    //public static final String FILEPATH = Paths.get(absolutePath,"file").toString();

    /** /file/src 保存文件源代码*/
    public static final String FILE_SRC_PATH = Paths.get(absolutePath,"file", "src").toString();

    /** /file/output/ast 保存文件生成AST树（图片格式）*/
    public static final String FILE_AST_PATH = Paths.get(absolutePath,"file", "output", "ast").toString();

    public static final String FILE_CG_PATH = Paths.get(absolutePath,"file", "output", "cg").toString();

    public static final String FILE_CFG_PATH = Paths.get(absolutePath,"file", "output", "cfg").toString();

    public static final String FILE_PDG_PATH = Paths.get(absolutePath,"file", "output", "pdg").toString();
}
