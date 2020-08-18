package id.co.mdd.todoapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import id.co.mdd.todoapp.R;
import id.co.mdd.todoapp.adapters.TodoAdapter;
import id.co.mdd.todoapp.databinding.ActivityMainBinding;
import id.co.mdd.todoapp.databinding.ActivityUpdateBinding;
import id.co.mdd.todoapp.models.DataItem;
import id.co.mdd.todoapp.models.TodoBaseModel;
import id.co.mdd.todoapp.repositories.todo.TodoRepository;
import id.co.mdd.todoapp.viewmodels.TodoViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static String id = "data";
    TodoAdapter todoAdapter;
    ActivityMainBinding binding;
    TodoViewModel viewModel;
    TodoRepository todoRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        todoAdapter = new TodoAdapter();
        getAllDatas();
        eventListener();
    }


    protected void getAllDatas(){
        viewModel = new ViewModelProvider(this).get(TodoViewModel.class);
        viewModel.getPostLiveData().observe(this, new Observer<TodoBaseModel>() {
            @Override
            public void onChanged(TodoBaseModel todoBaseModel) {
                List<DataItem> dataItems = todoBaseModel.getData();
                todoAdapter.setTodos(getApplicationContext(), dataItems);
                binding.rvTodo.setAdapter(todoAdapter);
                binding.rvTodo.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                            LinearLayoutManager.VERTICAL, false
                        ));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        binding = null;
    }


    private void eventListener(){

        todoRepository = new TodoRepository();

        todoAdapter.setListener(new TodoAdapter.TodosListener() {
            @Override
            public void deleteData(DataItem dataItem) {
                viewModel.deleteTodo(dataItem.getId());
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void updateData(DataItem dataItem) {
                ActivityUpdateBinding updateBinding;
                updateBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_update);

                if ( dataItem != null){
                    updateBinding.etData.setText(dataItem.getTask());
                    updateBinding.swStatus.setChecked(dataItem.isStatus());
                }

                updateBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (TextUtils.isEmpty(updateBinding.etData.getText())){
                            updateBinding.etData.setError("data tidak boleh kosong");
                        } else {
                            DataItem data = new DataItem();
                            data.setTask(updateBinding.etData.getText().toString());
                            data.setStatus(updateBinding.swStatus.isChecked());
                            Log.e("id", "id " + dataItem.getId());
                            data.setId(dataItem.getId());

                            todoRepository.service.updateTodo(dataItem.getId(), data).enqueue(new Callback<TodoBaseModel>() {
                                @Override
                                public void onResponse(Call<TodoBaseModel> call, Response<TodoBaseModel> response) {

                                }

                                @Override
                                public void onFailure(Call<TodoBaseModel> call, Throwable t) {
                                t.printStackTrace();
                                }
                            });

                        }
                    }
                });

            }

            @Override
            public void updateStatus(DataItem dataItem) {
                DataItem dataTodo = new DataItem();
                if (dataItem.isStatus()){
                    dataTodo.setStatus(false);
                } else {
                    dataTodo.setStatus(true);
                }

                dataTodo.setTask(dataItem.getTask());
                dataTodo.setId(dataItem.getId());

                todoRepository.service.updateTodo(dataItem.getId(), dataTodo).enqueue(new Callback<TodoBaseModel>() {
                    @Override
                    public void onResponse(Call<TodoBaseModel> call, Response<TodoBaseModel> response) {
                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onFailure(Call<TodoBaseModel> call, Throwable t) {
                        t.printStackTrace();
                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                    }
                });


            }
        });
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

                        if (response.code() >= 200 && response.code() < 300){
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

}