package com.anthony.TaskMaster;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
//import javafx.application.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TaskMasterApplication.class)
@WebAppConfiguration
@ActiveProfiles("local")
public class TaskRepositoryIntegrationTest {
    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    TaskRepository taskRepository;

    private static final String EXPECTED_TITLE = "Clean House";
    private static final String EXPECTED_DESCRIPTION = "Wash clothes";

    @Before
    public void setup() throws Exception {
        dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
        CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(Task.class);
        tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L,1L));
        dynamoDBMapper.batchDelete((List<Task >)taskRepository.findAll());
    }

    @Test
    public void readWriteTestCase(){
        Task user1 = new Task(EXPECTED_TITLE, EXPECTED_DESCRIPTION);
        taskRepository.save(user1);

        List<Task> result = (List<Task>) taskRepository.findAll();

        assertTrue("Not Empty", result.size() > 0);
//        assertTrue("Contains user with FirstName", result.get(0).getTitle().equals(EXPECTED_TITLE));
    }

}
