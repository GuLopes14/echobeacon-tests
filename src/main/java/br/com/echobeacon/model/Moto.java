package br.com.echobeacon.model;

import br.com.echobeacon.model.enums.Modelo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Moto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O campo 'placa' é obrigatório")
    @Size(min = 7, max = 7, message = "O campo 'placa' deve ter 7 caracteres")
    @Column(unique = true)
    private String placa;

    @NotBlank(message = "O campo 'chassi' é obrigatório")
    @Size(min = 17, max = 17, message = "O campo 'chassi' deve ter 17 caracteres")
    @Column(unique = true)
    private String chassi;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "O campo 'modelo' é obrigatório")
    private Modelo modelo;

    @NotBlank(message = "O campo problema é obrigatório")
    @Size(min = 5, max = 150, message = "O campo 'problema' deve ter entre 5 e 100 caracteres")
    private String problema;

    @OneToOne
    @JoinColumn(name = "echo_beacon_id", referencedColumnName = "id")
    private EchoBeacon echoBeacon;
}
