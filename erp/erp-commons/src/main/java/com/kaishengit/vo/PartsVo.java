package com.kaishengit.vo;

import java.io.Serializable;

/**
 * @Author: chuzhaohui
 * @Date: Created in 8:41 2018/8/4
 */
public class PartsVo implements Serializable {

    private Integer partsId;
    private Integer num;

    @Override
    public String toString() {
        return "PartsVo{" +
                "partsId=" + partsId +
                ", num=" + num +
                '}';
    }

    public Integer getPartsId() {
        return partsId;
    }

    public void setPartsId(Integer partsId) {
        this.partsId = partsId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
