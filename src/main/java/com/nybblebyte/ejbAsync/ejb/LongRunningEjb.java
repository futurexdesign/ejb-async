package com.nybblebyte.ejbAsync.ejb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Singleton;

import java.util.concurrent.Future;


@Singleton
public class LongRunningEjb {

    Logger logger;

    @PostConstruct
    public void init() {
        logger = LoggerFactory.getLogger(LongRunningEjb.class);
    }

    @Asynchronous
    public Future<Boolean> doLongRunningTask() {

        try {
            logger.info("Starting sleep..");
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new AsyncResult<Boolean>(true);
    }
}
