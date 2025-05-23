package com.codeblock.block.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodeBlockRequestDto {
    private String title;
    private String content;
    private String language;
    private String description;
    private boolean isPublic;
}