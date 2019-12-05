package com.work.demos.mybatis.spider.entity;

import com.bailian.servicetk.core.data.BaseEntity;
import com.bailian.servicetk.core.data.BaseEntityV2;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

/**
 * t_reference_mapping
 */
@TableName("t_reference_mapping")
public class InfoMappingEntity extends BaseEntityV2 {
    /**
     * 主键
     */
    @TableField("`id`")
    private Long id;

    /**
     * 文献编号
     */
    @TableField("`pro_num`")
    private Integer proNum;

    /**
     * 作者id
     */
    @TableField("`author_id`")
    private Integer authorId;

    /**
     * 机构id,0代表ncbi上没有该作者的所属信息
     */
    @TableField("`company_id`")
    private Integer companyId;

    public InfoMappingEntity(Long id, Integer proNum, Integer authorId, Integer companyId, Date createTime, Date updateTime) {
        this.id = id;
        this.proNum = proNum;
        this.authorId = authorId;
        this.companyId = companyId;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public InfoMappingEntity() {
        super();
    }

    /**
     * 主键
     * @return id 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 文献编号
     * @return pro_num 文献编号
     */
    public Integer getProNum() {
        return proNum;
    }

    /**
     * 文献编号
     * @param proNum 文献编号
     */
    public void setProNum(Integer proNum) {
        this.proNum = proNum;
    }

    /**
     * 作者id
     * @return author_id 作者id
     */
    public Integer getAuthorId() {
        return authorId;
    }

    /**
     * 作者id
     * @param authorId 作者id
     */
    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    /**
     * 机构id,0代表ncbi上没有该作者的所属信息
     * @return company_id 机构id,0代表ncbi上没有该作者的所属信息
     */
    public Integer getCompanyId() {
        return companyId;
    }

    /**
     * 机构id,0代表ncbi上没有该作者的所属信息
     * @param companyId 机构id,0代表ncbi上没有该作者的所属信息
     */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}