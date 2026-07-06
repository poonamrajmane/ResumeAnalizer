package com.AiAnalyzer.ResumeAnalyzer.service;

import com.AiAnalyzer.ResumeAnalyzer.helper.ResumeSections;
import org.springframework.stereotype.Component;

@Component
public class ResumeSectionExtractor {

    public ResumeSections extract(String text){

        ResumeSections sections=new ResumeSections();

        sections.setSkills(extractSection(text,"Skills","technical skiils"));

        sections.setExperience(extractSection(text,"experience","work experience"));
        sections.setProjects(extractSection(text,"projects"));
        sections.setEducation(extractSection(text,"education"));

    return sections;
    }

    private String extractSection(String text, String... keywords){
        String lower=text.toLowerCase();

        for (String keyword : keywords){
            int index= lower.indexOf(keyword.toLowerCase());

            if(index!=-1){
                return text.substring(index);
            }
        }
        return "";
    }
}
