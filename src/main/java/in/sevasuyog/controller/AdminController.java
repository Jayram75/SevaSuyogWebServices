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
import in.sevasuyog.model.response.AttributesResponse;
import in.sevasuyog.model.response.BhashaResponse;
import in.sevasuyog.model.response.CompanyResponse;
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
		try {
			if(!commonUtil.isOperationAllowed(session, Role.ADMIN)) {
				return ResponseMessage.OPERATION_NOT_ALLOWED.name();
			}
			commonService.delete(guid, Company.class);
			return ResponseMessage.SUCCESSFUL.name();
		} catch(ConstraintViolationException e) {
			ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
			return violation.getPropertyPath() + " " + violation.getMessage();
		} catch (Exception e) {
			LOGGER.error(e);
			return ResponseMessage.SOMETHING_WENT_WRONG.name();
		}
	}
	
	@PostMapping("/company") 
	public String addOrUpdateCompany(@RequestBody Company companyRequest) {
		try {
			if(!commonUtil.isOperationAllowed(session, Role.ADMIN)) {
				return ResponseMessage.OPERATION_NOT_ALLOWED.name();
			}
			commonService.addOrUpdate(companyRequest);
			return ResponseMessage.SUCCESSFUL.name();
		} catch(ConstraintViolationException e) {
			ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
			return violation.getPropertyPath() + " " + violation.getMessage();
		} catch (Exception e) {
			LOGGER.error(e);
			return ResponseMessage.SOMETHING_WENT_WRONG.name();
		}
	}
	
	@GetMapping("/company") 
	public CompanyResponse getCompanies() {
		CompanyResponse companyResponse = new CompanyResponse();
		try {
			if(!commonUtil.isOperationAllowed(session, Role.ADMIN)) {
				companyResponse.setMessage(ResponseMessage.OPERATION_NOT_ALLOWED);
				return companyResponse;
			}
			companyResponse.setMessage(ResponseMessage.SUCCESSFUL);
			companyResponse.setCompanies(commonService.getObjectList(Company.class));
		} catch (Exception e) {
			LOGGER.error(e);
			companyResponse.setMessage(ResponseMessage.SOMETHING_WENT_WRONG);
		}
		return companyResponse;
	}
	
	//Bhasha
	@DeleteMapping("/bhasha") 
	public String deleteBhasha(@RequestParam String guid) {
		try {
			if(!commonUtil.isOperationAllowed(session, Role.ADMIN)) {
				return ResponseMessage.OPERATION_NOT_ALLOWED.name();
			}
			commonService.delete(guid, Bhasha.class);
			return ResponseMessage.SUCCESSFUL.name();
		} catch(ConstraintViolationException e) {
			ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
			return violation.getPropertyPath() + " " + violation.getMessage();
		} catch (Exception e) {
			LOGGER.error(e);
			return ResponseMessage.SOMETHING_WENT_WRONG.name();
		}
	}
	
	@PostMapping("/bhasha") 
	public String addOrUpdateBhasha(@RequestBody Bhasha bhashaRequest) {
		try {
			if(!commonUtil.isOperationAllowed(session, Role.ADMIN)) {
				return ResponseMessage.OPERATION_NOT_ALLOWED.name();
			}
			commonService.addOrUpdate(bhashaRequest);
			return ResponseMessage.SUCCESSFUL.name();
		} catch(ConstraintViolationException e) {
			ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
			return violation.getPropertyPath() + " " + violation.getMessage();
		} catch (Exception e) {
			LOGGER.error(e);
			return ResponseMessage.SOMETHING_WENT_WRONG.name();
		}
	}
	
	@GetMapping("/bhasha") 
	public BhashaResponse getBhashayen() {
		BhashaResponse bhashaResponse = new BhashaResponse();
		try {
			if(!commonUtil.isOperationAllowed(session, Role.ADMIN)) {
				bhashaResponse.setMessage(ResponseMessage.OPERATION_NOT_ALLOWED);
				return bhashaResponse;
			}
			bhashaResponse.setMessage(ResponseMessage.SUCCESSFUL);
			bhashaResponse.setBhashayen(commonService.getObjectList(Bhasha.class));
		} catch (Exception e) {
			LOGGER.error(e);
			bhashaResponse.setMessage(ResponseMessage.SOMETHING_WENT_WRONG);
		}
		return bhashaResponse;
	}
	
	//Attributes
	@DeleteMapping("/attribute") 
	public String deleteAttribute(@RequestParam String guid) {
		try {
			if(!commonUtil.isOperationAllowed(session, Role.ADMIN)) {
				return ResponseMessage.OPERATION_NOT_ALLOWED.name();
			}
			attributeService.delete(guid);
			return ResponseMessage.SUCCESSFUL.name();
		} catch(ConstraintViolationException e) {
			ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
			return violation.getPropertyPath() + " " + violation.getMessage();
		} catch (Exception e) {
			LOGGER.error(e);
			return ResponseMessage.SOMETHING_WENT_WRONG.name();
		}
	}
	
	@PostMapping("/attribute") 
	public String addOrUpdateAttribute(@RequestBody Attribute attributeRequest) {
		try {
			if(!commonUtil.isOperationAllowed(session, Role.ADMIN)) {
				return ResponseMessage.OPERATION_NOT_ALLOWED.name();
			}
			attributeService.addOrUpdate(attributeRequest);
			return ResponseMessage.SUCCESSFUL.name();
		} catch(ConstraintViolationException e) {
			ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
			return violation.getPropertyPath() + " " + violation.getMessage();
		} catch (Exception e) {
			LOGGER.error(e);
			return ResponseMessage.SOMETHING_WENT_WRONG.name();
		}
	}
	
	@GetMapping("/attribute") 
	public AttributesResponse getAttributes() {
		AttributesResponse attributesResponse = new AttributesResponse();
		try {
			if(!commonUtil.isOperationAllowed(session, Role.ADMIN)) {
				attributesResponse.setMessage(ResponseMessage.OPERATION_NOT_ALLOWED);
				return attributesResponse;
			}
			attributesResponse.setMessage(ResponseMessage.SUCCESSFUL);
			attributesResponse.setAttributes(AttributeService.attributes);
		} catch (Exception e) {
			LOGGER.error(e);
			attributesResponse.setMessage(ResponseMessage.SOMETHING_WENT_WRONG);
		}
		return attributesResponse;
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
}
