package com.alvesbob.workshopmongo.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alvesbob.workshopmongo.domain.Post;

import com.alvesbob.workshopmongo.services.PostService;

@RestController
@RequestMapping(value="/posts")
public class PostResource {
	
	@Autowired
	private PostService service;
	
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Post> findById(@PathVariable("id") String id){
	
	Post obj = service.findById(id);
	
	return ResponseEntity.ok().body(obj);
	}
		

}
