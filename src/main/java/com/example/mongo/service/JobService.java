package com.example.mongo.service;

import com.example.mongo.model.JobFile;
import com.example.mongo.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
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

        public List<JobFile> retrieveJobs(String user) {
            List<JobFile> jobs = repository.findByName(user);
            return jobs;
        }

        public JobFile retrieveJobById(int id) {
            JobFile job = repository.findById(id);
            return job;
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
