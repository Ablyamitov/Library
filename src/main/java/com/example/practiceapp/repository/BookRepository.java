package com.example.practiceapp.repository;

import com.example.practiceapp.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {
    List<Book> findAllByStatus(String status);

    List<Book> findAllByOrderFullName(String name);
}
