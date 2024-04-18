package in.ashokit.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.ashokit.dto.LoginDto;
import in.ashokit.dto.QuoteDto;
import in.ashokit.dto.RegisterDto;
import in.ashokit.dto.ResetPwdDto;
import in.ashokit.dto.UserDto;
import in.ashokit.entity.CityEntity;
import in.ashokit.entity.CountryEntity;
import in.ashokit.entity.StateEntity;
import in.ashokit.entity.UserEntity;
import in.ashokit.repo.CityRepo;
import in.ashokit.repo.CountryRepo;
import in.ashokit.repo.StateRepo;
import in.ashokit.repo.UserRepo;
import in.ashokit.util.EmailUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CountryRepo countryRepo;

	@Autowired
	private StateRepo stateRepo;

	@Autowired
	private CityRepo cityRepo;

	@Autowired
	private EmailUtil emailUtils;

	@Override
	public Map<Integer, String> getCountries() {

		Map<Integer, String> countryMap = new HashMap<>();

		List<CountryEntity> countryList = countryRepo.findAll();
		countryList.forEach(c -> {
			countryMap.put(c.getCountryId(), c.getCountryName());
		});

		return countryMap;
	}

	@Override
	public Map<Integer, String> getStates(CountryEntity countryId) {

		Map<Integer, String> stateMap = new HashMap<>();

		List<StateEntity> stateList = stateRepo.findByCountry(countryId);

		stateList.forEach(c -> {
			stateMap.put(c.getStateId(), c.getStateName());
		});

		return stateMap;
	}

	@Override
	public Map<Integer, String> getCities(StateEntity stateId) {

		Map<Integer, String> cityMap = new HashMap<>();

		List<CityEntity> cityList = cityRepo.findByState(stateId);

		cityList.forEach(c -> {
			cityMap.put(c.getCityId(), c.getCityName());
		});

		return cityMap;
	}

	/*
	 * @Override public UserDto getUser(String email) {
	 * 
	 * UserEntity userEntity = userRepo.findByEmail(email);
	 * 
	 * 
	 * UserDto dto = new UserDto(); BeanUtils.copyProperties(userEntity, dto);
	 * 
	 * 
	 * 
	 * if (userEntity == null) { return null; } ModelMapper mapper = new
	 * ModelMapper(); UserDto userDto = mapper.map(userEntity, UserDto.class);
	 * 
	 * return userDto; }
	 */
	
	
	
	@Override
	public UserDto getUser(String email) {
	    UserEntity userEntity = userRepo.findByEmail(email);

	    if (userEntity == null) {
	        return null;
	    }

	    ModelMapper mapper = new ModelMapper();
	    return mapper.map(userEntity, UserDto.class);
	}
	
	@Override
	public boolean registerUser(RegisterDto regDto) {

		ModelMapper mapper = new ModelMapper();
		UserEntity entity = mapper.map(regDto, UserEntity.class);

		CountryEntity country = countryRepo.findById(regDto.getCountryId()).orElseThrow();
		StateEntity state = stateRepo.findById(regDto.getStateId()).orElseThrow();
		CityEntity city = cityRepo.findById(regDto.getCityId()).orElseThrow();

		entity.setPwd(generateRandom());
		entity.setPwdUpdated("NO");

		UserEntity savedEntity = userRepo.save(entity);

		String subject = "User Registrtation";
		String body = "Your temporary password is" + entity.getPwd();

		emailUtils.sendEmail(regDto.getEmail(), subject, body);

		return savedEntity.getUserid() != null;
	}

//	@Override
//	public UserDto login(LoginDto loginDto) {
//	}
//
//	

	@Override
	public boolean resetPwd(ResetPwdDto resetDto) {

		UserEntity userEntity = userRepo.findByEmail(resetDto.getEmail());

		if (userEntity != null) {
			userEntity.setPwd(resetDto.getNewPwd());
			userEntity.setPwdUpdated("YES");

			userRepo.save(userEntity);
			return true;
		}
		return false;
	}

	@Override
	public String getQuote() {

		QuoteDto[] quotations = null;

		String url = "https://type.fit/api/quotes  ";

		// web API call

		RestTemplate rt = new RestTemplate();
		ResponseEntity<String> forEntity = rt.getForEntity(url, String.class);

		String responseBody = forEntity.getBody();

		ObjectMapper mapper = new ObjectMapper();

		try {
			quotations = mapper.readValue(responseBody, QuoteDto[].class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Random r = new Random();
		int index = r.nextInt(quotations.length - 1);

		return quotations[index].getText();
	}

	@Override
	public String generateRandom() {
		String aToz = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random rand = new Random();
		StringBuilder res = new StringBuilder();
		for (int i = 0; i < 10; i++) {
			int randomIndex = rand.nextInt(aToz.length());
			res.append(aToz.charAt(randomIndex));

		}
		return res.toString();
	}

	@Override
	public UserDto getUser(LoginDto loginDto) {

		UserEntity userEntity = userRepo.findByEmailAndPwd(loginDto.getEmail(), loginDto.getPwd());

		if (userEntity == null) {
			return null;
		}
		ModelMapper mapper = new ModelMapper();
		return mapper.map(userEntity, UserDto.class);

	}

}
