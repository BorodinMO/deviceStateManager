package com.iot.dsm.dto;

import com.iot.dsm.common.WashMachineStatus;
import lombok.*;
import org.springframework.lang.NonNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WashMachineProgramRequestDto {

    @NonNull
    private WashMachineStatus status;

}
