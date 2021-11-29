package com.openclassrooms.poseidon.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id")
Integer id;

@Column(name = "username")
@NotBlank(message = "Must not be empty")
String username;


@Size(min = 8, max = 125)
@Pattern(regexp = PASSWORD_PATTERN, message = "Uppercase, lowercase letters and numbers must be present in the password. 8 chars min.")
@JsonIgnore
@Transient
String rawPassword;

@Column(name = "password")
@JsonIgnore
String encodedPassword;

@Column(name = "fullname")
@NotBlank(message = "Must not be empty")
String fullname;

@Column(name = "role")
@NotBlank(message = "Must not be empty")
String role;
}
