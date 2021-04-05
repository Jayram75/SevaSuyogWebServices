package in.sevasuyog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import in.sevasuyog.database.CommonDB;
import in.sevasuyog.model.City;
import in.sevasuyog.model.IndianState;
import in.sevasuyog.model.Locality;
import in.sevasuyog.model.request.CityRequest;
import in.sevasuyog.model.request.LocalityRequest;
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
		return commonDB.fetchAll(Strings.INDIAN_STATE_ID, stateId, City.class);
	}

	public void addOrUpdateLocality(LocalityRequest localityRequest) {
		try {
			Locality locality = commonDB.getFromGUID(localityRequest.getGuid(), Locality.class);
			if(locality == null) {
				locality = new Locality();
			}
			locality.setLocality(localityRequest.getLocality());
			locality.setGuid(localityRequest.getGuid());
			locality.setPinCode(localityRequest.getPinCode());
			
			Long cityId = commonDB.getIdFromGuid(localityRequest.getCityGuid(), City.class);
			locality.setCityId(cityId);
			
			commonDB.saveOrUpdate(locality);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException(String.format("'%s' is not a valid 'cityGuid'!", localityRequest.getCityGuid()));
		}
	}

	public List<Locality> getLocalities(String cityGuid) {
		Long cityId = commonDB.getIdFromGuid(cityGuid, City.class);
		if(cityId == null) return null;
		return commonDB.fetchAll(Strings.CITY_ID, cityId, Locality.class);
	}
}
