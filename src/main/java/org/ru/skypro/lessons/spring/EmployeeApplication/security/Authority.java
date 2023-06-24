package org.ru.skypro.lessons.spring.EmployeeApplication.security;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "authorities")
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "authority_id")
    private Long authorityId;

    @Column (name = "authority")
    private String authority;

    @OneToMany(mappedBy = "authority", orphanRemoval = true, cascade = CascadeType.ALL)
    protected List<AuthUser> authUsers;

}
