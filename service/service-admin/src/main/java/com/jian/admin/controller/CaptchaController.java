package com.jian.admin.controller;


import cn.hutool.core.map.MapUtil;
import com.google.code.kaptcha.Producer;
import com.jian.commonutils.R;
import com.jian.servicebase.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;


@Api(tags = "验证码管理")
@RestController
@RequestMapping("/admin/captcha")
public class CaptchaController {

    @Autowired
    Producer producer;
    @Autowired
    RedisUtil redisUtil;

    //获取验证码
    @ApiOperation(value = "获取验证码")
    @GetMapping("/get")
    public R captcha() throws IOException {

        String key = UUID.randomUUID().toString();
        String code = producer.createText();
        BufferedImage image = producer.createImage(code);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image,"jpg", out);

        BASE64Encoder encoder = new BASE64Encoder();
        String str = "data:image/jpeg;base64,";

        String base64Img = str + encoder.encode(out.toByteArray());

        redisUtil.hset("captcha",key,code,120);
        return R.success().data(MapUtil.builder()
                                        .put("key",key)
                                        .put("captchaImg",base64Img)
                                        .build());

    }
}
