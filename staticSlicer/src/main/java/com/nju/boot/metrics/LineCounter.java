package com.nju.boot.metrics;

import java.util.stream.Collectors;

public class LineCounter {
    private int linesOfCode = 0;
    private int lineOfComment = 0;
    private int lineOfBlanks = 0;

    public int getLinesOfCode() {
        return linesOfCode;
    }

    public int getLinesOfComment() {
        return lineOfComment;
    }

    public int getLinesOfBlanks() {
        return lineOfBlanks;
    }

    /**
     * 统计代码行数
     * @param text 代码
     */
    public LineCounter(String text) {
        text.lines().forEach(line->{
            String trimmedLine = line.trim();
            //如果是空白行
            if(trimmedLine.isBlank())lineOfBlanks++;
            //如果是注释行
            else if ((trimmedLine.isBlank())||
                    (trimmedLine.startsWith("//"))||
                    (trimmedLine.startsWith("/*"))||
                    (trimmedLine.startsWith("*"))||
                            (trimmedLine.startsWith("*/")))
                lineOfComment++;
            else
                linesOfCode++;

        });
    }
}
