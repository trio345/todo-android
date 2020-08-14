package id.co.mdd.todoapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import id.co.mdd.todoapp.R;
import id.co.mdd.todoapp.adapters.TodoAdapter;
import id.co.mdd.todoapp.databinding.ActivityMainBinding;
import id.co.mdd.todoapp.models.DataItem;
import id.co.mdd.todoapp.models.TodoBaseModel;
import id.co.mdd.todoapp.repositories.todo.TodoRepository;
import id.co.mdd.todoapp.viewmodels.TodoViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TodoAdapter todoAdapter;
    ActivityMainBinding binding;
    TodoRepository todoRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        todoAdapter = new TodoAdapter();

        TodoViewModel todoViewModel = new ViewModelProvider(this).get(TodoViewModel.class);

        todoViewModel.todoData();
//        postData();

        binding.rvTodo.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        todoRepository = new TodoRepository();
        todoRepository.service.getTodos().enqueue(new Callback<TodoBaseModel>() {
            @Override
            public void onResponse(Call<TodoBaseModel> call, Response<TodoBaseModel> response) {
                todoAdapter.setTodos(MainActivity.this,response.body().getData());
            }

            @Override
            public void onFailure(Call<TodoBaseModel> call, Throwable t) {
                t.printStackTrace();
            }
        });

        binding.btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postData();
            }
        });


        binding.rvTodo.setAdapter(todoAdapter);
//        binding.setViewModel(todoViewModel);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        binding = null;
    }


    private void postData(){
        DataItem data = new DataItem();
                String text = binding.etTambah.getText().toString();
                data.setTask(text);
                data.setStatus(false);


                todoRepository.service.postTodo(data).enqueue(new Callback<TodoBaseModel>() {
                    @Override
                    public void onResponse(Call<TodoBaseModel> call, Response<TodoBaseModel> response) {
                        String data = new Gson().toJson(response.body());
                        Log.e("data", "data" + data);

                        if (response.isSuccessful()){
                            Toast("Berhasil ditambah");
                        } else {
                            Toast("Gagal ditambah");
                        }
                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onFailure(Call<TodoBaseModel> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    public void Toast(String msg){
        Toast toast = Toast.makeText(this,
                msg,
                Toast.LENGTH_SHORT);

        toast.show();
    }

    public void deleteData(View view) {
        int id = view.getId();
        todoRepository.service.deleteTodo(id).enqueue(new Callback<TodoBaseModel>() {
            @Override
            public void onResponse(Call<TodoBaseModel> call, Response<TodoBaseModel> response) {
                if (response.code() >= 200 & response.code() <= 299) {
                    Toast(response.body().getMessage());
                } else {
                    Toast(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<TodoBaseModel> call, Throwable t) {
                t.printStackTrace();
                Toast("Gagal menghapus!");
            }
        });
    }
}