//package org.ru.skypro.lessons.spring.EmployeeApplication.security;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.List;
//import java.util.Objects;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//@Table(name = "authorities")
//public class Authority {
//
//    private static final Logger logger = LoggerFactory.getLogger(Authority.class);
//
//    @Id
//    @Column (name = "authority")
//    private String authority;
//
//    @OneToMany(mappedBy = "authority", orphanRemoval = true, cascade = CascadeType.ALL)
//    protected List<AuthUser> authUsers;
//
//}
