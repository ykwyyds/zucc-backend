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
import me.zhengjie.exception.BusinessException;
import me.zhengjie.modules.forum.domain.UserAttention;
import me.zhengjie.modules.forum.repository.CourseRepository;
import me.zhengjie.utils.*;
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

import java.math.BigInteger;
import java.sql.Timestamp;
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
public class UserAttentionServiceImpl implements UserAttentionService {

    private final UserAttentionRepository userAttentionRepository;
    private final CourseRepository courseRepository;
    private final UserAttentionMapper userAttentionMapper;

    @Override
    public Object attention(Long userId) {
        Long currentUserId= SecurityUtils.getCurrentUserId();
        UserAttention temp=userAttentionRepository.getByUser(currentUserId,userId);
        if(temp!=null){
            throw new BusinessException("您已关注过该用户。");
        }
        UserAttention a=new UserAttention();
        a.setUserAttentionId(userId);
        a.setUserId(currentUserId);
        a.setCreateBy(SecurityUtils.getCurrentUsername());
        a.setCreateTime(new Timestamp(new Date().getTime()));
        a.setUpdateBy(a.getCreateBy());
        a.setUpdateTime(a.getCreateTime());
        userAttentionRepository.save(a);
        return a;
    }

    @Override
    public List<Map<String,Object>> list() {
        Long currentUserId= SecurityUtils.getCurrentUserId();
        List<Map<String,Object>> list=userAttentionRepository.list(currentUserId);
        List<Map<String,Object>> resultList=new ArrayList<>();
        for (int i = 0; i <list.size() ; i++) {
            Map<String,Object> tempMap=list.get(i);
            Map<String,Object> map=new HashMap<>();
            for(String key:tempMap.keySet()){
                map.put(key,tempMap.get(key));
            }
            resultList.add(i,map);
        }
        int week=CourseUtil.getWeekOfDate(new Date());
        for (int i = 0; i < resultList.size(); i++) {
            Map<String,Object> m=resultList.get(i);
            Long userAttentionId= ((BigInteger) m.get("userAttentionId")).longValue();
            List<Integer> courseList=courseRepository.courseCountList(week,userAttentionId);
            boolean b=CourseUtil.checkInCourse(courseList);
            if(b){
                resultList.get(i).put("isInCourse", CommonConstant.YES);
            }else{
                resultList.get(i).put("isInCourse", CommonConstant.NO);
            }
        }
        return resultList;
    }

    @Override
    public Object cancel(Long userId) {
        Long currentUserId= SecurityUtils.getCurrentUserId();
        userAttentionRepository.cancel(currentUserId,userId);
        return "取消关注成功。";
    }
}