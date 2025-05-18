package com.igboke.library_api.repository;
import com.igboke.library_api.model.Book;
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository; 

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    // Custom query methods
    Book findByIsbn(String isbn);
    Book findByTitle(String title);
    Book findByAuthor(String author);
    Book findByPublishedYear(int publishedYear);

}
