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

import com.alipay.api.domain.UserIdentity;
import me.zhengjie.modules.forum.domain.Course;
import me.zhengjie.utils.*;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.forum.repository.CourseRepository;
import me.zhengjie.modules.forum.service.CourseService;
import me.zhengjie.modules.forum.service.dto.CourseDto;
import me.zhengjie.modules.forum.service.dto.CourseQueryCriteria;
import me.zhengjie.modules.forum.service.mapstruct.CourseMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.text.SimpleDateFormat;
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
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;


    @Override
    public List<CourseDto> queryAll(CourseQueryCriteria criteria){
        return courseMapper.toDto(courseRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public CourseDto findById(Long id) {
        Course course = courseRepository.findById(id).orElseGet(Course::new);
        ValidationUtil.isNull(course.getId(),"Course","id",id);
        return courseMapper.toDto(course);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CourseDto create(Course resources) {
        resources.setUserId(SecurityUtils.getCurrentUserId());
        return courseMapper.toDto(courseRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Course resources) {
        Course course = courseRepository.findById(resources.getId()).orElseGet(Course::new);
        ValidationUtil.isNull( course.getId(),"Course","id",resources.getId());
        course.copy(resources);
        courseRepository.save(course);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            courseRepository.deleteById(id);
        }
    }


    @Override
    public List<Integer> courseCountList(Integer courseWeek) {
        Long userId= SecurityUtils.getCurrentUserId();
        List<Integer> list=courseRepository.courseCountList(courseWeek,userId);
        List<Integer> resultList=new ArrayList<>();
        for(int i:CourseUtil.courseCount){
            if(!list.contains(i)){
                resultList.add(i);
            }
        }
        return resultList;
    }

    @Override
    public List<Map<String, Object>> myCourse() {
        List<Course> list=courseRepository.getByUser(SecurityUtils.getCurrentUserId());
        List<Map<String, Object>> result=new ArrayList<>();
        for(int courseCount:CourseUtil.courseCount){
            Map<String, Object> map=new HashMap<>();
            map.put(courseCount+"",courseCount);
            for(Course c:list){
                if(c.getCourseCount().intValue()==courseCount){
                    map.put(c.getCourseWeekStr(),c.getCourseName());
                }
            }
            result.add(map);
        }
        return result;
    }


}