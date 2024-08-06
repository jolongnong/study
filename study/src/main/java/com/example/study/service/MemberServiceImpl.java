package com.example.study.service;

import com.example.study.entity.MemberEntity;
import com.example.study.repository.MemberJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberJPARepository memberJPARepository;


    @Override
    public void userinfo(Model model, int pageNum) { //회원정보
        int pageSize = 10;
        String auth = "ROLE_USER";
        Long longCount =  memberJPARepository.countByAuth(auth);// 일반총인원

        int count =longCount.intValue(); //타입변환
        Sort sort = Sort.by(Sort.Order.desc("reg")); //내림차순
        Page<MemberEntity> page = memberJPARepository.findByAuth(auth, PageRequest.of(pageNum - 1, pageSize, sort));

        List<MemberEntity> getUsrelist = page.getContent();

        model.addAttribute("getUsrelist", getUsrelist);
        model.addAttribute("count", count);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("pageSize", pageSize);

        int pageCount = count / pageSize + (count % pageSize == 0 ? 0 : 1);
        int startPage = (pageNum - 1) / 10 * 10 + 1;
        int pageBlock = 10;  // 페이징(이전/다음)을 몇 개 단위로 끊을지
        int endPage = startPage + pageBlock - 1;
        if (endPage > pageCount) {
            endPage = pageCount;
        }

        model.addAttribute("pageCount", pageCount);
        model.addAttribute("startPage", startPage);
        model.addAttribute("pageBlock", pageBlock);
        model.addAttribute("endPage", endPage);


    }




}
