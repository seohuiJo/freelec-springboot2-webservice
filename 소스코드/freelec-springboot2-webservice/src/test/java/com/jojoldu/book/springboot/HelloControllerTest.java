package com.jojoldu.book.springboot;

import com.jojoldu.book.springboot.config.auth.SecurityConfig;
import com.jojoldu.book.springboot.web.HelloController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// 테스트를 진행할 때 JUnit에 내장된 실행자 외에 다른 실행자를 실행시킨다
// 여기서는 SpringRunner라는 스프링 실행자를 사용한다
// 즉, 스프링 부트 테스트와 JUnit사이에 연결자 역할을 한다
@RunWith(SpringRunner.class)
// 여러 스프링 테스트 어노테이션 중, Web에 집중할 수 있는 어노테이션이다.
// 선언할 경우 @Controller, @ControllerAdvice 등을 사용할 수 있다.
// 단, @Service, @Component, @Repository 등은 사용 할 수 없다.
@WebMvcTest(controllers= HelloController.class,
        excludeFilters={
        @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, classes= SecurityConfig.class)
        })
public class HelloControllerTest {
    @Autowired  // 스프링이 관리하는 빈(Bean)을 주입받는다
    private MockMvc mvc;    // 웹 API를 테스트할 때 사용한다

    @Test
    public void hello가_리턴된다() throws Exception{
        String hello="hello";

        mvc.perform(get("/hello"))  // MockMvc를 통해 /hello 주소로 HTTP GET 요청을 한다
                .andExpect(status().isOk()) // HTTP Header의 Status를 검증한다
                .andExpect(content().string(hello));    // Controller에서 'hello'를 리턴하기 때문에 이 값이 맞는지 검증한다
    }

    @Test
    public void helloDto가_리턴된다() throws Exception{
        String name="hello";
        int amount=1000;

        mvc.perform(
                get("/hello/dto")
                .param("name",name) // param: API를 테스트할 때 사용될 요청 파라미터를 설정함, 단 String만 허용
                .param("amount",String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount",is(amount)));
        // jsonPath: JSON 응답값을 필드별로 검증할 수 있는 메소드
        // $를 기준으로 필드명을 명시함
    }
}
