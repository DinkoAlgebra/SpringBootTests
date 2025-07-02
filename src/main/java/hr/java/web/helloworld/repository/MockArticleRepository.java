package hr.java.web.helloworld.repository;

import hr.java.web.helloworld.domain.Article;
import hr.java.web.helloworld.domain.Category;
import hr.java.web.helloworld.domain.SearchArticle;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MockArticleRepository implements ArticleRepository {

    private static List<Article> articleList;

    static {
        articleList = new ArrayList<>();

        Article firstArticle = new Article(1,
                "Tesla Model Y",
                "Electric car",
                new BigDecimal(50000),
                new Category(1, "CARS", "Description"));

        Article secondArticle = new Article(2,
                "Apartment on the main square",
                "Luxury apartment",
                new BigDecimal(500000),
                new Category(2, "PROPERTIES", "Description"));

        Article thirdArticle = new Article(3,
                "House on the beach",
                "Vacation house",
                new BigDecimal(5000000),
                new Category(2, "PROPERTIES", "Description"));

        Article fourthArticle = new Article(4,
                "Oldtimer Mercedes X 1800",
                "Vintage car",
                new BigDecimal(100000),
                new Category(1, "CARS", "Description"));


        articleList.add(firstArticle);
        articleList.add(secondArticle);
        articleList.add(thirdArticle);
        articleList.add(fourthArticle);
    }

    @Override
    public List<Article> getAllArticles() {
        return articleList;
    }

    @Override
    public List<Article> getArticlesByName(String articleName) {
        return articleList.stream()
                .filter(a -> a.getName().toLowerCase().contains(articleName.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public void saveNewArticle(Article article) {
        articleList.add(article);
    }

    @Override
    public List<Article> filterByParameters(SearchArticle searchArticle) {

        List<Article> articles = getAllArticles();

        if(Optional.ofNullable(searchArticle.getId()).isPresent()) {
            articles = articles.stream()
                    .filter(a -> a.getId().equals(searchArticle.getId()))
                    .collect(Collectors.toList());
        }

        if(!Optional.of(searchArticle.getName()).isEmpty()) {
            articles = articles.stream()
                    .filter(a -> a.getName().contains(searchArticle.getName()))
                    .collect(Collectors.toList());
        }

        if(!Optional.of(searchArticle.getDescription()).isEmpty()) {
            articles = articles.stream()
                    .filter(a -> a.getDescription().contains(searchArticle.getDescription()))
                    .collect(Collectors.toList());
        }

        if(Optional.ofNullable(searchArticle.getLowerPrice()).isPresent()) {
            articles = articles.stream()
                    .filter(a -> a.getPrice().compareTo(searchArticle.getLowerPrice()) >= 0)
                    .collect(Collectors.toList());
        }

        if(Optional.ofNullable(searchArticle.getUpperPrice()).isPresent()) {
            articles = articles.stream()
                    .filter(a -> a.getPrice().compareTo(searchArticle.getUpperPrice()) <= 0)
                    .collect(Collectors.toList());
        }

        if(Optional.ofNullable(searchArticle.getCategory()).isPresent()) {
            articles = articles.stream()
                    .filter(a -> a.getCategory().getId().equals(searchArticle.getCategory().getId()))
                    .collect(Collectors.toList());
        }


        return articles;
    }
}
