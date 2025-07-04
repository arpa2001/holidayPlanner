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
    public List<HolidayAllFields> getAllHolidayDetails(HttpServletRequest httpRequest) throws Exception {
        logger.info("Requested {}: {}", httpRequest.getMethod(), httpRequest.getRequestURL());
        List<HolidayAllFields> response = holidaysService.getAllHolidayDetails();
        logger.info("Response Payload: {}", objectMapper.writeValueAsString(response));
        return response;
    }

    @GetMapping("/allHolidays")
    public List<HolidayOutput> getAllHolidays(HttpServletRequest httpRequest) throws Exception {
        logger.info("Requested {}: {}", httpRequest.getMethod(), httpRequest.getRequestURL());
        List<HolidayOutput> response = holidaysService.getAllHolidays();
        logger.info("Response Payload: {}", objectMapper.writeValueAsString(response));
        return response;
    }

    @PostMapping("/monthYearList")
    public List<HolidayOutput> getMonthYearList(
                @RequestBody MonthYearRequest request, HttpServletRequest httpRequest) throws Exception {
        logger.info("Requested {}: {}", httpRequest.getMethod(), httpRequest.getRequestURL());
        logger.info("Request Payload: {}", objectMapper.writeValueAsString(request));
        List<HolidayOutput> response = holidaysService.getMonthYearList(request);
        logger.info("Response Payload: {}", objectMapper.writeValueAsString(response));
        return response;
    }

    @PostMapping("/holidayPlanner")
    public List<HolidayPlanOutput> getHolidayPlanner(
                @RequestBody HolidayPlanRequest request, HttpServletRequest httpRequest) throws Exception {
        logger.info("Requested {}: {}", httpRequest.getMethod(), httpRequest.getRequestURL());
        logger.info("Request Payload: {}", objectMapper.writeValueAsString(request));
        List<HolidayPlanOutput> response = holidaysService.getHolidayPlanner(request);
        logger.info("Response Payload: {}", objectMapper.writeValueAsString(response));
        return response;
    }
}
