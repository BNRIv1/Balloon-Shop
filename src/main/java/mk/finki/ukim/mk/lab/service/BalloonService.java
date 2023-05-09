package mk.finki.ukim.mk.lab.service;

import mk.finki.ukim.mk.lab.model.Balloon;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface BalloonService{

    List<Balloon> listAll();
    List<Balloon> searchByNameOrDescription(String text);

    Optional<Balloon> save (Long id, String name, String description, Long manufacturerId);

    void deleteById (Long id);

    Optional<Balloon> findById (Long id);

}
