package br.com.echobeacon.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.echobeacon.model.Moto;

import java.util.Optional;

public interface MotoRepository extends JpaRepository<Moto, Long> {
    Optional<Moto> findByPlaca(String placa);
    Optional<Moto> findByChassi(String chassi);
}
