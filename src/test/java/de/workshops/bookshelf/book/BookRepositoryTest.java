package de.workshops.bookshelf.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import de.workshops.bookshelf.config.BookshelfProperties;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({BookRepository.class, BookshelfProperties.class})
class BookRepositoryTest {

  @Autowired
  private BookRepository bookRepository;

  @Test
  void createBook() {
    Book book = Book.builder()
        .title("Title")
        .author("Author")
        .description("Description")
        .isbn("123-4567890")
        .build();
    bookRepository.createBook(book);

    List<Book> books = bookRepository.findAllBooks();

    assertNotNull(books);
    assertEquals(4, books.size());
    assertEquals(book.getIsbn(), books.get(3).getIsbn());
  }
}
