package bbv.hr.application.services;

import bbv.hr.application.interfaces.ApplicationService;
import bbv.hr.infrastructure.entities.JobApplication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationServiceImp implements ApplicationService {

    @Override
    public List<JobApplication> getApplications() {
        return List.of();
    }

}
