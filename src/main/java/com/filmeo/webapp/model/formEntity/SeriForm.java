package com.filmeo.webapp.model.formEntity;

import com.filmeo.webapp.model.entity.Genre;
import com.filmeo.webapp.model.entity.Human;
import com.filmeo.webapp.model.entity.Nationality;
import com.filmeo.webapp.model.entity.PlatformSeri;
import com.filmeo.webapp.type.SeriStatusEnum;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

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
    private List<Genre> genres;

    @NotNull
    @Min(1)
    private List<Human> casting;

    @NotNull
    @Min(1)
    private List<Nationality> nationalities;

    private List<PlatformSeri> platformSeris;
}
