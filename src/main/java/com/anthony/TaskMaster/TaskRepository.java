package com.anthony.TaskMaster;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.net.UnknownServiceException;
import java.util.Optional;

@EnableScan
public interface TaskRepository extends CrudRepository<Task, String>{
    Optional<Task> findById(String id);


}
