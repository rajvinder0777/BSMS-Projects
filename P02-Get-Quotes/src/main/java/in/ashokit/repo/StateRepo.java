package in.ashokit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.ashokit.entity.CountryEntity;
import in.ashokit.entity.StateEntity;

public interface StateRepo extends JpaRepository<StateEntity, Integer> {
	
	public List<StateEntity> findByCountry(CountryEntity country);
	
}
