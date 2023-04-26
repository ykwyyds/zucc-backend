package me.zhengjie.modules.system.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import me.zhengjie.modules.system.domain.User;

@Data
@ApiModel("用户信息")
public class UserVO extends User {
    @ApiModelProperty("是否正在上课。1：上课中，0：不是上课中")
    private Integer isInCourse;
    @ApiModelProperty("正在上课的名称")
    private String courseName;
}
