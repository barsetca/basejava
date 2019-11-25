package com.urise.webapp;

import com.urise.webapp.model.ContactsType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.SectionType;

import javax.sound.midi.Soundbank;

public class ResumeTestData {

    public static void main(String[] args) {
        Resume resume = new Resume("Victor");
        resume.setDataContacts(ContactsType.MOBIL, "+7(321)123-45-67");
        resume.setDataContacts(ContactsType.HOME_PHONE, "8(812)123-45-67");
        resume.setDataContacts(ContactsType.E_MAIL, "barsetca@rambler.ru");
        resume.setDataContacts(ContactsType.PROFILE_GITHUB, "https://github.com/barsetca");

        resume.setDataSections(SectionType.OBJECTIVE,"Junior JAVA");
        resume.setDataSections(SectionType.PERSONAL,"Motivation");
        resume.setDataSections(SectionType.ACHIEVEMENT,"More than 1000 small tasks");
        resume.setDataSections(SectionType.ACHIEVEMENT,"Project Base of date resumes");
        resume.setDataSections(SectionType.QUALIFICATION,"Electrical University");
        resume.setDataSections(SectionType.QUALIFICATION,"Diploma java developer");
        resume.setDataSections(SectionType.EDUCATION, "Electrical University", "1986 - 1993 Радиотехнический фвкультет");
        resume.setDataSections(SectionType.EDUCATION, "OBS", "2002 - 2003 Эффективный менеджмент, Маркетинг и финансы");
        resume.setDataSections(SectionType.EXPERIENCE, "Дом сотдружества", "1994 - 1998 Торговый агент, начальник отдела сбыта");
        resume.setDataSections(SectionType.EXPERIENCE, "Кей", "1998 - 2019 Директор по административному управлению");


        System.out.println(resume.getFullName());

       for (SectionType type : SectionType.values()){
           System.out.println(type.getTitle() + ": " + resume.getSectionData(type));
       }
        System.out.println("\n"+ SectionType.ACHIEVEMENT.getTitle() + resume.getSectionData(SectionType.ACHIEVEMENT));

        for (ContactsType type : ContactsType.values()){
            System.out.println(type.getTitle() + ": " + resume.getContactData(type));
        }


    }

}
