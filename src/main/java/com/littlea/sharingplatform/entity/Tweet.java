package com.littlea.sharingplatform.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ：LinXinRan
 * @date ：2020/2/8 23:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tweet {
    /**
     * 推文id
     */
    private Integer tweetId;
    /**
     * 标题
     */
    private String title;
    /**
     * 封面地址
     */
    private String coverAddress;
    /**
     * 摘要
     */
    private String summary;
    /**
     * 类别
     */
    private Integer type;
    /**
     * 主体内容
     */
    private String content;
    /**
     * 阅读量
     */
    private Integer readingVolume;
    /**
     * 发布日期
     */
    private String releaseDate;
    /**
     * 作者
     */
    private String author;

    public Tweet(Integer tweetId, String title, String summary, Integer type, String author) {
        this.tweetId = tweetId;
        this.title = title;
        this.summary = summary;
        this.type = type;
        this.author = author;
    }

    public Tweet(String title, String summary, Integer type, String author) {
        this.title = title;
        this.summary = summary;
        this.type = type;
        this.author = author;
    }

    public Tweet(String coverAddress, String content, String releaseDate) {
        this.coverAddress = coverAddress;
        this.content = content;
        this.releaseDate = releaseDate;
    }
}
