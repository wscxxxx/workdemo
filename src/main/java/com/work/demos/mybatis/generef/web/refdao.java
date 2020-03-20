package com.work.demos.mybatis.generef.web;

import io.swagger.annotations.ApiModelProperty;

public class refdao {

    //每页数量
    @ApiModelProperty("每页數量")
    private Integer pageSize;

    //当前页数
    @ApiModelProperty("当前页数")
    private Integer pageNum;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }
}
