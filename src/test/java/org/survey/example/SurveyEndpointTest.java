package org.survey.example;

import org.junit.jupiter.api.Test;
import org.survey.example.model.SurveyRequest;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SurveyEndpointTest {
    private static final String[] GENDERS = {"male", "female", "other"};
    private static final String[] REGIONS = {"singapore", "malaysia"};
    private static final String[] SURVEY = {"123456", "456789","156265","895632","584666"};
    private static final Random RANDOM = new Random();
    private static final String ENDPOINT = "http://localhost:8080/surveys";

    @Test
    void sendRandomSurveyData() throws Exception {
        while (true) {
            String jsonPayload = generateRandomSurvey();
            int responseCode = sendPostRequest(jsonPayload);
            System.out.println(LocalDateTime.now() + " Sent: " + jsonPayload);
            System.out.println("Response Code: " + responseCode);
            assertTrue(responseCode >= 200 && responseCode < 300, "Survey post failed with code " + responseCode);

            Thread.sleep(10_000); // 1 minute
            }
        }

    private String generateRandomSurvey() {
        int age = 18 + RANDOM.nextInt(50);
        String gender = GENDERS[RANDOM.nextInt(GENDERS.length)];
        String region = REGIONS[RANDOM.nextInt(REGIONS.length)];
        String surveyID = SURVEY[RANDOM.nextInt(SURVEY.length)];
        int score = 1 + RANDOM.nextInt(5);

        SurveyRequest request = new SurveyRequest();
        request.setAge(age);
        request.setGender(gender);
        request.setRegion(region);
        request.setSurveyID(surveyID);
        request.setScore(score);

        return request.toString();
    }

    private int sendPostRequest(String jsonPayload) throws Exception {
        URL url = new URL(ENDPOINT);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(jsonPayload.getBytes());
            os.flush();
        }

        return conn.getResponseCode();
    }
}
