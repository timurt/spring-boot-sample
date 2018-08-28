package com.backend.tasks.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Implement entity:
 * 1. Map to users
 * 2. Add equals and hashCode methods
 */
@Entity
public class Organization {
    @Id
    private Long id;
    private String name;

    /**
     * Map organization with users.
     * Use OneToMany association and map by organization field in User class.
     * Fetch lazy, cascade all
     */
    @JsonIgnore
    @OneToMany(mappedBy = "organization",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<User> users;

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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (!(obj instanceof Organization)) {
            return false;
        }

        Organization org = (Organization) obj;

        return Objects.equals(this.name, org.getName());
    }
}
