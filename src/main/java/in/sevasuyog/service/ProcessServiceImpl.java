package in.sevasuyog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class ProcessServiceImpl {
	@Autowired
	private SessionRegistry sessionRegistry;
	
    public void process() {
		TransactionSynchronizationManager.setActualTransactionActive(true);
        sessionRegistry.deleteAllExpiredSessions(SessionRegistry.maxInactiveInterval);
    }
}