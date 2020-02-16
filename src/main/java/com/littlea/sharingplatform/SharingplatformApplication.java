package com.littlea.sharingplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.math.BigInteger;

/***
 * @author chenqiting And LinXinRan
 */
@SpringBootApplication
@EnableAsync
public class SharingplatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(SharingplatformApplication.class, args);
    }

}
