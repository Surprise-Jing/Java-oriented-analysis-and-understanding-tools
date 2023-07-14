package com.nju.boot.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileDto {
    private String id;

    private String fileName;

    private String uploadTime;
}