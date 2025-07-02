package hr.java.web.helloworld.repository;

import hr.java.web.helloworld.domain.Article;
import hr.java.web.helloworld.domain.SearchArticle;

import java.util.List;

public interface ArticleRepository {
    List<Article> getAllArticles();
    List<Article> getArticlesByName(String articleName);
    void saveNewArticle(Article article);
    List<Article> filterByParameters(SearchArticle searchArticle);
}
