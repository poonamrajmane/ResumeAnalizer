package com.AiAnalyzer.ResumeAnalyzer.service;



import com.AiAnalyzer.ResumeAnalyzer.dto.ResumeAnalysisResponse;
import org.springframework.stereotype.Service;
import org.springframework.ai.chat.client.ChatClient;
import tools.jackson.databind.ObjectMapper;

@Service
public class ResumeAnalysisService {

    private final ChatClient chatClient;

   /* public ResumeAnalysisService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }*/

    private final ObjectMapper objectMapper;

    public ResumeAnalysisService(ChatClient.Builder builder, ObjectMapper objectMapper) {
        this.chatClient = builder.build();
        this.objectMapper = objectMapper;
    }




    public ResumeAnalysisResponse analyze(String resumeText) {


        String prompt = buildPrompt(resumeText);
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
               return  chatClient.prompt()
                       .user(prompt)
                       .call()
                       .entity(ResumeAnalysisResponse.class);


       // return new ResumeAnalysisResponse(response);
    }

    private String buildPrompt(String resumeText) {


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
}


