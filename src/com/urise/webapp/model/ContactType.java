package com.urise.webapp.model;

public enum ContactType {
    MOBIL("Мобильный телефон"),
    HOME_PHONE("Домашний телефон"),
    SKYPE("Скайп") {
        @Override
        public String toHtml0(String value) {
            return "<a href='skype:" + value + "'>" + value + "</a>";
        }
    },
    E_MAIL("Адрес электронной почты") {
        @Override
        public String toHtml0(String value) {
            return "<a href='mailto:" + value + "'>" + value + "</a>";
        }
    },
    PROFILE_LINKEDIN("Профиль LinkedIn"),
    PROFILE_GITHUB("Профиль GitHub"),
    PROFILE_STACKOVERFLOW("Профиль Stackoverflow"),
    HOME_PAGE("Домашняя страница");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    protected String toHtml0(String value) {
        return title + ": " + value;
    }

    public String toHtml(String value) {
        return (value == null) ? "" : toHtml0(value);
    }
}
