package com.AiAnalyzer.ResumeAnalyzer.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillMatchService {


    public List<String> findMissingSkills(
            List<String> resumeSkills,
            List<String> requiredSkills) {


        return requiredSkills.stream()
                .filter(skill ->
                        !resumeSkills.contains(skill))
                .toList();
    }


    public List<String> findMatchedSkills(
            List<String> resumeSkills,
            List<String> requiredSkills) {


        return requiredSkills.stream()
                .filter(resumeSkills::contains)
                .toList();
    }
}
