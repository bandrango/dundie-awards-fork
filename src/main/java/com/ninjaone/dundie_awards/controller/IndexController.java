package com.ninjaone.dundie_awards.controller;

import com.ninjaone.dundie_awards.application.dto.ActivityDTO;
import com.ninjaone.dundie_awards.application.dto.EmployeeDTO;
import com.ninjaone.dundie_awards.application.service.ActivityApplicationService;
import com.ninjaone.dundie_awards.application.service.EmployeeApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import lombok.RequiredArgsConstructor;

/**
 * Index Web Controller
 * Handles requests to render the home page.
 * Delegates business logic to Application Services.
 */
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    private final EmployeeApplicationService employeeService;
    private final ActivityApplicationService activityService;

    /**
     * GET /
     * Render home page with employees and activities
     */
    @GetMapping
    public String getIndex(Model model) {
        logger.info("HTTP Request: GET / - Rendering home page");
        try {
            long employeeCount = employeeService.getEmployeeCount();
            long activityCount = activityService.getActivityCount();

            logger.debug("Loading model with data: Employees={}, Activities={}", employeeCount, activityCount);

            List<EmployeeDTO> employees = employeeService.getAllEmployees();
            List<ActivityDTO> activities = activityService.getAllActivities();

            model.addAttribute("employees", employees);
            model.addAttribute("activities", activities);

            logger.info("Response: Home page loaded successfully");
            return "index";
        } catch (Exception e) {
            logger.error("Error loading home page: {}", e.getMessage(), e);
            throw e;
        }
    }
}
