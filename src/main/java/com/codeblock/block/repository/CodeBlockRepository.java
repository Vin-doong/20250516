package com.codeblock.block.repository;

import com.codeblock.block.entity.CodeBlock;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodeBlockRepository extends MongoRepository<CodeBlock, String> {
    // 작성자 ID로 코드블럭 찾기
    List<CodeBlock> findByAuthorId(String authorId);
    
    // 공개된 코드블럭만 찾기
    List<CodeBlock> findByIsPublicTrue();
    
    // 작성자 ID와 공개 여부로 코드블럭 찾기
    List<CodeBlock> findByAuthorIdAndIsPublic(String authorId, boolean isPublic);
    
    // 제목에 특정 키워드가 포함된 코드블럭 찾기
    List<CodeBlock> findByTitleContainingAndIsPublicTrue(String keyword);
    
    // 언어별로 코드블럭 찾기
    List<CodeBlock> findByLanguageAndIsPublicTrue(String language);
}