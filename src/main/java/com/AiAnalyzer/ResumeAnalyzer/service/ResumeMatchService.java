package com.AiAnalyzer.ResumeAnalyzer.service;

import com.AiAnalyzer.ResumeAnalyzer.dto.ResumeMatchAIResponse;
import com.AiAnalyzer.ResumeAnalyzer.dto.ResumeMatchResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResumeMatchService {


    private final ChatClient chatClient;

    private final JobSkillExtractor jobSkillExtractor;

    private final SkillMatchService skillMatchService;

    private final MatchScoreCalculator matchScoreCalculator;

    public ResumeMatchService(ChatClient.Builder builder, JobSkillExtractor jobSkillExtractor, SkillMatchService skillMatchService, MatchScoreCalculator matchScoreCalculator) {
        this.chatClient = builder.build();
        this.jobSkillExtractor = jobSkillExtractor;
        this.skillMatchService = skillMatchService;
        this.matchScoreCalculator = matchScoreCalculator;
    }


    public ResumeMatchResponse match(
            List<String> resumeSkills,
            String jobDescription){


        List<String> requiredSkills =
                jobSkillExtractor.extract(jobDescription);


        List<String> matched =
                skillMatchService.findMatchedSkills(
                        resumeSkills,
                        requiredSkills);


        List<String> missing =
                skillMatchService.findMissingSkills(
                        resumeSkills,
                        requiredSkills);


        int score =
                matchScoreCalculator.calculate(
                        matched.size(),
                        requiredSkills.size());


        // AI explanation
        ResumeMatchAIResponse ai =
                chatClient.prompt()
                        .user(buildPrompt(
                                score,
                                matched,
                                missing))
                        .call()
                        .entity(ResumeMatchAIResponse.class);


        return buildFinalResponse(
                score,
                matched,
                missing,
                ai);
    }


    private String buildPrompt(
            int score,
            List<String> matchedSkills,
            List<String> missingSkills) {


        return """
            You are a senior technical recruiter.

            Analyze the candidate's job match.

            IMPORTANT:
            - Do not modify the match score.
            - Do not calculate a new score.
            - Only explain the result.

            Current Match Score:
            %d%%

            Matched Skills:
            %s

            Missing Skills:
            %s


            Provide:

            1. Candidate strengths
            2. Risk areas
            3. Learning recommendations
            4. Final hiring recommendation


            Return ONLY JSON:

            {
              "recommendations": [],
              "explanation": ""
            }

            """
                .formatted(
                        score,
                        matchedSkills,
                        missingSkills
                );
    }


    private ResumeMatchResponse buildFinalResponse(
            int score,
            List<String> matchedSkills,
            List<String> missingSkills,
            ResumeMatchAIResponse aiResponse) {


        ResumeMatchResponse response =
                new ResumeMatchResponse();


        // Backend owns score
        response.setMatchScore(score);


        // Backend owns skill matching
        response.setMatchedSkills(
                matchedSkills
        );


        response.setMissingSkills(
                missingSkills
        );


        // AI owns explanation
        response.setRecommendations(
                aiResponse.getRecommendations()
        );


        response.setExplanation(
                aiResponse.getExplanation()
        );


        return response;
    }
}
