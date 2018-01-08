package springboot.controller;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import springboot.Introduction;
import springboot.model.Todo;
import springboot.model.constants.TodoPriority;
import springboot.service.TodoService;

import java.util.Arrays;

import static com.jayway.restassured.RestAssured.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Introduction.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HomeControllerTest {
    @MockBean
    private TodoService todoService;

    @LocalServerPort
    private int serverPort;

    private static final String NAME = "Todo1";
    private static final TodoPriority PRIORITY = TodoPriority.MEDIUM;

    private static final Todo todo = new Todo(NAME, PRIORITY);


    @Test
    public void allTest() {
        String TODO = "{\"code\":200,\"message\":null,\"value\":[{\"name\":\"Todo1\",\"priority\":\"MEDIUM\"}]}";
        when(todoService.getAll()).thenReturn(Arrays.asList(new Todo(NAME, PRIORITY)));

        given()
            .contentType("application/json")
            .when()
            .port(serverPort)
            .get("/todos")
            .then()
            .body(Matchers.containsString("value"))
            .body(Matchers.containsString(NAME))
            .body(Matchers.equalTo(TODO))
            .statusCode(200);

        verify(todoService).getAll();
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(this.todoService);
    }
}
