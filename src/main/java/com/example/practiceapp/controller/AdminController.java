package com.example.practiceapp.controller;

import com.example.practiceapp.domain.Book;
import com.example.practiceapp.security.JwtEntity;
import com.example.practiceapp.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final BookService bookService;
    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("allbookslist", bookService.getAllBooks());
        return "adminmain";
    }
    @GetMapping("/addnew")
    public String addNewEmployee(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);
        return "newbook";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("book") Book book) {
        bookService.save(book);
        return "redirect:/admin/";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable(value = "id") long id) {
        bookService.deleteById(id);
        return "redirect:/admin/";

    }


}
