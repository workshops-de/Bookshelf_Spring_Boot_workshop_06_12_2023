package de.workshops.bookshelf.book;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
@Validated
@RequiredArgsConstructor
class BookRestController {

  private final BookService bookService;

  @GetMapping
  List<Book> getAllBooks() {
    return bookService.getAllBooks();
  }

  @GetMapping("/{isbn}")
  Book getSingleBook(@PathVariable String isbn) throws BookNotFoundException {
    return bookService.searchBookByIsbn(isbn);
  }

  @GetMapping(params = "author")
  Book searchBookByAuthor(@RequestParam @Size(min = 3) String author)
      throws BookNotFoundException {
    return bookService.searchBookByAuthor(author);
  }

  @PostMapping("/search")
  List<Book> searchBooks(@RequestBody @Valid BookSearchRequest bookSearchRequest) {
    return bookService.searchBooks(bookSearchRequest);
  }

  @PostMapping
  public Book createBook(@RequestBody Book book) {
    return bookService.createBook(book);
  }

  @DeleteMapping("/{isbn}")
  public ResponseEntity<String> deleteBook(@PathVariable String isbn) throws BookNotFoundException {
    bookService.deleteBook(bookService.searchBookByIsbn(isbn));

    return ResponseEntity.ok("OK");
  }
}
