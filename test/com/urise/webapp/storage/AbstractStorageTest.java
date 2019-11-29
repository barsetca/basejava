package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.util.DateUtil;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
    protected Storage storage;
    private Resume resume1 = new Resume(UUID_1, "a");
    private Resume resume2 = new Resume(UUID_2, "b");
    private Resume resume3 = new Resume(UUID_3, "c");
    private Resume resume4 = new Resume(UUID_4, "d");

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    {
        resume1.setContactInfo(ContactsType.MOBIL, "+7(921) 855-0482 ");
        resume1.setContactInfo(ContactsType.E_MAIL, "a@yandex.ru");
        resume2.setContactInfo(ContactsType.MOBIL, "+7(911) 855-0482 ");
        resume2.setContactInfo(ContactsType.E_MAIL, "b@yandex.ru");
        resume3.setContactInfo(ContactsType.MOBIL, "+7(812) 855-0482 ");
        resume3.setContactInfo(ContactsType.E_MAIL, "c@yandex.ru");
        resume4.setContactInfo(ContactsType.MOBIL, "+7(495) 855-0482 ");
        resume4.setContactInfo(ContactsType.E_MAIL, "d@yandex.ru");

        resume1.setSectionInfo(SectionType.OBJECTIVE, new LineSections("Ведущий стажировок"));
        resume1.setSectionInfo(SectionType.PERSONAL, new LineSections("Аналитический склад ума"));
        resume2.setSectionInfo(SectionType.OBJECTIVE, new LineSections("Догист"));
        resume2.setSectionInfo(SectionType.PERSONAL, new LineSections("Cильная логика"));
        resume3.setSectionInfo(SectionType.OBJECTIVE, new LineSections("Дизайнер"));
        resume3.setSectionInfo(SectionType.PERSONAL, new LineSections("Креативность"));
        resume4.setSectionInfo(SectionType.OBJECTIVE, new LineSections("Продакт-менеджер"));
        resume4.setSectionInfo(SectionType.PERSONAL, new LineSections("Инициативность"));

        resume1.setSectionInfo(SectionType.ACHIEVEMENT,
                new ListSections(Arrays.asList("База данных резюме", "Маркет плайс Заплати и будь стастлив")));
        resume2.setSectionInfo(SectionType.ACHIEVEMENT,
                new ListSections(Arrays.asList("База данных контактов", "Приложение Управляй безопасностью данных")));
        resume3.setSectionInfo(SectionType.ACHIEVEMENT,
                new ListSections(Arrays.asList("База продуктовых предложений", "Сборник консольных игр")));
        resume4.setSectionInfo(SectionType.ACHIEVEMENT,
                new ListSections(Arrays.asList("Овощная база", "База сервтификатов и лицензий")));

        resume1.setSectionInfo(SectionType.EXPERIENCE,
                new PlaceSections(Arrays.asList(
                        new Place("\nSiemens AG","\nhttps://www.siemens.com/ru/ru/home.html",
                                Arrays.asList(
                                        new PlaceDescription(
                                                DateUtil.of(2005, Month.JANUARY),
                                                DateUtil.of(2007, Month.FEBRUARY),
                                                "\nРазработчик ПО",
                                                "\nРазработка информационной модели"))))));
        resume1.setSectionInfo(SectionType.EDUCATION,
                new PlaceSections(Arrays.asList(
                        new Place("\nЛИТМО","\n\nhttp://www.ifmo.ru/",
                                Arrays.asList(
                                        new PlaceDescription(
                                                DateUtil.of(1993, Month.SEPTEMBER),
                                                DateUtil.of(1996, Month.JULY),
                                                "\nпрограммист С, С++",""),
                                        new PlaceDescription(
                                                DateUtil.of(1987, Month.SEPTEMBER),
                                                DateUtil.of(1993, Month.JULY),
                                                "\nИнженер (программист Fortran, C)",""))))));
    }

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUpTest() {
        storage.clear();
        storage.save(resume3);
        storage.save(resume1);
        storage.save(resume2);
    }

    @Test
    public void sizeTest() {
        System.out.println(resume1);
        assertEquals(3, storage.size());
    }

    @Test
    public void clearTest() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void updateTest() {
        storage.update(resume3);
        assertEquals(resume3, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExistTest() {
        storage.update(resume4);
        assertEquals(resume4, storage.get(UUID_4));
    }

    @Test
    public void getAllSortedTest() {
        List<Resume> testStorage = storage.getAllSorted();
        List<Resume> storageArray = Arrays.asList(resume1, resume2, resume3);
        assertEquals(3, testStorage.size());
        assertEquals(storageArray, testStorage);
    }

    @Test
    public void saveTest() {
        storage.save(resume4);
        assertEquals(resume4, storage.get(UUID_4));
        assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistTest() {
        storage.save(resume3);
    }

    @Test
    public void deleteTest() {
        storage.delete(UUID_1);
        assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExistTest() {
        storage.delete(UUID_4);
    }

    @Test
    public void getTest() {
        assertEquals(resume3, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExistTest() {
        storage.get(UUID_4);
    }
}

