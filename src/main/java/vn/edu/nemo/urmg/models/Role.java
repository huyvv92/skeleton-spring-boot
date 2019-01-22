package vn.edu.nemo.urmg.models;

import vn.edu.nemo.core.IdEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="base_roles")
public class Role extends IdEntity {
    private String name;
    private String description;

    @ManyToMany
    @JoinTable(
            name = "base_role_privilege",
            joinColumns = @JoinColumn(name="role_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="privilege_id",referencedColumnName = "id")
    )
    private Set<Privilege> privileges;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Set<Privilege> privileges) {
        this.privileges = privileges;
    }
}
