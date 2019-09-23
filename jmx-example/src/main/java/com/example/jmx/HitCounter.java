package com.example.jmx;

import java.time.Instant;

public class HitCounter implements HitCounterMBean {

    private int hits = 0;

    public int getHits() {
        return hits;
    }

    public void increment() {
        hits++;
    }

    public void reset() {
        hits = 0;
    }

}
