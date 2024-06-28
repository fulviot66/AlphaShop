package com.xantrix.webapp.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xantrix.webapp.dtos.ArticoliDto;
import com.xantrix.webapp.entities.Articoli;
import com.xantrix.webapp.repository.ArticoliRepository;

@Service
@Transactional(readOnly = true)
public class ArticoliServiceImpl implements ArticoliService{

	@Autowired
	ArticoliRepository articoliRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public List<ArticoliDto> SelByDescrizione(String descrizione) {
		
		String filter = "%" + descrizione.toUpperCase() + "%";
		List<Articoli> articoli = articoliRepository.selByDescrizioneLike(filter);
		List<ArticoliDto> retVal = articoli
				.stream()
				.map(source -> modelMapper.map(source, ArticoliDto.class))
				.collect(Collectors.toList());
		
		return retVal;
	}

	@Override
	public List<ArticoliDto> SelByDescrizione(String descrizione, Pageable pageable) {
		
		String filter = "%" + descrizione.toUpperCase() + "%";
		List<Articoli> articoli = articoliRepository.findByDescrizioneLike(filter, pageable);
		List<ArticoliDto> retVal = articoli
				.stream()
				.map(source -> modelMapper.map(source, ArticoliDto.class))
				.collect(Collectors.toList());
		
		return retVal;
		
	}
	
	@Override
	public Articoli SelByCodArt2 (String codart) {
		
		return articoliRepository.findByCodArt(codart);
	}

	@Override
	public ArticoliDto SelByCodArt(String codart) {
		Articoli articoli = this.SelByCodArt2(codart);
		return this.ConvertToDto(articoli);
	}

	
	@Override
	public ArticoliDto SelByBarcode(String barcode) {
		
		Articoli articoli = articoliRepository.selByEAN(barcode);
		return this.ConvertToDto(articoli);
	}

	@Override
	@Transactional
	public void DelArticolo(Articoli articolo) {
		
		articoliRepository.delete(articolo);
	}

	@Override
	@Transactional
	public void InsArticolo(Articoli articolo) {

		articolo.setDescrizione(articolo.getDescrizione().toUpperCase());
		articoliRepository.save(articolo);
	}
	
	private ArticoliDto ConvertToDto(Articoli articoli) {
		ArticoliDto articoliDto = null;
		if (articoli != null) {
			articoliDto = modelMapper.map(articoli, ArticoliDto.class);
		}
		return articoliDto;
	}


}
