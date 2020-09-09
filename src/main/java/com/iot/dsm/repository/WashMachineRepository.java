package com.iot.dsm.repository;

import com.iot.dsm.entity.WashMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WashMachineRepository extends JpaRepository<WashMachine, Long> {

}
