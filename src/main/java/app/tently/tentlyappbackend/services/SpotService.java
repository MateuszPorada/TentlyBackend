package app.tently.tentlyappbackend.services;

import app.tently.tentlyappbackend.models.Spot;
import app.tently.tentlyappbackend.models.SpotResponse;
import app.tently.tentlyappbackend.modelsDTO.SpotDTO;
import app.tently.tentlyappbackend.repos.SpotRepo;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
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

    public List<SpotResponse> getPopularByCountryAndRegion(String country, String region, int size) {
        List<SpotResponse> spotResponseList = mapSpotsList(country, region, size);
        spotResponseList.sort(Comparator.comparing(SpotResponse::getLikeCount).reversed());
        return spotResponseList;
    }

    public List<SpotResponse> getByCountryAndRegion(String country, String region, int size) {
        return mapSpotsList(country, region, size);
    }

    private List<SpotResponse> mapSpotsList(String country, String region, int size) {
        Pageable top = PageRequest.of(0, size);
        List<Spot> spotList = spotRepo.getAllByCountryAndRegion(country, region, top);
        List<SpotResponse> spotResponseList = new ArrayList<>();
        for (Spot spot : spotList) {
            spotResponseList.add(new SpotResponse(spot));
        }
        return spotResponseList;
    }
}
