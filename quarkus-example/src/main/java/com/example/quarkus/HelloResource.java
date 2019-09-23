package com.example.quarkus;

import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.Histogram;
import io.prometheus.client.Summary;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.Random;

@Path("/hello")
public class HelloResource {

    /* PROMETHEUS METRICS */
    static final Counter requestsCompleted = Counter.build()
            .name("hello_requests_completed").help("Total requests.").labelNames("status").register();
    static final Gauge inprogressRequests = Gauge.build()
            .name("hello_requests_active").help("Currently active requests.").register();
    static final Histogram requestLatencyHist = Histogram.build()
            .name("hello_requests_latency_histogram_seconds").help("Latency of completed hello requests").register();
    static final Summary requestLatencySum = Summary.build()
            .quantile(0.5, 0.05)   // Add 50th percentile (= median) with 5% tolerated error
            .quantile(0.9, 0.01)   // Add 90th percentile with 1% tolerated error
            .name("hello_requests_latency_summary_seconds").help("Request latency in seconds.").register();
    /* END PROMETHEUS METRICS */

    @GET
    @Produces("text/plain")
    public Response doGet() {

        inprogressRequests.inc();
        Histogram.Timer requestHistTimer = requestLatencyHist.startTimer();
        Summary.Timer requestSumTimer = requestLatencySum.startTimer();
        try {
            doWork();
            requestsCompleted.labels("200").inc();
        } catch (Exception e) {
            requestsCompleted.labels("500").inc();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } finally {
            requestHistTimer.observeDuration();
            requestSumTimer.observeDuration();
            inprogressRequests.dec();
        }
        return Response.ok("Hello from Quarkus App!\n").build();
    }

    final static double MAX_SLEEP_TIME_SECONDS = 10.0;
    final static double SLEEP_TIME_STANDARD_DEVIATION = 3.0;
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
        double time = (random.nextGaussian() * standardDeviation) + upperBound / 2;
        return Math.min(Math.max(time, 0), upperBound);
    }
}

