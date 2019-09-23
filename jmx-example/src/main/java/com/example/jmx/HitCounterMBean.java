package com.example.jmx;

import java.time.Instant;

public interface HitCounterMBean {

    public int getHits();

    public void reset();

}
