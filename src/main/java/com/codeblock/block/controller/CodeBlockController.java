package com.codeblock.block.controller;

import com.codeblock.block.dto.CodeBlockRequestDto;
import com.codeblock.block.dto.CodeBlockResponseDto;
import com.codeblock.block.service.CodeBlockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/codeblocks")
@RequiredArgsConstructor
public class CodeBlockController {

    private final CodeBlockService codeBlockService;

    // 코드블럭 생성
    @PostMapping
    public ResponseEntity<CodeBlockResponseDto> createCodeBlock(
            @RequestBody CodeBlockRequestDto requestDto,
            @RequestHeader("X-AUTH-ID") String authorId) {
        CodeBlockResponseDto responseDto = codeBlockService.createCodeBlock(requestDto, authorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // ID로 코드블럭 조회
    @GetMapping("/{id}")
    public ResponseEntity<CodeBlockResponseDto> getCodeBlock(@PathVariable String id) {
        CodeBlockResponseDto responseDto = codeBlockService.getCodeBlockById(id);
        return ResponseEntity.ok(responseDto);
    }

    // 모든 공개 코드블럭 조회
    @GetMapping("/public")
    public ResponseEntity<List<CodeBlockResponseDto>> getAllPublicCodeBlocks() {
        List<CodeBlockResponseDto> responseDtos = codeBlockService.getAllPublicCodeBlocks();
        return ResponseEntity.ok(responseDtos);
    }

    // 사용자의 코드블럭 조회
    @GetMapping("/user")
    public ResponseEntity<List<CodeBlockResponseDto>> getUserCodeBlocks(
            @RequestHeader("X-AUTH-ID") String authorId) {
        List<CodeBlockResponseDto> responseDtos = codeBlockService.getUserCodeBlocks(authorId);
        return ResponseEntity.ok(responseDtos);
    }

    // 코드블럭 수정
    @PutMapping("/{id}")
    public ResponseEntity<CodeBlockResponseDto> updateCodeBlock(
            @PathVariable String id,
            @RequestBody CodeBlockRequestDto requestDto,
            @RequestHeader("X-AUTH-ID") String authorId) {
        CodeBlockResponseDto responseDto = codeBlockService.updateCodeBlock(id, requestDto, authorId);
        return ResponseEntity.ok(responseDto);
    }

    // 코드블럭 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCodeBlock(
            @PathVariable String id,
            @RequestHeader("X-AUTH-ID") String authorId) {
        codeBlockService.deleteCodeBlock(id, authorId);
        return ResponseEntity.noContent().build();
    }

    // 언어별 코드블럭 조회
    @GetMapping("/language/{language}")
    public ResponseEntity<List<CodeBlockResponseDto>> getCodeBlocksByLanguage(
            @PathVariable String language) {
        List<CodeBlockResponseDto> responseDtos = codeBlockService.getCodeBlocksByLanguage(language);
        return ResponseEntity.ok(responseDtos);
    }

    // 제목으로 코드블럭 검색
    @GetMapping("/search")
    public ResponseEntity<List<CodeBlockResponseDto>> searchCodeBlocks(
            @RequestParam String keyword) {
        List<CodeBlockResponseDto> responseDtos = codeBlockService.searchCodeBlocksByTitle(keyword);
        return ResponseEntity.ok(responseDtos);
    }

    // 코드블럭 좋아요
    @PostMapping("/{id}/like")
    public ResponseEntity<CodeBlockResponseDto> likeCodeBlock(@PathVariable String id) {
        CodeBlockResponseDto responseDto = codeBlockService.likeCodeBlock(id);
        return ResponseEntity.ok(responseDto);
    }
}