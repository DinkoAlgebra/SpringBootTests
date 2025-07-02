package hr.java.web.helloworld.repository;

import hr.java.web.helloworld.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataCategoryRepository extends JpaRepository<Category, Integer> {
    Category findByName(String name);
}
