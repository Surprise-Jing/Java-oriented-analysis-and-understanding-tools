package com.nju.boot.graphs.printer;

import com.nju.boot.edges.DummyEdge;
import com.nju.boot.graphs.augmented.ACFG;
import com.nju.boot.graphs.cfg.CFG;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class ACFGPrinter extends CFGPrinter{
    public ACFGPrinter(ACFG cfg, Writer writer) {
        super(cfg, writer);
    }
    protected void setUpDotExporter(){
        dotExporter.setEdgeAttributeProvider(e->{
            Map<String, Attribute> map = new HashMap<>();
            if(e instanceof DummyEdge)
                map.put("style",DefaultAttribute.createAttribute("dashed"));
            return map;
        });
        super.setUpDotExporter();
    }
}
