package id.co.mdd.todoapp.models;

import java.util.List;

public class TodoBaseModel{
	public List<DataItem> getData() {
		return data;
	}

	public void setData(List<DataItem> data) {
		this.data = data;
	}

	private List<DataItem> data;
	private String message;
	private boolean status;


	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}
}