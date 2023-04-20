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

import me.zhengjie.modules.forum.domain.UserAttention;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.forum.repository.UserAttentionRepository;
import me.zhengjie.modules.forum.service.UserAttentionService;
import me.zhengjie.modules.forum.service.dto.UserAttentionDto;
import me.zhengjie.modules.forum.service.dto.UserAttentionQueryCriteria;
import me.zhengjie.modules.forum.service.mapstruct.UserAttentionMapper;
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
public class UserAttentionServiceImpl implements UserAttentionService {

    private final UserAttentionRepository userAttentionRepository;
    private final UserAttentionMapper userAttentionMapper;

    @Override
    public Map<String,Object> queryAll(UserAttentionQueryCriteria criteria, Pageable pageable){
        Page<UserAttention> page = userAttentionRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(userAttentionMapper::toDto));
    }

    @Override
    public List<UserAttentionDto> queryAll(UserAttentionQueryCriteria criteria){
        return userAttentionMapper.toDto(userAttentionRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public UserAttentionDto findById(Long id) {
        UserAttention userAttention = userAttentionRepository.findById(id).orElseGet(UserAttention::new);
        ValidationUtil.isNull(userAttention.getId(),"UserAttention","id",id);
        return userAttentionMapper.toDto(userAttention);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserAttentionDto create(UserAttention resources) {
        return userAttentionMapper.toDto(userAttentionRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserAttention resources) {
        UserAttention userAttention = userAttentionRepository.findById(resources.getId()).orElseGet(UserAttention::new);
        ValidationUtil.isNull( userAttention.getId(),"UserAttention","id",resources.getId());
        userAttention.copy(resources);
        userAttentionRepository.save(userAttention);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            userAttentionRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<UserAttentionDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (UserAttentionDto userAttention : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("用户id", userAttention.getUserId());
            map.put("关注的用户id", userAttention.getUserAttentionId());
            map.put(" createBy",  userAttention.getCreateBy());
            map.put(" updateBy",  userAttention.getUpdateBy());
            map.put(" createTime",  userAttention.getCreateTime());
            map.put(" updateTime",  userAttention.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}