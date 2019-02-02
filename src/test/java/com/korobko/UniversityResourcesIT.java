package com.korobko;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.korobko.models.University;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UniversityResourcesIT {

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
        mockMvc.perform(get("/universities")).andExpect(status().is2xxSuccessful());
    }

    ;

    @Test
    public void B_whenAddingNewCorrectUniversity_thenCorrectStatusCodeAndResponse() throws Exception {
        University university = University.builder().name("My University").build();

        mockMvc
                .perform(post("/universities", university)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(university)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(redirectedUrl("http://localhost/universities/1"));
    }

    @Test(expected = Throwable.class)
    public void C_whenAddingNewUniversityWithSameName_thenErrorStatusCodeAndResponse() throws Exception {
        University university = University.builder().name("My University").build();

        mockMvc
                .perform(post("/universities", university)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(university)));
    }

    @Test
    public void D_whenDeletingCorrectUniversity_thenCorrectStatusCodeAndResponse() throws Exception {
        University university = University.builder().name("My University 2").build();
        mockMvc
                .perform(post("/universities", university)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(university)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(redirectedUrl("http://localhost/universities/3"));
        mockMvc
                .perform(delete("/universities/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(university)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void E_whenSearchingByFirstName_thenCorrectStatusCodeAndResponse() throws Exception {
        University university = University.builder().name("My University 3").build();
        mockMvc
                .perform(post("/universities", university)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(university)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(redirectedUrl("http://localhost/universities/4"));
        mockMvc
                .perform(get("/universities/search/findByName")
                        .param("name", "My University 3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }


    @Test(expected = NestedServletException.class)
    public void whenAddingNewUniversityWithEmptyName_thenErrorStatusCodeAndResponse() throws Exception {
        University university = University.builder().name("").build();

        mockMvc
                .perform(post("/universities", university)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(university)));

    }

    @Test(expected = NestedServletException.class)
    public void whenAddingNewUniversityWithoutName_thenErrorStatusCodeAndResponse() throws Exception {
        University university = University.builder().build();

        mockMvc
                .perform(post("/universities", university)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(university)));

    }

}