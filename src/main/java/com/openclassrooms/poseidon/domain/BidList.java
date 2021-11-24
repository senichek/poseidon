package com.openclassrooms.poseidon.domain;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "bidlist")
public class BidList {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "BidListId")
Integer BidListId;

@Column(name = "account")
String account;

@Column(name = "type")
String type;

@Column(name = "bidQuantity")
Double bidQuantity;

@Column(name = "askQuantity")
Double askQuantity;

@Column(name = "bid")
Double bid;

@Column(name = "ask")
Double ask;

@Column(name = "benchmark")
String benchmark;

@Column(name = "bidListDate")
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

@Column(name = "creationName")
String creationName;

@Column(name = "creationDate")
Timestamp creationDate;

@Column(name = "revisionName")
String revisionName;

@Column(name = "revisionDate")
Timestamp revisionDate;

@Column(name = "dealName")
String dealName;

@Column(name = "dealType")
String dealType;

@Column(name = "sourceListId")
String sourceListId;

@Column(name = "side")
String side;
}
