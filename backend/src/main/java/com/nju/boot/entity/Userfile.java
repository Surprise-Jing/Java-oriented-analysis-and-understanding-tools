package com.nju.boot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;

/**
 * <p>
 * 
 * </p>
 *
 * @author JingYa
 * @since 2023-07-12
 */
@TableName("j_userfile")
@ApiModel(value = "Userfile对象", description = "")
@AllArgsConstructor
public class Userfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("编号")
    private String id;

    @ApiModelProperty("用户编号")
    private String uid;

    @ApiModelProperty("文件编号")
    private String fid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    @Override
    public String toString() {
        return "Userfile{" +
            "id = " + id +
            ", uid = " + uid +
            ", fid = " + fid +
        "}";
    }
}
