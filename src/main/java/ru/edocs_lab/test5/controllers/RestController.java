package ru.edocs_lab.test5.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.edocs_lab.test5.domain.Todo;
import ru.edocs_lab.test5.domain.TodoRepository;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/rest")
public class RestController {

    @Autowired
    private TodoRepository repository;

    //-------------------Retrieve All
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<Todo>> getAllRecords() {
        List<Todo> todos = repository.findAllByOrderByIdDesc();
        if(todos.isEmpty()){
            return new ResponseEntity<List<Todo>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Todo>>(todos, HttpStatus.OK);
    }
    //-------------------Retrieve Single
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Todo> getRecord(@PathVariable("id") long id) {
        System.out.println("Fetching record with id " + id);
        Todo todo = repository.findOne(id);
        if (todo == null) {
            System.out.println("Record with id " + id + " not found");
            return new ResponseEntity<Todo>(HttpStatus.NOT_FOUND);
        }
        System.out.println("Record id: " + todo.getId());
        return new ResponseEntity<Todo>(todo, HttpStatus.OK);
    }
    //-------------------Create
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody Todo todo, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating record " + todo.getWhat());
        repository.save(todo);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/{id}").buildAndExpand(todo.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
    //------------------- Update
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Todo> updateRecord(@PathVariable("id") long id, @RequestBody Todo todo) {
        System.out.println("Updating record " + id);
        Todo currentRec = repository.findOne(id);
        if (currentRec==null) {
            System.out.println("Record with id " + id + " not found");
            return new ResponseEntity<Todo>(HttpStatus.NOT_FOUND);
        }
        currentRec.setWhat(todo.getWhat());
        currentRec.setWhen(todo.getWhen());
        repository.save(currentRec);
        return new ResponseEntity<Todo>(currentRec, HttpStatus.OK);
    }
    //------------------- Delete
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Todo> deleteRecord(@PathVariable("id") long id) {
        System.out.println("Fetching & Deleting record with id " + id);
        Todo todo = repository.findOne(id);
        if (todo == null) {
            System.out.println("Unable to delete. Record with id " + id + " not found");
            return new ResponseEntity<Todo>(HttpStatus.NOT_FOUND);
        }
        repository.delete(id);
        return new ResponseEntity<Todo>(HttpStatus.NO_CONTENT);
    }
}
