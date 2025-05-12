package com.techmanage.controller;

import com.techmanage.entity.User;
import com.techmanage.entity.UserType;
import com.techmanage.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerIntegrationTest {


    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void clearDatabase() {
        userRepository.deleteAll();
    }

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl() {
        return "http://localhost:" + port + "/api/users";
    }

    @Test
    void testCreateGetAndDeleteUser() {
        //Criando novo usuário
        User newUser = new User();
        newUser.setFullName("Carlos Souza");
        newUser.setEmail("carlos@example.com");
        newUser.setPhone("+55 11 95555-1234");
        newUser.setBirthDate(LocalDate.of(1992, 2, 20));
        newUser.setUserType(UserType.VIEWER);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity<>(newUser, headers);

        //POST
        ResponseEntity<User> postResponse = restTemplate.postForEntity(baseUrl(), request, User.class);
        assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());
        assertNotNull(postResponse.getHeaders().getLocation());
        assertNotNull(postResponse.getBody());
        Long userId = postResponse.getBody().getId();
        assertNotNull(userId);

        //GET by ID
        ResponseEntity<User> getResponse = restTemplate.getForEntity(baseUrl() + "/" + userId, User.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertEquals("Carlos Souza", getResponse.getBody().getFullName());

        //DELETE
        ResponseEntity<String> deleteResponse = restTemplate.exchange(
                baseUrl() + "/" + userId,
                HttpMethod.DELETE,
                null,
                String.class
        );
        assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());
        assertEquals("Usuário deletado com sucesso", deleteResponse.getBody());
    }
    @Test
    void testGetAllUsers() {
        ResponseEntity<User[]> response = restTemplate.getForEntity(baseUrl(), User[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testUpdateUser() {
        //Criação do usuário para atualizar
        User newUser = new User();
        newUser.setFullName("Maria Lima");
        newUser.setEmail("maria@example.com");
        newUser.setPhone("+55 11 91111-2222");
        newUser.setBirthDate(LocalDate.of(1990, 3, 10));
        newUser.setUserType(UserType.EDITOR);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity<>(newUser, headers);
        ResponseEntity<User> postResponse = restTemplate.postForEntity(baseUrl(), request, User.class);
        assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());
        Long userId = postResponse.getBody().getId();

        //Dados atualizados
        newUser.setFullName("Maria Lima Atualizada");
        newUser.setEmail("mariaatualizada@example.com");
        HttpEntity<User> updateRequest = new HttpEntity<>(newUser, headers);

        //PUT
        ResponseEntity<User> putResponse = restTemplate.exchange(
                baseUrl() + "/" + userId,
                HttpMethod.PUT,
                updateRequest,
                User.class
        );

        assertEquals(HttpStatus.OK, putResponse.getStatusCode());
        assertEquals("Maria Lima Atualizada", putResponse.getBody().getFullName());
        assertEquals("mariaatualizada@example.com", putResponse.getBody().getEmail());
    }

}