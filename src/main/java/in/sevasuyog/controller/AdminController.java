package in.sevasuyog.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.sevasuyog.annotation.Logging;
import in.sevasuyog.model.Attribute;
import in.sevasuyog.model.Bhasha;
import in.sevasuyog.model.City;
import in.sevasuyog.model.Company;
import in.sevasuyog.model.IndianState;
import in.sevasuyog.model.request.CityRequest;
import in.sevasuyog.model.response.EntitiesResponse;
import in.sevasuyog.service.AdminService;
import in.sevasuyog.service.AttributeService;
import in.sevasuyog.service.CommonService;
import in.sevasuyog.util.CommonUtil;
import in.sevasuyog.util.GetOperation;
import in.sevasuyog.util.Operation;
import io.swagger.annotations.Api;

@Validated
@Logging
@RestController
@RequestMapping(value = "/admin")
@Api
public class AdminController {
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
    private HttpSession session;
	
	@Autowired
	private AttributeService attributeService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
    private Validator validator;
	
	/* ---- DELETE ---- */
	
	@DeleteMapping("/city") 
	public String deleteCity(
			@Valid @RequestParam @NotBlank @Size(max = 4, min = 1) 
			String guid) {
		return delete(guid, City.class);	
	}
	
	@DeleteMapping("/state") 
	public String deleteState(@RequestParam String guid) {
		return delete(guid, IndianState.class);	
	}
	
	@DeleteMapping("/company") 
	public String deleteCompany(@RequestParam String guid) {
		return delete(guid, Company.class);	
	}
	
	@DeleteMapping("/bhasha") 
	public String deleteBhasha(@RequestParam String guid) {
		return delete(guid, Bhasha.class);	
	}
	
	@DeleteMapping("/attribute") 
	public String deleteAttribute(@RequestParam String guid) {
		String response = delete(guid, Attribute.class);
		attributeService.refresh();
		return response;
	}
	
	/* ---- POST ---- */

	@PostMapping("/city") 
	public String addOrUpdateCity(@RequestBody CityRequest cityRequest) {
		return new Operation() {
			@Override protected void operate() {
				Set<ConstraintViolation<CityRequest>> a = validator.validate(cityRequest);
				if(!a.isEmpty()) {
					throw new ConstraintViolationException(a);
				}
				adminService.addOrUpdateCity(cityRequest);
			}
		}.execute(commonUtil, session);
	}
	
	@PostMapping("/state") 
	public String addOrUpdateState(@RequestBody IndianState state) {
		return addOrUpdate(state);
	}
	
	@PostMapping("/company") 
	public String addOrUpdateCompany(@RequestBody Company companyRequest) {
		return addOrUpdate(companyRequest);
	}
	
	@PostMapping("/bhasha") 
	public String addOrUpdateBhasha(@RequestBody Bhasha bhashaRequest) {
		return addOrUpdate(bhashaRequest);
	}
	
	@PostMapping("/attribute") 
	public String addOrUpdateAttribute(@RequestBody Attribute attributeRequest) {
		String response = addOrUpdate(attributeRequest);
		attributeService.refresh();
		return response;
	}
	
	/* ---- GET ---- */
	
	@GetMapping("/city") 
	public EntitiesResponse<City> getCities(@RequestParam String stateGuid) {
		return new GetOperation<City>(commonUtil, session) {
			@Override protected List<City> fetch() {
				return adminService.getCities(stateGuid);
			}
		}.getEntities();
	}
	
	@GetMapping("/state") 
	public EntitiesResponse<IndianState> getStates() {
		return getEntities(IndianState.class);
	}
	
	@GetMapping("/company") 
	public EntitiesResponse<Company> getCompanies() {
		return getEntities(Company.class);
	}
	
	@GetMapping("/bhasha") 
	public EntitiesResponse<Bhasha> getBhashayen() {
		return getEntities(Bhasha.class);
	}
	
	@GetMapping("/attribute") 
	public EntitiesResponse<Attribute> getAttributes() {
		return new GetOperation<Attribute>(commonUtil, session) {
			@Override protected List<Attribute> fetch() {
				return attributeService.getAttributes();
			}
		}.getEntities();
	}

	@PostMapping("/refreshApp") 
	public String refreshApp() {
		return new Operation() {
			@Override protected void operate() {
				attributeService.refresh();
			}
		}.execute(commonUtil, session);
	}
	
	private String delete(String guid, Class<?> class1) {
		return new Operation() {
			@Override protected void operate() {
				commonService.delete(guid, class1);
			}
		}.execute(commonUtil, session);
	}
	
	private <T> String addOrUpdate(@RequestBody T request) {
		return new Operation() {
			@Override protected void operate() {
				commonService.addOrUpdate(request);
			}
		}.execute(commonUtil, session);
	}
	
	private <T> EntitiesResponse<T> getEntities(Class<T> clazz) {
		return new GetOperation<T>(commonUtil, session) {
			@Override protected List<T> fetch() {
				return commonService.getObjectList(clazz);
			}
		}.getEntities();
	}
}
