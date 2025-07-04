package com.arpajit.holidayPlanner.repository;

import com.arpajit.holidayPlanner.model.*;

import java.util.*;
import java.time.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HolidaysRepository extends JpaRepository<Holidays, String> {
    List<Holidays> findAll();
    List<Holidays> findByHolDtBetween(LocalDate start, LocalDate end);
    boolean existsByHolDt(LocalDate date);
}