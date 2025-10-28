package br.com.echobeacon.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.echobeacon.model.Moto;
import br.com.echobeacon.mqtt.MqttPublisher;
import br.com.echobeacon.service.MotoService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/motos")
public class MotoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MotoController.class);

    @Autowired
    private MotoService motoService;

    @Autowired
    private MqttPublisher mqttPublisher;

    @GetMapping
    public String listarMotos(Model model) {
        List<Moto> todasMotos = motoService.listarTodos();
        List<Moto> motosRecepcao = todasMotos.stream()
            .filter(m -> m.getEchoBeacon() == null)
            .toList();
        List<Moto> motosPatio = todasMotos.stream()
            .filter(m -> m.getEchoBeacon() != null)
            .toList();

        model.addAttribute("motosRecepcao", motosRecepcao);
        model.addAttribute("motosPatio", motosPatio);

        // Sem autenticação - todos são admin
        model.addAttribute("isAdmin", true);

        return "motos";
    }

    @GetMapping("/novo")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("moto", new Moto());
        return "cadastro-moto";
    }

    @PostMapping
    public String salvarMoto(@Valid @ModelAttribute Moto moto, org.springframework.validation.BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("moto", moto);
            return "cadastro-moto";
        }
        try {
            motoService.salvar(moto);
        } catch (IllegalArgumentException e) {
            model.addAttribute("moto", moto);
            model.addAttribute("errorMessage", e.getMessage());
            return "cadastro-moto";
        }
        return "redirect:/motos";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicao(@PathVariable("id") Long id, Model model) {
        Moto moto = motoService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Moto inválida com id: " + id));
        model.addAttribute("moto", moto);
        return "editar-moto";
    }

    @PostMapping("/editar/{id}")
    public String atualizarMoto(@PathVariable("id") Long id, @ModelAttribute Moto moto, Model model) {
        Moto motoOriginal = motoService.buscarPorId(id)
            .orElseThrow(() -> new IllegalArgumentException("Moto inválida com id: " + id));
        moto.setEchoBeacon(motoOriginal.getEchoBeacon());
        // Prevent changing placa to an existing one (except for itself)
        boolean placaDuplicada = !motoOriginal.getPlaca().equals(moto.getPlaca()) && motoService.listarTodos().stream().anyMatch(m -> m.getPlaca().equals(moto.getPlaca()));
        boolean chassiDuplicado = !motoOriginal.getChassi().equals(moto.getChassi()) && motoService.listarTodos().stream().anyMatch(m -> m.getChassi().equals(moto.getChassi()));
        if (placaDuplicada) {
            model.addAttribute("moto", moto);
            model.addAttribute("errorMessage", "Já existe uma moto com esta placa.");
            return "editar-moto";
        }
        if (chassiDuplicado) {
            model.addAttribute("moto", moto);
            model.addAttribute("errorMessage", "Já existe uma moto com este chassi.");
            return "editar-moto";
        }
        motoService.salvar(moto);
        return "redirect:/motos";
    }

    @GetMapping("/excluir/{id}")
    public String excluirMoto(@PathVariable("id") Long id) {
        motoService.excluir(id);
        return "redirect:/motos";
    }

    @PostMapping("/localizar/{id}")
    public String localizarMoto(@PathVariable("id") Long id) {
        Moto moto = motoService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Moto inválida com id: " + id));

        if (moto.getEchoBeacon() != null && moto.getEchoBeacon().getId() != null) {
            int echoBeaconNumero = moto.getEchoBeacon().getNumeroIdentificacao();
            String echoBeaconId = String.valueOf(echoBeaconNumero);

            LOGGER.info("Publicando comando MQTT 'ativar' para EchoBeacon {} (moto {} - placa {})",
                    echoBeaconId, id, moto.getPlaca());
            mqttPublisher.publishAtivar(echoBeaconId, moto);
        } else {
            LOGGER.warn("Moto {} não possui EchoBeacon associado para envio de comando.", id);
        }

        return "redirect:/motos";
    }
}
