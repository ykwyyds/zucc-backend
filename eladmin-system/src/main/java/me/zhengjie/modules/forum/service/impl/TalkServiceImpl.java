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

import me.zhengjie.base.CommonConstant;
import me.zhengjie.base.PageDTO;
import me.zhengjie.modules.forum.domain.Talk;
import me.zhengjie.modules.forum.domain.TalkAgree;
import me.zhengjie.modules.forum.domain.TalkCollect;
import me.zhengjie.modules.forum.repository.TalkAgreeRepository;
import me.zhengjie.modules.forum.repository.TalkCollectRepository;
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

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;

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
    private final TalkCollectRepository talkCollectRepository;
    private final TalkAgreeRepository talkAgreeRepository;
    private final TalkMapper talkMapper;
    @Override
    public PageDTO page1(String searchStr, Long searchUserId, Pageable pageable) {
        Page<Map<String,Object>> page=talkRepository.page1(searchStr,searchUserId,pageable);
        List<Map<String,Object>> list=page.getContent();
        List<Map<String,Object>> newList=new ArrayList<>();
        Long userId=null;
        if(SecurityUtils.getLoginUser()!=null){
            userId=SecurityUtils.getCurrentUserId();
        }
        for (int i = 0; i <list.size() ; i++) {
            Map<String,Object> oldMap=list.get(i);
            Map<String,Object> map=new HashMap<>();
            map.putAll(oldMap);
            if(map.get("imgs")==null){
                map.put("imgs","");
            }
            if(map.get("isAnonymous")!=null && !"".equals(map.get("isAnonymous")+"") && Integer.parseInt(map.get("isAnonymous")+"")== CommonConstant.YES){
                map.put("username","匿名用户");
                map.put("nickName","******");
                map.put("avatarPath","");
            }
            map.put("isAgree",CommonConstant.NO);
            map.put("isCollect",CommonConstant.NO);
            //查询当前帖子是否被当前用户点赞、收藏
            if(userId!=null){
                Long id= ((BigInteger) map.get("id")).longValue();//帖子id
                TalkAgree agree=talkAgreeRepository.getByUserAndTalk(id,userId);
                TalkCollect collect=talkCollectRepository.getByUserAndTalk(id,userId);
                if(agree!=null){
                    map.put("isAgree",CommonConstant.YES);
                }
                if(collect!=null){
                    map.put("isCollect",CommonConstant.YES);
                }
            }

            newList.add(i,map);
        }
        PageDTO newPage=new PageDTO();
        newPage.setContent(newList);
        newPage.setTotalElements(page.getTotalElements());
        return newPage;
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
        resources.setUserId(SecurityUtils.getCurrentUserId());
        resources.setCreateBy(SecurityUtils.getCurrentUsername());
        resources.setUpdateBy(resources.getCreateBy());
        resources.setCreateTime(new Timestamp(new Date().getTime()));
        resources.setUpdateTime(resources.getCreateTime());
        return talkMapper.toDto(talkRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Talk resources) {
        Talk talk = talkRepository.findById(resources.getId()).orElseGet(Talk::new);
        ValidationUtil.isNull( talk.getId(),"Talk","id",resources.getId());
        talk.copy(resources);
        talk.setUpdateTime(new Timestamp(new Date().getTime()));
        talk.setUpdateBy(SecurityUtils.getCurrentUsername());
        talkRepository.save(talk);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            talkRepository.deleteById(id);
        }
    }



}