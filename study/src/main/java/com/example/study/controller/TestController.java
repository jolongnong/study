package com.example.study.controller;

import com.example.study.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class TestController {

    private final MemberService memberService;

    @GetMapping("/")
    public String index(){

        return "index";
    }

    @GetMapping("/userinfo")
    public String userinfo(Model model,@RequestParam(value = "pageNum", defaultValue = "1") int pageNum){
            memberService.userinfo(model, pageNum);
        return "userinfo";
    }
}
