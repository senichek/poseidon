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
@Table(name = "trade")
public class Trade {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "trade_id")
Integer tradeId;

@Column(name = "account")
@NotBlank(message = "Must not be empty")
String account;

@Column(name = "type")
@NotBlank(message = "Must not be empty")
String type;

@Column(name = "buy_quantity")
@NotNull(message = "Must not be empty")
Double buyQuantity;

@Column(name = "sell_quantity")
@NotNull(message = "Must not be empty")
Double sellQuantity;
}
