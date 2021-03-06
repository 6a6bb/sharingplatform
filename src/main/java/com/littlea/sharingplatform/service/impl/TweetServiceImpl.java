package com.littlea.sharingplatform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.littlea.sharingplatform.constant.ResultConstant;
import com.littlea.sharingplatform.entity.Tweet;
import com.littlea.sharingplatform.mapper.TweetMapper;
import com.littlea.sharingplatform.service.TweetService;
import com.littlea.sharingplatform.util.UploadFileUtil;
import com.littlea.sharingplatform.vo.Page;
import com.littlea.sharingplatform.vo.PageLimit;
import com.littlea.sharingplatform.vo.Result;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author ：LinXinRan
 * @date ：2020/2/8 23:37
 */
@Service
@AllArgsConstructor
public class TweetServiceImpl extends ServiceImpl<TweetMapper, Tweet> implements TweetService {

    private TweetMapper tweetMapper;

    @Override
    @Transactional(rollbackFor = IOException.class)
    public Result addTweet(Tweet tweet, MultipartFile cover, MultipartFile[] files, String[] strings) throws IOException {
        //1、上传封面和文章图片并获得相关信息
        Tweet tweetMessage = UploadFileUtil.uploadText(cover,files,strings);
        if (tweetMessage == null) {
            return ResultConstant.PICTURE_FORMAT_ERROR;
        }else{
            BeanUtils.copyProperties(tweetMessage,tweet);
        }
        //2、存入数据库
        tweetMapper.insert(tweet);
        return ResultConstant.SUCCESS;
    }

    @Override
    public Result deleteTweet(Integer tweetId) {
        if(Objects.isNull(tweetId)){
            return ResultConstant.ARGS_ERROR;
        }
        if(tweetMapper.selectCountById(tweetId) == 0){
            return ResultConstant.TWEET_NOT_FOUND;
        }
        tweetMapper.deleteById(tweetId);
        return ResultConstant.SUCCESS;
    }

    @Override
    public Result getAllTweetList(PageLimit pageLimit, Integer type) {
        int size = tweetMapper.selectAllCount(pageLimit,type);
        List<Tweet> tweetList = tweetMapper.getAllTweetList(pageLimit,type);
        return new Result<>(new Page<>(size,tweetList));
    }

    @Override
    public Result getUserTweetList(PageLimit pageLimit, Integer type, String author) {
        int size = tweetMapper.selectCountByAuthor(pageLimit,type,author);
        List<Tweet> tweetList = tweetMapper.getUserTweetList(pageLimit,type,author);
        return new Result<>(new Page<>(size,tweetList));
    }

    @Override
    public Result getTweetDetail(Integer tweetId) {
        if(Objects.isNull(tweetId)){
            return ResultConstant.ARGS_ERROR;
        }
        if(tweetMapper.selectCountById(tweetId) == 0){
            return ResultConstant.TWEET_NOT_FOUND;
        }
        return new Result<>(tweetMapper.selectTweetById(tweetId));
    }

    @Override
    @Transactional(rollbackFor = IOException.class)
    public Result updateTweet(Tweet tweet, MultipartFile cover, MultipartFile[] files, String[] strings) throws IOException {
        if(tweetMapper.selectCountById(tweet.getTweetId()) == 0){
            return ResultConstant.TWEET_NOT_FOUND;
        }
        //1、上传封面和文章图片并获得相关信息
        Tweet tweetMessage = UploadFileUtil.uploadText(cover,files,strings);
        if (tweetMessage == null) {
            return ResultConstant.PICTURE_FORMAT_ERROR;
        }else{
            BeanUtils.copyProperties(tweetMessage,tweet);
        }
        //2、存入数据库
        tweetMapper.updateById(tweet);
        return ResultConstant.SUCCESS;
    }

    @Override
    public Result increaseTweetReading(Integer tweetId) {
        if(Objects.isNull(tweetId)){
            return ResultConstant.ARGS_ERROR;
        }
        if(tweetMapper.selectCountById(tweetId) == 0){
            return ResultConstant.TWEET_NOT_FOUND;
        }
        Tweet tweet = tweetMapper.selectById(tweetId);
        Integer readingVolume = tweet.getReadingVolume();
        readingVolume++;
        tweet.setReadingVolume(readingVolume);
        tweetMapper.updateById(tweet);
        return ResultConstant.SUCCESS;
    }
}
