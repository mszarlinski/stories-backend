package com.github.mszarlinski.stories;

import com.github.mszarlinski.stories.test.FakeJwtDecoder;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;

import java.time.Clock;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ContextConfiguration(classes = AcceptanceTestsConfiguration.class)
public class AcceptanceTests {

    static MongoDBContainer mongoTestContainer = new MongoDBContainer("mongo:4.2");

    static {
        // Cannot use @Container as single container is shared between many test classes
        mongoTestContainer.start();
    }

    @DynamicPropertySource
    static void mongoProperties(DynamicPropertyRegistry props) {
        props.add("spring.data.mongodb.uri", mongoTestContainer::getReplicaSetUrl);
    }

    @Autowired
    protected TestRestTemplate client;

    @Autowired
    protected MongoTemplate mongo;

    @Autowired
    protected Clock clock;

    @Autowired
    protected FakeJwtDecoder fakeJwtDecoder;

    @AfterEach
    void cleanup() {
        mongo.getDb().drop();
        fakeJwtDecoder.clear();
    }
}
