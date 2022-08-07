package com.jade.book.springboot.web;

import com.jade.book.springboot.config.auth.LoginUser;
import com.jade.book.springboot.config.auth.dto.SessionUser;
import com.jade.book.springboot.service.posts.PostsService;
import com.jade.book.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;


@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts", postsService.findAllDesc());

        //어노테이션 기반으로 개선
        //SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user != null) {
            // userName -> userNames로 변경 : 구글 계정 이름이 아닌 OS 사용자로 인식하여 충돌 방지를 위해
            model.addAttribute("userNames", user.getName());
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }

}
