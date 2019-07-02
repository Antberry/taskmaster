package com.anthony.TaskMaster;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
//import javafx.application.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TaskMasterApplication.class)
@WebAppConfiguration
@ActiveProfiles("local")
public class UserRepositoryIntegrationTest {
    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    UserRepository userRepository;

    private static final String EXPECTED_FIRSTNAME = "Anthony";
    private static final String EXPECTED_LASTNAME = "Berry";

    @Before
    public void setup() throws Exception {
        dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
        CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(User.class);
        tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L,1L));
        dynamoDBMapper.batchDelete((List<User >)userRepository.findAll());
    }

    @Test
    public void readWriteTestCase(){
        User user1 = new User(EXPECTED_FIRSTNAME, EXPECTED_LASTNAME);
        userRepository.save(user1);

        List<User> result = (List<User>) userRepository.findAll();

        assertTrue("Not Empty", result.size() > 0);
//        assertTrue("Contains user with FirstName", result.get(0).getFirstName().equals(EXPECTED_FIRSTNAME));
    }

}
