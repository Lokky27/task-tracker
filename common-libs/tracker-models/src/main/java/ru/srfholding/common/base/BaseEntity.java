package ru.srfholding.common.base;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    /**
     * Дата создания
     */
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;
    /**
     * Кем создано
     */
    @CreatedBy
    @Column(name = "created_by")
    private UUID createdBy;
    /**
     * Дата обновления
     */
    @LastModifiedDate
    @Column(name = "updated_at", updatable = false)
    private OffsetDateTime updatedAt;
    /**
     * Кем обновлено
     */
    @LastModifiedBy
    @Column(name = "updated_by")
    private UUID updatedBy;
    /**
     * Версия
     */
    @Version
    @Column(name = "version")
    private Integer version;
}
