package in.sevasuyog.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.sevasuyog.database.CommonDB;
import in.sevasuyog.model.Attribute;
import in.sevasuyog.model.User;
import in.sevasuyog.model.UserAttribute;
import in.sevasuyog.model.enums.AttributeName;

@Service
public class AttributeService {
	private static List<Attribute> attributes;
	
	@Autowired
	private CommonDB commonDB;
	
	public void init() {
		refresh();
	}

	public void refresh() {
		attributes = commonDB.fetchAll(Attribute.class);
	}
	
	public List<Attribute> getAttributes() {
		return attributes;
	}

	public boolean isTrue(Set<UserAttribute> userAttributes, AttributeName attributeName) {
		String attributeGUID = attributeName.getGUID();
		Attribute attribute = null;
		for(Attribute attr: attributes) {
			if(attr.getGuid().equalsIgnoreCase(attributeGUID)) {
				attribute = attr;
				break;
			}
		}
		if(attribute == null) {
			throw new UnsupportedOperationException(
				String.format("There is no attribute with GUID \"%s\"", attributeGUID)
			);
		}
		
		Boolean attributeValue = getBooleanValue(attribute.getDefaultValue());
		for(UserAttribute userAttribute: userAttributes) {
			if(userAttribute.getAttributeId().longValue() == attribute.getId().longValue()) {
				Boolean attrValue = getBooleanValue(userAttribute.getAttributeValue());
				if(attrValue != null) {
					attributeValue = attrValue;
				}
			}
		}
		
		if(attributeValue == null) {
			throw new UnsupportedOperationException(
				String.format("Incorrect default value set for attribute with GUID \"%s\"", attributeGUID)
			);
		}
		
		return attributeValue;
	}

	private Boolean getBooleanValue(String string) {
		if(string == null || string.isBlank()) {
			return null;
		}
		if(string.trim().equalsIgnoreCase("true")) return true;
		if(string.trim().equalsIgnoreCase("false")) return false;
		return null;
	}

	public void setValue(User user, AttributeName attributeName, String value) {
		Attribute attribute = null;
		for(Attribute attr: attributes) {
			if(attr.getGuid().equalsIgnoreCase(attributeName.getGUID())) {
				attribute = attr;
				break;
			}
		}
		
		for(UserAttribute userAttribute: user.getUserAttributes()) {
			if(userAttribute.getAttributeId().longValue() == attribute.getId().longValue()) {
				userAttribute.setAttributeValue(value.trim());
				commonDB.update(userAttribute);
				return;
			}
		}
		
		UserAttribute userAttribute = new UserAttribute();
		userAttribute.setUserId(user.getId());
		userAttribute.setAttributeId(attribute.getId());
		userAttribute.setAttributeValue(value.trim());
		commonDB.save(userAttribute);
	}
}
