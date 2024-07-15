package com.evandro.veiculosonline.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.evandro.veiculosonline.models.User;
import com.evandro.veiculosonline.models.Vehicle;
import com.evandro.veiculosonline.repository.VehicleRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class VehicleController {

    private static String imgPath = "./src/main/resources/static/images/";

    @Autowired
    private VehicleRepository vr;

    @GetMapping("/")
    public ModelAndView allVehicles() {
        Iterable<Vehicle> allVehicles = this.vr.findAll();
        return new ModelAndView("index", "vehicles", allVehicles);
    }

    @GetMapping("/vehicles/my")
    public ModelAndView myVehicles(HttpSession session) {
        User userSession = (User)session.getAttribute("userLogin");
        Iterable<Vehicle> myVehicles = this.vr.findByOwner(userSession);
        Vehicle vehicle = new Vehicle();
        return new ModelAndView("myVehicles","vehicles", myVehicles)
        .addObject("postVehicle", vehicle);
    }

    @PostMapping("/vehicles/save")
    public String save(Vehicle vehicle, @RequestParam("file") MultipartFile file, HttpSession session) {
        User userSession = (User)session.getAttribute("userLogin");
        vehicle.setOwner(userSession);
        String priceFormat = "R$ "+vehicle.getPrice();
        vehicle.setPrice(priceFormat);
        this.vr.save(vehicle);
        try {
            if(!file.isEmpty()){
                byte[] bytes = file.getBytes();
                Path path = Paths.get(imgPath+ String.valueOf(vehicle.getId()) +file.getOriginalFilename());
                Files.write(path, bytes);
                vehicle.setImage(String.valueOf(vehicle.getId()) +file.getOriginalFilename());
                this.vr.save(vehicle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @GetMapping("/vehicles/details/{vehicleId}")
    public ModelAndView details(@PathVariable("vehicleId") Long id) {
        Optional<Vehicle> vehicle = this.vr.findById(id);
        if (vehicle.isPresent()) {
            return new ModelAndView("vehicleDetail","vehicle", vehicle.get());
        }
        return new ModelAndView("vehicleDetail");
    }

    @GetMapping("/vehicles/remove/{vehicleId}")
    public String remove(@PathVariable("vehicleId") Long id) {
        this.vr.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/vehicles/edit/{vehicleId}")
    public ModelAndView edit(@PathVariable("vehicleId") Long id) {
        Optional<Vehicle> vehicle = this.vr.findById(id);
        if (vehicle.isPresent()) {
            return new ModelAndView("vehicleEdit", "veiculo", vehicle.get());
        }
        return new ModelAndView("vehicleEdit");
    }

    @PostMapping("/vehicles/edit/save/{vehicleId}")
    public String saveEdit(Vehicle formVeiculo, @PathVariable("vehicleId") Long id) {
        Optional<Vehicle> optional = this.vr.findById(id);
        if (optional.isPresent()) {
            Vehicle veiculo = optional.get();
            veiculo.setYearVehicle(formVeiculo.getYearVehicle());
            veiculo.setColor(formVeiculo.getColor());
            veiculo.setBrand(formVeiculo.getBrand());
            veiculo.setModelVehicle(formVeiculo.getModelVehicle());
            veiculo.setPrice(formVeiculo.getPrice());
            veiculo.setTypeVehicle(formVeiculo.getTypeVehicle());
            this.vr.save(veiculo);
        }
        return "redirect:/";
    }
}
