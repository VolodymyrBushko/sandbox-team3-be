package com.exadel.discountwebapp.history.service;

import com.exadel.discountwebapp.history.mapper.AuditMapper;
import com.exadel.discountwebapp.history.vo.AuditRequestVO;
import com.exadel.discountwebapp.history.vo.AuditResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.javers.core.Javers;
import org.javers.repository.jql.QueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditService {
    private final Javers javers;
    private final AuditMapper auditMapper;

    @SneakyThrows
    public List<AuditResponseVO> getHistory(AuditRequestVO request) {

        List<AuditResponseVO> rez = null;

        switch (request.getOperationType()) {
            case CHANGES:
                var clazz = request.getEntity().entityClass;

                var query = QueryBuilder.byInstanceId(request.getId(), clazz).build();

                var changes = javers.findChanges(query);

                rez = auditMapper.toVO(changes, request);
                break;
        }
        return rez;
    }
}
