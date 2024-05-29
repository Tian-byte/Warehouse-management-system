package com.wms.common;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Data
@Slf4j
public class QueryPageParam {
    //默认
    private static Integer PAGE_SIZE=20;
    private static Integer PAGE_NUM=1;

    private Integer pageSize=PAGE_SIZE;
    private Integer PageNum=PAGE_NUM;

    private HashMap param;
}
