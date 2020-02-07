package com.littlea.sharingplatform.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：LinXinRan
 * @date ：2020/2/4
 */
@RestController
public class TestController {

    @GetMapping("test")
    public String test(){
        return "test";
    }
}
