package com.sdw.soft.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfo implements Serializable {
    @NotBlank(message = "name 不允许为空")
    @Length(min = 2, max = 10, message = "name{min} - {max}之间")
    private String name;
    @NotNull(message = "price不允许为空")
    @DecimalMin(value = "0.1", message = "价格不能低于{value}")
    private BigDecimal price;

}
