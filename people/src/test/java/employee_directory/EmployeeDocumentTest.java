package employee_directory;

import bbv.hr.PeopleApplication;
import bbv.hr.api.config.SecurityConfig;
import bbv.hr.api.controllers.employee_directory.EmployeeDocumentController;
import bbv.hr.api.dtos.employee_directory.requests.UploadDocumentRequest;
import bbv.hr.application.interfaces.employee_directory.EmployeeDocumentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeDocumentController.class)
@ContextConfiguration(classes = PeopleApplication.class)
@Import(SecurityConfig.class)
class EmployeeDocumentTest extends EmployeeDirectoryApiTestSupport {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeDocumentService employeeDocumentService;

    @Test
    @DisplayName("TC-09 [P2] Fetch Verification Documents List")
    void shouldFetchVerificationDocumentsList() throws Exception {
        when(employeeDocumentService.getEmployeeDocuments(EMPLOYEE_ID))
                .thenReturn(List.of(documentResponse()));

        mockMvc.perform(get("/api/v1/employees/{employeeId}/documents", EMPLOYEE_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].documentType").value("ID_CARD"))
                .andExpect(jsonPath("$[0].documentName").value("National ID"));

        verify(employeeDocumentService).getEmployeeDocuments(EMPLOYEE_ID);
    }

    @Test
    @DisplayName("TC-10 [P2] Upload Document File & Validation")
    void shouldUploadDocumentFileAndValidation() throws Exception {
        UploadDocumentRequest request = UploadDocumentRequest.builder()
                .documentType("ID_CARD")
                .documentName("National ID")
                .documentUrl("https://files.example.com/emp-0024/id-card.pdf")
                .build();

        when(employeeDocumentService.uploadDocument(eq(EMPLOYEE_ID), any(UploadDocumentRequest.class)))
                .thenReturn(documentResponse());

        mockMvc.perform(post("/api/v1/employees/{employeeId}/documents", EMPLOYEE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.documentId").value("DOC-0024"))
                .andExpect(jsonPath("$.documentUrl").value("https://files.example.com/emp-0024/id-card.pdf"));

        verify(employeeDocumentService).uploadDocument(eq(EMPLOYEE_ID), any(UploadDocumentRequest.class));
    }
}
