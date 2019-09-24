package com.example.rest;

import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.Random;

@Path("/hello")
public class ExampleWebService {

    static final Counter requestsCompleted = Counter.build()
            .name("hello_requests_completed").help("Total requests.").labelNames("status").register();
    static final Gauge inprogressRequests = Gauge.build()
            .name("hello_requests_active").help("Currently active requests.").register();

    @GET
    @Produces("text/plain")
    public Response doGet() {

        inprogressRequests.inc();
        try {
            doWork();
            requestsCompleted.labels("200").inc();
        } catch (Exception e) {
            requestsCompleted.labels("500").inc();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } finally {
            inprogressRequests.dec();
        }
        return Response.ok("Hello from Thorntail App!\n").build();
    }

    final static double MAX_SLEEP_TIME_SECONDS = 10;
    final static double SLEEP_TIME_STANDARD_DEVIATION = 2.0;
    final static double ERR_RATE = 1.0 / 20.0;

    private Random random = new Random();

    /**
     * All this work makes me tired, sleep for a random time and occasionally throw an exception.
     *
     * @throws Exception
     */
    private void doWork() throws Exception {
        double sleepTimeSeconds = generateRandomTime(MAX_SLEEP_TIME_SECONDS, SLEEP_TIME_STANDARD_DEVIATION);
        Thread.sleep((long) (sleepTimeSeconds * 1000));
        if (random.nextDouble() < ERR_RATE) {
            throw new Exception("application exception");
        }
    }

    /**
     * Generates a random time based on the desired max and standard deviation.
     * Limits the value to between 0 and upperBound
     *
     * @return random double
     */
    private double generateRandomTime(double upperBound, double standardDeviation) {
        double time = (random.nextGaussian() * standardDeviation) + (upperBound / 2);
        return Math.min(Math.max(time, 0), upperBound);
    }
}

