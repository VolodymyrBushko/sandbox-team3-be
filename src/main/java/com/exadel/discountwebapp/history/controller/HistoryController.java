package com.exadel.discountwebapp.history.controller;

import com.exadel.discountwebapp.history.entityclassenum.HistoryEnum;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.javers.core.Changes;
import org.javers.core.Javers;
import org.javers.core.metamodel.object.CdoSnapshot;
import org.javers.repository.jql.JqlQuery;
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
    public String getHistoryByEntityId(@PathVariable HistoryEnum inputClazz,
                                       @PathVariable Long id) {

        var clazz = Class.forName(inputClazz.getClazz);

        var jqlQuery = QueryBuilder.byInstanceId(id, clazz);

        List<CdoSnapshot> snapshots = javers.findSnapshots(jqlQuery.build());

        return javers.getJsonConverter().toJson(snapshots);
    }

    @SneakyThrows
    @GetMapping("/diff/{inputClazz}/{id}")
    public String getDifferenceByEntityId(@PathVariable HistoryEnum inputClazz,
                             @PathVariable Long id) {

        var clazz = Class.forName(inputClazz.getClazz);

        var query = QueryBuilder.byInstanceId(id, clazz).build();

        var changes = javers.findChanges(query);


        return javers.getJsonConverter().toJson(changes);
    }

    @SneakyThrows
    @GetMapping("/general")
    public String getDifferenceByEntityId() {

        var changes = javers.findChanges( QueryBuilder.anyDomainObject().build());

        return javers.getJsonConverter().toJson(changes);
    }


}
