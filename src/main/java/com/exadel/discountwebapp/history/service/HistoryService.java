package com.exadel.discountwebapp.history.service;

import com.exadel.discountwebapp.history.enums.OperationsEnum;
import com.exadel.discountwebapp.history.mapper.HistoryMapper;
import com.exadel.discountwebapp.history.vo.HistoryRequestVO;
import com.exadel.discountwebapp.history.vo.HistoryResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.javers.core.Javers;
import org.javers.repository.jql.QueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService {
    private final Javers javers;
    private final HistoryMapper historyMapper;

    @SneakyThrows
    public List<HistoryResponseVO> getHistory(HistoryRequestVO request) {

        OperationsEnum[] operation = OperationsEnum.values();

        List<HistoryResponseVO> rez = null;

        for (OperationsEnum operations : operation) {

            switch (operations) {
                case CHANGES:
                    var clazz = Class.forName(request.getEntity().getClazz);

                    var query = QueryBuilder.byInstanceId(request.getId(), clazz).build();

                    var changes = javers.findChanges(query);

                    rez = historyMapper.toVO(changes, request);
            }
        }
        return rez;
    }
}
