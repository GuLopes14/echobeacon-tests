package br.com.echobeacon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.echobeacon.model.EchoBeacon;

public interface EchoBeaconRepository extends JpaRepository<EchoBeacon, Long> {
}
