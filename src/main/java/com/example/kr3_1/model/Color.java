package com.example.kr3_1.model;

public enum Color {
    RED("Красный"),
    GREEN("Зеленый"),
    BLUE("Синий"),
    WHITE("Белый"),
    BLACK("Черный"),
    YELLOW("Желтый"),
    ORANGE("Ораньжевый"),
    PURPLE("Фиолетовый"),
    PINK("Розовый"),
    BROWN("Коричневый"),
    GREY("Серый"),
    STRIPED("Полосатый");

    private final String translate;

    Color(String translate) {
        this.translate = translate;
    }

    public String getTranslate() {
        return translate;
    }

    @Override
    public String toString() {
        return getTranslate();
    }

}
