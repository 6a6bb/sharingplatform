package com.littlea.sharingplatform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName(value = "tweet")
public class Tweet {
    /**
     * 推文id
     */
    @TableId(value = "tweet_id",type = IdType.AUTO)
    private Integer tweetId;
    /**
     * 标题
     */
    @TableField(value = "title")
    private String title;
    /**
     * 封面地址
     */
    @TableField(value = "cover_address")
    private String coverAddress;
    /**
     * 摘要
     */
    @TableField(value = "summary")
    private String summary;
    /**
     * 类别
     */
    @TableField(value = "type")
    private Integer type;
    /**
     * 主体内容
     */
    @TableField(value = "content")
    private String content;
    /**
     * 阅读量
     */
    @TableField(value = "reading_volume")
    private Integer readingVolume;
    /**
     * 发布日期
     */
    @TableField(value = "release_date")
    private String releaseDate;
    /**
     * 作者
     */
    @TableField(value = "author")
    private String author;
}
