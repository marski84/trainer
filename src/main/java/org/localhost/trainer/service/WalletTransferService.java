package org.localhost.trainer.service;

import lombok.extern.slf4j.Slf4j;
import org.localhost.trainer.model.Wallet;
import org.localhost.trainer.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class WalletTransferService {

    private final WalletRepository walletRepository;
    private final TransferLogService transferLogService;


    public WalletTransferService(WalletRepository walletRepository, TransferLogService transferLogService) {
        this.walletRepository = walletRepository;
        this.transferLogService = transferLogService;
    }

    @Transactional
    public void transferMoney(Long fromWalletId, Long toWalletId, double amount) {

        log.info("Transfering {} from wallet {} to wallet {}", amount, fromWalletId, toWalletId);

        String logMsg = "Transfering %s from wallet %s to wallet %s".formatted(amount, fromWalletId, toWalletId);

        try {
            Wallet fromWallet = walletRepository.findById(fromWalletId).orElseThrow(() -> new RuntimeException("Wallet not found!"));
            Wallet toWallet = walletRepository.findById(toWalletId).orElseThrow(() -> new RuntimeException("Wallet not found!"));


            if (fromWallet.getBalance() < amount) {
                throw new RuntimeException("Niewystarczające środki!");
            }

            fromWallet.setBalance(fromWallet.getBalance() - amount);
            toWallet.setBalance(toWallet.getBalance() + amount);

            transferLogService.logSuccess(logMsg);


        } catch (Exception e) {
            log.error("Error during transfer: {}", logMsg);
            transferLogService.logError(logMsg);
            throw new RuntimeException(e);
        }
    }


}
