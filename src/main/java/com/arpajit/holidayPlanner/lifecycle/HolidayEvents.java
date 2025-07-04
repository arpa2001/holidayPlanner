package com.arpajit.holidayPlanner.lifecycle;

import org.apache.logging.log4j.*;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.*;
import org.springframework.stereotype.Component;
import jakarta.annotation.PreDestroy;

@Component
public class HolidayEvents {
    private static final Logger logger = LogManager.getLogger(HolidayEvents.class);

    @EventListener
    public void startupSuccess(ApplicationReadyEvent event) {
        logger.info("Ready to accept requests");
    }

    @PreDestroy
    public void appShutdown() {
        logger.fatal("Application is shutting down...");
    }
}
