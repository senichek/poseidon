package com.openclassrooms.poseidon.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rating")
public class Rating {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id")
Integer id;

@Column(name = "moodys_rating")
@NotBlank(message = "Must not be empty")
String moodysRating;

@Column(name = "sandp_rating")
@NotBlank(message = "Must not be empty")
String sandPRating;

@Column(name = "fitch_rating")
@NotBlank(message = "Must not be empty")
String fitchRating;

@Column(name = "order_number")
@NotNull(message = "Must not be empty")
Integer orderNumber;
}
