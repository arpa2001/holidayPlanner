package com.arpajit.holidayPlanner.service;

import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arpajit.holidayPlanner.dto.*;
import com.arpajit.holidayPlanner.repository.*;

@Service
public class HolidaysService {
    private static final Logger logger = LoggerFactory.getLogger(HolidaysService.class);

    @Autowired
    private HolidaysRepository holidaysRepo;

    public List<HolidayAllFields> getAllHolidayDetails() {
        logger.info("Generating response");
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
        logger.info("Generating response");
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
        logger.info("Year received: " + year);
        int month;
        try {
            if(request.getMonth()==null) {
                logger.error("Failing request as no month received");
                throw new IllegalArgumentException("month is missing");
            }
            month = Month.valueOf(request.getMonth()).getValue();
            logger.info("Month received: " + month);
        } catch (IllegalArgumentException e) {
            logger.info("Month received: " + request.getMonth());
            logger.error("Failed to parse month");
            throw new IllegalArgumentException(
                "month is not in correct format. Expected: Full month name all in UPPERCASE");
        } catch (RuntimeException e) {
            logger.info("Month received: " + request.getMonth());
            logger.error("Failed due to: " + e.getMessage());
            throw new RuntimeException("Exception due to entered month. ERROR: "+e.getMessage());
        }

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        logger.info("Generating response");
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
            logger.info("startDate received: " + start);
        } catch (DateTimeParseException e) {
            logger.info("startDate received: " + request.getStartDate());
            logger.error("Failed to parse startDate");
            throw new IllegalArgumentException("startDate is not in correct format. Expected: yyyy-mm-dd");
        } catch (RuntimeException e) {
            logger.info("startDate received: " + request.getStartDate());
            logger.error("Failed due to: " + e.getMessage());
            throw new RuntimeException("Exception due to entered startDate. ERROR: "+e.getMessage());
        }

        try {
            end = request.getEndDate() != null ? LocalDate.parse(request.getEndDate()) : LocalDate.of(LocalDate.now().getYear(), 12, 31);
            logger.info("endDate received: " + end);
        } catch (DateTimeParseException e) {
            logger.info("endDate received: " + request.getEndDate());
            logger.error("Failed to parse endDate");
            throw new IllegalArgumentException("endDate is not in correct format. Expected: yyyy-mm-dd");
        } catch (RuntimeException e) {
            logger.info("endDate received: " + request.getEndDate());
            logger.error("Failed due to: " + e.getMessage());
            throw new RuntimeException("Exception due to entered endDate. ERROR: "+e.getMessage());
        }

        leaves = request.getLeaves()==null ? 0 : Integer.valueOf(request.getLeaves());
        logger.info("leaves received: " + leaves);

        if(request.getDaysOfHolidays()==null) {
            logger.error("Failing as no daysOfHolidays received");
            throw new IllegalArgumentException(
                "Please enter how many days of holidays you need in daysOfHolidays");
        } else if(Integer.valueOf(request.getDaysOfHolidays())<leaves) {
            logger.error("Failing as daysOfHolidays is less than leaves");
            throw new IllegalArgumentException(
                "Your Holiday of "+request.getDaysOfHolidays()+" days can be anytime if you can take "+leaves+" leaves");
        } else {
            daysOfHolidays = Integer.valueOf(request.getDaysOfHolidays());
            logger.info("daysOfHolidays received: " + daysOfHolidays);
        }

        List<HolidayPlanOutput> plan = new ArrayList<>();
        logger.info("Generating response");
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
            if(totalLeaves<=leaves) {
                plan.add(new HolidayPlanOutput(start, currDay.minusDays(1), leaveDates));
            }
            start = start.plusDays(1);
        }
        logger.info("Response generation completed");
        return plan;
    }
}
