package id.co.mdd.todoapp.models;

public class DataItem{
	private String createdAt;
	private String task;
	private int id;
	private boolean status;
	private String updatedAt;

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setTask(String task){
		this.task = task;
	}

	public String getTask(){
		return task;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}
