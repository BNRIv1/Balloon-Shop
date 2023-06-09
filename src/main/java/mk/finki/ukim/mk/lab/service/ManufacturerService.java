package mk.finki.ukim.mk.lab.service;

import mk.finki.ukim.mk.lab.model.Manufacturer;

import java.util.List;
import java.util.Optional;

public interface ManufacturerService {
    List<Manufacturer> listAll();
    Optional<Manufacturer> findById(long id);

    Optional<Manufacturer> save(String name, String address, String country);
}
