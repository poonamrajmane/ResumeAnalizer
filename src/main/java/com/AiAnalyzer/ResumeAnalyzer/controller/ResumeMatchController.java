package com.AiAnalyzer.ResumeAnalyzer.controller;

import com.AiAnalyzer.ResumeAnalyzer.dto.ResumeMatchResponse;
import com.AiAnalyzer.ResumeAnalyzer.service.ResumeMatchService;
import com.AiAnalyzer.ResumeAnalyzer.service.ResumeService;
import com.AiAnalyzer.ResumeAnalyzer.service.ResumeVectorService;
import com.AiAnalyzer.ResumeAnalyzer.service.SkillExtractor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/resume")
public class ResumeMatchController {

    private final ResumeService resumeService;
    private final SkillExtractor skillExtractor;
    private final ResumeMatchService resumeMatchService;
    private final ResumeVectorService resumeVectorService;



    public ResumeMatchController(
            ResumeService resumeService,
            SkillExtractor skillExtractor,
            ResumeMatchService resumeMatchService, ResumeVectorService resumeVectorService) {

        this.resumeService = resumeService;
        this.skillExtractor = skillExtractor;
        this.resumeMatchService = resumeMatchService;
        this.resumeVectorService = resumeVectorService;
    }


    @PostMapping("/match")
    public ResponseEntity<ResumeMatchResponse> matchResume(
            @RequestParam("file") MultipartFile file,
            @RequestParam("jobDescription") String jobDescription) {


        // 1. Extract resume text from PDF
        String resumeText =
                resumeService.extractText(file);
        resumeVectorService.storeResume(resumeText);


        // 2. Extract skills from resume
        List<String> resumeSkills =
                skillExtractor.extractSkills(resumeText);


        // 3. Match against job description
        ResumeMatchResponse response =
                resumeMatchService.match(
                        resumeSkills,
                        jobDescription
                );


        return ResponseEntity.ok(response);
    }
}
