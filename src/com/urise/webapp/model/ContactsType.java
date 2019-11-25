package com.urise.webapp.model;

public enum ContactsType {
    MOBIL("Мобильный телефон"),
    HOME_PHONE("Домашний телефон"),
    E_MAIL("Адрес электронной почты"),
    PROFILE_LINKEDIN("Профиль LinkedIn"),
    PROFILE_GITHUB("Профиль GitHub"),
    PROFILE_STACKOVERFLOW("Профиль Stackoverflow"),
    HOME_PAGE("Домашняя страница");

    private String title;

    ContactsType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
