package hr.java.web.helloworld.repository;

import hr.java.web.helloworld.domain.Article;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface ArticleQueryByExampleExecutor extends QueryByExampleExecutor<Article> {
    <S extends Article> Iterable<S> findAll(Example<S> example);
}
