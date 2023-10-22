package com.intuit.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    public enum StatusType {
        booked,
        cancel,
        checkedIn
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String phoneNumber;
    @NotNull
    private Boolean isVip;
    @Enumerated(EnumType.STRING)
    private StatusType status;
    private Date time;

}
