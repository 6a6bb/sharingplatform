package com.littlea.sharingplatform.constant;

import com.littlea.sharingplatform.vo.Result;

/**
 * 可复用状态码类
 * 注意： 从本类获取的常量值不可修改任何内容
 *
 * @author Albumen
 */
public class  ResultConstant {
    public final static Result SYSTEM_ERROR = new Result<>(500, "系统错误");
    public final static Result ARGS_ERROR = new Result<>(400, "参数错误");
    public final static Result SUCCESS = new Result<>(200, "ok");
    public final static Result PICTURE_FORMAT_ERROR = new Result<>(400, "图片格式错误");
    public final static Result SINGLE_FILE_OUT_OF_SIZE = new Result<>(400, "单个文件超出上传限制");

    public final static Result TWEET_NOT_FOUND = new Result<>(400,"此推文不存在");
    private ResultConstant() {

    }
}
