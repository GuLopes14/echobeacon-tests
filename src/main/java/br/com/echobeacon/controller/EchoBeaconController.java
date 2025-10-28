package br.com.echobeacon.controller;

import br.com.echobeacon.model.EchoBeacon;
import br.com.echobeacon.model.Moto;
import br.com.echobeacon.service.EchoBeaconService;
import br.com.echobeacon.service.MotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/echobeacon")
public class EchoBeaconController {

    @Autowired
    private EchoBeaconService echoBeaconService;

    @Autowired
    private MotoService motoService;

    @GetMapping
    public String mostrarVinculo(Model model) {
        List<Moto> motosNaoVinculadas = motoService.listarTodos()
                .stream()
                .filter(m -> m.getEchoBeacon() == null)
                .toList();

        List<EchoBeacon> echoBeaconsNaoVinculados = echoBeaconService.listarTodos()
                .stream()
                .filter(eb -> motoService.listarTodos().stream().noneMatch(m -> eb.equals(m.getEchoBeacon())))
                .toList();

        model.addAttribute("motos", motosNaoVinculadas);
        model.addAttribute("echoBeacons", echoBeaconsNaoVinculados);
        return "vincular-echobeacon";
    }

    @PostMapping("/vincular")
    public String vincularEchoBeacon(@RequestParam("motoId") Long motoId, @RequestParam("echoBeaconId") Long echoBeaconId) {
        Moto moto = motoService.buscarPorId(motoId).orElseThrow();
        EchoBeacon echoBeacon = echoBeaconService.buscarPorId(echoBeaconId).orElseThrow();
        moto.setEchoBeacon(echoBeacon);
        motoService.salvar(moto);
        return "redirect:/motos";
    }

    @GetMapping("/novo")
    public String mostrarFormularioEchoBeacon(Model model) {
        model.addAttribute("echoBeacon", new EchoBeacon());
        return "cadastro-echobeacon";
    }

    @PostMapping
    public String salvarEchoBeacon(@ModelAttribute EchoBeacon echoBeacon, Model model) {
        try {
            echoBeaconService.salvar(echoBeacon);
            return "redirect:/echobeacon";
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            model.addAttribute("echoBeacon", echoBeacon);
            model.addAttribute("erro", "Já existe um EchoBeacon com este número de identificação.");
            return "cadastro-echobeacon";
        }
    }
}