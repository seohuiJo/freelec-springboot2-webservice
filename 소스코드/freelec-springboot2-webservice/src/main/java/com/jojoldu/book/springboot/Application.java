package com.jojoldu.book.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication
// 스프링부트의 자동 설정, 스프링 Bean 읽기와 생성을 모두 자동으로 설정함
// @SpringBootApplication이 있는 위치부터 설정을 읽기 때문에, 이 클래스는 항상 프로젝트의 최상단에 위치해야 함
@SpringBootApplication
public class Application {
    public static void main(String[] args){
        // 내장 WAS를 실행, 톰캣을 설치할 필요가 없음
        SpringApplication.run(Application.class, args);
    }
}
