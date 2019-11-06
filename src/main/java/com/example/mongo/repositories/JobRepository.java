package com.example.mongo.repositories;

import java.util.List;

import com.example.mongo.model.JobFile;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * We can either use a repository or query using mongotemplate (refer to MongoApplication)
 */
public interface JobRepository extends MongoRepository<JobFile, String> {

    public List<JobFile> findByName(String name);

    public JobFile findById(int id);

}
