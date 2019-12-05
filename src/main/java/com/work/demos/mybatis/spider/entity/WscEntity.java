package com.work.demos.mybatis.spider.entity;

import com.bailian.servicetk.core.data.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

/**
 * t_wsc
 */
@TableName("t_wsc")
public class WscEntity extends BaseEntity {
    /**
     * 代理服务器地理位置
     */
    @TableField("`names`")
    private String names;

    /**
     * http地址和端口
     */
    @TableField("`http_ip`")
    private String httpIp;

    /**
     * https地址和端口
     */
    @TableField("`https_ip`")
    private String httpsIp;

    /**
     * 最后验证时间
     */
    @TableField("`last_time`")
    private Date lastTime;

    public WscEntity(Integer id, String names, String httpIp, String httpsIp, Date lastTime, Date updateTime) {
        this.id = id;
        this.names = names;
        this.httpIp = httpIp;
        this.httpsIp = httpsIp;
        this.lastTime = lastTime;
        this.updateTime = updateTime;
    }

    public WscEntity() {
        super();
    }

    /**
     * 代理服务器地理位置
     * @return names 代理服务器地理位置
     */
    public String getNames() {
        return names;
    }

    /**
     * 代理服务器地理位置
     * @param names 代理服务器地理位置
     */
    public void setNames(String names) {
        this.names = names == null ? null : names.trim();
    }

    /**
     * http地址和端口
     * @return http_ip http地址和端口
     */
    public String getHttpIp() {
        return httpIp;
    }

    /**
     * http地址和端口
     * @param httpIp http地址和端口
     */
    public void setHttpIp(String httpIp) {
        this.httpIp = httpIp == null ? null : httpIp.trim();
    }

    /**
     * https地址和端口
     * @return https_ip https地址和端口
     */
    public String getHttpsIp() {
        return httpsIp;
    }

    /**
     * https地址和端口
     * @param httpsIp https地址和端口
     */
    public void setHttpsIp(String httpsIp) {
        this.httpsIp = httpsIp == null ? null : httpsIp.trim();
    }

    /**
     * 最后验证时间
     * @return last_time 最后验证时间
     */
    public Date getLastTime() {
        return lastTime;
    }

    /**
     * 最后验证时间
     * @param lastTime 最后验证时间
     */
    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }
}