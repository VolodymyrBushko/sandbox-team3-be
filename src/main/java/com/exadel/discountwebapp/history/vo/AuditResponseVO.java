package com.exadel.discountwebapp.history.vo;

import com.exadel.discountwebapp.history.enums.AuditEntityType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.javers.core.diff.changetype.PropertyChangeType;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
public class AuditResponseVO implements Serializable {
    private String author;
    private AuditEntityType entity;
    private Object id;
    private String property;
    private PropertyChangeType changeType;
    private Object was;
    private Object became;
    private LocalDateTime commitDate;
}
