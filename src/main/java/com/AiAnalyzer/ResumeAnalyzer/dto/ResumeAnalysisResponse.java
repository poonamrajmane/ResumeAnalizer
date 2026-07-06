package com.AiAnalyzer.ResumeAnalyzer.dto;

import java.util.List;

public class ResumeAnalysisResponse {

        /*private String analysis;

        public ResumeAnalysisResponse() {
        }

        public ResumeAnalysisResponse(String analysis) {
            this.analysis = analysis;
        }

        public String getAnalysis() {
            return analysis;
        }

        public void setAnalysis(String analysis) {
            this.analysis = analysis;
        }*/



    private Integer overallScore;
    private List<String> strengths;
    private List<String> weaknesses;
    private List<String> missingSkills;
    private List<String> interviewQuestions;
    private List<String> suggestions;

    private ScoreBreakdown scoreBreakdown;

    private String explanation;

    public ResumeAnalysisResponse(Integer overallScore, List<String> strengths, List<String> weaknesses, List<String> missingSkills, List<String> interviewQuestions, List<String> suggestions, ScoreBreakdown scoreBreakdown, String explanation) {
        this.overallScore = overallScore;
        this.strengths = strengths;
        this.weaknesses = weaknesses;
        this.missingSkills = missingSkills;
        this.interviewQuestions = interviewQuestions;
        this.suggestions = suggestions;
        this.scoreBreakdown = scoreBreakdown;
        this.explanation = explanation;
    }

    public ScoreBreakdown getScoreBreakdown() {
        return scoreBreakdown;
    }

    public void setScoreBreakdown(ScoreBreakdown scoreBreakdown) {
        this.scoreBreakdown = scoreBreakdown;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public Integer getOverallScore() {
        return overallScore;
    }

    public void setOverallScore(Integer overallScore) {
        this.overallScore = overallScore;
    }

    public List<String> getStrengths() {
        return strengths;
    }

    public void setStrengths(List<String> strengths) {
        this.strengths = strengths;
    }

    public List<String> getWeaknesses() {
        return weaknesses;
    }

    public void setWeaknesses(List<String> weaknesses) {
        this.weaknesses = weaknesses;
    }

    public List<String> getMissingSkills() {
        return missingSkills;
    }

    public void setMissingSkills(List<String> missingSkills) {
        this.missingSkills = missingSkills;
    }

    public List<String> getInterviewQuestions() {
        return interviewQuestions;
    }

    public void setInterviewQuestions(List<String> interviewQuestions) {
        this.interviewQuestions = interviewQuestions;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }
}

