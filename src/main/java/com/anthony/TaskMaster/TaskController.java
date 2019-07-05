package com.anthony.TaskMaster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class TaskController {
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
        Task task = taskRepository.findById(id).get();
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
        Task task = taskRepository.findById(id).get();
        if(task != null){
            task.setAssignee(assignee);
            task.setStatus("Assigned");
            taskRepository.save(task);
        }
        List<Task> allTask = (List)taskRepository.findAll();
        return allTask;
    }
}
