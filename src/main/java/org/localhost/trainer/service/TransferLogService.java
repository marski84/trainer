package org.localhost.trainer.service;

import org.localhost.trainer.model.TransferLog;
import org.localhost.trainer.model.enumeration.TransferStatus;
import org.localhost.trainer.repository.TransferLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferLogService {
    private final TransferLogRepository transferLogRepository;

    public TransferLogService(TransferLogRepository transferLogRepository) {
        this.transferLogRepository = transferLogRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logError(String msg) {
        transferLogRepository.save(new TransferLog(msg, TransferStatus.FAILURE));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void logSuccess(String msg) {
        transferLogRepository.save(new TransferLog(msg, TransferStatus.SUCCESS));
    }
}
