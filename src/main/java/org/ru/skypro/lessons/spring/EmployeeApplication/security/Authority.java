package org.ru.skypro.lessons.spring.EmployeeApplication.security;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private AuthUser user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Authority authority1)) return false;
        return Objects.equals(getAuthorityId(), authority1.getAuthorityId()) && getAuthority().equals(authority1.getAuthority());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAuthorityId(), getAuthority());
    }
}
