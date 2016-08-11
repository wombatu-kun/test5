package ru.edocs_lab.test5.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

public interface TodoRepository extends CrudRepository<Todo, Long> {

    public List<Todo> findAllByOrderByIdDesc();

}
