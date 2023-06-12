package spring.boot.mislav.sirac.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureMockMvc
class CourseControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @Test
    void getAllCourses() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/course"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetAllCoursesByStudentJmbag() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/course").param("jmbag", "your-jmbag"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}