package com.bookstore.commons.model.jpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Authority implements Serializable{

    private static final long serialVersionUID = 13154987513645846L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public enum AUTHORITIES{
        READ_AUTHORITY(1),EDIT_AUTHORITY(2),DELETE_AUTHORITY(3),CREATE_AUTHORITY(4),;
        private final Integer id;
        AUTHORITIES(Integer id) {
            this.id = id;
        }
        public Integer getValue() {
            return id;
        }
    }

    public enum AUTHORITY_NAMES{
        READ_AUTHORITY("READ_AUTHORITY"),
        CREATE_AUTHORITY("CREATE_AUTHORITY"),
        EDIT_AUTHORITY("EDIT_AUTHORITY"),
        DELETE_AUTHORITY("DELETE_AUTHORITY");

        private final String authorityName;

        private AUTHORITY_NAMES(String authorityName) {
            this.authorityName = authorityName;
        }

        public String getValue() {
            return authorityName;
        }
    }
}
