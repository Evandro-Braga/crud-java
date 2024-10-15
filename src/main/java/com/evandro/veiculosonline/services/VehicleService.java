package com.evandro.veiculosonline.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.evandro.veiculosonline.exceptions.VehicleNotFoundException;
import com.evandro.veiculosonline.models.User;
import com.evandro.veiculosonline.models.Vehicle;
import com.evandro.veiculosonline.repository.VehicleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vr;
    
    private static final String imgPath = "./src/main/resources/static/images/";

    public List<Vehicle> getAll() {
        return this.vr.findAll();
    }

    public Iterable<Vehicle> getByOwner(User user) {
        return this.vr.findByOwner(user);
    }

    public void create(Vehicle vehicle, MultipartFile file, User user) {
        vehicle.setOwner(user);
        this.vr.save(vehicle);
        try {
            if (!file.isEmpty()) {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(imgPath + String.valueOf(vehicle.getId()) + file.getOriginalFilename());
                Files.write(path, bytes);
                vehicle.setImage(String.valueOf(vehicle.getId()) + file.getOriginalFilename());
                this.vr.save(vehicle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Optional<Vehicle> getOne(Long id) {
        return Optional.ofNullable(this.vr.findById(id)
                .orElseThrow(VehicleNotFoundException::new));
    }

    public void delete(Long id) {
        this.vr.deleteById(id);
    }

    public void edit(Long id, Vehicle formVeiculo) {
        Optional<Vehicle> optional = this.vr.findById(id);
        if (optional.isPresent()) {
            Vehicle veiculo = optional.get();
            veiculo.setYearVehicle(formVeiculo.getYearVehicle());
            veiculo.setColor(formVeiculo.getColor());
            veiculo.setBrand(formVeiculo.getBrand());
            veiculo.setModelVehicle(formVeiculo.getModelVehicle());
            veiculo.setPrice(formVeiculo.getPrice());
            veiculo.setTypeVehicle(formVeiculo.getTypeVehicle());
            veiculo.setDescription(formVeiculo.getDescription());
            this.vr.save(veiculo);
        }
    }
}
