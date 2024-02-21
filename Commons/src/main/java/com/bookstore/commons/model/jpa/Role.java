package com.bookstore.commons.model.jpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable {

    private static final long serialVersionUID = 13154987513545846L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany(fetch = FetchType.EAGER , cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "role_authority",
            joinColumns = { @JoinColumn(name = "role_id") },
            inverseJoinColumns = { @JoinColumn(name = "authority_id") }
    )
    private Set<Authority> authorities = new HashSet<>();

    public enum ROLES{
        ROLE_USER(1),ROLE_ADMIN(2);
        private final Integer id;
        ROLES(Integer id) {
            this.id = id;
        }
        public Integer getValue() {
            return id;
        }
    }

    public enum ROLES_NAMES{
        ROLE_USER("ROLE_USER"),ROLE_ADMIN("ROLE_ADMIN");

        private final String roleName;

        private ROLES_NAMES(String roleName) {
            this.roleName = roleName;
        }

        public String getValue() {
            return roleName;
        }
    }

    public void addAuthority(Authority authority){
        authorities.add(authority);
    }
}
