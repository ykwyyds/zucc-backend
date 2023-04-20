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

import me.zhengjie.modules.forum.domain.TalkCollect;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
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
public class TalkCollectServiceImpl implements TalkCollectService {

    private final TalkCollectRepository talkCollectRepository;
    private final TalkCollectMapper talkCollectMapper;

    @Override
    public Map<String,Object> queryAll(TalkCollectQueryCriteria criteria, Pageable pageable){
        Page<TalkCollect> page = talkCollectRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(talkCollectMapper::toDto));
    }

    @Override
    public List<TalkCollectDto> queryAll(TalkCollectQueryCriteria criteria){
        return talkCollectMapper.toDto(talkCollectRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public TalkCollectDto findById(Long id) {
        TalkCollect talkCollect = talkCollectRepository.findById(id).orElseGet(TalkCollect::new);
        ValidationUtil.isNull(talkCollect.getId(),"TalkCollect","id",id);
        return talkCollectMapper.toDto(talkCollect);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TalkCollectDto create(TalkCollect resources) {
        return talkCollectMapper.toDto(talkCollectRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(TalkCollect resources) {
        TalkCollect talkCollect = talkCollectRepository.findById(resources.getId()).orElseGet(TalkCollect::new);
        ValidationUtil.isNull( talkCollect.getId(),"TalkCollect","id",resources.getId());
        talkCollect.copy(resources);
        talkCollectRepository.save(talkCollect);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            talkCollectRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<TalkCollectDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (TalkCollectDto talkCollect : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("收藏者id", talkCollect.getUserId());
            map.put("帖子id", talkCollect.getTalkId());
            map.put(" createBy",  talkCollect.getCreateBy());
            map.put(" updateBy",  talkCollect.getUpdateBy());
            map.put(" createTime",  talkCollect.getCreateTime());
            map.put(" updateTime",  talkCollect.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}