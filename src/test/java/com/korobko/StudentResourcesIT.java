package com.korobko;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.korobko.models.Student;
import com.korobko.models.University;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import static com.korobko.ResourcesRelationshipIT.STUDENT_ENDPOINT;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StudentResourcesIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    @Before
    public void setup() {
        mockMvc = webAppContextSetup(wac).build();
    }

    @Test
    public void A_whenStartingApplication_thenCorrectStatusCode() throws Exception {
        mockMvc.perform(get("/students")).andExpect(status().is2xxSuccessful());
    };

    @Test
    public void B_whenAddingNewCorrectStudent_thenCorrectStatusCodeAndResponse() throws Exception {
        Student student1 = Student.builder()
                .firstName("John")
                .lastName("White1")
                .build();

        mockMvc
                .perform(post("/students", student1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(student1)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(redirectedUrl("http://localhost/students/1"));
    }
    @Test
    public void C_whenDeletingCorrectStudent_thenCorrectStatusCodeAndResponse() throws Exception {
        Student student1 = Student.builder()
                .firstName("John")
                .lastName("White1")
                .build();
        mockMvc
                .perform(post("/students", student1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(student1)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(redirectedUrl("http://localhost/students/2"));
        mockMvc
                .perform(delete("/students/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(student1)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void D_whenSearchingByFirstName_thenCorrectStatusCodeAndResponse() throws Exception {
        Student student1 = Student.builder()
                .firstName("John")
                .lastName("White1")
                .build();
        mockMvc
                .perform(post("/students", student1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(student1)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(redirectedUrl("http://localhost/students/3"));
        mockMvc
                .perform(get("/students/search/findByFirstName")
                        .param("firstName", "John")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }


    @Test(expected = NestedServletException.class)
    public void whenAddingNewStudentWithoutFirstName_thenErrorStatusCodeAndResponse() throws Exception {
        Student student1 = Student.builder()
                .lastName("White1")
                .build();

        mockMvc
                .perform(post("/students", student1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(student1)));

    }
    @Test(expected = NestedServletException.class)
    public void whenAddingNewStudentWithoutLastName_thenErrorStatusCodeAndResponse() throws Exception {
        Student student1 = Student.builder()
                .firstName("John")
                .build();

        mockMvc
                .perform(post("/students", student1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(student1)));

    }

    @Test(expected = NestedServletException.class)
    public void whenAddingNewStudentWithEmptyFirstName_thenErrorStatusCodeAndResponse() throws Exception {
        Student student1 = Student.builder()
                .firstName("")
                .lastName("White1")
                .build();
        mockMvc
                .perform(post("/students", student1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(student1)));

    }

    @Test(expected = NestedServletException.class)
    public void whenAddingNewStudentWithEmptyLastName_thenErrorStatusCodeAndResponse() throws Exception {
        Student student1 = Student.builder()
                .firstName("John")
                .lastName("")
                .build();
        mockMvc
                .perform(post("/students", student1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(student1)));

    }

}