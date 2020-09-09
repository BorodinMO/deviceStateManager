package com.iot.dsm.entity;

import com.iot.dsm.common.WashMachineStatus;
import lombok.*;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WashMachine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Enumerated(EnumType.ORDINAL)
    private WashMachineStatus status;

}
