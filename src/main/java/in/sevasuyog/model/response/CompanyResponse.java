package in.sevasuyog.model.response;

import java.util.List;

import in.sevasuyog.model.Company;
import in.sevasuyog.model.enums.ResponseMessage;

public class CompanyResponse {
	private ResponseMessage message;
	private List<Company>  companies;
	
	public ResponseMessage getMessage() {
		return message;
	}
	public void setMessage(ResponseMessage message) {
		this.message = message;
	}
	public List<Company> getCompanies() {
		return companies;
	}
	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}
}
