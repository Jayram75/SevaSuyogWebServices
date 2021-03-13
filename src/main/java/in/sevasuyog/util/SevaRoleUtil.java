package in.sevasuyog.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.sevasuyog.database.CommonDB;
import in.sevasuyog.model.SevaRole;

@Service
public class SevaRoleUtil {
private static List<SevaRole> sevaRoles;
	
	@Autowired
	private CommonDB commonDB;
	
	public void init() {
		refresh();
	}

	public void refresh() {
		sevaRoles = commonDB.fetchAll(SevaRole.class);
	}

	public SevaRole getSevaRole(String roleGUID) {
		if(roleGUID == null || roleGUID.isBlank()) {
			return null;
		}
		for(SevaRole sevaRole: sevaRoles) {
			if(sevaRole.getGuid().equalsIgnoreCase(roleGUID.trim())) {
				return sevaRole;
			}
		}
		return null;
	}
}
