package com.iot.dsm.mapping;

import com.iot.dsm.config.MappingConfiguration;
import com.iot.dsm.dto.WashMachineDto;
import com.iot.dsm.entity.WashMachine;
import org.mapstruct.Mapper;

@Mapper(config = MappingConfiguration.class)
public interface WashMachineMapper {

    WashMachineDto toDto(WashMachine washMachine);

}
