package org.localhost.trainer.service;

import jakarta.transaction.Transactional;
import org.localhost.trainer.model.Product;
import org.localhost.trainer.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final AuditService auditService;

    public ProductService(ProductRepository productRepository, AuditService auditService) {
        this.productRepository = productRepository;
        this.auditService = auditService;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Transactional
    public void updatePrice(UUID id, double price) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setPrice(price);

        throw new RuntimeException("something went wrong");
//        productRepository.save(product);
    }

    @Transactional
    public void concurencyExample(UUID id) {
        System.out.println(Thread.currentThread().getName() + " - Pobieram produkt...");
        Product product = productRepository.findById(id).orElseThrow();

        System.out.println(Thread.currentThread().getName() + " - Zasypiam na chwilę (symulacja myślenia użytkownika)...");
        try {
            Thread.sleep(2000); // 2 sekundy pauzy. W tym czasie drugi wątek też pobierze wersję X!
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + " - Próbuję zapisać...");
        product.setPrice(100000);
        // Tu następuje commit i sprawdzenie wersji
    }

    @Transactional // Domyślna propagacja (REQUIRED)
    public void buyProductWithAudit(UUID id) {
        // 1. Zapisujemy w logu, że próbowaliśmy
        auditService.logEvent("Próba zakupu produktu: " + id);

        // 2. Symulujemy błąd biznesowy
        throw new RuntimeException("Awaria płatności!");
    }
}
