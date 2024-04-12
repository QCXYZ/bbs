package com.bbs.entity;

import com.bbs.util.ContentType;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "content")
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ContentType contentType;  // 枚举类型包括 TEXT, IMAGE, VIDEO
    @Column(nullable = false, length = 1000)
    private String content;
    @ElementCollection
    @CollectionTable(name = "content_tag", joinColumns = @JoinColumn(name = "content_id"))
    @Column(name = "tag")
    private Set<String> tags = new HashSet<>();
}
