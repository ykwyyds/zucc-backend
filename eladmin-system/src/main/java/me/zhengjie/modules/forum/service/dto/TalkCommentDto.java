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
* @date 2023-04-20
**/
@Data
public class TalkCommentDto implements Serializable {

    /** id */
    private Long id;

    /** 帖子id */
    private Long talkId;

    /** 父评论id，可能为空 */
    private Long pId;

    /** 发表评论者id */
    private Long userId;

    /** 评论内容 */
    private String content;

    private String createBy;

    private String updateBy;

    private Timestamp createTime;

    private Timestamp updateTime;
}