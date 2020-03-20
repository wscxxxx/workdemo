package com.work.demos.mybatis.generef.web;

import java.util.Objects;

public class Gene_info {
    private String name;
    private String tid;
    private String intro;
    private String c_point;
    private String p_point;
    @Override
    public String toString() {
        return "gene{" +
                "name='" + name + '\'' +
                ", tid='" + tid + '\'' +
                ", intro='" + intro + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gene_info gene_info = (Gene_info) o;
        return Objects.equals(name, gene_info.name) &&
                Objects.equals(tid, gene_info.tid) &&
                Objects.equals(intro, gene_info.intro);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, tid, intro);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
