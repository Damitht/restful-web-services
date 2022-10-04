package com.damith.rest.webservices.restfulwebservices.user;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.damith.rest.webservices.restfulwebservices.jpa.PostRepository;
import com.damith.rest.webservices.restfulwebservices.jpa.UserRepository;

import org.springframework.hateoas.*;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import jakarta.validation.Valid;

@RestController
public class UserJpaResource {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@GetMapping("/jpa/users")
	public List<User> retrieveAllUsers(){
		return userRepository.findAll();
	}
	
	@GetMapping("/jpa/users/{id}")
	public EntityModel<User> retrieveUserById(@PathVariable Integer id){
		Optional<User> user = userRepository.findById(id);
		
		if(user.isEmpty()) {
			throw new UserNotFoundException("id : "+id);
		}
		
		EntityModel<User> entityModel = EntityModel.of(user.get());
		
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		entityModel.add(link.withRel("all-users"));
		
		return entityModel;
	}
	
	@DeleteMapping("/jpa/users/{id}")
	public void deleteUserById(@PathVariable Integer id){
		userRepository.deleteById(id);
	}
	
	@PostMapping("/jpa/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User savedUser = userRepository.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retrievePostsByUser(@PathVariable Integer id){
		
		Optional<User> user = userRepository.findById(id);
		
		if(user.isEmpty())
			throw new UserNotFoundException("id : "+id);
		
		return user.get().getPosts();
	}
	
	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Object> createPostForUser(@PathVariable Integer id, @Valid @RequestBody Post post) {
		Optional<User> user = userRepository.findById(id);
		
		if(user.isEmpty())
			throw new UserNotFoundException("id : "+id);
		
		post.setUser(user.get());
		Post savedPost = postRepository.save(post);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedPost.getId())
				.toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping("/jpa/users/{userId}/posts/{postId}")
	public Post retirievePostById(@PathVariable Integer userId, @PathVariable Integer postId) {
		Optional<User> user =  userRepository.findById(userId);
		
		if(user.isEmpty())
			throw new UserNotFoundException("userId : "+userId);
		
		Optional<Post> post = postRepository.findById(postId);
		
		if(post.isEmpty())
			throw new PostNotFoundException("PostId : "+postId);
		
		return post.get();
	}

}
