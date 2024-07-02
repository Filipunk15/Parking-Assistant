package cz.filipunk.parkingapp.controller;

import cz.filipunk.parkingapp.entity.ParkingSpot;
import cz.filipunk.parkingapp.entity.User;
import cz.filipunk.parkingapp.service.ParkingSpotService;
import cz.filipunk.parkingapp.service.UserService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ParkingSpotController {

	@Autowired
	private ParkingSpotService service;
	
	@Autowired
	private UserService usservice;

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("parkingSpots", service.findAll());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
			model.addAttribute("currentUser", auth.getName());
	        List<ParkingSpot> availableSpots = service.findByReserved(false);
	        model.addAttribute("parkingSpots", availableSpots);
		} else {
			model.addAttribute("currentUser", null);
	        List<ParkingSpot> availableSpots = service.findByReserved(false);
	        model.addAttribute("parkingSpots", availableSpots);
		}
		return "index";
	}

	@GetMapping("/add-spot")
	public String addSpotForm(Model model) {
		model.addAttribute("parkingSpot", new ParkingSpot());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
			model.addAttribute("currentUser", auth.getName());
		} else {
			model.addAttribute("currentUser", null);
		}
		return "add-spot";
	}

	@PostMapping("/add-spot")
	public String addSpotSubmit(@ModelAttribute ParkingSpot parkingSpot) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
	        String username = auth.getName();
	        User owner = usservice.findByUsername(username);
	        parkingSpot.setOwner(owner);
	    }
	    service.save(parkingSpot);
	    return "redirect:/";
	}

    @GetMapping("/reserve/{id}")
    public String showReserveDetails(@PathVariable("id") Long id, Model model) {
        Optional<ParkingSpot> parkingSpotOpt = service.findById(id);
        if (parkingSpotOpt.isPresent()) {
            ParkingSpot parkingSpot = parkingSpotOpt.get();
            model.addAttribute("parkingSpot", parkingSpot);
            model.addAttribute("owner", parkingSpot.getOwner());
            return "reserve-details";
        } else {
            return "error";
        }
    }

    @PostMapping("/reserve/{id}")
    public String reserveSpot(@PathVariable("id") Long id) {
        Optional<ParkingSpot> parkingSpotOpt = service.findById(id);
        if (parkingSpotOpt.isPresent()) {
            ParkingSpot parkingSpot = parkingSpotOpt.get();
            parkingSpot.setReserved(true);
            service.save(parkingSpot);
            return "redirect:/";
        } else {
            return "error";
        }
    }

	@GetMapping("/search")
	public String search(@RequestParam(value = "address", required = false) String address, Model model) {
		if (address != null) {
			model.addAttribute("results", service.searchParkingSpots(address));
		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
			model.addAttribute("currentUser", auth.getName());
		} else {
			model.addAttribute("currentUser", null);
		}
		return "search";
	}
}