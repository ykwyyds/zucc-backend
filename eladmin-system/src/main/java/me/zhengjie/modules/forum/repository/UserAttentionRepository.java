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

import me.zhengjie.modules.forum.domain.UserAttention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
* @website https://eladmin.vip
* @author cuican
* @date 2023-04-20
**/
@Transactional
public interface UserAttentionRepository extends JpaRepository<UserAttention, Long>, JpaSpecificationExecutor<UserAttention> {

    @Query(value="select * from user_attention where user_id=:currentUserId and user_attention_id=:userId",nativeQuery=true)
    UserAttention getByUser(@Param("currentUserId") Long currentUserId, @Param("userId")Long userId);

    @Modifying
    @Query(value="delete from user_attention where user_id=:currentUserId and user_attention_id=:userId",nativeQuery=true)
    void cancel(@Param("currentUserId") Long currentUserId, @Param("userId")Long userId);

    @Query(value="select u.username,u.nick_name as nickname,u.avatar_path as avatarPath,a.user_attention_id as userAttentionId " +
            "from sys_user u,user_attention a " +
            "where a.user_id=:currentUserId and a.user_attention_id=u.user_id"
            ,nativeQuery=true)
    List<Map<String, Object>> list(@Param("currentUserId") Long currentUserId);
}