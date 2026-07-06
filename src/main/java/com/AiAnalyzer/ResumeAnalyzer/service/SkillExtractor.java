package com.AiAnalyzer.ResumeAnalyzer.service;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SkillExtractor {

    public List<String> extractSkills(String text) {


        List<String> skills = new ArrayList<>();

        List<String> knownSkills = List.of("java", "spring", "spring boot", "microservices", "docker", "kubernetes", "aws", "sql", "redis", "rest");

        String lower = text.toLowerCase();

        for (String skill : knownSkills) {
            if (lower.contains(skill)) {
                skills.add(skill);

            }
        }

        return skills;
    }
}
