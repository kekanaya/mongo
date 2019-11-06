package com.example.mongo.controller;

import com.example.mongo.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        model.put("jobs", jobService.retrieveJobs(name));
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

    private String getLoggedinUserName() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return principal.toString();
    }
}
