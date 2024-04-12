package com.example.practiceapp.controller;

import com.example.practiceapp.domain.Book;
import com.example.practiceapp.security.JwtEntity;
import com.example.practiceapp.security.JwtTokenProvider;
import com.example.practiceapp.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reader")
public class ReaderController {
    private final BookService bookService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("allbookslist", bookService.getAccessibleBooks());
        return "readermain";
    }

    @GetMapping("/order/{id}")
    public String orderById(@PathVariable(value = "id") long id, Authentication authentication) {
        JwtEntity jwtEntity = (JwtEntity) authentication.getPrincipal();
        String username = jwtEntity.getUsername();
        bookService.orderById(id, username);
        return "redirect:/reader/";

    }

    @GetMapping("/mybooks")
    public String myBooks(Model model,Authentication authentication) {
        JwtEntity jwtEntity = (JwtEntity) authentication.getPrincipal();
        String username = jwtEntity.getUsername();
        model.addAttribute("mybookslist", bookService.getMyBooks(username));
        return "mybooks";
    }


    @GetMapping("/returnBook/{id}")
    public String returnById(@PathVariable(value = "id") long id) {
        bookService.returnById(id);
        return "redirect:/reader/mybooks";

    }


}
