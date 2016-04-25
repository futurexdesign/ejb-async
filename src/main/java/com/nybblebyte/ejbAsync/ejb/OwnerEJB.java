package com.nybblebyte.ejbAsync.ejb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import java.util.concurrent.Future;


@Singleton
public class OwnerEJB {

    // stateful ejb, own the future for long running...

    Logger logger;

    private Future<Boolean> futureState;

    @EJB
    LongRunningEjb lre;


    @PostConstruct
    public void init() {
        logger = LoggerFactory.getLogger(OwnerEJB.class);
    }

    public void doTask() {
        logger.info("Owner - > Triggering long running task");
        futureState = lre.doLongRunningTask();
    }

    /**
     *
     * @return false if long running task is not running.  True if it is
     */
    public boolean getStatus() {

        if (futureState == null) {
            return false;
        }

        if (futureState.isDone() || futureState.isCancelled()) {
            // discard our future
            futureState = null;

            return false;
        }

        return true;
    }

}
