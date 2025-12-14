package org.localhost.trainer.model;

import jakarta.persistence.*;
import org.localhost.trainer.model.enumeration.TransferStatus;

@Entity
public class TransferLog {

    @Enumerated(EnumType.STRING)
    TransferStatus status;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;

    public TransferLog(String message, TransferStatus status) {
        this.message = message;
        this.status = status;
    }
}
