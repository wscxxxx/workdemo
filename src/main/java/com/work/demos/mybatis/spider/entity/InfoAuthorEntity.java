package com.work.demos.mybatis.spider.entity;

import com.bailian.servicetk.core.data.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * t_gene_info_author
 */
@TableName("t_gene_info_author")
public class InfoAuthorEntity extends BaseEntity {
    /**
     * 
     */
    @TableField("`name`")
    private String name;

    public InfoAuthorEntity(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public InfoAuthorEntity() {
        super();
    }

    /**
     * 
     * @return name 
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name 
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}