package cz.filipunk.parkingapp.repository;

import cz.filipunk.parkingapp.entity.ParkingSpot;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {
	 List<ParkingSpot> findByAddressContaining(String address);
	 Optional<ParkingSpot> findById(Long id);
	 List<ParkingSpot> findByReserved(boolean reserved);
}