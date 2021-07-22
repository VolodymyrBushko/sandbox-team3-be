package com.exadel.discountwebapp.history.mapper;

import com.exadel.discountwebapp.history.vo.AuditRequestVO;
import com.exadel.discountwebapp.history.vo.AuditResponseVO;
import org.javers.core.Changes;
import org.javers.core.diff.changetype.ValueChange;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuditMapper {
    public List<AuditResponseVO> toVO(Changes changes, AuditRequestVO auditRequestVO) {

        List<AuditResponseVO> auditResponseVOs  = new ArrayList<>();

        final List<ValueChange> changesList = changes.getChangesByType(ValueChange.class);

        for (ValueChange change : changesList) {
            var auditableResponse = AuditResponseVO.builder()
                    .author(change.getCommitMetadata().get().getAuthor())
                    .entity(auditRequestVO.getEntity())
                    .id(change.getAffectedLocalId())
                    .property(change.getPropertyName())
                    .changeType(change.getChangeType())
                    .was(change.getLeft())
                    .became(change.getRight())
                    .commitDate(change.getCommitMetadata().get().getCommitDate())
                    .build();

            auditResponseVOs .add(auditableResponse);
        }
        return auditResponseVOs ;
    }
}
