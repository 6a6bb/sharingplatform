package com.littlea.sharingplatform.controller;

import com.littlea.sharingplatform.constant.ResultConstant;
import com.littlea.sharingplatform.entity.Tweet;
import com.littlea.sharingplatform.service.TweetService;
import com.littlea.sharingplatform.vo.PageLimit;
import com.littlea.sharingplatform.vo.Result;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author ：LinXinRan
 * @date ：2020/2/8 23:37
 */
@RestController
@AllArgsConstructor
public class TweetController {

    private TweetService tweetService;

    @PostMapping("api/v1/tweet/add")
    public Result addTweet(String title, String summary, MultipartFile cover , Integer type ,MultipartFile[] files, String[] strings){
        String author = "a";
        Tweet tweet = new Tweet(title,summary,type,author);
        try {
            return tweetService.addTweet(tweet,cover,files,strings);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultConstant.SYSTEM_ERROR;
        }
    }

    @GetMapping("api/v1/tweet/delete")
    public Result deleteTweet(Integer tweetId){
        return tweetService.deleteTweet(tweetId);
    }

    @GetMapping("api/v1/tweet/getAllList")
    public Result getAllTweetList(PageLimit pageLimit,Integer type){
        return tweetService.getAllTweetList(pageLimit,type);
    }

    @GetMapping("api/v1/tweet/getUserList")
    public Result getUserTweetList(PageLimit pageLimit,Integer type){
        String author = "a";
        return tweetService.getUserTweetList(pageLimit,type,author);
    }

    @GetMapping("api/v1/tweet/getDetail")
    public Result getTweetDetail(Integer tweetId){
        return tweetService.getTweetDetail(tweetId);
    }

    @PostMapping("api/v1/tweet/update")
    public Result updateTweet(Integer tweetId, String title, String summary, MultipartFile cover, Integer type ,MultipartFile[] files, String[] strings){
        String author = "a";
        Tweet tweet = new Tweet(tweetId,title,summary,type,author);
        try {
            return tweetService.updateTweet(tweet,cover,files,strings);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultConstant.SYSTEM_ERROR;
        }
    }

    @GetMapping("api/v1/tweet/increaseReading")
    public Result increaseTweetReading(Integer tweetId){
        return tweetService.increaseTweetReading(tweetId);
    }
}
