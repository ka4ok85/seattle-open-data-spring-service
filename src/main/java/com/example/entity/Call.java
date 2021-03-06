package com.example.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.rest.core.annotation.RestResource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "calls")
public class Call {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String address;

	@Column(name = "incident_type")
	private String type;

	@Column(name = "incident_number", unique = true)
	private String incidentNumber;

	private float latitude;

	private float longitude;

	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	private LocalDateTime datetime;

	@JsonIgnore
	@RestResource(exported = false)
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "incident_number", referencedColumnName = "incident_number", insertable = false, updatable = false)
	private WeatherRecord weatherRecord;

	public Call() {
	}

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIncidentNumber() {
		return incidentNumber;
	}

	public void setIncidentNumber(String incidentNumber) {
		this.incidentNumber = incidentNumber;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public LocalDateTime getDatetime() {
		return datetime;
	}

	public void setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
	}

	public WeatherRecord getWeatherRecord() {
		return weatherRecord;
	}

	public void setWeatherRecord(WeatherRecord weatherRecord) {
		this.weatherRecord = weatherRecord;
	}

	@Override
	public String toString() {
		return "Call [id=" + id + ", address=" + address + ", type=" + type + ", incidentNumber=" + incidentNumber
				+ ", latitude=" + latitude + ", longitude=" + longitude + ", datetime=" + datetime + ", weatherRecord="
				+ weatherRecord + "]";
	}

}
