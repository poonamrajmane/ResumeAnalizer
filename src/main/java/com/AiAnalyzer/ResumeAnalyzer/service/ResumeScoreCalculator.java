package com.AiAnalyzer.ResumeAnalyzer.service;

import com.AiAnalyzer.ResumeAnalyzer.dto.ScoreBreakdown;
import com.AiAnalyzer.ResumeAnalyzer.helper.ResumeSections;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResumeScoreCalculator {


    public ScoreBreakdown calculate(
            ResumeSections sections,
            List<String> skills) {


        ScoreBreakdown score = new ScoreBreakdown();


        // Skills score
        score.setSkills(
                Math.min(skills.size() * 10,100)
        );


        // Experience score
        score.setExperience(
                calculateExperience(
                        sections.getExperience()
                )
        );


        // Project score
        score.setProjects(
                calculateProjects(
                        sections.getProjects()
                )
        );


        // Education score
        score.setEducation(80);


        return score;
    }


    private int calculateExperience(String experience){

        if(experience == null || experience.isEmpty()){
            return 40;
        }

        return 85;
    }


    private int calculateProjects(String projects){

        if(projects == null || projects.isEmpty()){
            return 40;
        }

        return 80;
    }



    public Integer calculateOverallScore(
            ScoreBreakdown score){
Integer overallScore= (int)(score.getSkills()*0.4 + score.getExperience()*0.3 + score.getProjects()*0.2 + score.getEducation()*0.1);
        return overallScore;

    }
}