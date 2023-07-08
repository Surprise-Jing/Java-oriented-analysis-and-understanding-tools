package com.nju.boot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author JingYa
 * @since 2023-07-07
 */
@TableName("j_files")
@ApiModel(value = "Files对象", description = "")
public class Files implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("编号")
    private String id;

    @ApiModelProperty("文件名")
    private String name;

    @ApiModelProperty("文件类型")
    private String type;

    @ApiModelProperty("MD5值")
    private String md5;

    @ApiModelProperty("下载链接")
    private String url;

    @ApiModelProperty("上传时间")
    private LocalDateTime uploadTime;

    @ApiModelProperty("是否删除")
    private Boolean deleted;

    @ApiModelProperty("是否禁用链接")
    private Boolean enable;

    public String  getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    @Override
    public String toString() {
        return "Files{" +
            "id = " + id +
            ", name = " + name +
            ", type = " + type +
            ", md5 = " + md5 +
            ", url = " + url +
            ", uploadTime = " + uploadTime +
            ", deleted = " + deleted +
            ", enable = " + enable +
        "}";
    }
}
