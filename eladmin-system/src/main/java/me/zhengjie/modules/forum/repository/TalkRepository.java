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

import me.zhengjie.modules.forum.domain.Talk;
import me.zhengjie.modules.forum.service.dto.TalkDto;
import me.zhengjie.modules.forum.service.dto.TalkQueryCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * @author cuican
 * @website https://eladmin.vip
 * @date 2023-04-19
 **/
public interface TalkRepository extends JpaRepository<Talk, Long>, JpaSpecificationExecutor<Talk> {
    @Query(value = "select t.id as id ,t.user_id as userId,t.title,t.subject,t.content,t.is_anonymous as isAnonymous,t.create_time as createTime,u.username,u.nick_name as nickname,u.avatar_path as avatarPath," +
            "ifnull(a.agreeTotal,0 ) as agreeTotal,ifnull(c.commenTotal,0) as commenTotal ,ifnull(c1.collectTotal,0) as collectTotal " +
            "from talk t inner join sys_user u on u.user_id=t.user_id " +
            "left join (select count(id) as agreeTotal,talk_id from talk_agree group by  talk_id)a on a.talk_id=t.id "+
            "left join (select count(id) as commenTotal,talk_id from talk_comment group by  talk_id)c on c.talk_id=t.id " +
            "left join (select count(id) as collectTotal,talk_id from talk_collect group by  talk_id)c1 on c1.talk_id=t.id " +
            "where 1=1 " +
            "and (if(:searchStr !='',t.title like concat('%',:searchStr,'%') or t.subject like concat('%',:searchStr,'%') ,1=1)) " +
            "and (if(ifnull(:searchUserId,'')!='',t.user_id=:searchUserId,1=1)) ",
            countQuery = "select count(id) from talk t where 1=1 " +
                    "and (if(:searchStr !='',t.title like concat('%',:searchStr,'%') or t.subject like concat('%',:searchStr,'%'),1=1)) " +
                    "and (if(ifnull(:searchUserId,'')!='',t.user_id=:searchUserId,1=1)) ", nativeQuery = true
    )
    Page<Map<String, Object>> page1(@Param("searchStr") String searchStr,@Param("searchUserId")  Long searchUserId, Pageable pageable);

    @Query(value = "select distinct subject from talk " ,
            nativeQuery = true
    )
    List<String> hotSubjectList();
}