package com.AiAnalyzer.ResumeAnalyzer.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobSkillExtractor {


    private final List<String> knownSkills =
            List.of(
                    "java",
                    "spring boot",
                    "microservices",
                    "kafka",
                    "docker",
                    "kubernetes",
                    "aws",
                    "azure",
                    "sql"
            );


    public List<String> extract(String jd){


        String lower = jd.toLowerCase();


        return knownSkills.stream()
                .filter(lower::contains)
                .toList();

    }
}
