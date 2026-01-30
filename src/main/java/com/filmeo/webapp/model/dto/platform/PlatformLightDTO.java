package com.filmeo.webapp.model.dto.platform;

import com.filmeo.webapp.model.entity.*;
import lombok.Data;


@Data
public class PlatformLightDTO {
    private String name;
    private String link;
    private String logoUrl;

    public PlatformLightDTO(StreamingPlatform streamingPlatform) {
        this.name = streamingPlatform.getName();
        this.link = streamingPlatform.getLink();
        this.logoUrl = streamingPlatform.getLogoUrl();
    }
}
