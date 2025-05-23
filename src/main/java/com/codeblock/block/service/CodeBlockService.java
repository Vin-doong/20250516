package com.codeblock.block.service;

import com.codeblock.block.dto.CodeBlockRequestDto;
import com.codeblock.block.dto.CodeBlockResponseDto;
import com.codeblock.block.entity.CodeBlock;
import com.codeblock.block.repository.CodeBlockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CodeBlockService {

    private final CodeBlockRepository codeBlockRepository;

    // 코드블럭 생성
    public CodeBlockResponseDto createCodeBlock(CodeBlockRequestDto requestDto, String authorId) {
        CodeBlock codeBlock = CodeBlock.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .language(requestDto.getLanguage())
                .description(requestDto.getDescription())
                .authorId(authorId)
                .isPublic(requestDto.isPublic())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .likeCount(0)
                .build();
        
        CodeBlock savedCodeBlock = codeBlockRepository.save(codeBlock);
        return convertToResponseDto(savedCodeBlock);
    }

    // 코드블럭 조회 (ID로)
    public CodeBlockResponseDto getCodeBlockById(String id) {
        CodeBlock codeBlock = codeBlockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("코드블럭을 찾을 수 없습니다: " + id));
        return convertToResponseDto(codeBlock);
    }

    // 모든 공개 코드블럭 조회
    public List<CodeBlockResponseDto> getAllPublicCodeBlocks() {
        List<CodeBlock> codeBlocks = codeBlockRepository.findByIsPublicTrue();
        return codeBlocks.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    // 사용자의 모든 코드블럭 조회
    public List<CodeBlockResponseDto> getUserCodeBlocks(String authorId) {
        List<CodeBlock> codeBlocks = codeBlockRepository.findByAuthorId(authorId);
        return codeBlocks.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    // 코드블럭 수정
    public CodeBlockResponseDto updateCodeBlock(String id, CodeBlockRequestDto requestDto, String authorId) {
        CodeBlock codeBlock = codeBlockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("코드블럭을 찾을 수 없습니다: " + id));
        
        // 작성자 확인
        if (!codeBlock.getAuthorId().equals(authorId)) {
            throw new RuntimeException("코드블럭을 수정할 권한이 없습니다.");
        }
        
        codeBlock.setTitle(requestDto.getTitle());
        codeBlock.setContent(requestDto.getContent());
        codeBlock.setLanguage(requestDto.getLanguage());
        codeBlock.setDescription(requestDto.getDescription());
        codeBlock.setPublic(requestDto.isPublic());
        codeBlock.setUpdatedAt(LocalDateTime.now());
        
        CodeBlock updatedCodeBlock = codeBlockRepository.save(codeBlock);
        return convertToResponseDto(updatedCodeBlock);
    }

    // 코드블럭 삭제
    public void deleteCodeBlock(String id, String authorId) {
        CodeBlock codeBlock = codeBlockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("코드블럭을 찾을 수 없습니다: " + id));
        
        // 작성자 확인
        if (!codeBlock.getAuthorId().equals(authorId)) {
            throw new RuntimeException("코드블럭을 삭제할 권한이 없습니다.");
        }
        
        codeBlockRepository.delete(codeBlock);
    }

    // 언어별 코드블럭 조회
    public List<CodeBlockResponseDto> getCodeBlocksByLanguage(String language) {
        List<CodeBlock> codeBlocks = codeBlockRepository.findByLanguageAndIsPublicTrue(language);
        return codeBlocks.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    // 제목 검색
    public List<CodeBlockResponseDto> searchCodeBlocksByTitle(String keyword) {
        List<CodeBlock> codeBlocks = codeBlockRepository.findByTitleContainingAndIsPublicTrue(keyword);
        return codeBlocks.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    // 좋아요 증가
    public CodeBlockResponseDto likeCodeBlock(String id) {
        CodeBlock codeBlock = codeBlockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("코드블럭을 찾을 수 없습니다: " + id));
        
        codeBlock.setLikeCount(codeBlock.getLikeCount() + 1);
        CodeBlock updatedCodeBlock = codeBlockRepository.save(codeBlock);
        return convertToResponseDto(updatedCodeBlock);
    }

    // 엔티티를 DTO로 변환
    private CodeBlockResponseDto convertToResponseDto(CodeBlock codeBlock) {
        return CodeBlockResponseDto.builder()
                .id(codeBlock.getId())
                .title(codeBlock.getTitle())
                .content(codeBlock.getContent())
                .language(codeBlock.getLanguage())
                .authorId(codeBlock.getAuthorId())
                .description(codeBlock.getDescription())
                .createdAt(codeBlock.getCreatedAt())
                .updatedAt(codeBlock.getUpdatedAt())
                .isPublic(codeBlock.isPublic())
                .likeCount(codeBlock.getLikeCount())
                .build();
    }
}