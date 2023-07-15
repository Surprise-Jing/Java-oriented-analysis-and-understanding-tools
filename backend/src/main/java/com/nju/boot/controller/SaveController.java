package com.nju.boot.controller;

import com.nju.boot.handler.DisableBaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;

@RestController
@RequestMapping("/save")
@Api(tags = "生成报告和获取图片")
public class SaveController {

    @GetMapping("")
    @ApiOperation(value = "获取图片")
    @PermitAll
    @DisableBaseResponse
    public void get(@RequestParam("id") String id, HttpServletResponse response) throws Exception {
        if ("".equals(id)) {
            return;
        }
//        byte[] data = upload.getBytes();
//        response.setContentType("image/png");
//        response.setCharacterEncoding("UTF-8");
//        OutputStream outputStream = response.getOutputStream();
//        InputStream in = new ByteArrayInputStream(data);
//        int len;
//        byte[] buf = new byte[1024];
//        while ((len = in.read(buf, 0, 1024)) != -1) {
//            outputStream.write(buf, 0, len);
//        }
//        outputStream.close();
    }

}
