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
@Api(tags = "4关注管理")
@RequestMapping("/api/userAttention")
public class UserAttentionController {

    private final UserAttentionService userAttentionService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('userAttention:list')")
    public void exportUserAttention(HttpServletResponse response, UserAttentionQueryCriteria criteria) throws IOException {
        userAttentionService.download(userAttentionService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询关注")
    @ApiOperation("查询关注")
    @PreAuthorize("@el.check('userAttention:list')")
    public ResponseEntity<Object> queryUserAttention(UserAttentionQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(userAttentionService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增关注")
    @ApiOperation("新增关注")
    @PreAuthorize("@el.check('userAttention:add')")
    public ResponseEntity<Object> createUserAttention(@Validated @RequestBody UserAttention resources){
        return new ResponseEntity<>(userAttentionService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改关注")
    @ApiOperation("修改关注")
    @PreAuthorize("@el.check('userAttention:edit')")
    public ResponseEntity<Object> updateUserAttention(@Validated @RequestBody UserAttention resources){
        userAttentionService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除关注")
    @ApiOperation("删除关注")
    @PreAuthorize("@el.check('userAttention:del')")
    public ResponseEntity<Object> deleteUserAttention(@RequestBody Long[] ids) {
        userAttentionService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}