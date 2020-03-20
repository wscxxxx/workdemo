package com.work.demos.utils;

/**
 * @author gxh
 * 用于前后端传值，勿作他用！
 * 包含构造器两个
 */
public class Result {

    private Integer statue;

    private String message;

    private Object data;

    private PageInfo pageInfo;

    public Result() {

    }

    public Result(Integer status) {
        this.statue = status;
        if (status.equals(ResultConstant.SUCCESS_STATUE)) {
            this.message = ResultConstant.SUCCESS_MESSAGE;
        }else {
            this.message = ResultConstant.FAIL_MESSAGE;
        }
    }

    public Result(Integer statue, String message) {
        this.message = message;
        this.statue = statue;
    }

    public Result(Integer statue, String message, Object data) {
        this.statue = statue;
        this.message = message;
        this.data = data;
    }

    public Result(Integer statue, String message, Object data, PageInfo pageInfo) {
        this.statue = statue;
        this.message = message;
        this.data = data;
        this.pageInfo = pageInfo;
    }

    public Integer getStatue() {
        return statue;
    }


    public void setStatue(Integer statue) {
        this.statue = statue;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}
