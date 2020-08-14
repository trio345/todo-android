package id.co.mdd.todoapp.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;

import id.co.mdd.todoapp.adapters.TodoAdapter;
import id.co.mdd.todoapp.models.DataItem;
import id.co.mdd.todoapp.models.DataItem;
import id.co.mdd.todoapp.models.TodoBaseModel;
import id.co.mdd.todoapp.repositories.todo.TodoRepository;
import id.co.mdd.todoapp.repositories.todo.TodoService;
import id.co.mdd.todoapp.utils.APIUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodoViewModel extends ViewModel {
    TodoAdapter todoAdapter;
    MutableLiveData<TodoBaseModel> postLiveData = new MutableLiveData<>();


    public TodoViewModel() {
        postLiveData = new MutableLiveData<TodoBaseModel>();
        init();

    }

    public MutableLiveData<TodoBaseModel> getPostLiveData(){

        return postLiveData;
    }
    private void init(){
        todoData();
    }


    public void todoData() {
        TodoRepository todoRepository = new TodoRepository();
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
}
