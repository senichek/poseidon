package com.openclassrooms.poseidon.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rulename")
public class RuleName {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id")
Integer id;

@Column(name = "name")
@NotBlank(message = "Must not be empty")
String name;

@Column(name = "description")
@NotBlank(message = "Must not be empty")
String description;

@Column(name = "json")
@NotBlank(message = "Must not be empty")
String json;

@Column(name = "template")
@NotBlank(message = "Must not be empty")
String template;

@Column(name = "sql_str")
String sqlStr;

@Column(name = "sql_part")
String sqlPart;
}
