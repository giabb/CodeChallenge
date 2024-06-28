package org.challenge.ms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.challenge.ms.dto.IstatCodiciDto;
import org.challenge.ms.dto.IstatListaDto;
import org.challenge.ms.model.IstatCodiciEntity;
import org.challenge.ms.service.IstatCodiciService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Code Challenge", description = "Code Challenge CSE - BG")
@RestController
@RequestMapping("/api/city")
public class IstatCodiciController {

	@Autowired
	private IstatCodiciService istatCodiciService;

	@Autowired
	private ModelMapper modelMapper;

	@Operation(
			summary = "Retrieve list of city",
			description = "Get the information of all the cities in the DB")
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = IstatListaDto.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@GetMapping("/list")
	@ResponseBody
	public List<IstatListaDto> retrieveCityList() {
		List<IstatCodiciEntity> istatCodiciDtos = istatCodiciService.getCityList();
		return istatCodiciDtos.stream()
				.map(this::convertListToDto)
				.collect(Collectors.toList());
	}

	@Operation(
			summary = "Retrieve information about specific city",
			description = "Get the information of a specific city given its codCatasto")
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = IstatCodiciDto.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@GetMapping("/{codCatasto}")
	@ResponseBody
	public IstatCodiciDto retrieveCityByCodCatasto(@PathVariable("codCatasto") String codCatasto) {
		return convertToDto(istatCodiciService.getCityByCodCatasto(codCatasto));
	}

	private IstatCodiciDto convertToDto(IstatCodiciEntity istatCodiciEntity) {
		return modelMapper.map(istatCodiciEntity, IstatCodiciDto.class);
	}

	private IstatListaDto convertListToDto(IstatCodiciEntity istatCodiciEntity) {
		return modelMapper.map(istatCodiciEntity, IstatListaDto.class);
	}

}
