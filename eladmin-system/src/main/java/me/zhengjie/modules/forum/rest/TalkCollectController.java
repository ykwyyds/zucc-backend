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
import me.zhengjie.modules.forum.domain.TalkCollect;
import me.zhengjie.modules.forum.service.TalkCollectService;
import me.zhengjie.modules.forum.service.dto.TalkCollectQueryCriteria;
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
@Api(tags = "2帖子收藏管理")
@RequestMapping("/api/talkCollect")
public class TalkCollectController {

    private final TalkCollectService talkCollectService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('talkCollect:list')")
    public void exportTalkCollect(HttpServletResponse response, TalkCollectQueryCriteria criteria) throws IOException {
        talkCollectService.download(talkCollectService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询帖子收藏")
    @ApiOperation("查询帖子收藏")
    @PreAuthorize("@el.check('talkCollect:list')")
    public ResponseEntity<Object> queryTalkCollect(TalkCollectQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(talkCollectService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增帖子收藏")
    @ApiOperation("新增帖子收藏")
    @PreAuthorize("@el.check('talkCollect:add')")
    public ResponseEntity<Object> createTalkCollect(@Validated @RequestBody TalkCollect resources){
        return new ResponseEntity<>(talkCollectService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改帖子收藏")
    @ApiOperation("修改帖子收藏")
    @PreAuthorize("@el.check('talkCollect:edit')")
    public ResponseEntity<Object> updateTalkCollect(@Validated @RequestBody TalkCollect resources){
        talkCollectService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除帖子收藏")
    @ApiOperation("删除帖子收藏")
    @PreAuthorize("@el.check('talkCollect:del')")
    public ResponseEntity<Object> deleteTalkCollect(@RequestBody Long[] ids) {
        talkCollectService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}