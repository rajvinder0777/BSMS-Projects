package in.ashokit.dto;

import org.springframework.stereotype.Component;

import in.ashokit.entity.CityEntity;
import in.ashokit.entity.CountryEntity;
import in.ashokit.entity.StateEntity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Component
public class RegisterDto {

	private String name;

	private String email;

	private String pwd;

	private Integer countryId;

	private Long phone;

	private Integer stateId;

	private Integer cityId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	public Long getPhone() {        
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

}
