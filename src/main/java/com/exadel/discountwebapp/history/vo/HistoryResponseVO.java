package com.exadel.discountwebapp.history.vo;

import com.exadel.discountwebapp.history.enums.HistoryEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.javers.core.diff.changetype.PropertyChangeType;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@Setter
@Getter
@Builder
public class HistoryResponseVO implements Serializable {
    public String author;
    public HistoryEnum entity;
    public Object id;
    public String property;
    public PropertyChangeType changeType;
    public Object was;
    public Object became;
    public LocalDateTime commitDate;
}
