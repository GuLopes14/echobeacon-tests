package br.com.echobeacon.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "echobeacon_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String picture;

    @Column(name = "role")
    private String role = "USER"; // Valor padrão: USER

    public boolean isAdmin() {
        return "ADMIN".equals(this.role);
    }
}
