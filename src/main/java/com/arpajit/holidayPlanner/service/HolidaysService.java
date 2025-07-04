package com.arpajit.holidayPlanner.service;

import java.time.*;
import java.time.format.DateTimeParseException;
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
        int year = request.getYear()!=null ? Integer.valueOf(request.getYear()) : Year.now().getValue();
        int month;
        try {
            if(request.getMonth()==null) throw new IllegalArgumentException("month is missing");
            month = Month.valueOf(request.getMonth()).getValue();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                "month is not in correct format. Expected: Full month name all in UPPERCASE");
        } catch (RuntimeException e) {
            throw new RuntimeException("Exception due to entered month. ERROR: "+e.getMessage());
        }

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
        LocalDate start;
        LocalDate end;
        int leaves;
        int daysOfHolidays;

        try {
            start = request.getStartDate() != null ? LocalDate.parse(request.getStartDate()) : LocalDate.now();
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("startDate is not in correct format. Expected: yyyy-mm-dd");
        } catch (RuntimeException e) {
            throw new RuntimeException("Exception due to entered startDate. ERROR: "+e.getMessage());
        }

        try {
            end = request.getEndDate() != null ? LocalDate.parse(request.getEndDate()) : LocalDate.of(LocalDate.now().getYear(), 12, 31);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("endDate is not in correct format. Expected: yyyy-mm-dd");
        } catch (RuntimeException e) {
            throw new RuntimeException("Exception due to entered endDate. ERROR: "+e.getMessage());
        }

        leaves = request.getLeaves()==null ? 0 : Integer.valueOf(request.getLeaves());

        if(request.getDaysOfHolidays()==null)
            throw new IllegalArgumentException(
                "Please enter how many days of holidays you need in daysOfHolidays");
        else if(Integer.valueOf(request.getDaysOfHolidays())<leaves)
            throw new IllegalArgumentException(
                "Your Holiday of "+request.getDaysOfHolidays()+" days can be anytime if you can take "+leaves+" leaves");
        else daysOfHolidays = Integer.valueOf(request.getDaysOfHolidays());

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
