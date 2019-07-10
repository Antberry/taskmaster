package com.anthony.TaskMaster;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
public class TaskController {
    private S3Client s3Client;

    @Autowired
    TaskRepository taskRepository;

    @GetMapping("/tasks")
    public List<Task> tasks(){
        List<Task> allTask = ((List)taskRepository.findAll());
        return allTask;
    }

    @PostMapping("/tasks")
    public List<Task> tasksPost(@RequestBody Task task){
        if(task.getAssignee().equals("")){
            task.setStatus("Available");
        }
        else {
            task.setStatus("Assigned");
        }
        taskRepository.save(task);
        List<Task> allTask = (List)taskRepository.findAll();
        return allTask;
    }

    @PutMapping("/tasks/{id}/status")
    public List<Task> putTask(@PathVariable UUID id){
        Task task = taskRepository.findById(id);
        if(task.getStatus().equals("Available")){
            task.setStatus("Assigned");
        }
        else if(task.getStatus().equals("Assigned")){
            task.setStatus("Accepted");
        }
        else if(task.getStatus().equals("Accepted")){
            task.setStatus("Finished");
        }
        taskRepository.save(task);
        List<Task> allTask = (List)taskRepository.findAll();
        return allTask;
    }

    @GetMapping("/users/{name}/tasks")
    public List<Task> allTaskByUser(@PathVariable String name){
        List<Task> allTasks = taskRepository.findByAssignee(name);
        return allTasks;
    }

    @PutMapping("/task/{id}/assign/{assignee}")
    public List<Task> getTaskIdByUser(@PathVariable UUID id, @PathVariable String assignee){
        Task task = taskRepository.findById(id);
        if(task != null){
            task.setAssignee(assignee);
            task.setStatus("Assigned");
            taskRepository.save(task);
        }
        List<Task> allTask = (List)taskRepository.findAll();
        return allTask;
    }

    @PostMapping("/tasks/{id}/{filePath}")
    public Task uploadPicByTaskId(@RequestBody Task task, @PathVariable  String filePath){
        String pic = this.s3Client.uploadFile(filePath);

        taskRepository.save(task);
        List<Task> allTask = (List)taskRepository.findAll();
        return (Task) allTask;
    }

    @GetMapping("/tasks/{id}")
    public Task getTaskById(@PathVariable UUID id ){
        Task getTask = taskRepository.findById(id);
        return getTask;
    }
}
