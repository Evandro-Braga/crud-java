package com.evandro.veiculosonline.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.evandro.veiculosonline.exceptions.VehicleNotFoundException;
import com.evandro.veiculosonline.models.User;
import com.evandro.veiculosonline.models.Vehicle;
import com.evandro.veiculosonline.services.VehicleService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(path = {"/", "/vehicles"})
public class VehicleController {

    @Autowired
    private VehicleService service;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("vehicles", this.service.getAll());
        return "index";
    }

    @GetMapping("/my")
    public String getMy(HttpSession session, Model model) {
        User user = (User) session.getAttribute("userLogin");
        model.addAttribute("vehicles", service.getByOwner(user))
                .addAttribute("postVehicle", new Vehicle());
        return "vehicles_my";
    }

    @PostMapping("/save")
    public String save(Vehicle vehicle, @RequestParam("file") MultipartFile file, HttpSession session) {
        User user = (User) session.getAttribute("userLogin");
        service.create(vehicle, file, user);
        return "redirect:/";
    }

    @GetMapping("/details/{vehicleId}")
    public String details(@PathVariable("vehicleId") Long id, Model model) {
        Optional<Vehicle> vehicle = service.getOne(id);
        model.addAttribute("vehicle", vehicle.get());
        return "vehicle_detail";
    }

    @GetMapping("/delete/{vehicleId}")
    public String delete(@PathVariable("vehicleId") Long id) {
        service.delete(id);
        return "redirect:/";
    }

    @GetMapping("/edit/{vehicleId}")
    public String edit(@PathVariable("vehicleId") Long id, Model model) {
        Optional<Vehicle> vehicle = service.getOne(id);
        model.addAttribute("veiculo", vehicle.get());
        return "vehicle_edit";

    }

    @PostMapping("/edit/{vehicleId}")
    public String saveEdit(Vehicle formVeiculo, @PathVariable("vehicleId") Long id) {
        service.edit(id, formVeiculo);
        return "redirect:/";
    }

    @ExceptionHandler(VehicleNotFoundException.class)
    public String handleVehicleNotFoundException(Exception ex) {
        return "error";
    }
}
