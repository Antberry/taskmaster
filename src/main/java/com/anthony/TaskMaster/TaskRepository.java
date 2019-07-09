package com.anthony.TaskMaster;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

@EnableScan
public interface TaskRepository extends CrudRepository<Task, String>{
    Task findById(UUID id);
    List<Task> findByAssignee(String assignee);
//
//    List<Object> findById(UUID taskId);
}
