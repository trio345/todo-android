package id.co.mdd.todoapp.repositories.todo;

import id.co.mdd.todoapp.utils.APIUtils;

public class TodoRepository {
    public static final String BASE_URL = "https://online-course-todo.herokuapp.com/api/v1/";

    public TodoService service;

    public TodoRepository() {
        service = APIUtils.client(TodoService.class, BASE_URL);
    }
}
