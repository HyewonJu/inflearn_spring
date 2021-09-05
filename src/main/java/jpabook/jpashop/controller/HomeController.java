package jpabook.jpashop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j // log 출력
public class HomeController {

    @RequestMapping("/")
    public String home() {
        log.info("<===home controller===>"); // 로그 찍기
        return "home";
    }
}