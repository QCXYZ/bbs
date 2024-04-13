package com.bbs.controller;

import com.bbs.entity.Content;
import com.bbs.service.ContentService;
import com.bbs.util.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/api/content")
public class ContentController {
    @Resource
    private ContentService contentService;

    @PostMapping
    public ResponseEntity<?> createContent(
            @RequestParam Long user_id,
            @RequestParam String content_type,
            @RequestParam String content,
            @RequestParam(required = false) Set<String> tags) {
        ContentType contentType;
        try {
            contentType = ContentType.valueOf(content_type.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("无效的内容类型");
        }

        Content createdContent = contentService.createContent(user_id, contentType, content, tags);

        Map<String, Object> response = new HashMap<>();
        response.put("content_id", createdContent.getId());
        response.put("message", "内容发布成功");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{content_id}")
    public ResponseEntity<?> updateContent(
            @PathVariable Long content_id,
            @RequestParam String content,
            @RequestParam(required = false) Set<String> tags) {
        contentService.updateContent(content_id, content, tags);

        Map<String, String> response = new HashMap<>();
        response.put("message", "内容编辑成功");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{content_id}")
    public ResponseEntity<?> deleteContent(@PathVariable Long content_id) {
        contentService.deleteContent(content_id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "内容删除成功");
        return ResponseEntity.ok(response);
    }

}
