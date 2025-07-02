package hr.java.web.helloworld.service;

import hr.java.web.helloworld.domain.Category;

import java.util.List;

public interface CategoryService {
    Category findByName(String name);
    List<Category> findAll();
}
