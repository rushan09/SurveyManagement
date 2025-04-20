package org.survey.example.controller;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.survey.example.model.SurveyRequest;
import org.survey.example.service.SurveyLoggingService;

@Path("/surveys")
public class SurveyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SurveyController.class);
    private final SurveyLoggingService loggingService;

    public SurveyController() {
        this.loggingService = new SurveyLoggingService();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response receiveSurvey(SurveyRequest request) {
        LOGGER.info(request.toString());

        try {
            loggingService.logSurvey(request);
            return Response.ok().build();
        } catch (Exception e) {
            LOGGER.error("Failed to log survey", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
