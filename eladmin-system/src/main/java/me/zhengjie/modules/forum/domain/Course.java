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
* @date 2023-04-20
**/
@Entity
@Data
@Table(name="course")
public class Course implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    @ApiModelProperty(value = "id")
    private Long id;

    @Column(name = "`user_id`")
    @ApiModelProperty(value = "用户id")
    private Long userId;

    @Column(name = "`course_name`")
    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @Column(name = "`course_count`")
    @ApiModelProperty(value = "是第几节课")
    private Integer courseCount;

    @Column(name = "`course_week`")
    @ApiModelProperty(value = "是周几，比如1")
    private Integer courseWeek;

    @Column(name = "`course_week_str`")
    @ApiModelProperty(value = "是周几，比如“周一”")
    private String courseWeekStr;

    @Column(name = "`course_begin`")
    @ApiModelProperty(value = "开始时间， hh-MM格式")
    private String courseBegin;

    @Column(name = "`course_end`")
    @ApiModelProperty(value = "结束时间， hh-MM格式")
    private String courseEnd;

    @Column(name = "`create_by`")
    @ApiModelProperty(value = "createBy")
    private String createBy;

    @Column(name = "`update_by`")
    @ApiModelProperty(value = "updateBy")
    private String updateBy;

    @Column(name = "`create_time`")
    @ApiModelProperty(value = "createTime")
    private Timestamp createTime;

    @Column(name = "`update_time`")
    @ApiModelProperty(value = "updateTime")
    private Timestamp updateTime;

    @Column(name = "`teacher`")
    @ApiModelProperty(value = "授课老师")
    private String teacher;

    @Column(name = "`place`")
    @ApiModelProperty(value = "上课地点")
    private String place;
    public void copy(Course source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
