package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/cars")
@Validated
public class CarController {

    @Autowired
    private CarRepository carRepository;

    @GetMapping("/add")
    public String showAddCarForm(Model model) {
        model.addAttribute("car", new Car());
        return "addCar";
    }

    @PostMapping("/add")
    public String addCar(@Valid @ModelAttribute("car") Car car, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "addCar";
        }
        try {
            carRepository.save(car);
        } catch (Exception e) {
            model.addAttribute("error", "Failed to add car. Please try again.");
            return "addCar";
        }
        return "redirect:/cars";
    }

    @GetMapping("/search")
    public String searchCars(
            @RequestParam(required = false) String marka,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) Integer poczatekProdukcji,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model modelAttribute
    ) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Car> cars;

        if (marka != null && model != null && poczatekProdukcji != null) {
            cars = carRepository.findByMarkaContainingAndModelContainingAndPoczatekProdukcji(marka, model, poczatekProdukcji, pageable);
        } else if (marka != null && model != null) {
            cars = carRepository.findByMarkaContainingAndModelContaining(marka, model, pageable);
        } else if (marka != null) {
            cars = carRepository.findByMarkaContaining(marka, pageable);
        } else {
            cars = carRepository.findAll(pageable);
        }
        modelAttribute.addAttribute("vehicles", cars.getContent());
        modelAttribute.addAttribute("currentPage", page);
        modelAttribute.addAttribute("totalPages", cars.getTotalPages());
        return "searchResults";
    }

    @GetMapping
    public String listCars(Model model) {
        List<Car> cars = carRepository.findAll();
        model.addAttribute("vehicles", cars);
        return "index";
    }

    @GetMapping("/delete/{id}")
    public String showDeleteCarForm(@PathVariable Long id, Model model) {
        Car carToDelete = carRepository.findById(id).orElse(null);

        if (carToDelete != null) {
            model.addAttribute("carToDelete", carToDelete);
            return "deleteCar";
        } else {
            return "redirect:/cars";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteCar(@PathVariable Long id) {
        try {
            carRepository.deleteById(id);
        } catch (Exception e) {
            return "redirect:/cars";
        }
        return "redirect:/cars";
    }
}
