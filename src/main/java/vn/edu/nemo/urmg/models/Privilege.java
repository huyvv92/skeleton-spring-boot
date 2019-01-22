package vn.edu.nemo.urmg.models;

import vn.edu.nemo.core.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "base_privileges")
public class Privilege extends IdEntity {
    private String name;
    private String description;

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
}
