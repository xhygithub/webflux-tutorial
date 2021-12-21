package com.example.webfluxtutorial.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceRecord {
    private static final int MAX_SALES_COMMENT_LENGTH = 100;
    private String id;

    private String serviceOrderId;

    private String dealerId;

    private String orderNumber;

    private String wipNumber;

    private String wipId;

    private List<String> serviceTypes;

    private String reservationCustomerName;

    private String reservationCustomerLastName;

    private Date lastChangeDate;
}
