package springboot.repository;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import springboot.model.Todo;
import springboot.model.constants.TodoPriority;

public class TodoRepositoryTest {
    private TodoRepository todoRepository;

    private final String NAME = "Bobo";
    private final TodoPriority PRIORITY = TodoPriority.MEDIUM;
    private final Todo todo = new Todo(NAME, PRIORITY);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.todoRepository = new TodoRepository();
    }

    @Test
    public void storeTest() {
        boolean isTrue = true;

        isTrue = todoRepository.store(todo);

        Assert.assertThat(isTrue, Matchers.notNullValue());
        Assert.assertThat(isTrue, Matchers.equalTo(true));
    }
}
