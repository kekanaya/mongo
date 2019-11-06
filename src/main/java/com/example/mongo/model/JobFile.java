package com.example.mongo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Document(collection = "jobs") //If the collection is not specified, it will create a default collection named "jobFile"
public class JobFile {
    @Id
    private long id;
    private String name;
    private String jobName;
    private Date runDate;
    private int nodes;
    private boolean success;
    private String description;
    @Transient
    public static final String SEQUENCE_NAME = "job_seq";
    @Transient String formattedDate;

    public JobFile() {};

    public JobFile(String name, String jobName, int nodes, Date runDate, boolean success, String description) {
        this.name = name;
        this.jobName = jobName;
        this.runDate = runDate;
        this.nodes = nodes;
        this.success = success;
        this. description = description;
    }

    public JobFile(long id, String name, String jobName, int nodes, Date runDate, boolean success, String description) {
        this.id = id;
        this.name = name;
        this.jobName = jobName;
        this.runDate = runDate;
        this.nodes = nodes;
        this.success = success;
        this. description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public int getNodes() {
        return nodes;
    }

    public void setNodes(int nodes) {
        this.nodes = nodes;
    }

    public Date getRunDate() {
        return runDate;
    }

    public void setRunDate(Date runDate) {
        this.runDate = runDate;
    }

    public String getFormattedDate() {
        String strDateFormat = "hh:mm:ss a";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        return dateFormat.format(getRunDate());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        JobFile other = (JobFile) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

}
