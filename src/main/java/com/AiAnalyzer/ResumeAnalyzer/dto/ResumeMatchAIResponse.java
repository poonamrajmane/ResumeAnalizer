package com.AiAnalyzer.ResumeAnalyzer.dto;

import java.util.List;

public class ResumeMatchAIResponse {


    private List<String> recommendations;
    private String explanation;

    public List<String> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<String> recommendations) {
        this.recommendations = recommendations;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
