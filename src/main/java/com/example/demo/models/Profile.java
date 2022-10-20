package com.example.demo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;

    @Entity
    public class Profile {
        public Profile(String nickname, String surename, String name, String patron, String about_me, int age) {
            this.nickname = nickname;
            this.surename = surename;
            this.name = name;
            this.patron = patron;
            this.about_me = about_me;
            this.age = age;
        }

        public Profile() {
        }

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;
        @NotEmpty(message = "Поле не может быть пустым")
        @NotBlank(message = "Поле не должно состоять только из пробелов")
        @Size(min = 2, max = 40, message = "Размер данного поля должен быть в диапазоне от 2 до 40")
        private String nickname, surename, name;

        @NotEmpty(message = "Поле не может быть пустым")
        @NotBlank(message = "Поле не должно состоять только из пробелов")
        @Size(min = 2, max = 50, message = "Размер данного поля должен быть в диапазоне от 2 до 50")
        private String patron, about_me;

        @Min(value = 18, message = "Пользователь должен быть старше 18")
        @NotNull(message = "Поле не может быть пустым")
        @Positive(message = "Поле должно быть больше 0")
        private int age;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getSurename() {
            return surename;
        }

        public void setSurename(String surename) {
            this.surename = surename;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPatron() {
            return patron;
        }

        public void setPatron(String patron) {
            this.patron = patron;
        }

        public String getAbout_me() {
            return about_me;
        }

        public void setAbout_me(String about_me) {
            this.about_me = about_me;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
