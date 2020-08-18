package id.co.mdd.todoapp.viewmodels;

import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;

import id.co.mdd.todoapp.models.TodoBaseModel;
import id.co.mdd.todoapp.repositories.todo.TodoRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodoViewModel extends ViewModel {
    MutableLiveData<TodoBaseModel> postLiveData;
    TodoRepository todoRepository = new TodoRepository();


    public TodoViewModel() {
        postLiveData = new MutableLiveData<>();
        init();

    }

    public MutableLiveData<TodoBaseModel> getPostLiveData(){

        return postLiveData;
    }
    private void init(){
        todoData();
    }


    public void todoData() {
        todoRepository.service.getTodos().enqueue(new Callback<TodoBaseModel>() {
            @Override
            public void onResponse(Call<TodoBaseModel> call, Response<TodoBaseModel> response) {
                postLiveData.setValue(response.body());
//
                String jsonRes = new Gson().toJson(response.body());
                Log.e("data", "data: " + jsonRes);
            }

            @Override
            public void onFailure(Call<TodoBaseModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    public void deleteTodo(int id){
        todoRepository.service.deleteTodo(id).enqueue(new Callback<TodoBaseModel>() {
            @Override
            public void onResponse(Call<TodoBaseModel> call, Response<TodoBaseModel> response) {

            }

            @Override
            public void onFailure(Call<TodoBaseModel> call, Throwable t) {
                t.printStackTrace();
                Log.e("Delete", t.getMessage());
            }
        });
    }
}
