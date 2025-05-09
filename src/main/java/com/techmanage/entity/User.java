package com.techmanage.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

@Entity
@Table(name = "tb_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Nome completo é obrigatório")
    private String fullName;
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email precisa ser valido")
    @Column(unique = true)
    private String email;
    //TODO regex
    @Pattern(regexp = "", message = "Formato de telefone valido. ex: +55 11 99999-9999")
    @NotBlank(message = "Telefone é obrigatório")
    private String phone;
    @NotNull(message = "Data de nascimento é obrigatório")
    private LocalDate birthDate;
    @NotNull(message = "Tipo de usuario é obrigatório")
    @Enumerated(EnumType.STRING)
    private UserType userType;
}
