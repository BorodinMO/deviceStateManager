package com.iot.dsm.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.dsm.common.WashMachineStatus;
import com.iot.dsm.dto.WashMachineDto;
import com.iot.dsm.dto.WashMachineProgramRequestDto;
import com.iot.dsm.entity.WashMachine;
import com.iot.dsm.mapping.WashMachineMapper;
import com.iot.dsm.service.WashMachineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = WashMachineController.class)
@ActiveProfiles("test")
class WashMachineControllerTest {

    private static final WashMachineStatus STATUS_1 = WashMachineStatus.DELICATE_PROGRAM;
    private static final WashMachineStatus STATUS_2 = WashMachineStatus.IDLE;

    private static final Long ID_1 = 1L;
    private static final Long ID_2 = 2L;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    WashMachineService washMachineService;

    @MockBean
    WashMachineMapper washMachineMapper;

    private List<WashMachine> washMachines;

    private List<WashMachineDto> washMachineDtos;

    @BeforeEach
    void setUp() {
        this.washMachines = new ArrayList<>();

        WashMachine washMachine1 = WashMachine
                .builder()
                .id(ID_1)
                .status(STATUS_1)
                .build();

        WashMachine washMachine2 = WashMachine
                .builder()
                .id(ID_2)
                .status(STATUS_2)
                .build();

        washMachines.add(washMachine1);
        washMachines.add(washMachine2);

        this.washMachineDtos = new ArrayList<>();

        WashMachineDto washMachineDto1 = WashMachineDto
                .builder()
                .id(ID_1)
                .status(STATUS_1)
                .build();

        WashMachineDto washMachineDto2 = WashMachineDto
                .builder()
                .id(ID_2)
                .status(STATUS_2)
                .build();

        washMachineDtos.add(washMachineDto1);
        washMachineDtos.add(washMachineDto2);
    }

    @Test
    void shouldGetAllWashMachines() throws Exception {
        when(washMachineService.getWashMachines()).thenReturn(washMachines);
        when(washMachineMapper.toDto(washMachines.get(0))).thenReturn(washMachineDtos.get(0));
        when(washMachineMapper.toDto(washMachines.get(1))).thenReturn(washMachineDtos.get(1));

        mockMvc.perform(get("/washmachines"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(ID_1))
                .andExpect(jsonPath("$[0].status").value(STATUS_1.toString()))
                .andExpect(jsonPath("$[1].id").value(ID_2))
                .andExpect(jsonPath("$[1].status").value(STATUS_2.toString()));
    }

    @Test
    void shouldGetWashMachineById() throws Exception {
        when(washMachineService.getWashMachine(ID_1)).thenReturn(washMachines.get(0));
        when(washMachineMapper.toDto(washMachines.get(0))).thenReturn(washMachineDtos.get(0));

        mockMvc.perform(get("/washmachines/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID_1))
                .andExpect(jsonPath("$.status").value(STATUS_1.toString()));
    }

    @Test
    void shouldStartProgramForWashMachineAndReturnUpdatedStatus() throws Exception {
        WashMachine modifiedWashMachine = WashMachine
                .builder()
                .id(ID_1)
                .status(STATUS_2)
                .build();

        WashMachineDto modifiedWashMachineDto = WashMachineDto
                .builder()
                .id(ID_1)
                .status(STATUS_2)
                .build();

        WashMachineProgramRequestDto requestDto = WashMachineProgramRequestDto
                .builder()
                .status(STATUS_2)
                .build();

        String requestBody = new ObjectMapper().writeValueAsString(requestDto);

        when(washMachineService.startProgram(ID_1, STATUS_2)).thenReturn(modifiedWashMachine);
        when(washMachineMapper.toDto(modifiedWashMachine)).thenReturn(modifiedWashMachineDto);

        mockMvc.perform(put("/washmachines/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(jsonPath("$.id").value(ID_1))
                .andExpect(jsonPath("$.status").value(STATUS_2.toString()));
    }

    @Test
    void shouldGetOperations() throws Exception {
        mockMvc.perform(get("/washmachines/operations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(WashMachineStatus.IDLE.toString()))
                .andExpect(jsonPath("$[1]").value(WashMachineStatus.WOOL_PROGRAM.toString()))
                .andExpect(jsonPath("$[2]").value(WashMachineStatus.DELICATE_PROGRAM.toString()))
                .andExpect(jsonPath("$[3]").value(WashMachineStatus.QUICK_WASH_PROGRAM.toString()));
    }

}