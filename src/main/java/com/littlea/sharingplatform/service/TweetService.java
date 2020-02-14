package com.littlea.sharingplatform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.littlea.sharingplatform.entity.Tweet;
import com.littlea.sharingplatform.vo.PageLimit;
import com.littlea.sharingplatform.vo.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author ：LinXinRan
 * @date ：2020/2/8 23:37
 */
public interface TweetService extends IService<Tweet> {

    /**
     * 发布推文
     * @param tweet 推文信息
     * @param cover 封面图片
     * @return 发布结果
     * @throws IOException IO错误
     */
    Result addTweet(Tweet tweet, MultipartFile cover) throws IOException;

    /**
     * 删除推文
     * @param tweetId 推文id
     * @return 删除结果
     */
    Result deleteTweet(Integer tweetId);

    /**
     * 查看推文总列表
     * @param pageLimit 分页信息
     * @param type 推文类别
     * @return 推文总列表
     */
    Result getAllTweetList(PageLimit pageLimit, Integer type);

    /**
     * 查看当前用户发布的推文列表
     * @param pageLimit 分页信息
     * @param type 推文类别
     * @param author 当前用户
     * @return 当前用户发布的推文列表
     */
    Result getUserTweetList(PageLimit pageLimit,Integer type,String author);

    /**
     * 获得推文详情
     * @param tweetId 推文id
     * @return 推文详情
     */
    Result getTweetDetail(Integer tweetId);

    /**
     * 修改推文
     * @param tweet 推文信息
     * @param cover 封面图片
     * @return 修改结果
     * @throws IOException IO错误
     */
    Result updateTweet(Tweet tweet, MultipartFile cover)throws IOException;

    /**
     * 增加推文阅读量
     * @param tweetId 推文id
     * @return 增加结果
     */
    Result increaseTweetReading(Integer tweetId);
}
