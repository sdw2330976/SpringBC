package com.sdw.soft.web;

import com.sdw.soft.validate.DateTime;
import com.sdw.soft.vo.GoodsInfo;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@Validated
@RestController
@RequestMapping("/api/goods/")
public class GoodsInfoController {

    @GetMapping("/info")
    public String validate(@Validated GoodsInfo goodsInfo) {
        return "ok";
    }


    @GetMapping("/query")
    public String query(@NotBlank(message = "name 不能为空") @Length(min = 2, max = 10, message = "name 长度必须在{min} - {max}之间") String name) {
        return "ok";
    }

    @GetMapping("/time")
    public String dateTime(@DateTime(message = "您输入的格式错误，正确的格式为：{format}", format = "yyyy-MM-dd HH:mm") String dateTime) {
        return "ok";
    }
}
