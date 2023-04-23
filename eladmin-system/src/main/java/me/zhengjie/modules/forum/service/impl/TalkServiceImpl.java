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

import me.zhengjie.config.FileProperties;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.forum.domain.Talk;
import me.zhengjie.modules.system.domain.User;
import me.zhengjie.utils.*;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;

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
    public Page<Map<String,Object>> page1(String searchStr, Pageable pageable) {
        Page<Map<String,Object>> page=talkRepository.page1(searchStr,pageable);
        return page;
    }

    @Override
    public Talk getById(Long id) {
        return talkRepository.getById(id);
    }

    @Override
    public List<String> hotSubjectList() {
        return talkRepository.hotSubjectList();
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



}