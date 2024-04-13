package com.bbs.service;

import com.bbs.entity.Content;
import com.bbs.entity.User;
import com.bbs.repository.ContentRepository;
import com.bbs.util.ContentType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

@Service
public class ContentService {
    @Resource
    private ContentRepository contentRepository;
    @Resource
    private UserService userService; // 添加对UserService的依赖

    public Content createContent(Long userId, ContentType contentType, String content, Set<String> tags) {
        User user = userService.getUser(userId); // 通过UserService获取用户实例

        Content newContent = new Content();
        newContent.setUser(user);
        newContent.setContentType(contentType);
        newContent.setContent(content);
        newContent.setTags(tags);
        return contentRepository.save(newContent);
    }

    public void updateContent(Long contentId, String newContent, Set<String> newTags) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new RuntimeException("内容不存在!"));
        content.setContent(newContent);
        content.setTags(newTags);
        contentRepository.save(content);
    }

    public void deleteContent(Long contentId) {
        contentRepository.deleteById(contentId);
    }

}
