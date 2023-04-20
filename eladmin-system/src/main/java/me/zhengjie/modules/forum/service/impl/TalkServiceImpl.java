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
package me.zhengjie.modules.forum.service.impl;

import me.zhengjie.modules.forum.domain.Talk;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.forum.repository.TalkRepository;
import me.zhengjie.modules.forum.service.TalkService;
import me.zhengjie.modules.forum.service.dto.TalkDto;
import me.zhengjie.modules.forum.service.dto.TalkQueryCriteria;
import me.zhengjie.modules.forum.service.mapstruct.TalkMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @website https://eladmin.vip
* @description 服务实现
* @author cuican
* @date 2023-04-19
**/
@Service
@RequiredArgsConstructor
public class TalkServiceImpl implements TalkService {

    private final TalkRepository talkRepository;
    private final TalkMapper talkMapper;

    @Override
    public Object page1(String searchStr, Pageable pageable) {
        return talkRepository.page1(searchStr,pageable);
    }
    @Override
    public Map<String,Object> queryAll(TalkQueryCriteria criteria, Pageable pageable){
        Page<Talk> page = talkRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(talkMapper::toDto));
    }

    @Override
    public List<TalkDto> queryAll(TalkQueryCriteria criteria){
        return talkMapper.toDto(talkRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public TalkDto findById(Long id) {
        Talk talk = talkRepository.findById(id).orElseGet(Talk::new);
        ValidationUtil.isNull(talk.getId(),"Talk","id",id);
        return talkMapper.toDto(talk);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TalkDto create(Talk resources) {
        return talkMapper.toDto(talkRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Talk resources) {
        Talk talk = talkRepository.findById(resources.getId()).orElseGet(Talk::new);
        ValidationUtil.isNull( talk.getId(),"Talk","id",resources.getId());
        talk.copy(resources);
        talkRepository.save(talk);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            talkRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<TalkDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (TalkDto talk : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("发帖人id", talk.getUserId());
            map.put("帖子标题", talk.getTitle());
            map.put("帖子所属话题", talk.getSubject());
            map.put("帖子内容", talk.getContent());
            map.put("是否匿名发帖，1-匿名，0-不匿名", talk.getIsAnonymous());
            map.put("点赞数", talk.getAgreeTotal());
            map.put("收藏数", talk.getCollectTotal());
            map.put("评论数", talk.getCommentTotal());
            map.put("创建人用户名", talk.getCreateBy());
            map.put("更新人用户名", talk.getUpdateBy());
            map.put("创建时间", talk.getCreateTime());
            map.put("更新时间", talk.getUpdateTime());
            map.put("0-非热门，1-热门", talk.getIsHot());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }


}