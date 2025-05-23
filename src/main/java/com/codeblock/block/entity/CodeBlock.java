package com.codeblock.block.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "code_blocks")
public class CodeBlock {
    @Id
    private String id;
    
    private String title;
    private String content;
    private String language; // 프로그래밍 언어 (java, python 등)
    private String authorId; // 작성자 ID (Member 테이블 참조)
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isPublic; // 공개/비공개 여부
    private int likeCount; // 좋아요 수
}