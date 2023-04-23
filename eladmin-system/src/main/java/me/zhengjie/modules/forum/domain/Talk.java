/*
*  Copyright 2019-2020 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package me.zhengjie.modules.forum.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://eladmin.vip
* @description /
* @author cuican
* @date 2023-04-19
**/
@Entity
@Data
@Table(name="talk")
public class Talk implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    @ApiModelProperty(value = "id")
    private Long id;

    @Column(name = "`user_id`")
    @ApiModelProperty(value = "发帖人id")
    private Long userId;

    @Column(name = "`title`")
    @ApiModelProperty(value = "帖子标题")
    private String title;

    @Column(name = "`subject`")
    @ApiModelProperty(value = "帖子所属话题")
    private String subject;

    @Column(name = "`content`")
    @ApiModelProperty(value = "帖子内容")
    private String content;

    @Column(name = "`is_anonymous`")
    @ApiModelProperty(value = "是否匿名发帖，1-匿名，0-不匿名")
    private Integer isAnonymous;


    @Column(name = "`create_by`")
    @ApiModelProperty(value = "创建人用户名")
    private String createBy;

    @Column(name = "`update_by`")
    @ApiModelProperty(value = "更新人用户名")
    private String updateBy;

    @Column(name = "`create_time`")
    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

    @Column(name = "`update_time`")
    @ApiModelProperty(value = "更新时间")
    private Timestamp updateTime;

    @Column(name = "`is_hot`")
    @ApiModelProperty(value = "0-非热门，1-热门")
    private Integer isHot;

    @Column(name = "`imgs`")
    @ApiModelProperty(value = "图片，多个图片用;分隔")
    private String imgs;

    public void copy(Talk source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
