package com.example.baseproject.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Company
 * @Discription
 * @Author guoxiaojing
 * @CreateDate 2018/1/25 15:52
 * @Version 1.0
 */
@Entity
public class PersonModel implements Serializable{
    @Id
    @GeneratedValue
    private Long id;
    @Column(length = 20)
    private String name;
    @Column(length = 20)
    private Integer age;
    @Column(length = 20)
    private String address;
    @Column(length = 20)
    private boolean isMan;

    public PersonModel(){

    }

    public PersonModel(Long id, String name, Integer age, String address) {

        super();

        this.id = id;

        this.name = name;

        this.age = age;

        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isMan() {
        return isMan;
    }

    public void setMan(boolean man) {
        isMan = man;
    }
}
