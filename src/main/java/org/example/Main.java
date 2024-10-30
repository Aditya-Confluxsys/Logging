package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private final static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        log.trace("Trace Trace");
        log.info("Log is here");
        log.warn("warn warn");
        log.info("again below warn");

        try {
            int r = 10/ 10 ;
        } catch (Exception e) {
            log.error("Error is here",e.getMessage(),e);
        }
    }
}
