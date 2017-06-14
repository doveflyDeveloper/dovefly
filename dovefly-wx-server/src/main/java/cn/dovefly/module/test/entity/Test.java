package cn.dovefly.module.test.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Test {
    private Integer id;

    private String testName;

    private Integer testAge;

    private BigDecimal testPoint;

    private Date testDate;

    private Date testDatetime;

    private Date testTimestamp;

    private Boolean testStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName == null ? null : testName.trim();
    }

    public Integer getTestAge() {
        return testAge;
    }

    public void setTestAge(Integer testAge) {
        this.testAge = testAge;
    }

    public BigDecimal getTestPoint() {
        return testPoint;
    }

    public void setTestPoint(BigDecimal testPoint) {
        this.testPoint = testPoint;
    }

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    public Date getTestDatetime() {
        return testDatetime;
    }

    public void setTestDatetime(Date testDatetime) {
        this.testDatetime = testDatetime;
    }

    public Date getTestTimestamp() {
        return testTimestamp;
    }

    public void setTestTimestamp(Date testTimestamp) {
        this.testTimestamp = testTimestamp;
    }

    public Boolean getTestStatus() {
        return testStatus;
    }

    public void setTestStatus(Boolean testStatus) {
        this.testStatus = testStatus;
    }
}