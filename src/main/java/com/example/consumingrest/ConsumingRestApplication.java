package com.example.consumingrest;

import com.example.consumingrest.model.Album;
import com.example.consumingrest.model.Comment;
import com.example.consumingrest.model.Post;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

@SpringBootApplication
public class ConsumingRestApplication {

	private static final Logger log = LoggerFactory.getLogger(ConsumingRestApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ConsumingRestApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {

			// Get a post
			Post post = restTemplate.getForObject("https://jsonplaceholder.typicode.com/todos/1", Post.class);

			log.info(post.toString());

			// Get a comment
			Comment comment = restTemplate.getForObject("https://jsonplaceholder.typicode.com/comments/1", Comment.class);

			log.info((comment.toString()));

			// Get album

			Album album = restTemplate.getForObject("https://jsonplaceholder.typicode.com/albums/1", Album.class);

			log.info((album.toString()));

			Post userPost = new Post();
			post.setBody("Created post body");
			post.setTitle("Created post title");
			post.setUserId(2);
			post.setId(102);

			ResponseEntity<String> result = restTemplate.postForEntity("https://jsonplaceholder.typicode.com/posts", userPost.convertToJson().toString(), String.class);

			log.info(result.getBody());

			// Delete
			restTemplate.delete("https://jsonplaceholder.typicode.com/posts/1");

			// Get all posts
			List<Post> posts = getAllPosts(restTemplate);

			// Save posts to file
			savePostsToFile(posts);

		};

	}

	private List<Post> getAllPosts(RestTemplate restTemplate) {
		ResponseEntity<List<Post>> responseEntity = restTemplate.exchange("https://jsonplaceholder.typicode.com/posts", HttpMethod.GET, null, new ParameterizedTypeReference<List<Post>>() {
			@Override
			public Type getType() {
				return super.getType();
			}
		});

		return responseEntity.getBody();

	}

	private void savePostsToFile(List<Post> posts) {

		JSONArray result = new JSONArray();

		posts.forEach(p -> {
			try {
				JSONObject obj = p.convertToJson();
				result.put(obj);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		});

		FileWriter file = null;


		try {
			file = new FileWriter("salida.json");
			file.write(result.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				file.flush();
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
