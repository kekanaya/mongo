package com.example.mongo.controller;

import com.example.mongo.model.JobFile;
import com.example.mongo.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Parameter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MongoController {

    @Autowired
    JobService jobService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Date - dd/MM/yyyy
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, false));
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String handler (ModelMap model) {
        model.put("name", getLoggedinUserName());
        return "welcome";
    }

    @RequestMapping(value = "/jobs", method = RequestMethod.GET)
    public String showJobs(ModelMap model){
        String name = getLoggedinUserName();
        if(name.equals("admin")) {
            model.put("jobs", jobService.retrieveAllJobs());
        } else {
            model.put("jobs", jobService.retrieveJobs(name));
        }
        return "job-list";
    }

    @RequestMapping(value = "/rerun", method = RequestMethod.GET)
    public String rerunJob(@RequestParam int id, ModelMap model){
        jobService.rerunJob(id);
        return "redirect:jobs";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteJob(@RequestParam int id, ModelMap model){
        jobService.deleteJob(id);
        return "redirect:jobs";
    }

    @RequestMapping(value = "/newJob", method = RequestMethod.GET)
    public String newJob(ModelMap model){
        return "newJob";
    }

    @RequestMapping(value = "/runNewJob", method = RequestMethod.GET)
    public String runNewJob(@RequestParam String jobName, @RequestParam int nodes, @RequestParam String description, ModelMap model){
        jobService.runNewJob(getLoggedinUserName(), jobName, nodes, description);
        return "success";
    }

    @RequestMapping(value = "/filter", method = RequestMethod.GET)
    public String filterJobs(@RequestParam String jobName, @RequestParam String success, @RequestParam String runDate, ModelMap model) throws ParseException {
        String name = getLoggedinUserName();
        List<JobFile> jobs;
        Date date = null;
        if(!StringUtils.isEmpty(runDate)) {
            date=new SimpleDateFormat("dd/MM/yyyy").parse(runDate);
        }
        Boolean isSuccess = null;
        if (!StringUtils.isEmpty(success)) {
            isSuccess = new Boolean(success);
        }

        if(name.equals("admin")) {
            jobs = jobService.retrieveFilteredJobs(null, jobName, isSuccess, date);
        } else {
            jobs = jobService.retrieveFilteredJobs(name, jobName, isSuccess, date);
        }
        model.put("jobs", jobs);
        model.put("jobName", jobName);
        model.put("success", success);
        model.put("runDate", runDate);
        return "job-list";
    }

    private String getLoggedinUserName() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return principal.toString();
    }
}
