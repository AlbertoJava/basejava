package com.urise.webapp.storage;

import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ResumeDataTest {

public static Resume fillResumeSections (Resume r, int countJobOrganization, int countEducationOrganization, int countQualification, int countAchivements){

    List<Organization> organizationList = new ArrayList<>();
    for (int i=0;i<countJobOrganization;i++){
        organizationList.add(new Organization("JOB_" + i,"http://job_"+ 1+".org",
                LocalDate.of(2001,01,30 ),
                LocalDate.of(2002,01,30 ),
                "Title_"+i,
                "description_"+i));
    }

    List<Organization> educationList = new ArrayList<>();
    for (int i=0;i<countEducationOrganization;i++){
        organizationList.add(new Organization("Education_" + i,"http://education_"+ 1+".org",
                LocalDate.of(2001,01,30 ),
                LocalDate.of(2002,01,30 ),
                "Title_"+i,
                "description_"+i));
    }

    List <String> qualifications = new ArrayList<>();
    for (int i =0;i<countQualification;i++) {
        qualifications.add("This is  string №" + i + " describing qualifications!");
    }

    List <String> achivements = new ArrayList<>();
    for (int i=0;i<countAchivements;i++) {
        achivements.add("This is  string №" + i + " describing achievements!");
    }
    r.addSection(SectionType.PERSONAL, new TextSection("This is describing string of myown!"));
    r.addSection(SectionType.OBJECTIVE, new TextSection("This is describing string of willing position!"));
    r.addSection(SectionType.QUALIFICATIONS, new ListSection(qualifications));
    r.addSection(SectionType.ACHIEVEMENT, new ListSection(achivements));
    r.addSection(SectionType.EXPIRIENCE,new OrganizationSection (organizationList));
    r.addSection(SectionType.EDUCATION, new OrganizationSection(educationList));
    return r;
}
}
