//package com.example.ezlearning.auth.entity;
//
//import javax.persistence.*;
//import java.util.Objects;
//
//@Entity
//@Table(name = "auth_user_group", schema = "public", catalog = "finalproject")
//public class AuthUserGroup {
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Id
//    @Column(name = "auth_user_group_id")
//    private Long authUserGroupId;
//    @Basic
//    @Column(name = "auth_group")
//    private String authGroup;
//    @Basic
//    @Column(name = "username")
//    private String username;
//
//    public Long getAuthUserGroupId() {
//        return authUserGroupId;
//    }
//
//    public void setAuthUserGroupId(Long authUserGroupId) {
//        this.authUserGroupId = authUserGroupId;
//    }
//
//    public String getAuthGroup() {
//        return authGroup;
//    }
//
//    public void setAuthGroup(String authGroup) {
//        this.authGroup = authGroup;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        AuthUserGroup that = (AuthUserGroup) o;
//        return Objects.equals(authUserGroupId, that.authUserGroupId) && Objects.equals(authGroup, that.authGroup) && Objects.equals(username, that.username);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(authUserGroupId, authGroup, username);
//    }
//}
