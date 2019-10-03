package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

public abstract class AbstractStorageTest {
    private static final String UUID_1 = "UUID_1";
    private static final String UUID_2 = "UUID_2";
    private static final String UUID_3 = "UUID_3";
    private static final String UUID_4 = "UUID_4";

    private static final Resume R_1 = new Resume(UUID_1, "FullName_1");
    private static final Resume R_2 = new Resume(UUID_2, "FullName_2");
    private static final Resume R_3 = new Resume(UUID_3, "FullName_3");
    private static final Resume R_4 = new Resume(UUID_4, "FullName_4");

    static {
<<<<<<< HEAD
        ResumeDataTest.fillResumeSections(R_1,3,3,3,3);
        ResumeDataTest.fillResumeSections(R_2,4,4,4,4);
        ResumeDataTest.fillResumeSections(R_3,5,5,5,5);
        ResumeDataTest.fillResumeSections(R_4,6,6,6,6);
=======
        List<Organization> organizationList = new ArrayList<>();
        organizationList.add(new Organization("JOB_1", "http://job_1.org",
                LocalDate.of(2001, 01, 30),
                LocalDate.of(2002, 01, 30),
                "Title1",
                "description1"));
        organizationList.add(new Organization("JOB_2", "http://job_2.org",
                LocalDate.of(2001, 01, 30),
                LocalDate.of(2002, 01, 30),
                "Title2",
                "description2"));

        R_1.addSection(SectionType.PERSONAL, new TextSection("This is describing string of myown!"));
        R_1.addSection(SectionType.OBJECTIVE, new TextSection("This is describing string of willing position!"));
        List<String> qualifications = new ArrayList<>();
        qualifications.add("This is  string №" + 0 + " describing qualifications!");
        R_1.addSection(SectionType.QUALIFICATIONS, new ListSection(qualifications));
        List<String> achivements = new ArrayList<>();
        achivements.add("This is  string №" + 0 + " describing achievements!");
        R_1.addSection(SectionType.ACHIEVEMENT, new ListSection(achivements));
        R_1.addSection(SectionType.EXPIRIENCE, new OrganizationSection(organizationList));

        List<Organization> educationList = new ArrayList<>();
        educationList.add(new Organization("VUZ_1", "http://vuz_1.org",
                LocalDate.of(2001, 01, 30),
                LocalDate.of(2002, 01, 30),
                "Title1",
                "description1"));
        educationList.add(new Organization("VUZ_2", "http://vuz_2.org",
                LocalDate.of(2001, 01, 30),
                LocalDate.of(2002, 01, 30),
                "Title2",
                "description2"));

        R_1.addSection(SectionType.EDUCATION, new OrganizationSection(educationList));

>>>>>>> lesson 8.
    }

    protected Storage storage;

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();

        storage.save(R_1);
        storage.save(R_2);
        storage.save(R_3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(R_3);
    }

    @Test
    public void saveNotExist() {
        int sizeBefore = storage.size();
        storage.save(R_4);
        assertEquals(sizeBefore + 1, storage.size());
        assertEquals(R_4, storage.get(UUID_4));
    }

    @Test
    public void getExist() {
        assertEquals(R_1, storage.get(UUID_1));
        assertEquals(R_2, storage.get(UUID_2));
        assertEquals(R_3, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("notExistableUUID");
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteExist() {
        int sizeBefore = storage.size();
        storage.delete(UUID_1);
        assertEquals(sizeBefore - 1, storage.size());
        storage.get(UUID_1);

    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("notExistableUUID");
    }

    @Test
    public void getAllSorted() {
        assertEquals(Arrays.asList(R_1, R_2, R_3), storage.getAllSorted());
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }

    @Test
    public void updateExist() {
        Resume rBefore = storage.get(UUID_1);
        Resume rAfter = new Resume(UUID_1, "FullName_1");
        storage.update(rAfter);
        assertNotSame(storage.get(UUID_1), rBefore);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        Resume rAfter = new Resume("test");
        storage.update(rAfter);
    }
}