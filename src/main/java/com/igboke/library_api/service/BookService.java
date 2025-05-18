package com.igboke.library_api.service;
import com.igboke.library_api.model.Book; // Import the Book entity
import com.igboke.library_api.repository.BookRepository; // Import the BookRepository
import org.springframework.beans.factory.annotation.Autowired; // For dependency injection
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional; 
import com.igboke.library_api.exception.ResourceNotFoundException; // Import the custom exception

@Service 
public class BookService {

    private final BookRepository bookRepository;

    @Autowired // Marks the constructor for dependency injection
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // --- CRUD duties ---

    // Get all books
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Get a book by ID
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    // Create a new book
    public Book createBook(Book book) {

        return bookRepository.save(book); // Use the method from JpaRepository maybe add some validation here? ISBN to check back later
    }

    // Update an existing book
    public Book updateBook(Long id, Book updatedBookDetails) {
        // Find the existing book first
        Optional<Book> existingBookOptional = bookRepository.findById(id);

        if (existingBookOptional.isPresent()) {
            Book existingBook = existingBookOptional.get();
            // Update the existing book's properties with the new details
            existingBook.setTitle(updatedBookDetails.getTitle());
            existingBook.setAuthor(updatedBookDetails.getAuthor());
            existingBook.setIsbn(updatedBookDetails.getIsbn());

            // Save the updated book (JpaRepository's save() handles both INSERT and UPDATE)
            return bookRepository.save(existingBook);
        } else {
            // Handle case where book is not found - maybe throw a custom exception
             throw new ResourceNotFoundException("Book not found with id " + id); 
        }
    }

    // Delete a book by ID
    public boolean deleteBook(Long id) {
         Optional<Book> bookOptional = bookRepository.findById(id);
         if (bookOptional.isPresent()) {
             bookRepository.deleteById(id); 
             return true; 
         } else {
             return false;
         }
    }
}
