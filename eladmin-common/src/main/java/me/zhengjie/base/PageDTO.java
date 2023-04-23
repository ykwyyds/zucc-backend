package me.zhengjie.base;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class PageDTO {
    private List<Map<String,Object>> content;
    private Long totalElements;
}
