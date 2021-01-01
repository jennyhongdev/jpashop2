package com.example.jpashop2.controller;

import com.example.jpashop2.domain.Member;
import com.example.jpashop2.repository.MemberRepository;
import com.example.jpashop2.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AccountApiController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    //아이디 중복체크 //ajax 통신이니, ApiController로 와야함 (@RestController)
    @GetMapping("/idCheck")
    public String id_check(String email) {
        List<Member> memberList = memberRepository.findByEmail(email);
        System.out.println("몇 개 있나요? " + memberList.size());

        if (memberList.size() == 0) { //가입한 적 없으면
            return "noMember";
        } else { //가입한 적 있으면
            return "yesMember";
        }
    }

    //회원가입 form 제출
    @PostMapping("/api/signup")
    public Long signUpSubmit(@RequestBody Member member) {
        return memberService.join(member);
        //service 안 거치고, 바로 repository 직행하면 에러남
        //에러: No EntityManager with actual transaction available for current thread - cannot reliably process 'persist' call
        //return memberRepository.save(member); //이렇게 리파지터리 직행 x
    }

    /*
    //로그인 form 제출
    @PostMapping("/api/login")
    public String loginAuth(@RequestBody LoginForm form, HttpSession httpSession) {
        //Account logined = accountService.login(form, httpSession);
        //return logined.getId();
        String result = accountService.login(form, httpSession);
        return result;
    }

     */





}