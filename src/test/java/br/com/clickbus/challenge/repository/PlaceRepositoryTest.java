package br.com.clickbus.challenge.repository;


import br.com.clickbus.challenge.entity.Place;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
@Disabled
class PlaceRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private PlaceRepository repository;

    private Place place;

    @BeforeEach
    void setUp() {
        place = Place.of("Butanta", "bt", "Sao Paulo", "SP");
        testEntityManager.persist(place);
    }

    @Test
    void whenFindAllReturnAllItems() {
        List<Place> actual = repository.findAll();

        assertEquals("Butanta", actual.get(0).getName());
    }

    @Test
    void whenFindByIdReturnPlace() {
        Place placeSaved = repository.save(place);
        Optional<Place> actual = repository.findById(placeSaved.getId());

        assertTrue(actual.isPresent());
    }

    @Test
    void whenFindByIdReturnEmpty() {
        Optional<Place> actual = repository.findById(100L);

        assertFalse(actual.isPresent());
    }


    @Test
    void whenFindByNameReturnEmpty() {
        List<Place> places = repository.findByName("Cotia");
        assertTrue(places.isEmpty());
    }

    @Test
    void whenFindByNameReturnPlaces() {
        List<Place> places = repository.findByName("Butanta");

        assertFalse(places.isEmpty());
    }


    @Test
    void whenTryToSavePlaceWithAttributesNull(){
        Place place = new Place();

        Assertions.assertThrows(ConstraintViolationException.class, ()->{
            repository.save(place);
        });
    }

    @Test
    void whenSaveOk(){
        Place actual = repository.save(this.place);

        //assertNotNull(actual.getId());
        assertEquals(place.getName(), actual.getName());
        assertEquals(place.getSlug(), actual.getSlug());
        assertEquals(place.getState(), actual.getState());
        assertEquals(place.getCreatedAt(), actual.getCreatedAt());
        assertNull(actual.getUpdatedAt());
    }
}
