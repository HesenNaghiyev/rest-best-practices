package com.restfulservices.Restful.WebServices.controller;


import com.restfulservices.Restful.WebServices.user.User;
import com.restfulservices.Restful.WebServices.user.UserDaoService;
import com.restfulservices.Restful.WebServices.user.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;



@RestController
public class UserController {

    @Autowired
    private UserDaoService userDaoService;

    @GetMapping(path = "/users")
    public List<User> retrieveAllUsers(){
        return userDaoService.findAll();
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable  int id){
       User user= userDaoService.findOne(id);

       if (user==null)
           throw new UserNotFoundException("user with this "+ id+ " not founded");

       //Hateoes to get  link of all users

        EntityModel<User> resource = EntityModel.of(user);

        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());

        resource.add(linkTo.withRel("all-users"));

       return resource;
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser( @Valid @RequestBody User user){
      User savedUser=userDaoService.save(user);


      //for getting newly created user link

       URI location= ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
       return ResponseEntity.created(location).build();
    }


    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable  int id){
        User user= userDaoService.deleteById(id);
        if (user==null)
            throw new UserNotFoundException("user with this "+ id+ " not founded");
    }

}
