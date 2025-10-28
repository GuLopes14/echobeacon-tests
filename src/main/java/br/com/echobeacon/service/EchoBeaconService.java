package br.com.echobeacon.service;

import br.com.echobeacon.model.EchoBeacon;
import br.com.echobeacon.repository.EchoBeaconRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EchoBeaconService {

    @Autowired
    private EchoBeaconRepository echoBeaconRepository;

    public EchoBeacon salvar(EchoBeacon echoBeacon) {
        return echoBeaconRepository.save(echoBeacon);
    }

    public List<EchoBeacon> listarTodos() {
        return echoBeaconRepository.findAll();
    }

    public Optional<EchoBeacon> buscarPorId(Long id) {
        return echoBeaconRepository.findById(id);
    }
}