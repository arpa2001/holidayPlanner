package com.arpajit.holidayPlanner.dto;

import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HolidayPlanOutput {
    private LocalDate startAt;
    private LocalDate endAt;
    private List<LocalDate> leaveDates;

    public HolidayPlanOutput(LocalDate startAt, LocalDate endAt, List<LocalDate> leaveDates) {
        this.startAt = startAt;
        this.endAt = endAt;
        this.leaveDates = leaveDates;
    }

    public String getStartAt() {
        return startAt.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
    }
    public String getEndAt() {
        return endAt.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
    }
    public List<String> getLeaveDates() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        return leaveDates.stream()
                        .map(date -> date.format(formatter))
                        .collect(Collectors.toList());
    }
}
