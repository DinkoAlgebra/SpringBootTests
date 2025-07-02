package hr.java.web.helloworld.service;

import hr.java.web.helloworld.domain.Category;
import hr.java.web.helloworld.repository.SpringDataCategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    SpringDataCategoryRepository categoryRepository;
    @Override
    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
