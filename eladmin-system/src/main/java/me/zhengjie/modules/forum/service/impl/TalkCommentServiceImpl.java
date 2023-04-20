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
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.forum.repository.TalkCommentRepository;
import me.zhengjie.modules.forum.service.TalkCommentService;
import me.zhengjie.modules.forum.service.dto.TalkCommentDto;
import me.zhengjie.modules.forum.service.dto.TalkCommentQueryCriteria;
import me.zhengjie.modules.forum.service.mapstruct.TalkCommentMapper;
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
* @date 2023-04-20
**/
@Service
@RequiredArgsConstructor
public class TalkCommentServiceImpl implements TalkCommentService {

    private final TalkCommentRepository talkCommentRepository;
    private final TalkCommentMapper talkCommentMapper;

    @Override
    public Map<String,Object> queryAll(TalkCommentQueryCriteria criteria, Pageable pageable){
        Page<TalkComment> page = talkCommentRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(talkCommentMapper::toDto));
    }

    @Override
    public List<TalkCommentDto> queryAll(TalkCommentQueryCriteria criteria){
        return talkCommentMapper.toDto(talkCommentRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public TalkCommentDto findById(Long id) {
        TalkComment talkComment = talkCommentRepository.findById(id).orElseGet(TalkComment::new);
        ValidationUtil.isNull(talkComment.getId(),"TalkComment","id",id);
        return talkCommentMapper.toDto(talkComment);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TalkCommentDto create(TalkComment resources) {
        return talkCommentMapper.toDto(talkCommentRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(TalkComment resources) {
        TalkComment talkComment = talkCommentRepository.findById(resources.getId()).orElseGet(TalkComment::new);
        ValidationUtil.isNull( talkComment.getId(),"TalkComment","id",resources.getId());
        talkComment.copy(resources);
        talkCommentRepository.save(talkComment);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            talkCommentRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<TalkCommentDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (TalkCommentDto talkComment : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("帖子id", talkComment.getTalkId());
            map.put("父评论id，可能为空", talkComment.getPId());
            map.put("发表评论者id", talkComment.getUserId());
            map.put("评论内容", talkComment.getContent());
            map.put(" createBy",  talkComment.getCreateBy());
            map.put(" updateBy",  talkComment.getUpdateBy());
            map.put(" createTime",  talkComment.getCreateTime());
            map.put(" updateTime",  talkComment.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}