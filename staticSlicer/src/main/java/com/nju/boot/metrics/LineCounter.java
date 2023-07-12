package com.nju.boot.metrics;

import java.util.stream.Collectors;

public class LineCounter {
    private int linesOfCode = 0;
    private int lineOfComment = 0;
    private int lineOfBlanks = 0;

    public int getLinesOfCode() {
        return linesOfCode;
    }

    public int getLineOfComment() {
        return lineOfComment;
    }

    public int getLineOfBlanks() {
        return lineOfBlanks;
    }

    public LineCounter(String text) {
        text.lines().forEach(line->{
            String trimmedLine = line.trim();
            if(trimmedLine.isBlank())lineOfBlanks++;
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
