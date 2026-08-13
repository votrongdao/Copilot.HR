package bbv.hr.api.controllers;

import bbv.hr.application.interfaces.ApplicationService;
import bbv.hr.infrastructure.entities.JobApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/applications")
public class ApplicationController {
    private final ApplicationService service;

    public ApplicationController(ApplicationService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<JobApplication>> getApplication() {
        return ResponseEntity.ok(service.getApplications());
    }
}
