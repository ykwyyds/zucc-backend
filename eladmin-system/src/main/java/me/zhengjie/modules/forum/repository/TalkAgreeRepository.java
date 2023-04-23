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

import me.zhengjie.modules.forum.domain.TalkAgree;
import me.zhengjie.modules.forum.domain.TalkCollect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
* @website https://eladmin.vip
* @author cuican
* @date 2023-04-20
**/
public interface TalkAgreeRepository extends JpaRepository<TalkAgree, Long>, JpaSpecificationExecutor<TalkAgree> {
    @Query(value = "select t.* from talk_agree t where t.user_id=:userId and talk_id=:talkId "
            , nativeQuery = true
    )
    TalkAgree getByUserAndTalk(@Param("talkId") Long talkId,@Param("userId")  Long userId);
}