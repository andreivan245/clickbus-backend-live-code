package br.com.clickbus.challenge.service;


import br.com.clickbus.challenge.dto.PlaceDTO;
import br.com.clickbus.challenge.entity.Place;
import br.com.clickbus.challenge.repository.PlaceRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class PlaceService {

    @Autowired
    private PlaceRepository repository;

    public List<Place> findAll() {
       return repository.findAll();
    }

    public Optional<Place> findById(@NotNull Long id) {
       return repository.findById(id);
    }

    public Place save(@NotNull Place place) {
        return repository.save(place);
    }

    public List<Place> findByName(@NotNull String name) {
       return repository.findByName(name);
    }

    public Place alter(@NotNull Place place,@NotNull PlaceDTO placeDTO) {

            place.setName(placeDTO.getName());
            place.setSlug(placeDTO.getSlug());
            place.setCity(place.getCity());
            place.setState(placeDTO.getState());
            place.setUpdatedAt(LocalDateTime.now());

            return repository.save(place);
    }
}
