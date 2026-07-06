package com.AiAnalyzer.ResumeAnalyzer.service;



import com.AiAnalyzer.ResumeAnalyzer.dto.ResumeAnalysisResponse;
import com.AiAnalyzer.ResumeAnalyzer.dto.ScoreBreakdown;
import com.AiAnalyzer.ResumeAnalyzer.helper.ResumeSections;

import org.springframework.stereotype.Service;
import org.springframework.ai.chat.client.ChatClient;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Service
public class ResumeAnalysisService {

    private final ChatClient chatClient;

    private final ResumeSectionExtractor sectionExtractor;

    private final SkillExtractor skillExtractor;

    private final ResumeScoreCalculator scoreCalculator;


   /* public ResumeAnalysisService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }*/

    private final ObjectMapper objectMapper;

    public ResumeAnalysisService(ChatClient.Builder builder, ResumeSectionExtractor sectionExtractor, SkillExtractor skillExtractor, ResumeScoreCalculator scoreCalculator, ObjectMapper objectMapper) {
        this.chatClient = builder.build();
        this.sectionExtractor = sectionExtractor;
        this.skillExtractor = skillExtractor;
        this.scoreCalculator = scoreCalculator;
        this.objectMapper = objectMapper;
    }


    public ResumeAnalysisResponse analyze1(String resumeText) {


        String prompt = buildPrompt1(resumeText);
/*
        String prompt = """
            You are an experienced technical recruiter.

            Analyze the following resume.

            Resume:
            %s

            Give:
            1. Overall summary
            2. Strengths
            3. Weaknesses
            4. Missing skills
            5. Interview preparation advice
            """.formatted(resumeText);
*/



        
      /*  String response = chatClient.prompt()
                .user(prompt)
                .call()
                .content();
        try {
            return objectMapper.readValue(response, ResumeAnalysisResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse AI response: " + response, e);
        }*/
        return chatClient.prompt()
                .user(prompt)
                .call()
                .entity(ResumeAnalysisResponse.class);


        // return new ResumeAnalysisResponse(response);
    }

    private String buildPrompt1(String resumeText) {


        String prompt = """
You are an expert technical recruiter and resume reviewer.

Analyze the resume below and return ONLY valid JSON.

STRICT RULES:
- Return ONLY JSON (no explanation, no markdown)
- Follow this structure exactly:
{
  "overallScore": number (0-100),
  "strengths": string[],
  "weaknesses": string[],
  "missingSkills": string[],
  "interviewQuestions": string[],
  "suggestions": string[]
}

Resume:
%s
""".formatted(resumeText);






return prompt;
    }





    public ResumeAnalysisResponse analyze(String resumeText) {

        // 1. Extract sections

        ResumeSections sections = sectionExtractor.extract(resumeText);

        // 2. Extract deterministic skills
        List<String> skills = skillExtractor.extractSkills(resumeText);



        // 3. AI evaluation only
        String prompt = buildPrompt(sections, skills);

        return chatClient.prompt()
                .user(prompt)
                .call()
                .entity(ResumeAnalysisResponse.class);
    }

    private String buildPrompt(ResumeSections sections, List<String> skills) {

        return """
                You are a senior technical recruiter.
                
                You will evaluate a candidate based on structured inputs.
                
                IMPORTANT RULES:
                - Do NOT extract skills
                - Only evaluate and reason
                - Be strict and realistic
                
                KNOWN SKILLS:
                %s
                
                EXPERIENCE:
                %s
                
                PROJECTS:
                %s
                
                EDUCATION:
                %s
                
                OUTPUT FORMAT (STRICT JSON):
                {
                  "overallScore": number,
                  "strengths": [],
                  "weaknesses": [],
                  "missingSkills": [],
                  "interviewQuestions": [],
                  "suggestions": []
                }
                """.formatted(
                skills,
                sections.getExperience(),
                sections.getProjects(),
                sections.getEducation()
        );
    }






    public ResumeAnalysisResponse analyzewithscorecalculator(String resumeText) {


        // 1. Extract sections
        ResumeSections sections =
                sectionExtractor.extract(resumeText);


        // 2. Extract known skills
        List<String> skills =
                skillExtractor.extractSkills(resumeText);


        // 3. Generate backend score
        ScoreBreakdown score =
                scoreCalculator.calculate(
                        sections,
                        skills
                );


        Integer overallScore =
                scoreCalculator.calculateOverallScore(score);



        // 4. Send evaluation request to AI

        String prompt = """
    You are a technical recruiter.

    Candidate score:
    %s

    Score breakdown:
    Skills: %s
    Experience: %s
    Projects: %s
    Education: %s


    Resume:

    %s


    Provide:
    - strengths
    - weaknesses
    - missing skills
    - interview questions
    - improvement suggestions

    Do not calculate score.
    """.formatted(
                overallScore,
                score.getSkills(),
                score.getExperience(),
                score.getProjects(),
                score.getEducation(),
                resumeText
        );


        ResumeAnalysisResponse response =
                chatClient.prompt()
                        .user(prompt)
                        .call()
                        .entity(ResumeAnalysisResponse.class);


        // 5. Attach calculated score

       response.setOverallScore(overallScore);
       response.setScoreBreakdown(score);


        return response;
    }



}

