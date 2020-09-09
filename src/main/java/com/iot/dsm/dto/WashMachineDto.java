package com.iot.dsm.dto;

import com.iot.dsm.common.WashMachineStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WashMachineDto {

    private Long id;

    private WashMachineStatus status;

}
