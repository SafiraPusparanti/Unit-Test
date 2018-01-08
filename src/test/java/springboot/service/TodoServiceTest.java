package springboot.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import springboot.model.Todo;
import springboot.model.constants.TodoPriority;
import springboot.repository.TodoRepository;
import java.util.ArrayList;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.mockito.*;

public class TodoServiceTest {

    //instantiate TodoService
    @Mock
    private TodoRepository todoRepository;

    private TodoService todoService;

    @Before
    public void setUp(){
        /**
         * instantiate Mockito Library
         */
        MockitoAnnotations.initMocks(this);
        this.todoService = new TodoService(this.todoRepository);
    }

    @After
    public void setDown(){
        /**
         * Memastikan kalau fungsi yang dipanggil pada getAll di todoService hanya todoRepository,getAll tanpa ada fungsi lain
         * kalau ada fungsi lain yang terlibat pada todoService.getAll selain todoRepository.getAll maka akan error
         * bisa dijadikan satu untuk semua unit testing jadi seperti ini, jadi cukup ditulis satu kali saja dibawah anotasi after
         */
        Mockito.verifyNoMoreInteractions(todoRepository);
    }

    @Test
    public void getAllTest() throws Exception {

        /**
         * Unit testing ini ngecek todoService aja, todoRepository dimock jadi todoRepositorynya bohongan
         * Jadi bikin ArrayList todoList untuk kembalian jika fungsi pada todoService berhasil dijalankan
         */
        List<Todo> todoLists = new ArrayList<Todo>();
        Todo firstTodo = new Todo("Makan", TodoPriority.MEDIUM);
        todoLists.add(firstTodo);

        List<Todo> returnTodo = new ArrayList<Todo>();

        /**
         * inisialisasai kalau panggil todoService.getALl akan mereturn todoList
         */
        BDDMockito.given(todoRepository.getAll()).willReturn(todoLists);

        /**
         * panggil todoService.getAll()
         */
        returnTodo = todoService.getAll();

        /**
         * menegaskan/menuntut kalau todoList tidak boleh null
         */
        Assert.assertThat(returnTodo, org.hamcrest.Matchers.notNullValue());

        /**
         * menegaskan/menuntut kalau todoList tidak boleh empty (isEmpty() bernilai false)
         */
        Assert.assertThat(returnTodo.isEmpty(), org.hamcrest.Matchers.equalTo(false));

        /**
         * memastikan kalau todoRepository memanggil fungsi getAll pada repository
         * bukan todoService.getAll mereturnkan hardcode yang isinya sama dengan kembalian yang seharusnya
         */
        BDDMockito.then(todoRepository).should().getAll();
    }

    @Test
    public void saveToDoTest(){
        boolean isTrue = true;

        Todo todo = new Todo("Bobo", TodoPriority.MEDIUM);

        BDDMockito.given(todoRepository.store(todo)).willReturn(isTrue);

        isTrue = todoService.saveTodo("Bobo", TodoPriority.MEDIUM);

        Assert.assertThat(isTrue, Matchers.notNullValue());
        Assert.assertThat(isTrue, Matchers.equalTo(true));

        BDDMockito.then(todoRepository).should().store(todo);
    }
}
