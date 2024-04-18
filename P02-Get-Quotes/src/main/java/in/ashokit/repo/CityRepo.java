package in.ashokit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.ashokit.entity.CityEntity;
import in.ashokit.entity.StateEntity;

public interface CityRepo extends JpaRepository<CityEntity, Integer>{

	//@Query(value="select * from city_master where state=:state",nativeQuery=true)
	public List<CityEntity> findByState(StateEntity state);
}
