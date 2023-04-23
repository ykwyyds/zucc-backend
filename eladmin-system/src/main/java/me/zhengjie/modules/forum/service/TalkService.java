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
package me.zhengjie.modules.forum.service;

import me.zhengjie.modules.forum.domain.Talk;
import me.zhengjie.modules.forum.service.dto.TalkDto;
import me.zhengjie.modules.forum.service.dto.TalkQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://eladmin.vip
* @description 服务接口
* @author cuican
* @date 2023-04-19
**/
public interface TalkService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(TalkQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<TalkDto>
    */
    List<TalkDto> queryAll(TalkQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return TalkDto
     */
    TalkDto findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return TalkDto
    */
    TalkDto create(Talk resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(Talk resources);

    /**
    * 多选删除
    * @param ids /
    */
    void deleteAll(Long[] ids);


    Object page1(String searchStr, Pageable pageable);

    Talk getById(Long id);

    List<String> hotSubjectList();
}