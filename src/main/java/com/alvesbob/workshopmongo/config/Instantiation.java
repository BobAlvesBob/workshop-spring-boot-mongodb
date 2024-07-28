package com.alvesbob.workshopmongo.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.alvesbob.workshopmongo.domain.Post;
import com.alvesbob.workshopmongo.domain.User;
import com.alvesbob.workshopmongo.dto.AuthorDTO;
import com.alvesbob.workshopmongo.repository.PostRepository;
import com.alvesbob.workshopmongo.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public void run(String... args) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        userRepository.deleteAll();
        postRepository.deleteAll();

        User maria = new User(null, "Maria Brown", "maria@gmail.com");
        User alex = new User(null, "Alex Green", "alex@gmail.com");
        User bob = new User(null, "Bob Grey", "bob@gmail.com");

        userRepository.saveAll(Arrays.asList(maria, alex, bob));

        Post post1 = new Post(null, sdf.parse("21/03/2018"), "Partiu Viagem", "Vou viajar pra SP, abraço!", new AuthorDTO(maria));
        Post post2 = new Post(null, sdf.parse("23/03/2018"), "Bom dia", "Acordei feliz hoje!", new AuthorDTO(maria));

        // Salvar posts primeiro para gerar os IDs
        post1 = postRepository.save(post1);
        System.out.println("Post1 ID: " + post1.getId());
        post2 = postRepository.save(post2);
        System.out.println("Post2 ID: " + post2.getId());

        // Adicionar posts à lista de posts da Maria
        maria.getPosts().addAll(Arrays.asList(post1, post2));

        // Salvar novamente o usuário Maria com os novos posts
        userRepository.save(maria);
        
     // Verificar os posts da Maria
        User mariaFromDb = userRepository.findById(maria.getId()).orElse(null);
        if (mariaFromDb != null) {
            mariaFromDb.getPosts().forEach(post -> System.out.println("Maria's Post ID: " + post.getId()));
        }
    }
}

