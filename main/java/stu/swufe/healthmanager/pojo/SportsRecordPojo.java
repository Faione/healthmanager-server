package stu.swufe.healthmanager.pojo;

import java.util.Date;

public class SportsRecordPojo {
    private String id;

    private String sportsId;

    private String sportsname;

    private String userId;

    private String value;

    private Date createTime;

    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSportsId() {
        return sportsId;
    }

    public void setSportsId(String sportsId) {
        this.sportsId = sportsId == null ? null : sportsId.trim();
    }

    public String getSportsname() {
        return sportsname;
    }

    public void setSportsname(String sportsname) {
        this.sportsname = sportsname == null ? null : sportsname.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}