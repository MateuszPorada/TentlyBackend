package app.tently.tentlyappbackend.modelsDTO;

import lombok.Data;

import java.util.List;

@Data
public class CountryModelDTO {
    private String countryName;
    private String countryShortCode;
    private List<RegionModelDTO> regions;
}

