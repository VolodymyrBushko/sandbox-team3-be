package com.exadel.discountwebapp.history.mapper;

import com.exadel.discountwebapp.history.vo.HistoryRequestVO;
import com.exadel.discountwebapp.history.vo.HistoryResponseVO;
import org.javers.core.Changes;
import org.javers.core.diff.changetype.ValueChange;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HistoryMapper {
    private final HistoryResponseVO historyResponseVO = new HistoryResponseVO();

    public List<HistoryResponseVO> toVO(Changes changes, HistoryRequestVO historyRequestVO) {

        ArrayList<HistoryResponseVO> build = new ArrayList<>();

        for (var i = 0; i < changes.size(); i++) {
            var bu = HistoryResponseVO.builder()
                    .author(changes.getChangesByType(ValueChange.class).get(i).getCommitMetadata().get().getAuthor())
                    .entity(historyRequestVO.getEntity())
                    .id(changes.getChangesByType(ValueChange.class).get(i).getAffectedLocalId())
                    .property(changes.getChangesByType(ValueChange.class).get(i).getPropertyName())
                    .changeType(changes.getChangesByType(ValueChange.class).get(i).getChangeType())
                    .was(changes.getChangesByType(ValueChange.class).get(i).getLeft())
                    .became(changes.getChangesByType(ValueChange.class).get(i).getRight())
                    .commitDate(changes.getChangesByType(ValueChange.class).get(i).getCommitMetadata().get().getCommitDate())
                    .build();

            build.add(bu);
        }
        return build;
    }
}
