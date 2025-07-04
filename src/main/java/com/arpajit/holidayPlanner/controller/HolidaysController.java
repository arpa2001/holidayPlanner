package com.arpajit.holidayPlanner.controller;

import com.arpajit.holidayPlanner.service.*;
import com.arpajit.holidayPlanner.dto.*;

import org.slf4j.*;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class HolidaysController {
    private static final Logger logger = LoggerFactory.getLogger(HolidaysController.class);

    @Autowired
    private HolidaysService holidaysService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/allHolidayDetails")
    public List<HolidayAllFields> getAllHolidayDetails(HttpServletRequest httpRequest) {
        logger.info("Requested {}: {}", httpRequest.getMethod(), httpRequest.getRequestURL());
        return holidaysService.getAllHolidayDetails();
    }

    @GetMapping("/allHolidays")
    public List<HolidayOutput> getAllHolidays(HttpServletRequest httpRequest) {
        logger.info("Requested {}: {}", httpRequest.getMethod(), httpRequest.getRequestURL());
        return holidaysService.getAllHolidays();
    }

    @PostMapping("/monthYearList")
    public List<HolidayOutput> getMonthYearList(
                @RequestBody MonthYearRequest request, HttpServletRequest httpRequest) throws Exception {
        logger.info("Requested {}: {}", httpRequest.getMethod(), httpRequest.getRequestURL());
        logger.info("Request Payload: {}", objectMapper.writeValueAsString(request));
        return holidaysService.getMonthYearList(request);
    }

    @PostMapping("/holidayPlanner")
    public List<HolidayPlanOutput> getHolidayPlanner(
                @RequestBody HolidayPlanRequest request, HttpServletRequest httpRequest) throws Exception {
        logger.info("Requested {}: {}", httpRequest.getMethod(), httpRequest.getRequestURL());
        logger.info("Request Payload: {}", objectMapper.writeValueAsString(request));
        return holidaysService.getHolidayPlanner(request);
    }
}
