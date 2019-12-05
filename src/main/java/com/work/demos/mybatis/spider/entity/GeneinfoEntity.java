package com.work.demos.mybatis.spider.entity;

import com.bailian.servicetk.core.data.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * t_gene_info_new
 */
@TableName("t_gene_info_new")
public class GeneinfoEntity extends BaseEntity {
    /**
     * 文献编号
     */
    @TableField("`pre_num`")
    private Integer preNum;

    /**
     * 
     */
    @TableField("`pre_title`")
    private String preTitle;

    /**
     * 摘要中文
     */
    @TableField("`abstract_zh`")
    private Integer abstractZh;

    /**
     * doi
     */
    @TableField("`doi`")
    private String doi;

    /**
     * 书号
     */
    @TableField("`bookcode`")
    private String bookcode;

    /**
     * 出版社
     */
    @TableField("`publish_company`")
    private String publishCompany;

    /**
     * 出版年份
     */
    @TableField("`publish_year`")
    private Integer publishYear;

    /**
     * 出版月份
     */
    @TableField("`publish_month`")
    private Integer publishMonth;

    /**
     * 关键字
     */
    @TableField("`key_words`")
    private String keyWords;

    /**
     * 摘要英文
     */
    @TableField("`abstract_en`")
    private String abstractEn;

    public GeneinfoEntity(Integer id, Integer preNum, String preTitle, Integer abstractZh, String doi, String bookcode, String publishCompany, Integer publishYear, Integer publishMonth, String keyWords) {
        this.id = id;
        this.preNum = preNum;
        this.preTitle = preTitle;
        this.abstractZh = abstractZh;
        this.doi = doi;
        this.bookcode = bookcode;
        this.publishCompany = publishCompany;
        this.publishYear = publishYear;
        this.publishMonth = publishMonth;
        this.keyWords = keyWords;
    }

    public GeneinfoEntity(Integer id, Integer preNum, String preTitle, Integer abstractZh, String doi, String bookcode, String publishCompany, Integer publishYear, Integer publishMonth, String keyWords, String abstractEn) {
        this.id = id;
        this.preNum = preNum;
        this.preTitle = preTitle;
        this.abstractZh = abstractZh;
        this.doi = doi;
        this.bookcode = bookcode;
        this.publishCompany = publishCompany;
        this.publishYear = publishYear;
        this.publishMonth = publishMonth;
        this.keyWords = keyWords;
        this.abstractEn = abstractEn;
    }

    public GeneinfoEntity() {
        super();
    }

    /**
     * 文献编号
     * @return pre_num 文献编号
     */
    public Integer getPreNum() {
        return preNum;
    }

    /**
     * 文献编号
     * @param preNum 文献编号
     */
    public void setPreNum(Integer preNum) {
        this.preNum = preNum;
    }

    /**
     * 
     * @return pre_title 
     */
    public String getPreTitle() {
        return preTitle;
    }

    /**
     * 
     * @param preTitle 
     */
    public void setPreTitle(String preTitle) {
        this.preTitle = preTitle == null ? null : preTitle.trim();
    }

    /**
     * 摘要中文
     * @return abstract_zh 摘要中文
     */
    public Integer getAbstractZh() {
        return abstractZh;
    }

    /**
     * 摘要中文
     * @param abstractZh 摘要中文
     */
    public void setAbstractZh(Integer abstractZh) {
        this.abstractZh = abstractZh;
    }

    /**
     * doi
     * @return doi doi
     */
    public String getDoi() {
        return doi;
    }

    /**
     * doi
     * @param doi doi
     */
    public void setDoi(String doi) {
        this.doi = doi == null ? null : doi.trim();
    }

    /**
     * 书号
     * @return bookcode 书号
     */
    public String getBookcode() {
        return bookcode;
    }

    /**
     * 书号
     * @param bookcode 书号
     */
    public void setBookcode(String bookcode) {
        this.bookcode = bookcode == null ? null : bookcode.trim();
    }

    /**
     * 出版社
     * @return publish_company 出版社
     */
    public String getPublishCompany() {
        return publishCompany;
    }

    /**
     * 出版社
     * @param publishCompany 出版社
     */
    public void setPublishCompany(String publishCompany) {
        this.publishCompany = publishCompany == null ? null : publishCompany.trim();
    }

    /**
     * 出版年份
     * @return publish_year 出版年份
     */
    public Integer getPublishYear() {
        return publishYear;
    }

    /**
     * 出版年份
     * @param publishYear 出版年份
     */
    public void setPublishYear(Integer publishYear) {
        this.publishYear = publishYear;
    }

    /**
     * 出版月份
     * @return publish_month 出版月份
     */
    public Integer getPublishMonth() {
        return publishMonth;
    }

    /**
     * 出版月份
     * @param publishMonth 出版月份
     */
    public void setPublishMonth(Integer publishMonth) {
        this.publishMonth = publishMonth;
    }

    /**
     * 关键字
     * @return key_words 关键字
     */
    public String getKeyWords() {
        return keyWords;
    }

    /**
     * 关键字
     * @param keyWords 关键字
     */
    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords == null ? null : keyWords.trim();
    }

    /**
     * 摘要英文
     * @return abstract_en 摘要英文
     */
    public String getAbstractEn() {
        return abstractEn;
    }

    /**
     * 摘要英文
     * @param abstractEn 摘要英文
     */
    public void setAbstractEn(String abstractEn) {
        this.abstractEn = abstractEn == null ? null : abstractEn.trim();
    }
}