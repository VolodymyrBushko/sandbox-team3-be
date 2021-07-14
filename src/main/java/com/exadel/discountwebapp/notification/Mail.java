package com.exadel.discountwebapp.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mail {

    private String subject;
    private String template;
    private String from;
    private String[] to;
    private Map<String, Object> variables;
}
