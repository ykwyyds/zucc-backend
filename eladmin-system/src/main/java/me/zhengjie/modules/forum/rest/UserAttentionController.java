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
import me.zhengjie.modules.forum.domain.UserAttention;
import me.zhengjie.modules.forum.service.UserAttentionService;
import me.zhengjie.modules.forum.service.dto.UserAttentionQueryCriteria;
import org.apache.commons.lang.StringUtils;
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
@Api(tags = "4关注管理")
@RequestMapping("/api/userAttention")
public class UserAttentionController {

    private final UserAttentionService userAttentionService;
    @PostMapping("/attention")
    @ApiOperation("1.关注某用户")
    @ApiImplicitParam(name="userId",value="用户id")
    public ResponseEntity<Object> add(Long userId){
        return new ResponseEntity<>(userAttentionService.attention(userId),HttpStatus.OK);
    }
    @GetMapping("/list")
    @ApiOperation("2.关注列表")
    public ResponseEntity<List<Map<String,Object>>> list(){
        return new ResponseEntity<>(userAttentionService.list(),HttpStatus.OK);
    }

    @PostMapping("/cancel")
    @ApiOperation("3.取消关注")
    @ApiImplicitParam(name="userId",value="用户id")
    public ResponseEntity<Object> del(Long userId){
        return new ResponseEntity<>(userAttentionService.cancel(userId),HttpStatus.OK);
    }
}