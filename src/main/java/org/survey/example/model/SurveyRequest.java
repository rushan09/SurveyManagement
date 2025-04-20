package org.survey.example.model;

import jakarta.validation.constraints.*;

import java.util.Objects;

public class SurveyRequest {

    @NotNull
    private Integer age;

    @NotNull
    @Pattern(regexp = "male|female|other")
    private String gender;

    @NotNull
    private String region;

    @NotNull
    private String surveyID;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer score;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSurveyID() {
        return surveyID;
    }

    public void setSurveyID(String surveyID) {
        this.surveyID = surveyID;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "{ \"age\": "+ this.age +", \n \"gender\": \""+ this.gender + "\", \n \"region\": \"" + this.region + "\", \n \"surveyID\": \"" + this.surveyID + "\", \n \"score\": " + this.score + "}";
    }

}
