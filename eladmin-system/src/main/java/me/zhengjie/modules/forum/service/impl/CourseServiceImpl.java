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

import me.zhengjie.modules.forum.domain.Course;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
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
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    public Map<String,Object> queryAll(CourseQueryCriteria criteria, Pageable pageable){
        Page<Course> page = courseRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(courseMapper::toDto));
    }

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
    public void download(List<CourseDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (CourseDto course : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("用户id", course.getUserId());
            map.put("课程名称", course.getCourseName());
            map.put("是第几节课", course.getCourseCount());
            map.put("是周几，比如1", course.getCourseWeek());
            map.put("是周几，比如“周一”", course.getCourseWeekStr());
            map.put("开始时间， hh-MM格式", course.getCourseBegin());
            map.put("结束时间， hh-MM格式", course.getCourseEnd());
            map.put(" createBy",  course.getCreateBy());
            map.put(" updateBy",  course.getUpdateBy());
            map.put(" createTime",  course.getCreateTime());
            map.put(" updateTime",  course.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}