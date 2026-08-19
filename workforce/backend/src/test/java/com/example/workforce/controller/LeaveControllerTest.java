package com.example.workforce.controller;

import com.example.workforce.WorkforceApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = WorkforceApplication.class)
class LeaveControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void canReachLeaveTypes() throws Exception {
        mockMvc.perform(get("/leave-types")).andExpect(status().isOk());
    }
}
