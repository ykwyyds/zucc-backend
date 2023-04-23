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
package me.zhengjie.modules.forum.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://eladmin.vip
* @description /
* @author cuican
* @date 2023-04-19
**/
@Data
public class TalkDto implements Serializable {

    /** id */
    private Long id;

    /** 发帖人id */
    private Long userId;

    /** 帖子标题 */
    private String title;

    /** 帖子所属话题 */
    private String subject;

    /** 帖子内容 */
    private String content;

    /** 是否匿名发帖，1-匿名，0-不匿名 */
    private Integer isAnonymous;


    /** 创建人用户名 */
    private String createBy;

    /** 更新人用户名 */
    private String updateBy;

    /** 创建时间 */
    private Timestamp createTime;

    /** 更新时间 */
    private Timestamp updateTime;

    /** 0-非热门，1-热门 */
    private Integer isHot;

    private String username;
    private String nickname;

}