package cz.filipunk.parkingapp.service;

import cz.filipunk.parkingapp.entity.ParkingSpot;
import cz.filipunk.parkingapp.repository.ParkingSpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingSpotService {

    @Autowired
    private ParkingSpotRepository repository;

    public List<ParkingSpot> findAll() {
        return repository.findAll();
    }

    public void save(ParkingSpot parkingSpot) {
        repository.save(parkingSpot);
    }
    
    public List<ParkingSpot> searchParkingSpots(String address) {
        return repository.findByAddressContaining(address);
    }
    
    public Optional<ParkingSpot> reserveSpot(Long id) {
        Optional<ParkingSpot> parkingSpot = repository.findById(id);
        if (parkingSpot.isPresent() && !parkingSpot.get().isReserved()) {
            parkingSpot.get().setReserved(true);
            repository.save(parkingSpot.get());
            return parkingSpot;
        }
        return Optional.empty();
    }
    
    public Optional<ParkingSpot> findById(Long id) {
        return repository.findById(id);
    }
    
    public List<ParkingSpot> findByReserved(boolean reserved) {
        return repository.findByReserved(reserved);
    }
    
}