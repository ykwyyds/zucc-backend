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
import me.zhengjie.exception.BusinessException;
import me.zhengjie.modules.forum.domain.Talk;
import me.zhengjie.modules.forum.domain.TalkCollect;
import me.zhengjie.modules.forum.service.TalkCollectService;
import me.zhengjie.modules.forum.service.dto.TalkCollectQueryCriteria;
import me.zhengjie.utils.SecurityUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
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
@Api(tags = "2帖子收藏管理")
@RequestMapping("/api/talkCollect")
public class TalkCollectController {

    private final TalkCollectService talkCollectService;

    @PostMapping("/collect")
    @ApiOperation("1.收藏帖子")
    @ApiImplicitParam(name="talkId",value="帖子id")
    public ResponseEntity<Object> collect(Long talkId){
        return new ResponseEntity<>(talkCollectService.collect(talkId),HttpStatus.OK);
    }
    @GetMapping("/myCollectPage")
    @ApiOperation("2.我的收藏，分页")
    @ApiImplicitParam(name="searchStr",value="搜索条件：关键字")
    public ResponseEntity<Object> page1(String searchStr, Pageable pageable){
        if(StringUtils.isEmpty(searchStr)){
            searchStr="";
        }
        return new ResponseEntity<>(talkCollectService.myCollectPage(searchStr,pageable),HttpStatus.OK);
    }

    @PostMapping("/cancel")
    @ApiOperation("3.取消收藏")
    @ApiImplicitParam(name="talkId",value="帖子id")
    public ResponseEntity<Object> cancel(Long talkId){
        return new ResponseEntity<>(talkCollectService.cancel(talkId),HttpStatus.OK);
    }
}