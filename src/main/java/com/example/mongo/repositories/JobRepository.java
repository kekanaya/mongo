package com.example.mongo.repositories;

import java.util.Date;
import java.util.List;

import com.example.mongo.model.JobFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

/**
 * We can either use a repository or query using mongotemplate (refer to MongoApplication)
 */
public interface JobRepository extends MongoRepository<JobFile, String> {

    public List<JobFile> findByName(String name);

    public JobFile findById(int id);

    public List<JobFile> findByNameAndJobNameAndSuccessAndRunDate(String name, String jobName, Boolean success, Date runDate);

}
