package com.exadel.discountwebapp.history.controller;

import com.exadel.discountwebapp.history.entityclassenum.HistoryEnum;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.EnumUtils;
import org.javers.core.Javers;
import org.javers.core.metamodel.object.CdoSnapshot;
import org.javers.repository.jql.QueryBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/history")
@RequiredArgsConstructor
public class HistoryController {
    private final Javers javers;

    @SneakyThrows
    @GetMapping("/{inputClazz}/{id}")
    public String getHistory(@PathVariable HistoryEnum inputClazz,
                             @PathVariable Long id) {

        var clazz = Class.forName(inputClazz.getClazz);

        var jqlQuery = QueryBuilder.byClass(clazz);

        List<CdoSnapshot> snapshots = javers.findSnapshots(jqlQuery.build());

        return javers.getJsonConverter().toJson(snapshots);
    }
}
