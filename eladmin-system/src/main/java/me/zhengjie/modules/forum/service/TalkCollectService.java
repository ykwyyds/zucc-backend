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
package me.zhengjie.modules.forum.service;

import me.zhengjie.base.PageDTO;
import me.zhengjie.modules.forum.domain.TalkCollect;
import me.zhengjie.modules.forum.service.dto.TalkCollectDto;
import me.zhengjie.modules.forum.service.dto.TalkCollectQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://eladmin.vip
* @description 服务接口
* @author cuican
* @date 2023-04-20
**/
public interface TalkCollectService {



    Object collect(Long id);

    Object cancel(Long id);

    PageDTO myCollectPage(String searchStr, Pageable pageable);

    Object agree(Long talkId);

    Object cancelAgree(Long talkId);
}