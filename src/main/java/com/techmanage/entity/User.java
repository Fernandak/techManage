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
    @Pattern(regexp = "^\\+\\d{2}\\s\\d{2}\\s\\d{5}-\\d{4}$",message = "Formato de telefone valido. ex: +55 11 99999-9999")
    @NotBlank(message = "Telefone é obrigatório")
    private String phone;
    @NotNull(message = "Data de nascimento é obrigatório")
    private LocalDate birthDate;
    @NotNull(message = "Tipo de usuario é obrigatório")
    @Enumerated(EnumType.STRING)
    private UserType userType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
