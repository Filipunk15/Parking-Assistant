package cz.filipunk.parkingapp.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ParkingSpot {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String address;
	private LocalDateTime availableFrom;
	private LocalDateTime availableTo;
	private Double pricePerHour;
	private boolean reserved;

	@ManyToOne
	private User owner;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public LocalDateTime getAvailableFrom() {
		return availableFrom;
	}

	public void setAvailableFrom(LocalDateTime availableFrom) {
		this.availableFrom = availableFrom;
	}

	public LocalDateTime getAvailableTo() {
		return availableTo;
	}

	public void setAvailableTo(LocalDateTime availableTo) {
		this.availableTo = availableTo;
	}

	public Double getPricePerHour() {
		return pricePerHour;
	}

	public void setPricePerHour(Double pricePerHour) {
		this.pricePerHour = pricePerHour;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public boolean isReserved() {
		return reserved;
	}

	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}

}
