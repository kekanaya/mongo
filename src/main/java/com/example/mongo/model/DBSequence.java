package com.example.mongo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * MongoDB doesn't provide something similar to "@GeneratedValue" annotation for spring boot.
 * This class is used to manage the sequences as a solution to that issue.
 */
@Document(collection = "sequences")
public class DBSequence {

    @Id
    private String id;
    private long seq;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }
}
