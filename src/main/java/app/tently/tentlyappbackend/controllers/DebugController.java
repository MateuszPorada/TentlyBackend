package app.tently.tentlyappbackend.controllers;

import app.tently.tentlyappbackend.models.CountryModel;
import app.tently.tentlyappbackend.models.RegionModel;
import app.tently.tentlyappbackend.models.Spot;
import app.tently.tentlyappbackend.models.User;
import app.tently.tentlyappbackend.modelsDTO.SpotDTO;
import app.tently.tentlyappbackend.modelsDTO.UserDTO;
import app.tently.tentlyappbackend.services.SpotService;
import app.tently.tentlyappbackend.services.UserService;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@RestController
public class DebugController {
    private final UserService userService;
    private final SpotService spotService;

    public DebugController(UserService userService, SpotService spotService) {
        this.userService = userService;
        this.spotService = spotService;
    }

    @GetMapping(value = "generate/users")
    public void generateUsers() {
        UserDTO userDTO = new UserDTO("mateusz6797@interia.pl", "Kappa123", "asonix", "Poland", "Podkarpackie");
        UserDTO userDTO1 = new UserDTO("costam2@gmail.com", "COstam2", "costam2", "Poland", "Podkarpackie");
        userService.saveUser(userService.mapDTOToUser(userDTO));
        userService.saveUser(userService.mapDTOToUser(userDTO1));
    }

    @GetMapping(value = "generate/spots")
    public void generateSpots() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("C:\\ProjektySpringBoot\\TentlyAppBackend\\src\\main\\resources\\static\\sample.json"));
//        System.out.println(bufferedReader.read());
        Optional<User> user = userService.findUser(1L);
        CountryModel[] countryModelArray = new Gson().fromJson(bufferedReader, CountryModel[].class);

        if (user.isPresent()) {
            for (CountryModel countryModel : countryModelArray) {
                for (RegionModel regionModel : countryModel.getRegions()) {
                    for (int i = 0; i < 5; i++) {
                        String[] urls = {"http://77.55.194.14:6060/image/217d592b-a852-460c-8e48-949b21764f1a"};
                        Spot spot = spotService.mapDTOToSpot(new SpotDTO(regionModel.getName() + "Dolina" + i, "Jakiś opis", countryModel.getCountryName(), regionModel.getName(), Arrays.asList(urls)));
                        spot.setUser(user.get());
                        spotService.saveSpot(spot);
                    }
                }
            }
        }

    }
}
