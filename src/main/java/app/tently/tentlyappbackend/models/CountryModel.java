package app.tently.tentlyappbackend.models;

import lombok.Data;

import java.util.List;

@Data
public class CountryModel {
    private String countryName;
    private String countryShortCode;
    private List<RegionModel> regions;
}

