package app.tently.tentlyappbackend.services;

import app.tently.tentlyappbackend.models.Spot;
import app.tently.tentlyappbackend.modelsDTO.SpotDTO;
import app.tently.tentlyappbackend.repos.SpotRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpotService {

    private final SpotRepo spotRepo;
    private final ModelMapper modelMapper;


    public SpotService(SpotRepo spotRepo, ModelMapper modelMapper) {
        this.spotRepo = spotRepo;
        this.modelMapper = modelMapper;
    }

    public Optional<Spot> findSpot(Long id) {
        return spotRepo.findById(id);
    }

    public List<Spot> findAllSpot() {
        return spotRepo.findAll();
    }

    public Spot mapDTOToSpot(SpotDTO spotDTO) {
        return modelMapper.map(spotDTO, Spot.class);
    }

    public Optional<Spot> findSpotByName(String name) {
        return spotRepo.findByName(name);
    }

    public void saveSpot(Spot spot) {
        spotRepo.save(spot);
    }


    public void deleteSpot(Long id) {
        spotRepo.deleteById(id);
    }

    public Optional<Spot> findSpotById(Long spot_id) {
        return spotRepo.findById(spot_id);
    }

    public List<Spot> getPopularBYCountryAndRegion(String country, String region) {
        return spotRepo.getAllByCountryAndRegion(country, region);
    }
}
