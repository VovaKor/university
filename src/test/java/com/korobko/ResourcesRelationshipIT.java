package com.korobko;

import com.korobko.models.Student;
import com.korobko.models.University;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ResourcesRelationshipIT {
    @Autowired
    private TestRestTemplate restTemplate;


    private static String UNIVERSITY_ENDPOINT = "http://localhost:8080/universities/";
    public static String STUDENT_ENDPOINT = "http://localhost:8080/students/";

    private static String UNIVERSITY_NAME = "My University";


    @Test
    public void A_whenSaveStudentsIntoUniversity_thenCorrect() {
        University university = University.builder().name(UNIVERSITY_NAME).build();

        restTemplate.postForEntity(UNIVERSITY_ENDPOINT, university, University.class);

        Student student1 = Student.builder()
                .firstName("John")
                .lastName("White1")
                .build();
        restTemplate.postForEntity(STUDENT_ENDPOINT, student1, Student.class);

        Student student2 = Student.builder()
                .firstName("John")
                .lastName("White2")
                .build();
        restTemplate.postForEntity(STUDENT_ENDPOINT, student2, Student.class);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-Type", "text/uri-list");
        HttpEntity<String> bookHttpEntity
                = new HttpEntity<>(UNIVERSITY_ENDPOINT + "/1", requestHeaders);
        restTemplate.exchange(STUDENT_ENDPOINT + "/1/university",
                HttpMethod.PUT, bookHttpEntity, String.class);
        restTemplate.exchange(STUDENT_ENDPOINT + "/2/university",
                HttpMethod.PUT, bookHttpEntity, String.class);

        ResponseEntity<University> universityGetResponse =
                restTemplate.getForEntity(STUDENT_ENDPOINT + "/1/university", University.class);
        assertEquals("university is incorrect",
                universityGetResponse.getBody().getName(), UNIVERSITY_NAME);
    }


    @Test
    public void B_whenSearchingByUniversityName_thenCorrectStatusCodeAndResponse() {
        ResponseEntity<ServerResponse> studentResponseEntity =
                restTemplate.getForEntity(STUDENT_ENDPOINT +
                        "search/findStudentsByUniversityNameLike?name=" +
                        "My Univer%",
                        ServerResponse.class);
        assertEquals(2 ,studentResponseEntity.getBody()._embedded.students.size());

    }

    @Test
    public void B_whenSearchingByUniversityId_thenCorrectStatusCodeAndResponse() {
        ResponseEntity<ServerResponse> studentResponseEntity =
                restTemplate.getForEntity(STUDENT_ENDPOINT +
                                "search/findStudentsByUniversityId?id=" +
                                "1",
                        ServerResponse.class);
        assertEquals(2 ,studentResponseEntity.getBody()._embedded.students.size());

    }

}
