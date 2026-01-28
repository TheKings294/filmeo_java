package com.filmeo.webapp.model.formEntity;

import com.filmeo.webapp.type.SeriStatusEnum;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SeriForm {
    @NotNull
    @Max(100)
    private String title;

    private String resume;

    private String posterURL;

    @NotNull
    @Min(1)
    private Integer seasons;

    @NotNull
    @Min(1)
    private Integer episode;

    @NotNull
    private SeriStatusEnum status;

    @NotNull
    @Min(1)
    private List<Integer> genresId = new ArrayList<>();

    @NotNull
    @Min(1)
    private List<Integer> castingId = new ArrayList<>();

    @NotNull
    @Min(1)
    private List<Integer> nationalitiesId = new ArrayList<>();

    private List<PlatformSeriForm> platformSerisId = new ArrayList<>();
}
