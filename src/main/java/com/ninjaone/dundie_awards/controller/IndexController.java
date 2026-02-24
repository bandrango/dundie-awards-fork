package com.ninjaone.dundie_awards.controller;

import com.ninjaone.dundie_awards.repository.ActivityRepository;
import com.ninjaone.dundie_awards.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @GetMapping()
    public String getIndex(Model model) {
        logger.info("GET / - Request to retrieve home page (index)");
        try {
            long employeeCount = employeeRepository.count();
            long activityCount = activityRepository.count();
            
            logger.debug("Loading model with data: Employees={}, Activities={}", employeeCount, activityCount);
            
            model.addAttribute("employees", employeeRepository.findAll());
            model.addAttribute("activities", activityRepository.findAll());
            
            logger.info("Home page loaded successfully");
            return "index";
        } catch (Exception e) {
            logger.error("Error loading home page: {}", e.getMessage(), e);
            throw e;
        }
    }
}
