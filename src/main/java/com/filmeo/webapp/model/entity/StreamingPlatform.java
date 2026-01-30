package com.filmeo.webapp.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class StreamingPlatform extends BaseEntity {
    @Column(nullable = false, length = 40)
    private String name;

    @Column(nullable = false)
    private String link;

    private String logoUrl;

    @OneToMany(mappedBy = "platform")
    private List<PlatformMovie> movies = new ArrayList<PlatformMovie>();

    @OneToMany(mappedBy = "platform")
    private List<PlatformSeri> series = new ArrayList<PlatformSeri>();
}
