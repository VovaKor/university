package com.korobko;

import com.korobko.repositories.UniversityRepository;
import org.apache.tomcat.util.file.Matcher;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UniversityApplicationContextIT {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {

    }

}
