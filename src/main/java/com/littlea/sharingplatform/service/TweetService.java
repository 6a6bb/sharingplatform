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
     * @param tweet
     * @param cover
     * @param files
     * @param strings
     * @return
     * @throws IOException
     */
    Result addTweet(Tweet tweet, MultipartFile cover, MultipartFile[] files, String[] strings) throws IOException;

    /**
     * 删除推文
     * @param tweetId
     * @return
     */
    Result deleteTweet(Integer tweetId);

    /**
     * 查看推文总列表
     * @param pageLimit
     * @param type
     * @return
     */
    Result getAllTweetList(PageLimit pageLimit, Integer type);

    /**
     * 查看当前用户发布的推文列表
     * @param pageLimit
     * @param type
     * @param author
     * @return
     */
    Result getUserTweetList(PageLimit pageLimit,Integer type,String author);

    /**
     * 获得推文详情
     * @param tweetId
     * @return
     */
    Result getTweetDetail(Integer tweetId);

    /**
     * 修改推文
     * @param tweet
     * @param cover
     * @param files
     * @param strings
     * @return
     * @throws IOException
     */
    Result updateTweet(Tweet tweet, MultipartFile cover, MultipartFile[] files, String[] strings) throws IOException;

    /**
     * 增加推文阅读量
     * @param tweetId
     * @return
     */
    Result increaseTweetReading(Integer tweetId);
}
