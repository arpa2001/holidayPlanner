package com.arpajit.holidayPlanner.controller;

import com.arpajit.holidayPlanner.service.*;
import com.arpajit.holidayPlanner.dto.*;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class HolidaysController {
    @Autowired
    private HolidaysService holidaysService;

    @GetMapping("/allHolidayDetails")
    public List<HolidayAllFields> getAllHolidayDetails() {
        return holidaysService.getAllHolidayDetails();
    }

    @GetMapping("/allHolidays")
    public List<HolidayOutput> getAllHolidays() {
        return holidaysService.getAllHolidays();
    }

    @PostMapping("/monthYearList")
    public List<HolidayOutput> getMonthYearList(@RequestBody MonthYearRequest request) {
        return holidaysService.getMonthYearList(request);
    }

    @PostMapping("/holidayPlanner")
    public List<HolidayPlanOutput> getHolidayPlanner(@RequestBody HolidayPlanRequest request) {
        return holidaysService.getHolidayPlanner(request);
    }
}
