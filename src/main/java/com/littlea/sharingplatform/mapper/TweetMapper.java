package com.littlea.sharingplatform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.littlea.sharingplatform.entity.Tweet;
import com.littlea.sharingplatform.vo.PageLimit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ：LinXinRan
 * @date ：2020/2/8 23:37
 */
@Mapper
public interface TweetMapper extends BaseMapper<Tweet> {

    /**
     * 由id获得数目
     * @param tweetId
     * @return
     */
    int selectCountById(@Param("tweetId") int tweetId);

    /**
     * 由类别获得总推文数量
     * @param pageLimit
     * @param type
     * @return
     */
    int selectAllCount(@Param("pageLimit") PageLimit pageLimit,@Param("type") Integer type);

    /**
     * 获得总推文列表
     * @param pageLimit
     * @param type
     * @return
     */
    List<Tweet> getAllTweetList(@Param("pageLimit") PageLimit pageLimit, @Param("type") Integer type);

    /**
     * 由用户获得推文列表
     * @param pageLimit
     * @param type
     * @param author
     * @return
     */
    int selectCountByAuthor(@Param("pageLimit") PageLimit pageLimit,@Param("type") Integer type, @Param("author") String author);

    /**
     * 获得用户推文列表
     * @param pageLimit
     * @param type
     * @param author
     * @return
     */
    List<Tweet> getUserTweetList(@Param("pageLimit") PageLimit pageLimit, @Param("type") Integer type, @Param("author") String author);

    /**
     * 由id获得推文详情
     * @param tweetId
     * @return
     */
    Tweet selectTweetById(@Param("tweetId") Integer tweetId);
}
