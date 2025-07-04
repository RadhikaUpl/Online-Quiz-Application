package com.radhaa.QUIZAPPLICATIONBackend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//this is an input request to stripe api
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest
{
    private long amount;
//    private long quantity;
    private String name;
    private String currency;
}
