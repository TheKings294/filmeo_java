package com.filmeo.webapp.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class PlatformSeri {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "seri_id")
    private Seri seri;

    @ManyToOne
    @JoinColumn(name = "platform_id")
    private StreamingPlatform platform;

    @Column(nullable = false)
    private LocalDate endDate;
}
