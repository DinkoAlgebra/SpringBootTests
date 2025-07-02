package hr.java.web.helloworld.controller;

import hr.java.web.helloworld.domain.Category;
import hr.java.web.helloworld.dto.ArticleDTO;
import hr.java.web.helloworld.dto.SearchArticleDTO;
import hr.java.web.helloworld.publisher.CustomSpringEventPublisher;
import hr.java.web.helloworld.service.ArticleService;
import hr.java.web.helloworld.service.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/start")
@AllArgsConstructor
@SessionAttributes({"categoryList", "searchArticleDTO"})
public class StartController {

    private ArticleService articleService;
    private CategoryService categoryService;
    //private CustomSpringEventPublisher eventPublisher;

    @GetMapping
    public String start(Model model, HttpServletRequest request) {

        HttpSession createdSession = request.getSession(true);
        //eventPublisher.publishCustomEvent("The first screen has been displayed!");

        model.addAttribute("searchArticleDTO", new SearchArticleDTO());
        model.addAttribute("categoryList", categoryService.findAll());
        model.addAttribute("filteredArticles", articleService.getAllArticles());
        return "startPage";
    }

    @PostMapping("/saveNewArticle.html")
    public String saveNewArticle(@ModelAttribute ArticleDTO articleDTO, Model model) {
        articleService.saveNewArticle(articleDTO);
        return "redirect:/start";
    }

    @PostMapping("/articleSearch.html")
    public String articleSearch(@ModelAttribute SearchArticleDTO searchArticleDTO, Model model) {
        model.addAttribute("filteredArticles",
                articleService.filterByParameters(searchArticleDTO));
        return "startPage";
    }

}
