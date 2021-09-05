package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping(value = "hello") // localhost:8080/hello
    public String hello(Model model) {
        // Controller가 view로 데이터 넘기기 위해 model 사용(model에 데이터를 담아서 넘기는 방식)
        model.addAttribute("data", "hello!!!");
        // name이 "data"라는 키에 "hello!!!"라는 값을 담아서 view로 넘김
        return "hello"; // resources:templates/hello.html로 매핑된다
    }
}
