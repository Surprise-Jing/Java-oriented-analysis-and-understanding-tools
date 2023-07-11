package com.nju.boot.graphs.printer;

import java.io.Writer;

public abstract class GraphPrinter {
    Writer writer;

    public Writer getWriter() {
        return writer;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
    }

    public abstract void print();
}
