package hr.java.web.helloworld.controller;

import hr.java.web.helloworld.dto.ArticleDTO;
import hr.java.web.helloworld.service.ArticleService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/web-shop")
@AllArgsConstructor
public class ArticleController {

    private ArticleService articleService;

    @GetMapping
    public List<ArticleDTO> getAllArticles() {
        return articleService.getAllArticles().stream().toList();
    }

    @GetMapping("/{articleName}")
    public List<ArticleDTO> filterArticlesByName(@PathVariable String articleName) {
        return articleService.getArticlesByName(articleName).stream().toList();
    }
}
