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
package me.zhengjie.modules.forum.repository;

import me.zhengjie.modules.forum.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
* @website https://eladmin.vip
* @author cuican
* @date 2023-04-20
**/
public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {

    /**
     * 查询：今天已排课的课程节数列表
     * @param courseWeek
     * @param userId
     * @return
     */
    @Query(value="select distinct course_count from course where user_id=:userId and course_week=:courseWeek",nativeQuery=true )
    List<Integer> courseCountList(@Param("courseWeek") Integer courseWeek, @Param("userId")Long userId);

    @Query(value="select * from course where user_id=:userId order by course_week,course_count",nativeQuery=true )
    List<Course> getByUser(@Param("userId")Long userId);

}