package com.urise.webapp.model;

public enum ContactType {
    MOBIL("Мобильный телефон"),
    HOME_PHONE("Домашний телефон"),
    SKYPE("Скайп"),
    E_MAIL("Адрес электронной почты"),
    PROFILE_LINKEDIN("Профиль LinkedIn"),
    PROFILE_GITHUB("Профиль GitHub"),
    PROFILE_STACKOVERFLOW("Профиль Stackoverflow"),
    HOME_PAGE("Домашняя страница");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
