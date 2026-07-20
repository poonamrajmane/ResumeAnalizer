package com.AiAnalyzer.ResumeAnalyzer.service;


import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResumeVectorService {
    private final VectorStore vectorStore;
    private final TokenTextSplitter splitter;

    public ResumeVectorService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
        this.splitter = new TokenTextSplitter();
    }

    public void storeResume(String resumeText) {
        System.out.println("===== ResumeVectorService Called =====");

        List<Document> documents =
                splitter.apply(List.of(new Document(resumeText)));
        System.out.println("Documents created : " + documents.size());

        vectorStore.add(documents);
        System.out.println("Documents stored successfully");

    }
}
