package com.nju.boot.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MetricsDto {

    private String funcName;

    private int cyclomatic;

    private int maxdepth;

    private int calling;

    private int called;

    private int param;

}
