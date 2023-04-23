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
import me.zhengjie.modules.forum.service.dto.TalkCommentDto;
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
@Api(tags = "3帖子评论")
@RequestMapping("/api/talkComment")
public class TalkCommentController {

    private final TalkCommentService talkCommentService;
    @PostMapping("/add")
    @ApiOperation("1.评论帖子")
    @ApiImplicitParams({
            @ApiImplicitParam(name="talkId",value="帖子id",type = "Long"),
            @ApiImplicitParam(name="content",value="评论内容",type = "String")
    })
    public ResponseEntity<Object> add(Long talkId,String content){
        return new ResponseEntity<>(talkCommentService.add(talkId,content),HttpStatus.OK);
    }

    @GetMapping("/add")
    @ApiOperation("2.评论列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="talkId",value="帖子id",type = "Long"),
    })
    public ResponseEntity<List<Map<String,Object>>> list(Long talkId){
        return new ResponseEntity<List<Map<String,Object>>>(talkCommentService.list(talkId),HttpStatus.OK);
    }
}