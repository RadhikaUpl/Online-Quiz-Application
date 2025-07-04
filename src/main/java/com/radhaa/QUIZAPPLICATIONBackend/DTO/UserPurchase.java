package com.radhaa.QUIZAPPLICATIONBackend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserPurchase
{
    private String paymentStatus;
//    private int userId;
    private int cardId;
}
