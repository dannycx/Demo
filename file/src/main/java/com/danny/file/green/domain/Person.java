package com.danny.file.green.domain;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by danny on 18-7-11.
 */
@Entity
public class Person {

    @Id
    private Long id;
    @Property(nameInDb = "USERNAME")
    private String username;
    @Property(nameInDb = "NICKNAME")
    private String nickname;
    public String getNickname() {
        return this.nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1257518810)
    public Person(Long id, String username, String nickname) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
    }
    @Generated(hash = 1024547259)
    public Person() {
    }
}
