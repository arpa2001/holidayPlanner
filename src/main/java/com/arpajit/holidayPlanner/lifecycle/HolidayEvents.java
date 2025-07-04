package com.arpajit.holidayPlanner.lifecycle;

import org.slf4j.*;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class HolidayEvents {
    private static final Logger logger = LoggerFactory.getLogger(HolidayEvents.class);

    @EventListener
    public void startupSuccess(ApplicationReadyEvent event) {
        logger.info("Ready to accept requests");
    }
}
