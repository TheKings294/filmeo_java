package com.filmeo.webapp.model.dto.platformseries;

import com.filmeo.webapp.model.dto.platform.PlatformLightDTO;
import com.filmeo.webapp.model.dto.seri.SeriLightDTO;
import com.filmeo.webapp.model.entity.PlatformSeri;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PlatformSeriesDTO {
    private SeriLightDTO seri;
    private PlatformLightDTO platform;
    private LocalDate endDate;

    public PlatformSeriesDTO(PlatformSeri platformSeri) {
        this.seri = new SeriLightDTO(platformSeri.getSeri());
        this.platform = new PlatformLightDTO(platformSeri.getPlatform());
        this.endDate = platformSeri.getEndDate();
    }
}
