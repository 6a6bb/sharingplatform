package com.littlea.sharingplatform.config;

import com.littlea.sharingplatform.constant.ResultConstant;
import com.littlea.sharingplatform.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * @author ：LinXinRan
 * @date ：Created in 2019/11/3 16:12
*/
@ControllerAdvice
public class MyExceptionHandler {


    /**
     * 处理单个文件大小过大异常
     * @return 返回前端的json数据
     */
    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    @ResponseBody
    public Result fileUploadExceptionHandler()  {
        //单个文件过大
        return ResultConstant.SINGLE_FILE_OUT_OF_SIZE;
    }
}
