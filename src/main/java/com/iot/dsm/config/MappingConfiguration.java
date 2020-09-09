package com.iot.dsm.config;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@MapperConfig(
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        componentModel = "Spring"
)
public class MappingConfiguration {
}
