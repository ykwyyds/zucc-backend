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
import me.zhengjie.exception.BusinessException;
import me.zhengjie.modules.forum.domain.TalkAgree;
import me.zhengjie.modules.forum.domain.TalkCollect;
import me.zhengjie.modules.forum.repository.TalkAgreeRepository;
import me.zhengjie.modules.forum.repository.TalkRepository;
import me.zhengjie.utils.*;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.forum.repository.TalkCollectRepository;
import me.zhengjie.modules.forum.service.TalkCollectService;
import me.zhengjie.modules.forum.service.dto.TalkCollectDto;
import me.zhengjie.modules.forum.service.dto.TalkCollectQueryCriteria;
import me.zhengjie.modules.forum.service.mapstruct.TalkCollectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.util.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://eladmin.vip
* @description 服务实现
* @author cuican
* @date 2023-04-20
**/
@Service
@RequiredArgsConstructor
public class TalkCollectServiceImpl implements TalkCollectService {
    private final TalkAgreeRepository talkAgreeRepository;
    private final TalkCollectRepository talkCollectRepository;
    private final TalkCollectMapper talkCollectMapper;
    private final TalkRepository talkRepository;

    @Override
    public Object collect(Long talkId) {
        Long userId= SecurityUtils.getCurrentUserId();
        if(userId==null){
            throw new BusinessException("请先登录。");
        }
        TalkCollect t=talkCollectRepository.getByUserAndTalk(talkId,userId);
        if(t!=null){
            throw new BusinessException("您已收藏过该帖子。");
        }
        TalkCollect c=new TalkCollect();
        c.setCreateBy(SecurityUtils.getCurrentUsername());
        c.setUpdateBy(c.getCreateBy());
        c.setTalkId(talkId);
        c.setUserId(userId);
        talkCollectRepository.save(c);
        return c;
    }

    @Override
    public Object cancel(Long talkId) {
        Long userId=SecurityUtils.getCurrentUserId();
        talkCollectRepository.cancel(userId,talkId);
        return "取消收藏成功。";
    }

    @Override
    public PageDTO myCollectPage(String searchStr, Pageable pageable) {
        Long userId=SecurityUtils.getCurrentUserId();
        Page<Map<String,Object>> page=talkCollectRepository.myCollectPage(searchStr,userId,pageable);
        List<Map<String,Object>> list=page.getContent();
        List<Map<String,Object>> newList=new ArrayList<>();
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
}