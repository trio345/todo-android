package id.co.mdd.todoapp.repositories.todo;

import java.util.ArrayList;

import id.co.mdd.todoapp.models.DataItem;
import id.co.mdd.todoapp.models.TodoBaseModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TodoService {

    @GET("todos")
    Call<TodoBaseModel> getTodos();

    @Headers("Content-Type: application/json")
    @POST("todos")
    Call<TodoBaseModel> postTodo(@Body DataItem data);

    @Headers("Content-Type: application/json")
    @PUT("todos/{id}")
    Call<TodoBaseModel> updateTodo(@Path("id") int id, @Body DataItem data);

    @DELETE("todos/{id}")
    Call<TodoBaseModel> deleteTodo(@Path("id") int id);

}
