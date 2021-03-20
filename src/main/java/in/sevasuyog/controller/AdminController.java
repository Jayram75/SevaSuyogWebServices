package in.sevasuyog.controller;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.sevasuyog.annotation.Logging;
import in.sevasuyog.model.Attribute;
import in.sevasuyog.model.enums.ResponseMessage;
import in.sevasuyog.model.enums.Role;
import in.sevasuyog.model.response.AttributesResponse;
import in.sevasuyog.service.AttributeService;
import in.sevasuyog.util.CommonUtil;
import io.swagger.annotations.Api;

@Logging
@RestController
@RequestMapping(value = "/admin")
@Api
public class AdminController {
	private static final Logger LOGGER = LogManager.getLogger(MyController.class);
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
    private HttpSession session;
	
	@Autowired
	private AttributeService attributeService;
	
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
