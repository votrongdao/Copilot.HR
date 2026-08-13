package bbv.hr.application.interfaces;

import bbv.hr.infrastructure.entities.JobApplication;

import java.util.List;

public interface ApplicationService {
    List<JobApplication> getApplications();
}
