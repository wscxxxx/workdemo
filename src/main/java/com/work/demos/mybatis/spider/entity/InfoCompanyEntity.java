package com.work.demos.mybatis.spider.entity;

import com.bailian.servicetk.core.data.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * t_reference_company
 */
@TableName("t_reference_company")
public class InfoCompanyEntity extends BaseEntity {
    /**
     * 
     */
    @TableField("`company`")
    private String company;

    public InfoCompanyEntity(Integer id) {
        this.id = id;
    }

    public InfoCompanyEntity(Integer id, String company) {
        this.id = id;
        this.company = company;
    }

    public InfoCompanyEntity() {
        super();
    }

    /**
     * 
     * @return company 
     */
    public String getCompany() {
        return company;
    }

    /**
     * 
     * @param company 
     */
    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }
}