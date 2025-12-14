package org.localhost.trainer.service;

import org.localhost.trainer.model.Wallet;
import org.localhost.trainer.model.enumeration.TransferStatus;
import org.localhost.trainer.repository.TransferLogRepository;
import org.localhost.trainer.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WalletTransferService {

    private final WalletRepository walletRepository;
    private final TransferLogService transferLogService;


    public WalletTransferService(WalletRepository walletRepository, TransferLogRepository transferLogRepository, TransferLogService transferLogService) {
        this.walletRepository = walletRepository;
        this.transferLogService = transferLogService;
    }

    @Transactional
    public void transferMoney(Long fromWalletId, Long toWalletId, double amount) {

        Wallet fromWallet = walletRepository.findById(fromWalletId).orElseThrow(() ->
        {
            transferLogService.saveTransferLog("Transfer from wallet " + fromWalletId + " to wallet " + toWalletId + " of amount " + amount, TransferStatus.FAILURE);
            return new RuntimeException("Wallet not found!");
        });

        Wallet toWallet = walletRepository.findById(toWalletId).orElseThrow(() ->
        {
            transferLogService.saveTransferLog("Transfer from wallet " + fromWalletId + " to wallet " + toWalletId + " of amount " + amount, TransferStatus.FAILURE);
            return new RuntimeException("Wallet not found!");
        });
        fromWallet.setBalance(fromWallet.getBalance() - amount);
        toWallet.setBalance(toWallet.getBalance() + amount);

        transferLogService.saveTransferLog("Transfer from wallet " + fromWalletId + " to wallet " + toWalletId + " of amount " + amount, TransferStatus.SUCCESS);


    }


}
