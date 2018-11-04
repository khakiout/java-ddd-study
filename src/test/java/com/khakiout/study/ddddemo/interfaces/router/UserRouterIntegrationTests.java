package com.khakiout.study.ddddemo.interfaces.router;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.khakiout.study.ddddemo.app.config.security.JwtService;
import com.khakiout.study.ddddemo.app.model.ApplicationUser;
import com.khakiout.study.ddddemo.domain.entity.UserEntity;
import javax.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient
@Transactional
public class UserRouterIntegrationTests {

    Logger logger = LoggerFactory.getLogger(UserRouterIntegrationTests.class);

    @Autowired
    private WebTestClient webClient;

    @Autowired
    private JwtService jwtService;

    @Test
    public void testShowAllMustReturn2Users() {
        ApplicationUser user = new ApplicationUser("testuser");
        String token = jwtService.generateToken(user);

        logger.info("Token: {}", token);
        webClient.get().uri("/users")
            .header(HttpHeaders.AUTHORIZATION, "bearer " + token)
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(UserEntity.class)
            .hasSize(2);
    }

    @Test
    public void testGetFirstUserMustReturnMark() {
        EntityExchangeResult<UserEntity> response = webClient.get().uri("/users/{id}", 1)
            .exchange()
            .expectStatus().isOk()
            .expectBody(UserEntity.class)
            .returnResult();

        UserEntity userEntity = response.getResponseBody();
        assertEquals("Mark", userEntity.getFirstName());
        assertEquals("msantos@gmail.com", userEntity.getEmailValue());
    }

    @Test
    public void testGetSecondUserMustReturnMarcelo() {
        EntityExchangeResult<UserEntity> response = webClient.get().uri("/users/{id}", 2)
            .exchange()
            .expectStatus().isOk()
            .expectBody(UserEntity.class)
            .returnResult();

        UserEntity userEntity = response.getResponseBody();
        assertEquals("Marcelo", userEntity.getFirstName());
        assertEquals("marcelo@gmail.com", userEntity.getEmailValue());
    }

    @Test
    public void testGetNonExistentEntityMustReturn404() {
        webClient.get().uri("/users/{id}", 2000)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    public void testCreateInvalidEntityMustFail() {
        UserEntity body = new UserEntity(null, null, null, null);
        webClient.post().uri("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromObject(body))
            .exchange()
            .expectStatus().isBadRequest();
    }

    @Test
    public void testCreateInvalidEmailOnEntityMustFail() {
        UserEntity body = new UserEntity(null, "User", "One", null);
        webClient.post().uri("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromObject(body))
            .exchange()
            .expectStatus().isBadRequest();
    }

    @Test
    public void testCreateEmptyDataMustFail() {
        webClient.post().uri("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .body(null)
            .exchange()
            .expectStatus().isBadRequest();
    }

    @Test
    public void testCreateValidEntityMustSucceed() {
        UserEntity body = new UserEntity(null, "Mark", "Vasquez", "mv@gmail.com");
        EntityExchangeResult<UserEntity> response = webClient.post().uri("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromObject(body))
            .exchange()
            .expectStatus().isCreated()
            .expectBody(UserEntity.class)
            .returnResult();

        UserEntity updated = response.getResponseBody();
        assertNotNull(updated.getId());
        assertEquals("Mark", updated.getFirstName());
        assertEquals("mv@gmail.com", updated.getEmailValue());
    }

    @Test
    public void testUpdateInvalidEntityMustFail() {
        UserEntity body = new UserEntity(null, null, null, "rmico@gmail.com");
        webClient.put().uri("/users/{id}", 2)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromObject(body))
            .exchange()
            .expectStatus().isBadRequest();
    }

    @Test
    public void testUpdateEmptyDataMustFail() {
        webClient.put().uri("/users/1")
            .contentType(MediaType.APPLICATION_JSON)
            .body(null)
            .exchange()
            .expectStatus().isBadRequest();
    }

    @Test
    public void testUpdateNonExistentEntityMustFail() {
        UserEntity body = new UserEntity(null, "Rico", "Mico", "rmico@gmail.com");
        webClient.put().uri("/users/{id}", 2000)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromObject(body))
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    public void testUpdateEntityWithNullIdMustSucceed() {
        UserEntity body = new UserEntity(null, "Rico", "Mico", "rmico@gmail.com");
        EntityExchangeResult<UserEntity> response = webClient.put().uri("/users/{id}", 2)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromObject(body))
            .exchange()
            .expectStatus().isOk()
            .expectBody(UserEntity.class)
            .returnResult();

        UserEntity updated = response.getResponseBody();
        assertEquals("Rico", updated.getFirstName());
        assertEquals("rmico@gmail.com", updated.getEmailValue());
    }

    @Test
    public void testUpdateEntityWithDifferentIdMustUseRouteId() {
        UserEntity body = new UserEntity(4L, "Rex", "Mico", "rmico@gmail.com");
        EntityExchangeResult<UserEntity> response = webClient.put().uri("/users/{id}", 2)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromObject(body))
            .exchange()
            .expectStatus().isOk()
            .expectBody(UserEntity.class)
            .returnResult();

        UserEntity updated = response.getResponseBody();
        assertEquals(2L, updated.getId(), 0);
        assertEquals("Rex", updated.getFirstName());
        assertEquals("rmico@gmail.com", updated.getEmailValue());
    }

    @Test
    public void testDeleteExistingEntityMustReturn200() {
        UserEntity body = new UserEntity(null, "Rico", "Mico", "rmico@gmail.com");
        EntityExchangeResult<UserEntity> response = webClient.post().uri("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromObject(body))
            .exchange()
            .expectStatus().isCreated()
            .expectBody(UserEntity.class)
            .returnResult();

        UserEntity created = response.getResponseBody();
        webClient.delete().uri("/users/{id}", created.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk();
    }

    @Test
    public void testDeleteNonExistentEntityMustReturn404() {
        webClient.delete().uri("/users/{id}", 2000)
            .exchange()
            .expectStatus().isNotFound();
    }

}
