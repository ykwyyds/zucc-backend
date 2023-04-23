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
import me.zhengjie.modules.forum.domain.Talk;
import me.zhengjie.modules.forum.service.TalkService;
import me.zhengjie.modules.forum.service.dto.TalkQueryCriteria;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://eladmin.vip
* @author cuican
* @date 2023-04-19
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "1.帖子管理")
@RequestMapping("/api/talk")
public class TalkController {

    private final TalkService talkService;


    @GetMapping("/page")
    @ApiOperation("1.分页查询；根据关键词、话题搜索帖子")
    @ApiImplicitParam(name="searchStr",value="搜索条件：关键字")
    public ResponseEntity<Object> page1(String searchStr, Pageable pageable){
        if(StringUtils.isEmpty(searchStr)){
            searchStr="";
        }
        return new ResponseEntity<>(talkService.page1(searchStr,pageable),HttpStatus.OK);
    }
//    @GetMapping
//    @Log("查询帖子")
//    @ApiOperation("查询帖子")
//    public ResponseEntity<Object> queryTalk(TalkQueryCriteria criteria, Pageable pageable){
//        return new ResponseEntity<>(talkService.queryAll(criteria,pageable),HttpStatus.OK);
//    }

    @PostMapping("/add")
    @ApiOperation("2新增帖子")
    public ResponseEntity<Object> add(@Validated @RequestBody Talk resources){
        return new ResponseEntity<>(talkService.create(resources),HttpStatus.OK);
    }

    @PutMapping("/edit")
    @ApiOperation("4修改帖子")
    public ResponseEntity<Object> edit(@Validated @RequestBody Talk resources){
        talkService.update(resources);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/del")
    @ApiOperation("5删除帖子")
    public ResponseEntity<Object> del(@RequestBody Long[] ids) {
        talkService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getById")
    @ApiOperation("3根据id查询帖子信息")
    public ResponseEntity<Object> getById(Long id) {
        return new ResponseEntity<>(talkService.getById(id),HttpStatus.OK);
    }

    @GetMapping("/hotSubjectList")
    @ApiOperation("6.热门话题/话题列表")
    public ResponseEntity<Object> hotSubjectList() {
        return new ResponseEntity<>(talkService.hotSubjectList(),HttpStatus.OK);
    }
}