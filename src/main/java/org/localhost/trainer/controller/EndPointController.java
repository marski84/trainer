package org.localhost.trainer.controller;

import org.localhost.trainer.service.ProductService;
import org.localhost.trainer.service.WalletTransferService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/test")
public class EndPointController {

    private final ProductService productService;
    private final WalletTransferService walletTransferService;

    public EndPointController(ProductService productService, WalletTransferService walletTransferService) {
        this.productService = productService;
        this.walletTransferService = walletTransferService;
    }

    @GetMapping
    public void test() {
        UUID productId = UUID.fromString("456420cc-e071-488b-9e2b-d6748e745c6e");
        productService.updatePrice(productId, 15000.00);
    }

    @GetMapping("/concurency")
    public String testConcurency() throws InterruptedException {
        UUID productId = UUID.fromString("456420cc-e071-488b-9e2b-d6748e745c6e");

        // Wątek 1 (Kasia)
        CompletableFuture<Void> user1 = CompletableFuture.runAsync(() -> {
            try {
                productService.concurencyExample(productId);
            } catch (Exception e) {
                System.out.println("Kasia: Błąd! " + e.getCause());
            }
        });

        // Wątek 2 (Tomek)
        CompletableFuture<Void> user2 = CompletableFuture.runAsync(() -> {
            try {
                productService.concurencyExample(productId);
            } catch (Exception e) {
                System.out.println("Tomek: Błąd! " + e.getCause());
            }
        });

        // Czekamy aż oboje skończą
        CompletableFuture.allOf(user1, user2).join();

        return "Test zakończony - sprawdź logi w konsoli!";
    }

    @GetMapping("/audit")
    public void testAudit() {
        UUID productId = UUID.fromString("456420cc-e071-488b-9e2b-d6748e745c6e");
        productService.buyProductWithAudit(productId);
    }


    @GetMapping("/transfer")
    public void testTransfer() {
        walletTransferService.transferMoney(1L, 2L, 10000.00);
        walletTransferService.transferMoney(200L, 2L, 120000.00);
    }
}
