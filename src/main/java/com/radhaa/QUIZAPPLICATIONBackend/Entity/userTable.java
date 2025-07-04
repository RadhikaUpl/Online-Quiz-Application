package com.radhaa.QUIZAPPLICATIONBackend.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="userEntity")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class userTable
{
    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int signInid;
    @NonNull
    @Column(unique = true)
    private String username;
    @NonNull
    @Column(unique = true)
    private String email;
    @NonNull
    private String password;

//    public int getSignInid() {
//        return signInid;
//    }
//
//    public void setSignInid(int signInid) {
//        this.signInid = signInid;
//    }

//    public userTable(){
//
//    }
    public userTable(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;

    }

//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonManagedReference(value="userTable")
    List<UserQuizPurchase> list=new ArrayList<>();

//    public List<UserQuizPurchase> getList() {
//        return list;
//    }
//
//    public void setList(List<UserQuizPurchase> list) {
//        this.list = list;
//    }

    @OneToMany(mappedBy = "userTable",cascade = CascadeType.ALL)
    @JsonManagedReference(value="user_score")
    List<ScoreStoringEntity> scores=new ArrayList<>();

}
