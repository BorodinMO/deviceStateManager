package com.iot.dsm.service;

import com.iot.dsm.common.WashMachineStatus;
import com.iot.dsm.entity.WashMachine;
import com.iot.dsm.exception.WashMachineBusyException;
import com.iot.dsm.exception.WashMachineNotFoundException;
import com.iot.dsm.repository.WashMachineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Service
@RequiredArgsConstructor
public class WashMachineService {

    private final WashMachineRepository washMachineRepository;

    @Transactional
    public WashMachine getWashMachine(Long id) {
        return washMachineRepository.findById(id).orElseThrow(WashMachineNotFoundException::new);
    }

    @Transactional
    public List<WashMachine> getWashMachines() {
        return washMachineRepository.findAll();
    }

    @Transactional
    public WashMachine startProgram(Long id, WashMachineStatus status) {
        WashMachine washMachine = getWashMachine(id);
        WashMachineStatus currentStatus = washMachine.getStatus();

        if (isFalse(WashMachineStatus.IDLE.equals(currentStatus) || WashMachineStatus.IDLE.equals(status))) {
            throw new WashMachineBusyException();
        }

        washMachine.setStatus(status);
        return washMachine;
    }

}
