package com.urise.webapp.storage;

import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ResumeDataTest {

public static Resume fillResumeSections (Resume r, int countJobOrganization, int countEducationOrganization, int countQualification, int countAchivements){

    List<Organization> organizationList = new ArrayList<>();
    List<Organization.Position> positions;
    for (int i=0;i<countJobOrganization;i++){
         positions= getPositions(i);
        organizationList.add(new Organization(
                "JOB_" + i,
                "http://job_"+ 1+".org",
                positions));
    }

    List<Organization> educationList = new ArrayList<>();
    for (int i=0;i<countEducationOrganization;i++){
        positions= getPositions(i);
        organizationList.add(new Organization(
                "Education_" + i,
                "http://education_"+ 1+".org",
                positions
                ));
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
    r.addContact(ContactType.MAIL, "mail@mail.ru");
    r.addContact(ContactType.PHONE,"1111-11-1111");
    return r;
}

    private static List<Organization.Position> getPositions(int organizationNumber) {
        List<Organization.Position> positions = new ArrayList<>();
        for (int j=0;j<3;j++){
            positions.add(new Organization.Position(
                    LocalDate.of(2000 + j,1,1),
                    LocalDate.of(2001 + j,12,31),
                    "Position title " + organizationNumber +"_"+j,
                    "Position description " + organizationNumber +"_"+j
            ));
        }
        return positions;
    }
}
