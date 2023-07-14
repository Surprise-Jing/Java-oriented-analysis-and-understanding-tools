package com.nju.boot.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LinesDto {

    /** 有效代码行 */
    private int linesOfCode;

    /** 注释代码行 */
    private int linesOfComment;

    /** 空白代码行 */
    private int linesOfBlanks;

    /** 全部代码行 */
    private int linesOfAll;

}
