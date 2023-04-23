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

import me.zhengjie.exception.BusinessException;
import me.zhengjie.modules.forum.domain.TalkCollect;
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
        return null;
    }
}