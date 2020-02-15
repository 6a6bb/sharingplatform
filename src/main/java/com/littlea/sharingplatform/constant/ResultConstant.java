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

    public final static Result TWEET_NOT_FOUND = new Result<>(400,"此推文不存在");

    public final static Result LOGIN_ERROR = new Result(401, "未登录");

    public final static Result PERMISSION_DENIED = new Result(1000, "权限不足");

    public final static Result TOKEN_EXPIRE = new Result(1001, "登录密匙无效");

    public final static Result PASSWORD_ERROR = new Result(1002, "账号或密码错误");

    public final static Result ARG_ERROR = new Result(600, "参数错误");

    public final static Result USERNAME_EXIST = new Result(1003,"用户名已经存在");
    public static final Result EMAIL_HOST_EXIST = new Result(1004,"邮箱已经被注册");
    public static final Result NO_ROLE_UPDATE = new Result(1005,"角色权限不足");
    private ResultConstant() {

    }
}
