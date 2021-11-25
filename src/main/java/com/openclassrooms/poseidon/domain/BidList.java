package com.openclassrooms.poseidon.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bidlist")
public class BidList {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "bid_list_id")
Integer bidListId;

@Column(name = "account")
@NotBlank(message = "Account is mandatory")
String account;

@Column(name = "type")
@NotBlank(message = "Type is mandatory")
String type;

@Column(name = "bid_quantity")
@NotNull(message = "Bid Quantity is mandatory")
Double bidQuantity;

@Column(name = "ask_quantity")
Double askQuantity;

@Column(name = "bid")
Double bid;

@Column(name = "ask")
Double ask;

@Column(name = "benchmark")
String benchmark;

@Column(name = "bid_list_date")
Timestamp bidListDate;

@Column(name = "commentary")
String commentary;

@Column(name = "security")
String security;

@Column(name = "status")
String status;

@Column(name = "trader")
String trader;

@Column(name = "book")
String book;

@Column(name = "creation_name")
String creationName;

@Column(name = "creation_date")
Timestamp creationDate;

@Column(name = "revision_name")
String revisionName;

@Column(name = "revision_date")
Timestamp revisionDate;

@Column(name = "deal_name")
String dealName;

@Column(name = "deal_type")
String dealType;

@Column(name = "source_list_id")
String sourceListId;

@Column(name = "side")
String side;
}
