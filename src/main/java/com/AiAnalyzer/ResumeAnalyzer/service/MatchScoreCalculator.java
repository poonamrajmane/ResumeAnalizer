package com.AiAnalyzer.ResumeAnalyzer.service;

import org.springframework.stereotype.Service;

@Service
public class MatchScoreCalculator {


    public int calculate(
            int matched,
            int required){


        if(required == 0){
            return 0;
        }


        return (matched * 100) / required;
    }
}
