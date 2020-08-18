package id.co.mdd.todoapp.adapters;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import id.co.mdd.todoapp.R;
import id.co.mdd.todoapp.databinding.TodoListBinding;
import id.co.mdd.todoapp.models.DataItem;
import id.co.mdd.todoapp.models.TodoBaseModel;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {
    private List<DataItem> todos = new ArrayList<>();
    private Context context;
    public TodosListener listener;

    public void setTodos(Context context, List<DataItem> todos) {
        this.todos = todos;
        this.context = context;
        notifyDataSetChanged();
    }

    public interface TodosListener{
        void deleteData(DataItem dataItem);
        void updateData(DataItem dataItem);
        void updateStatus(DataItem dataItem);

    }

    public void setListener(TodosListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public TodoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TodoListBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.todo_list,
                parent,
                false
        );

        return new ViewHolder(binding);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull TodoAdapter.ViewHolder holder, int position) {
        holder.bindData(todos.get(position));
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TodoListBinding binding;

        public ViewHolder(TodoListBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public void bindData(DataItem todoData) {
            binding.setTodos(todoData);

//            binding.btnDelete.setOnClickListener(view -> );

            //support api 26
//            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("UTC"));
//            LocalDateTime date = LocalDateTime.parse(todoBaseModel.getCreatedAt(), dateTimeFormatter);
//
//            DateTimeFormatter localFormatter = DateTimeFormatter
//                    .ofPattern("EEEE, dd MMMM yyyy", new Locale("id", "ID"));
//            String localeDate = date.format(localFormatter);

//            binding.setDate(localeDate);
            binding.setLogo(todoData.isStatus());

            binding.btnDelete.setOnClickListener(view -> listener.deleteData(todoData));
            binding.btnUpdate.setOnClickListener(view -> listener.updateData(todoData));

            binding.ivIcon.setOnClickListener(view -> listener.updateStatus(todoData));

        }





    }
}
