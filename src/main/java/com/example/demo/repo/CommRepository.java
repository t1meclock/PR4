package com.example.demo.repo;

import com.example.demo.models.Comm;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommRepository extends CrudRepository<Comm, Long> {
    List<Comm> findByAuthor(String author);
    List<Comm> findByAuthorContains(String author);
}
