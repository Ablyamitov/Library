package com.example.practiceapp.service;

import com.example.practiceapp.domain.Book;
import com.example.practiceapp.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    @Transactional
    public void save(Book book) { bookRepository.save(book); }

    @Transactional
    public List<Book> getAllBooks()
    {
        return bookRepository.findAll();
    }

    @Transactional
    public void deleteById(long id)
    {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void orderById(long id, String username) {
        Book book = bookRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        book.setStatus("Забронированный");
        book.setOrderFullName(username);
        bookRepository.save(book);
    }



    public List<Book> getMyBooks(String username) {
        return bookRepository.findAllByOrderFullName(username);
    }

    public List<Book> getAccessibleBooks() {
        return bookRepository.findAllByStatus("Доступный");
    }

    @Transactional
    public void returnById(long id) {
        Book book = bookRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        book.setStatus("Доступный");
        book.setOrderFullName(null);
        bookRepository.save(book);
    }
}
