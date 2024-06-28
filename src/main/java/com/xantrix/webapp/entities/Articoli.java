package com.xantrix.webapp.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ARTICOLI")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Articoli implements Serializable {

	private static final long serialVersionUID = 5083489851022863543L;
	
	@Id
	@Column(name = "CODART")
	@Size(min= 5, max = 20, message = "{Size.Articoli.codArt.Validation}")
	@NotNull(message = "{NotNull.Articoli.codArt.Validation}")
	private String codArt;
	
	@Column(name = "DESCRIZIONE")
	@Size(min= 6, max = 80, message = "{Size.Articoli.descrizione.Validation}")
	private String descrizione;
	
	@Column(name = "UM")
	@NotNull(message = "{NotNull.Articoli.um.Validation}")
	private String um;
	
	@Column(name = "CODSTAT")
	private String codStat;
	
	@Column(name = "PZCART")
	@NotNull(message = "{NotNull.Articoli.pzcart.Validation}")
	private Integer pzCart;
	
	@Column(name = "PESONETTO")
	private Double pesoNetto;
	
	@Column(name = "IDSTATOART")
	@NotNull(message = "{NotNull.Articoli.idStatoArt.Validation}")
	private String idStatoArt;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATACREAZIONE")
	private Date dataCreaz;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "articolo", orphanRemoval = true)
	@JsonManagedReference
	private Set<Barcode> barcode = new HashSet<>();
	
	@OneToOne(mappedBy = "articolo", cascade = CascadeType.ALL, orphanRemoval = true)
	private Ingredienti ingredienti;
	
	@ManyToOne
	@JoinColumn (name = "IDIVA", referencedColumnName = "idIva")
	private Iva iva;
	
	@ManyToOne
	@JoinColumn (name = "IDFAMASS", referencedColumnName = "ID")
	@NotNull(message = "{NotNull.Articoli.famAssort.Validation}")
	private FamAssort famAssort; 
	
	
}
