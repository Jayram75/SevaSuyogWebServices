package in.sevasuyog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import in.sevasuyog.database.CommonDB;
import in.sevasuyog.model.City;
import in.sevasuyog.model.IndianState;
import in.sevasuyog.model.request.CityRequest;
import in.sevasuyog.util.Strings;

@Service
public class AdminService {
	@Autowired
	private CommonDB commonDB;

	public void addOrUpdateCity(CityRequest cityRequest) {
		try {
			City city = commonDB.getFromGUID(cityRequest.getGuid(), City.class);
			if(city == null) {
				city = new City();
			}
			city.setCity(cityRequest.getCity());
			city.setGuid(cityRequest.getGuid());
			
			Long stateId = commonDB.getIdFromGuid(cityRequest.getStateGuid(), IndianState.class);
			city.setIndianStateId(stateId);
			
			commonDB.saveOrUpdate(city);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException(String.format("'%s' is not a valid 'stateGuid'!", cityRequest.getStateGuid()));
		}
	}

	public List<City> getCities(String stateGuid) {
		Long stateId = commonDB.getIdFromGuid(stateGuid, IndianState.class);
		if(stateId == null) return null;
		return commonDB.fetchAll(City.class, Strings.INDIAN_STATE_ID, stateId);
	}
	
	
}
