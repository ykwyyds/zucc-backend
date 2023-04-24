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
package me.zhengjie.modules.forum.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.forum.domain.Course;
import me.zhengjie.modules.forum.service.CourseService;
import me.zhengjie.modules.forum.service.dto.CourseQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://eladmin.vip
* @author cuican
* @date 2023-04-20
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "5课程表管理")
@RequestMapping("/api/course")
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/courseCountList")
    @ApiOperation("1.新增课程时的[第几节课】下拉列表")
    @ApiImplicitParam(name="courseWeek",value="周几",type="Integer")
    public ResponseEntity<List<Integer>> courseCountList(Integer courseWeek){
        return new ResponseEntity<List<Integer>>(courseService.courseCountList(courseWeek),HttpStatus.OK);
    }

    @PostMapping("/add")
    @ApiOperation("2.新增课程表")
    public ResponseEntity<Object> add( @RequestBody Course resources){
        return new ResponseEntity<>(courseService.create(resources),HttpStatus.CREATED);
    }

    @GetMapping("/list")
    @ApiOperation("3.查询课程表，不分页")
    public ResponseEntity<Object> list(CourseQueryCriteria criteria){
        return new ResponseEntity<>(courseService.queryAll(criteria),HttpStatus.OK);
    }



    @PutMapping("/updateCourse")
    @ApiOperation("4.修改课程表")
    public ResponseEntity<Object> updateCourse(@Validated @RequestBody Course resources){
        courseService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/deleteCourse")
    @ApiOperation("5.删除课程表")
    public ResponseEntity<Object> deleteCourse(@RequestBody Long[] ids) {
        courseService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/myCourse")
    @ApiOperation("6.我的课表")
    public ResponseEntity<List<Map<String,Object>>> myCourse(){
        return new ResponseEntity<List<Map<String,Object>>>(courseService.myCourse(),HttpStatus.OK);
    }

}