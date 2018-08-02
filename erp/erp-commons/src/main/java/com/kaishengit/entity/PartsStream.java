package com.kaishengit.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class PartsStream implements Serializable {

   public static final Integer PARTSSTREAM_TYPE_NEW = 0;
   public static final Integer PARTSSTREAM_TYPE_IN  = 1;
   public static final Integer PARTSSTREAM_TYPE_OUT = 2;

    /**
     * 备件流水ID
     */
    private Integer id;

    /**
     * 备件ID
     */
    private Integer partsId;

    private Integer preInventory;

    /**
     * 所需数量
     */
    private Integer num;

    private Integer afterInventory;

    /**
     * 员工ID
     */
    private Integer employeeId;

    /**
     * 0.新增类型 1.入库 2.出库
     */
    private Integer type;

    /**
     * 创建时间
     */
    private Date createTime;

    private Employee employee;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Parts getParts() {
        return parts;
    }

    public void setParts(Parts parts) {
        this.parts = parts;
    }

    private Parts parts;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPartsId() {
        return partsId;
    }

    public void setPartsId(Integer partsId) {
        this.partsId = partsId;
    }

    public Integer getPreInventory() {
        return preInventory;
    }

    public void setPreInventory(Integer preInventory) {
        this.preInventory = preInventory;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getAfterInventory() {
        return afterInventory;
    }

    public void setAfterInventory(Integer afterInventory) {
        this.afterInventory = afterInventory;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        PartsStream other = (PartsStream) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPartsId() == null ? other.getPartsId() == null : this.getPartsId().equals(other.getPartsId()))
            && (this.getPreInventory() == null ? other.getPreInventory() == null : this.getPreInventory().equals(other.getPreInventory()))
            && (this.getNum() == null ? other.getNum() == null : this.getNum().equals(other.getNum()))
            && (this.getAfterInventory() == null ? other.getAfterInventory() == null : this.getAfterInventory().equals(other.getAfterInventory()))
            && (this.getEmployeeId() == null ? other.getEmployeeId() == null : this.getEmployeeId().equals(other.getEmployeeId()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPartsId() == null) ? 0 : getPartsId().hashCode());
        result = prime * result + ((getPreInventory() == null) ? 0 : getPreInventory().hashCode());
        result = prime * result + ((getNum() == null) ? 0 : getNum().hashCode());
        result = prime * result + ((getAfterInventory() == null) ? 0 : getAfterInventory().hashCode());
        result = prime * result + ((getEmployeeId() == null) ? 0 : getEmployeeId().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", partsId=").append(partsId);
        sb.append(", preInventory=").append(preInventory);
        sb.append(", num=").append(num);
        sb.append(", afterInventory=").append(afterInventory);
        sb.append(", employeeId=").append(employeeId);
        sb.append(", type=").append(type);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}