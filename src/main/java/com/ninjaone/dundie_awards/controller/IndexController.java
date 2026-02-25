package com.ninjaone.dundie_awards.controller;

import com.ninjaone.dundie_awards.application.dto.ActivityPageDTO;
import com.ninjaone.dundie_awards.application.dto.EmployeeDTO;
import com.ninjaone.dundie_awards.application.service.ActivityApplicationService;
import com.ninjaone.dundie_awards.application.service.EmployeeApplicationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    private final EmployeeApplicationService employeeService;
    private final ActivityApplicationService activityService;
    private static final int DEFAULT_PAGE_SIZE = 10;

    @GetMapping
    public String getIndex(
            @RequestParam(defaultValue = "0") int page,
            Model model) {
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        ActivityPageDTO activities = activityService.getActivitiesPaginated(page, DEFAULT_PAGE_SIZE);

        model.addAttribute("employees", employees);
        model.addAttribute("activities", activities);

        return "index";
    }
}
