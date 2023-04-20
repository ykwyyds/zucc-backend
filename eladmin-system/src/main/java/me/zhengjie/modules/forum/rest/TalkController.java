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
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
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

    @GetMapping
    @Log("分页查询帖子")
    @ApiOperation("分页查询帖子")
    public ResponseEntity<Object> page1(String searchStr, Pageable pageable){
        return new ResponseEntity<>(talkService.page1(searchStr,pageable),HttpStatus.OK);
    }
//    @GetMapping
//    @Log("查询帖子")
//    @ApiOperation("查询帖子")
//    public ResponseEntity<Object> queryTalk(TalkQueryCriteria criteria, Pageable pageable){
//        return new ResponseEntity<>(talkService.queryAll(criteria,pageable),HttpStatus.OK);
//    }

    @PostMapping
    @Log("新增帖子")
    @ApiOperation("新增帖子")
    @PreAuthorize("@el.check('talk:add')")
    public ResponseEntity<Object> createTalk(@Validated @RequestBody Talk resources){
        return new ResponseEntity<>(talkService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改帖子")
    @ApiOperation("修改帖子")
    @PreAuthorize("@el.check('talk:edit')")
    public ResponseEntity<Object> updateTalk(@Validated @RequestBody Talk resources){
        talkService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除帖子")
    @ApiOperation("删除帖子")
    @PreAuthorize("@el.check('talk:del')")
    public ResponseEntity<Object> deleteTalk(@RequestBody Long[] ids) {
        talkService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}