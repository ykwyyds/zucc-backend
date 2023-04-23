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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.config.FileProperties;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.forum.domain.Talk;
import me.zhengjie.modules.forum.service.TalkService;
import me.zhengjie.utils.FileUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
* @website https://eladmin.vip
* @author cuican
* @date 2023-04-19
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "0-公共接口")
@RequestMapping("/api/common")
public class CommonController {
    private final FileProperties properties;

    @ApiOperation("0-图片上传")
    @PostMapping(value = "/uploadImg")
    public ResponseEntity<Object> uploadImg(@RequestParam MultipartFile multipartFile){
        // 文件大小验证
        FileUtil.checkSize(properties.getAvatarMaxSize(), multipartFile.getSize());
        // 验证文件上传的格式
        String image = "gif jpg png jpeg";
        String fileType = FileUtil.getExtensionName(multipartFile.getOriginalFilename());
        if(fileType != null && !image.contains(fileType)){
            throw new BadRequestException("文件格式错误！, 仅支持 " + image +" 格式");
        }
        File file = FileUtil.upload(multipartFile, properties.getPath().getAvatar());
        String path=file.getPath();
        return new ResponseEntity<>(path, HttpStatus.OK);
    }
}