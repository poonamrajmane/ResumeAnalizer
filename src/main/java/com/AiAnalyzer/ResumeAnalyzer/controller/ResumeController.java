package com.AiAnalyzer.ResumeAnalyzer.controller;

import com.AiAnalyzer.ResumeAnalyzer.dto.ResumeAnalysisResponse;
import com.AiAnalyzer.ResumeAnalyzer.service.ResumeAnalysisService;
import com.AiAnalyzer.ResumeAnalyzer.service.ResumeService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {

        private final ResumeService resumeService;
        private final ResumeAnalysisService resumeAnalysisService;

    public ResumeController(ResumeService resumeService, ResumeAnalysisService resumeAnalysisService) {
            this.resumeService = resumeService;
        this.resumeAnalysisService = resumeAnalysisService;
    }

        @PostMapping("/upload")
        public ResponseEntity<String> uploadResume(@RequestParam("file") MultipartFile file) {
            String extractedText = resumeService.extractText(file);
            return ResponseEntity.ok(extractedText);
        }


    @PostMapping("/analyze")
    public ResumeAnalysisResponse analyze(@RequestParam MultipartFile file) {

        String resumeText = resumeService.extractText(file);

        //return resumeAnalysisService.analyze1(resumeText);
        return resumeAnalysisService.analyze(resumeText);

    }
    }





