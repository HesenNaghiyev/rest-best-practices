package com.restfulservices.Restful.WebServices.controller;


import com.restfulservices.Restful.WebServices.post.Post;
import com.restfulservices.Restful.WebServices.post.PostRepository;
import com.restfulservices.Restful.WebServices.user.User;
import com.restfulservices.Restful.WebServices.user.UserNotFoundException;
import com.restfulservices.Restful.WebServices.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
public class UserJpaController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping(path = "/jpa/users")
    public List<User> retrieveAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/jpa/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable  int id){
       Optional<User> user= userRepository.findById(id);

       if (user.isEmpty())
           throw new UserNotFoundException("user with this "+ id+ " not founded");

       //Hateoes to get  link of all users

        EntityModel<User> resource = EntityModel.of(user.get());

        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());

        resource.add(linkTo.withRel("all-users"));

       return resource;
    }

    @PostMapping("/jpa/users")
    public ResponseEntity<Object> createUser( @Valid @RequestBody User user){
      User savedUser=userRepository.save(user);


      //for getting newly created user link

       URI location= ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
       return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/jpa/users/{id}")
    public void deleteUser(@PathVariable  int id){
         userRepository.deleteById(id);

    }



    @GetMapping(path = "/jpa/users/{id}/post")
    public List<Post> retrieveAllUsersWithPost(@PathVariable int id){
       Optional<User> user =userRepository.findById(id);
       if (user.isEmpty()){
           throw new UserNotFoundException("User not found");
       }
      List<Post> posts = user.get().getPosts();
       return posts;
    }



    @PostMapping("/jpa/users/{id}/post")
    public ResponseEntity<Object> createPost(@PathVariable  int id, @RequestBody Post post){
        Optional<User> userOptional =userRepository.findById(id);
        if (userOptional.isEmpty()){
            throw new UserNotFoundException("User not found");
        }


        User user =userOptional.get();
        post.setUser(user);

        postRepository.save(post);



        //for getting newly created user link

        URI location= ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }


}
