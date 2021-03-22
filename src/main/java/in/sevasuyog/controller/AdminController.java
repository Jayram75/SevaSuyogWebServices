package in.sevasuyog.controller;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
import in.sevasuyog.model.Company;
import in.sevasuyog.model.enums.ResponseMessage;
import in.sevasuyog.model.enums.Role;
import in.sevasuyog.model.response.EntitiesResponse;
import in.sevasuyog.service.AttributeService;
import in.sevasuyog.service.CommonService;
import in.sevasuyog.util.CommonUtil;
import io.swagger.annotations.Api;

@Logging
@RestController
@RequestMapping(value = "/admin")
@Api
public class AdminController {
	private static final Logger LOGGER = LogManager.getLogger(AdminController.class);
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
    private HttpSession session;
	
	@Autowired
	private AttributeService attributeService;
	
	@Autowired
	private CommonService commonService;
	
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
		return getEntities(Attribute.class);
	}
	
	@PostMapping("/refreshApp") 
	public String refreshApp() {
		try {
			if(!commonUtil.isOperationAllowed(session, Role.ADMIN)) {
				return ResponseMessage.OPERATION_NOT_ALLOWED.name();
			}
			attributeService.refresh();
			return ResponseMessage.SUCCESSFUL.name();
		} catch (Exception e) {
			LOGGER.error(e);
			return ResponseMessage.SOMETHING_WENT_WRONG.name();
		}
	}
	

	
	private String delete(String guid, Class<?> class1) {
		try {
			if(!commonUtil.isOperationAllowed(session, Role.ADMIN)) {
				return ResponseMessage.OPERATION_NOT_ALLOWED.name();
			}
			commonService.delete(guid, class1);
			return ResponseMessage.SUCCESSFUL.name();
		} catch(ConstraintViolationException e) {
			ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
			return violation.getPropertyPath() + " " + violation.getMessage();
		} catch (Exception e) {
			LOGGER.error(e);
			return ResponseMessage.SOMETHING_WENT_WRONG.name();
		}
	}
	
	private <T> String addOrUpdate(@RequestBody T request) {
		try {
			if(!commonUtil.isOperationAllowed(session, Role.ADMIN)) {
				return ResponseMessage.OPERATION_NOT_ALLOWED.name();
			}
			commonService.addOrUpdate(request);
			return ResponseMessage.SUCCESSFUL.name();
		} catch(ConstraintViolationException e) {
			ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
			return violation.getPropertyPath() + " " + violation.getMessage();
		} catch (Exception e) {
			LOGGER.error(e);
			return ResponseMessage.SOMETHING_WENT_WRONG.name();
		}
	}
	
	public <T> EntitiesResponse<T> getEntities(Class<T> clazz) {
		EntitiesResponse<T> entitiesResponse = new EntitiesResponse<T>();
		try {
			if(!commonUtil.isOperationAllowed(session, Role.ADMIN)) {
				entitiesResponse.setMessage(ResponseMessage.OPERATION_NOT_ALLOWED);
				return entitiesResponse;
			}
			entitiesResponse.setMessage(ResponseMessage.SUCCESSFUL);
			entitiesResponse.setEntities(commonService.getObjectList(clazz));
		} catch (Exception e) {
			LOGGER.error(e);
			entitiesResponse.setMessage(ResponseMessage.SOMETHING_WENT_WRONG);
		}
		return entitiesResponse;
	}
}
