package com.example.mongo.service;

import com.example.mongo.model.JobFile;
import com.example.mongo.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobRepository repository;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;
    @Autowired
    MongoTemplate mongoTemplate;

    public List<JobFile> retrieveJobs(String user) {
        List<JobFile> jobs = repository.findByName(user);
        return jobs;
    }

    public List<JobFile> retrieveAllJobs() {
        List<JobFile> jobs = repository.findAll();
        return jobs;
    }

    public JobFile retrieveJobById(int id) {
        JobFile job = repository.findById(id);
        return job;
    }

//    public List<JobFile> retrieveFilteredJobs(String name, String jobName, Boolean success, Date runDate) {
//        List<JobFile> jobs = repository.findByNameAndJobNameAndSuccessAndRunDate(name, jobName, success, runDate);
//        return jobs;
//    }

    public List<JobFile> retrieveFilteredJobs(String name, String jobName, Boolean success, Date runDate) {
        Query query = new Query();
        if (!StringUtils.isEmpty(name)) {
            query.addCriteria(Criteria.where("name").is(name));
        }
        if (!StringUtils.isEmpty(jobName)) {
            query.addCriteria(Criteria.where("jobName").is(jobName));
        }
        if (success != null) {
            query.addCriteria(Criteria.where("success").is(success));
        }
        if (runDate != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(runDate);
            c.add(Calendar.DATE, 1);
            Date nextDay = c.getTime();
            query.addCriteria(Criteria.where("runDate").gt(runDate).lt(nextDay));
        }
        return mongoTemplate.find(query, JobFile.class);
    }

    public void rerunJob(int id) {
        //actual situation - job run back end logic should be called here
        JobFile job = retrieveJobById(id);
        job.setId(sequenceGeneratorService.generateSequence(JobFile.SEQUENCE_NAME));
        job.setRunDate(Calendar.getInstance().getTime());
        repository.insert(job);
    }

    public void deleteJob(int id) {
        JobFile job = retrieveJobById(id);
        repository.delete(job);
    }

    public void runNewJob(String name, String jobName, int nodes, String description){
        JobFile job = new JobFile(sequenceGeneratorService.generateSequence(JobFile.SEQUENCE_NAME), name, jobName, nodes,
                Calendar.getInstance().getTime(), true, description);
        repository.insert(job);
    }

    public void oneTimeInsert() {
        repository.save(new JobFile(1, "kalani", "Job1", 5, new Date(), true,"test spark 5 nodes"));
        repository.save(new JobFile(2,"Joe", "Job2", 8, new Date(), true, "test cluster"));
        repository.save(new JobFile(3,"Ben", "Job3", 9, new Date(),false, "test summary"));
        repository.save(new JobFile(4, "kalani", "Job2", 5, new Date(), false,"test spark 100 nodes"));
    }

}
