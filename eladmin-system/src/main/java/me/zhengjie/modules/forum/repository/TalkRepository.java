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

/**
* @website https://eladmin.vip
* @author cuican
* @date 2023-04-19
**/
public interface TalkRepository extends JpaRepository<Talk, Long>, JpaSpecificationExecutor<Talk> {
//    @Query(value="select count(id) from Talk where title like concat('%',:searchStr,'%')")
//    long totalCount(@Param("searchStr") String searchStr);
//
//    @Query(value="select t,u.username,u.nickName from Talk t ,User u where t.title like concat('%',:searchStr,'%')")
//    List<TalkDto> pageList(@Param("searchStr") String searchStr);
//    @Query(value ="select count(id) from Talk where title like concat('%',:#{criteria.searchStr},'%')",
//            countQuery="select t,u.username,u.nickName from Talk t ,User u where t.title like concat('%',:#{criteria.searchStr},'%')"
    @Query(value="select t.*,u.username,u.nick_name as nickName from talk t ,sys_user u where if(:searchStr !='',t.title like concat('%',:searchStr,'%'),1=1) and u.user_id=t.user_id",
            countQuery ="select count(id) from talk t where where if(:searchStr !='',t.title like concat('%',:searchStr,'%'),1=1)",nativeQuery = true
    )
    Page page1(@Param("searchStr") String searchStr, Pageable pageable);
}