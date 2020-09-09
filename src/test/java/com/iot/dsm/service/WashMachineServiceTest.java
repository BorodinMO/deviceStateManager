package com.iot.dsm.service;

import com.iot.dsm.common.WashMachineStatus;
import com.iot.dsm.entity.WashMachine;
import com.iot.dsm.exception.WashMachineBusyException;
import com.iot.dsm.exception.WashMachineNotFoundException;
import com.iot.dsm.repository.WashMachineRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WashMachineServiceTest {

    private static final Long EXISTING_ID = 12345L;
    private static final Long NON_EXISTING_ID = 54321L;

    @Mock
    WashMachineRepository washMachineRepository;

    @InjectMocks
    WashMachineService washMachineService;

    @Test
    void shouldGetAllWashMachines() {
        List<WashMachine> washMachines = new ArrayList<>();
        washMachines.add(WashMachine
                .builder()
                .id(EXISTING_ID)
                .status(WashMachineStatus.IDLE)
                .build());

        when(washMachineRepository.findAll())
                .thenReturn(washMachines);

        assertEquals(washMachines, washMachineService.getWashMachines());
    }

    @Test
    void givenExistingIdshouldGetWashMachineWithSpecifiedId() {
        WashMachine washMachine = WashMachine
                .builder()
                .id(EXISTING_ID)
                .status(WashMachineStatus.IDLE)
                .build();

        when(washMachineRepository.findById(EXISTING_ID))
                .thenReturn(Optional.of(washMachine));

        assertEquals(washMachine, washMachineService.getWashMachine(EXISTING_ID));
    }

    @Test
    void givenNonExistingIdShouldThrowException() {
        when(washMachineRepository.findById(NON_EXISTING_ID))
                .thenReturn(Optional.empty());

        assertThrows(WashMachineNotFoundException.class,
                () -> washMachineService.getWashMachine(NON_EXISTING_ID));
    }

    @Test
    void givenNonIdleStatusForWashMachineWithIdleStatusShouldChangeStatusOfWashingMachine() {
        WashMachine washMachine = WashMachine
                .builder()
                .id(EXISTING_ID)
                .status(WashMachineStatus.IDLE)
                .build();

        when(washMachineRepository.findById(EXISTING_ID))
                .thenReturn(Optional.of(washMachine));

        washMachineService.startProgram(EXISTING_ID, WashMachineStatus.DELICATE_PROGRAM);

        assertEquals(WashMachineStatus.DELICATE_PROGRAM, washMachine.getStatus());
    }

    @Test
    void givenIdleStatusForWashMachineWithNonIdleStatusShouldChangeStatusOfWashingMachine() {
        WashMachine washMachine = WashMachine
                .builder()
                .id(EXISTING_ID)
                .status(WashMachineStatus.DELICATE_PROGRAM)
                .build();

        when(washMachineRepository.findById(EXISTING_ID))
                .thenReturn(Optional.of(washMachine));

        washMachineService.startProgram(EXISTING_ID, WashMachineStatus.IDLE);

        assertEquals(WashMachineStatus.IDLE, washMachine.getStatus());
    }

    @Test
    void givenNonIdleStatusForWashingMachineWithNonIdleStatusShouldThrowException() {
        WashMachine washMachine = WashMachine
                .builder()
                .id(EXISTING_ID)
                .status(WashMachineStatus.DELICATE_PROGRAM)
                .build();

        when(washMachineRepository.findById(EXISTING_ID))
                .thenReturn(Optional.of(washMachine));

        assertThrows(WashMachineBusyException.class,
                () -> washMachineService.startProgram(EXISTING_ID, WashMachineStatus.QUICK_WASH_PROGRAM));
    }

}