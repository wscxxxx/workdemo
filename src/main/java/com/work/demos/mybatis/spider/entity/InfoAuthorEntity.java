package com.work.demos.mybatis.spider.entity;

import com.bailian.servicetk.core.data.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * t_reference_author
 */
@TableName("t_reference_author")
public class InfoAuthorEntity extends BaseEntity {
    /**
     * 是否已写入ES
     */
    @TableField("`is_write_es`")
    private String isWriteEs;

    /**
     * 
     */
    @TableField("`name`")
    private String name;

    public InfoAuthorEntity(Integer id, String isWriteEs) {
        this.id = id;
        this.isWriteEs = isWriteEs;
    }

    public InfoAuthorEntity(Integer id, String isWriteEs, String name) {
        this.id = id;
        this.isWriteEs = isWriteEs;
        this.name = name;
    }

    public InfoAuthorEntity() {
        super();
    }

    /**
     * 是否已写入ES
     * @return is_write_es 是否已写入ES
     */
    public String getIsWriteEs() {
        return isWriteEs;
    }

    /**
     * 是否已写入ES
     * @param isWriteEs 是否已写入ES
     */
    public void setIsWriteEs(String isWriteEs) {
        this.isWriteEs = isWriteEs == null ? null : isWriteEs.trim();
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