package com.arpajit.holidayPlanner.service;

import java.time.*;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arpajit.holidayPlanner.dto.*;
import com.arpajit.holidayPlanner.repository.*;

@Service
public class HolidaysService {
    @Autowired
    private HolidaysRepository holidaysRepo;

    public List<HolidayAllFields> getAllHolidayDetails() {
        return holidaysRepo.findAll()
                            .stream()
                            .map(h -> new HolidayAllFields(
                                h.getHolId(),
                                h.getHolDt(),
                                h.getHolName(),
                                h.getHolType(),
                                h.getHolSource(),
                                h.getCreatedDt(),
                                h.getModifiedDt(),
                                h.getCreatedBy(),
                                h.getModifiedBy(),
                                h.getModRemarks()))
                            .toList();
    }

    public List<HolidayOutput> getAllHolidays() {
        return holidaysRepo.findAll()
                            .stream()
                            .map(h -> new HolidayOutput(
                                h.getHolDt(),
                                h.getHolName(),
                                h.getHolType()))
                            .toList();
    }

    public List<HolidayOutput> getMonthYearList(MonthYearRequest request) {
        int year = request.getYear();
        int month = request.getMonth();

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        return holidaysRepo.findByHolDtBetween(start, end)
                            .stream()
                            .map(h -> new HolidayOutput(
                                h.getHolDt(),
                                h.getHolName(),
                                h.getHolType()))
                            .toList();
    }

    public List<HolidayPlanOutput> getHolidayPlanner(HolidayPlanRequest request) {
        LocalDate start = request.getStartDate();
        LocalDate end = request.getEndDate();
        int leaves = request.getLeaves();
        int daysOfHolidays = request.getDaysOfHolidays();

        List<HolidayPlanOutput> plan = new ArrayList<>();
        while(!start.isAfter(end)) {
            LocalDate currDay = start;
            int totalLeaves = 0;
            List<LocalDate> leaveDates = new ArrayList<>();
            for(int i=0;i<daysOfHolidays;i++) {
                if(totalLeaves>leaves) break;
                if(holidaysRepo.existsByHolDt(currDay)
                        ||currDay.getDayOfWeek().name()=="SATURDAY"
                        ||currDay.getDayOfWeek().name()=="SUNDAY") {
                } else {
                    totalLeaves++;
                    leaveDates.add(currDay);
                }
                currDay = currDay.plusDays(1);
            }
            System.out.printf("\n\ncurrDay = %tF%n, totalLeaves = %d, leaves = %d\n\n",currDay, totalLeaves, leaves);
            if(totalLeaves<=leaves)
                plan.add(new HolidayPlanOutput(start, currDay.minusDays(1), leaveDates));
            start = start.plusDays(1);
        }
        return plan;
    }
}
