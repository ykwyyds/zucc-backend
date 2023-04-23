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

import me.zhengjie.modules.forum.domain.TalkComment;
import me.zhengjie.utils.*;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.forum.repository.TalkCommentRepository;
import me.zhengjie.modules.forum.service.TalkCommentService;
import me.zhengjie.modules.forum.service.mapstruct.TalkCommentMapper;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

/**
* @website https://eladmin.vip
* @description 服务实现
* @author cuican
* @date 2023-04-20
**/
@Service
@RequiredArgsConstructor
public class TalkCommentServiceImpl implements TalkCommentService {

    private final TalkCommentRepository talkCommentRepository;
    private final TalkCommentMapper talkCommentMapper;

    @Override
    public Object add(Long talkId, String content) {
        Long userId= SecurityUtils.getCurrentUserId();
        TalkComment c=new TalkComment();
        c.setContent(content);
        c.setCreateBy(SecurityUtils.getCurrentUsername());
        c.setCreateTime(new Timestamp(new Date().getTime()));
        c.setUpdateBy(c.getCreateBy());
        c.setUpdateTime(c.getCreateTime());
        c.setUserId(userId);
        c.setTalkId(talkId);
        talkCommentRepository.save(c);
        return c;
    }
}