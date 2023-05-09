package mk.finki.ukim.mk.lab.service.impl;

import mk.finki.ukim.mk.lab.model.Manufacturer;
import mk.finki.ukim.mk.lab.repository.impl.InMemoryManufacturerRepository;
import mk.finki.ukim.mk.lab.repository.jpa.ManufacturerRepository;
import mk.finki.ukim.mk.lab.service.ManufacturerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public List<Manufacturer> listAll() {
        return this.manufacturerRepository.findAll();
    }

    @Override
    public Optional<Manufacturer> findById(long id) {
        return this.manufacturerRepository.findById(id);
    }

    @Override
    public Optional<Manufacturer> save(String name, String address, String country) {
        return Optional.of(this.manufacturerRepository.save(new Manufacturer(name, country, address)));
    }
}
