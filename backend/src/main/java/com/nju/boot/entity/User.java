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
 * User对象
 * </p>
 *
 * @author JingYa
 * @since 2023-07-06
 */
@TableName("j_user")
@ApiModel(value = "User对象", description = "")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户编号")
    private String id;

    @ApiModelProperty("用户名称(唯一)")
    private String username;

    @ApiModelProperty("用户密码(密文存储)")
    private String password;

    @ApiModelProperty("用户电话号码")
    private String phoneNumber;

    @ApiModelProperty("用户邮箱")
    private String email;

    @ApiModelProperty("最近登录日期")
    private String updateAt;

    @ApiModelProperty("用户状态，0表示正常，1表示被禁")
    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String  id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String  getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
            "id = " + id +
            ", username = " + username +
            ", password = " + password +
            ", phoneNumber = " + phoneNumber +
            ", email = " + email +
            ", updateAt = " + updateAt +
            ", status = " + status +
        "}";
    }
}
