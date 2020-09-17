package com.iot.dsm.controller;

import com.iot.dsm.common.WashMachineStatus;
import com.iot.dsm.dto.WashMachineDto;
import com.iot.dsm.dto.WashMachineProgramRequestDto;
import com.iot.dsm.mapping.WashMachineMapper;
import com.iot.dsm.service.WashMachineService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("washmachines")
@RequiredArgsConstructor
public class WashMachineController {

    private final WashMachineMapper washMachineMapper;

    private final WashMachineService washMachineService;

    @GetMapping("/{id}")
    @ApiOperation("Get washing machine with specified id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Wash machine found."),
            @ApiResponse(code = 404, message = "Wash machine not found.")
    })
    public WashMachineDto getWashMachine(@PathVariable Long id) {
        return washMachineMapper.toDto(washMachineService.getWashMachine(id));
    }

    @GetMapping
    @ApiOperation("Get list of all washing machines")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Wash machines found."),
    })
    public List<WashMachineDto> getWashMachines() {
        return washMachineService.getWashMachines()
                .stream()
                .map(washMachineMapper::toDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @ApiOperation("Execute program for specified wash machine.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Wash machine status changed."),
            @ApiResponse(code = 400, message = "Wash machine is busy"),
            @ApiResponse(code = 404, message = "Wash machine was not found.")
    })
    public WashMachineDto startProgram(@PathVariable Long id, @RequestBody WashMachineProgramRequestDto request) {
        return washMachineMapper.toDto(washMachineService.startProgram(id, request.getStatus()));
    }

    @GetMapping("/operations")
    @ApiOperation("Get list of possible operations for wash machine.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Operations retrieved.")
    })
    public List<WashMachineStatus> getOperations() {
        return Arrays.asList(WashMachineStatus.values());
    }

}
