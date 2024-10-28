package br.com.sccon.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.sccon.application.input.PersonRequestDTO;
import br.com.sccon.application.output.RetornoReponseDTO;
import br.com.sccon.application.service.PersonService;
import br.com.sccon.application.swagger.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Endpoint Person")
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/v1/person")
public class PersonController {
	
	@Autowired
	private PersonService personService;
	
	@ApiOperation(method = "GET", description = "Lista das pessoas", summary = "Lista das pessoas")
	@GetMapping()
	public ResponseEntity<Map<String, Object>> getAllPerson() {
		return new ResponseEntity<>(personService.getListaPerson(), HttpStatus.OK);
	}
	
	@ApiOperation(method = "POST", description = "Cadastrar pessoa", summary = "Cadastrar pessoa")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> cadastrarPerson(@RequestBody PersonRequestDTO personRequestDTO)  {
		RetornoReponseDTO dto = personService.cadastrarPerson(personRequestDTO);		
		return new ResponseEntity<>(dto, dto.getStatus());
	}
	
	
	@ApiOperation(method = "DELETE", description = "Remover pessoa", summary = "Remover pessoa")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> removerPessoa(@PathVariable(name="id") int id)  {
		RetornoReponseDTO dto = personService.removerPessoa(id);	
		return new ResponseEntity<>(dto, dto.getStatus());
	}
	
	@ApiOperation(method = "PUT", description = "Alterar pessoa", summary = "Alterar pessoa")
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> alterarPessoa(@PathVariable(name="id") int id, @RequestBody PersonRequestDTO personRequestDTO)  {
		RetornoReponseDTO dto = personService.alterarPessoa(id, personRequestDTO);	
		return new ResponseEntity<>(dto, dto.getStatus());
	}
	
	
	@ApiOperation(method = "PATCH", description = "Alterar dados pessoa", summary = "Alterar dados pessoa")
	@PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> alterarDadosPessoa(@PathVariable(name="id") int id, @RequestBody PersonRequestDTO personRequestDTO)  {
		RetornoReponseDTO dto = personService.alterarPessoa(id, personRequestDTO);	
		return new ResponseEntity<>(dto, dto.getStatus());
	}
	
	@ApiOperation(method = "GET", description = "Retorna a idade de uma pessoa", summary = "Retorna a idade de uma pessoa")
	@GetMapping(value = "/{id}/age")
	public ResponseEntity<Object> obterIdadePessoa(
			@PathVariable(name="id") int id,
			@RequestParam(value = "output" ,required = true, defaultValue = "") String output)  {
		RetornoReponseDTO dto = personService.obterIdadePessoa(id, output);	
		return new ResponseEntity<>(dto, dto.getStatus());
	}
	
	
	@ApiOperation(method = "GET", description = "Retorna o salário de uma pessoa", summary = "Retorna o salário de uma pessoa")
	@GetMapping(value = "/{id}/salary")
	public ResponseEntity<Object> obterSalarioPessoa(
			@PathVariable(name="id") int id,
			@RequestParam(value = "output" ,required = true, defaultValue = "") String output)  {
		RetornoReponseDTO dto = personService.obterSalarioPessoa(id, output);	
		return new ResponseEntity<>(dto, dto.getStatus());
	}
	
	
	@ApiOperation(method = "GET", description = "Busca os dados pelo id", summary = "Obter person filtrando o id")
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> obterPersonPorId(@PathVariable(name="id") int id)  {
		Object obj = personService.obterPersonPorId(id);
		if(obj instanceof RetornoReponseDTO) {
			return new ResponseEntity<>(obj, ((RetornoReponseDTO)obj).getStatus());
		}else {
			return new ResponseEntity<>(obj, HttpStatus.OK);
		}		
	}
	
	
	@EventListener(ContextRefreshedEvent.class)
	public void warmup() {
		personService.populaPerson();
	}
	

}
