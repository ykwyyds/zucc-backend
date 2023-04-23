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
package me.zhengjie.modules.forum.repository;

import me.zhengjie.modules.forum.domain.TalkComment;
import me.zhengjie.modules.forum.service.dto.TalkCommentDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
* @website https://eladmin.vip
* @author cuican
* @date 2023-04-20
**/
public interface TalkCommentRepository extends JpaRepository<TalkComment, Long>, JpaSpecificationExecutor<TalkComment> {
    @Query(value="select c.id,c.talk_id as talkId,c.user_id as userId,c.content,c.create_time as createTime," +
            "u.username,u.nick_name as nickname,u.avatar_path as avatarPath " +
            "from talk_comment c,sys_user u where c.talk_id=:talkId and c.user_id=u.user_id"
            ,nativeQuery = true)
    List<Map<String,Object>> list(@Param("talkId") Long talkId);
}