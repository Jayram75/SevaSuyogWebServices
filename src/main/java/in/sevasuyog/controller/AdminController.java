package in.sevasuyog.controller;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.sevasuyog.annotation.Logging;
import in.sevasuyog.model.Attribute;
import in.sevasuyog.model.Bhasha;
import in.sevasuyog.model.City;
import in.sevasuyog.model.Company;
import in.sevasuyog.model.IndianState;
import in.sevasuyog.model.MySession;
import in.sevasuyog.model.User;
import in.sevasuyog.model.enums.AttributeName;
import in.sevasuyog.model.enums.ResponseMessage;
import in.sevasuyog.model.request.CityRequest;
import in.sevasuyog.model.response.EntitiesResponse;
import in.sevasuyog.service.AdminService;
import in.sevasuyog.service.AttributeService;
import in.sevasuyog.service.CommonService;
import in.sevasuyog.service.SessionRegistry;
import in.sevasuyog.service.UserService;
import in.sevasuyog.util.CommonUtil;
import in.sevasuyog.util.GetOperation;
import io.swagger.annotations.Api;

@Logging
@RestController
@RequestMapping(value = "/admin")
@Api
public class AdminController {
	@Autowired
    private HttpSession session;
	
	@Autowired
	private AttributeService attributeService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private AdminService adminService;

	@Autowired
	private UserService userService;

	@Autowired
	public CommonUtil commonUtil;
	
	@Autowired
	public SessionRegistry sessionRegistry;
	
	@Autowired
	public ObjectMapper objectMapper;
	
	@PostMapping("/block") 
	public String blockUser(
			@Valid @RequestParam @NotBlank @Size(max = 50, min = 1) 
			String username) {
		commonUtil.isOperationAllowed(session);
		User user = userService.loadUserByUsername(username);
		attributeService.setValue(user, AttributeName.BLOCKED, "true");
		logout(user);
		return ResponseMessage.SUCCESSFUL.name();
	}

	@PostMapping("/unblock") 
	public String unblockUser(
			@Valid @RequestParam @NotBlank @Size(max = 50, min = 1) 
			String username) {
		commonUtil.isOperationAllowed(session);
		User user = userService.loadUserByUsername(username);
		attributeService.setValue(user, AttributeName.BLOCKED, "false");
		return ResponseMessage.SUCCESSFUL.name();
	}
	
	@PostMapping("/verify") 
	public String verifyUser(
			@Valid @RequestParam @NotBlank @Size(max = 50, min = 1) 
			String username) {
		commonUtil.isOperationAllowed(session);
		User user = userService.loadUserByUsername(username);
		attributeService.setValue(user, AttributeName.VERIFIED, "true");
		return ResponseMessage.SUCCESSFUL.name();
	}
	
	@PostMapping("/unverify") 
	public String unverifyUser(
			@Valid @RequestParam @NotBlank @Size(max = 50, min = 1) 
			String username) {
		commonUtil.isOperationAllowed(session);
		User user = userService.loadUserByUsername(username);
		attributeService.setValue(user, AttributeName.VERIFIED, "false");
		logout(user);
		return ResponseMessage.SUCCESSFUL.name();
	}
	
	/* ---- DELETE ---- */
	
	@DeleteMapping("/city") 
	public String deleteCity(
			@Valid @RequestParam @NotBlank @Size(max = 4, min = 1) 
			String guid) {
		return delete(guid, City.class);	
	}
	
	@DeleteMapping("/state") 
	public String deleteState(
			@Valid @RequestParam @NotBlank @Size(max = 3, min = 1) 
			String guid) {
		return delete(guid, IndianState.class);	
	}
	
	@DeleteMapping("/company") 
	public String deleteCompany(
			@Valid @RequestParam @NotBlank @Size(max = 3, min = 1) 
			String guid) {
		return delete(guid, Company.class);	
	}
	
	@DeleteMapping("/bhasha") 
	public String deleteBhasha(
			@Valid @RequestParam @NotBlank @Size(max = 3, min = 1) 
			String guid) {
		return delete(guid, Bhasha.class);	
	}
	
	@DeleteMapping("/attribute") 
	public String deleteAttribute(
			@Valid @RequestParam @NotBlank @Size(max = 3, min = 1) 
			String guid) {
		String response = delete(guid, Attribute.class);
		attributeService.refresh();
		return response;
	}
	
	/* ---- POST ---- */

	@PostMapping("/city") 
	public String addOrUpdateCity(@RequestBody CityRequest cityRequest) {
		commonUtil.isOperationAllowed(session);
		commonUtil.validate(cityRequest);
		adminService.addOrUpdateCity(cityRequest);
		return ResponseMessage.SUCCESSFUL.name();
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
		return new MyGetOperation<City>() {
			@Override protected List<City> fetch(Class<City> clazz) {
				return adminService.getCities(stateGuid);
			}
		}.getEntities(City.class);
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
		return new MyGetOperation<Attribute>() {
			@Override protected List<Attribute> fetch(Class<Attribute> clazz) {
				return attributeService.getAttributes();
			}
		}.getEntities(Attribute.class);
	}

	@PostMapping("/refreshApp") 
	public String refreshApp() {
		commonUtil.isOperationAllowed(session);
		attributeService.refresh();
		return ResponseMessage.SUCCESSFUL.name();
	}
	
	private String delete(String guid, Class<?> clazz) {
		commonUtil.isOperationAllowed(session);
		commonService.delete(guid, clazz);
		return ResponseMessage.SUCCESSFUL.name();
	}
	
	private <T> String addOrUpdate(@RequestBody T request) {
		commonUtil.isOperationAllowed(session);
		commonUtil.validate(request);
		commonService.addOrUpdate(request);
		return ResponseMessage.SUCCESSFUL.name();
	}
	
	private <T> EntitiesResponse<T> getEntities(Class<T> clazz) {
		return new MyGetOperation<T>().getEntities(clazz);
	}
	
	private void logout(User user) {
		Collection<MySession> usersSessions = sessionRegistry.getAllSessions(user.getId());
		usersSessions.forEach((session) -> {
			sessionRegistry.expireNow(session);
	    });
	}
	
	class MyGetOperation<T> extends GetOperation<T> {
		public MyGetOperation() {
			commonUtil.isOperationAllowed(session);
		}
		@Override
		protected List<T> fetch(Class<T> clazz) {
			return commonService.getObjectList(clazz);
		}
	}
}

