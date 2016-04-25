package com.nybblebyte.ejbAsync.ejb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import java.security.acl.Owner;


@Singleton
@Startup
public class TimerBean {

    Logger logger;

    @EJB
    OwnerEJB ejb;

    @Resource
    TimerService timerService;

    @PostConstruct
    public void initialize(){

        logger = LoggerFactory.getLogger(TimerBean.class);
        logger.info("Timer startup: Setting schedule");
        ScheduleExpression expression = new ScheduleExpression();
        expression.second("*/5").minute("*").hour("*");
        timerService.createCalendarTimer(expression);
    }

    @Timeout
    public void execute(){
       logger.info(String.format("Timer tripped: Is Long running task running? %s", Boolean.toString(ejb.getStatus())));

        if (!ejb.getStatus()) {
            logger.info("Task isn't running, starting");
            ejb.doTask();
        }
    }
}
