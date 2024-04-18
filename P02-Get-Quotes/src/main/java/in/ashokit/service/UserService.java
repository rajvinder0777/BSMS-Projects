package in.ashokit.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import in.ashokit.dto.LoginDto;
import in.ashokit.dto.RegisterDto;
import in.ashokit.dto.ResetPwdDto;
import in.ashokit.dto.UserDto;
import in.ashokit.entity.CountryEntity;
import in.ashokit.entity.StateEntity;

@Service
public interface UserService {

	public Map<Integer,String> getCountries();
	
	public Map<Integer,String> getStates(CountryEntity cid);
	
	public Map<Integer,String> getCities(StateEntity sid);
	
	public UserDto getUser(String email);
	
	public boolean registerUser(RegisterDto regDto);
	
	public UserDto getUser (LoginDto loginDto);
	
	public boolean resetPwd (ResetPwdDto resetDto);
	
	public String getQuote();
	
	public String generateRandom();

	
	
}
