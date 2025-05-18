package com.igboke.library_api.controller;

import com.igboke.library_api.model.Book;
import com.igboke.library_api.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books") 
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    
    @GetMapping // Maps HTTP GET requests to this method
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    // GET /api/books/{id}
    @GetMapping("/{id}") 
    public ResponseEntity<Book> getBookById(@PathVariable Long id) { // Extract ID from path
        Optional<Book> book = bookService.getBookById(id);
        if (book.isPresent()) {
            return ResponseEntity.ok(book.get()); // Return 200 OK with the book data
        } else {
            return ResponseEntity.notFound().build(); // Return 404 Not Found
        }
    }

    // Create a new book
    // POST /api/books
    @PostMapping // Maps HTTP POST requests to this method
    @ResponseStatus(HttpStatus.CREATED) 
    public ResponseEntity<Book> createBook(@RequestBody Book book) { 
        Book createdBook = bookService.createBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    // Update an existing book
    // PUT /api/books/{id}
    @PutMapping("/{id}") 
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        Book updatedBook = bookService.updateBook(id, bookDetails);
        if (updatedBook != null) { 
            return ResponseEntity.ok(updatedBook); 
        } else {
             return ResponseEntity.notFound().build(); // Return 404 Not Found
        }
    }

    // Delete a book by ID
    // DELETE /api/books/{id}
    @DeleteMapping("/{id}") // Maps HTTP DELETE requests with an ID path variable
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        boolean deleted = bookService.deleteBook(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
             return ResponseEntity.notFound().build();
        }
    }
}
