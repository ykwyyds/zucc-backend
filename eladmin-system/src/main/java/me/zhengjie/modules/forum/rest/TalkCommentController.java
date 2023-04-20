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
import me.zhengjie.modules.forum.domain.TalkComment;
import me.zhengjie.modules.forum.service.TalkCommentService;
import me.zhengjie.modules.forum.service.dto.TalkCommentQueryCriteria;
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
* @date 2023-04-20
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "帖子评论管理")
@RequestMapping("/api/talkComment")
public class TalkCommentController {

    private final TalkCommentService talkCommentService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('talkComment:list')")
    public void exportTalkComment(HttpServletResponse response, TalkCommentQueryCriteria criteria) throws IOException {
        talkCommentService.download(talkCommentService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询帖子评论")
    @ApiOperation("查询帖子评论")
    @PreAuthorize("@el.check('talkComment:list')")
    public ResponseEntity<Object> queryTalkComment(TalkCommentQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(talkCommentService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增帖子评论")
    @ApiOperation("新增帖子评论")
    @PreAuthorize("@el.check('talkComment:add')")
    public ResponseEntity<Object> createTalkComment(@Validated @RequestBody TalkComment resources){
        return new ResponseEntity<>(talkCommentService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改帖子评论")
    @ApiOperation("修改帖子评论")
    @PreAuthorize("@el.check('talkComment:edit')")
    public ResponseEntity<Object> updateTalkComment(@Validated @RequestBody TalkComment resources){
        talkCommentService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除帖子评论")
    @ApiOperation("删除帖子评论")
    @PreAuthorize("@el.check('talkComment:del')")
    public ResponseEntity<Object> deleteTalkComment(@RequestBody Long[] ids) {
        talkCommentService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}