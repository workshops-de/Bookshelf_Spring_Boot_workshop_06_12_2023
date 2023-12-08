package de.workshops.bookshelf.book;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class BookController {

    private final ObjectMapper mapper;

    private final ResourceLoader resourceLoader;

    private List<Book> books;

    @PostConstruct
    void init() throws IOException {
        this.books = mapper.readValue(
            resourceLoader
                .getResource("classpath:books.json")
                .getInputStream(),
            new TypeReference<>() {
            }
        );
    }

    @GetMapping
    public String getAllBooks(Model model) {
        model.addAttribute("books", books);

        return "books";
    }
}
