package com.filmeo.webapp.model.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Nationality extends BaseEntity {
    @Column(nullable = false, length = 100)
    private String name;

    private String flagUrl;

    @OneToMany(mappedBy = "nationality")
    private List<Human> humans = new ArrayList<Human>();
}
