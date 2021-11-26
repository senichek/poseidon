package com.openclassrooms.poseidon.domain;

import java.sql.Timestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "curvepoint")
public class CurvePoint {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id")
Integer id;

@Column(name = "curve_id")
@NotNull(message = "Must not be null")
Integer curveId;

@Column(name = "as_of_date")
Timestamp asOfDate;

@Column(name = "term")
@NotNull(message = "Must not be null")
Double term;

@Column(name = "value")
@NotNull(message = "Must not be null")
Double value;

@Column(name = "creation_date")
Timestamp creationDate;
}
