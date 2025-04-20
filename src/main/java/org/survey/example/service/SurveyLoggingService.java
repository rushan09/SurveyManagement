package org.survey.example.service;

import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.loggingingestion.LoggingClient;
import com.oracle.bmc.loggingingestion.model.LogEntry;
import com.oracle.bmc.loggingingestion.model.LogEntryBatch;
import com.oracle.bmc.loggingingestion.model.PutLogsDetails;
import com.oracle.bmc.loggingingestion.requests.PutLogsRequest;
import com.oracle.bmc.loggingingestion.responses.PutLogsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.survey.example.model.SurveyRequest;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

public class SurveyLoggingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SurveyLoggingService.class);

    private final LoggingClient loggingClient;

    public SurveyLoggingService() {
        try {
            ConfigFileAuthenticationDetailsProvider provider =
                    new ConfigFileAuthenticationDetailsProvider("DEFAULT");
            String endpoint = "https://ingestion.logging.ap-singapore-1.oci.oraclecloud.com";

            this.loggingClient = new LoggingClient(provider);
            loggingClient.setEndpoint(endpoint);
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize LoggingClient", e);
        }
    }

    public void logSurvey(SurveyRequest request) {

        String logId = "ocid1.log.oc1.ap-singapore-1.amaaaaaanhug7iyay5oy4x2kvbygtj3ojndwzqd5nc35k6oszedh65wnmj5q";
        LogEntry logEntry = LogEntry.builder()
                .data(request.toString())
                .id(UUID.randomUUID().toString())
                .build();

        PutLogsDetails putLogsDetails = PutLogsDetails.builder()
                .specversion("1.0")
                .logEntryBatches(Collections.singletonList(
                        LogEntryBatch.builder()
                                .entries(Collections.singletonList(logEntry))
                                .source("surveyapp")
                                .type("surveytype")
                                .defaultlogentrytime(new Date())
                                .build()
                ))
                .build();

        PutLogsRequest putLogsRequest = PutLogsRequest.builder()
                .logId(logId)
                .putLogsDetails(putLogsDetails)
                .build();

        PutLogsResponse response = loggingClient.putLogs(putLogsRequest);
        LOGGER.info("Log pushed. Status: {}", response.get__httpStatusCode__());
    }
}
