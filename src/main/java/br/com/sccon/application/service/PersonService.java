package br.com.sccon.application.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.sccon.application.input.PersonRequestDTO;
import br.com.sccon.application.output.PersonReponseDTO;
import br.com.sccon.application.output.RetornoReponseDTO;
import br.com.sccon.domain.model.Person;

@Service
public class PersonService {
	
	private List<Person> listPerson = new ArrayList<Person>();
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	public Object obterPersonPorId(int id) {
		
		Optional<Person> opt =listPerson.stream()
		  .filter(person -> id == person.getId().intValue())
		  .findAny();
		if(opt.isPresent()) {
			return objectMapper.convertValue(opt.get(), PersonReponseDTO.class);
		}else {
			RetornoReponseDTO dto = new RetornoReponseDTO();	
			dto.setStatus(HttpStatus.NOT_FOUND);
		    dto.setMensagem("Registro não encontrado!");
		    return dto;
		}
		
	}
	
	public RetornoReponseDTO obterIdadePessoa(int id, String output) {
		RetornoReponseDTO dto = new RetornoReponseDTO();
		Optional<Person> opt =listPerson.stream()
		  .filter(person -> id == person.getId().intValue())
		  .findAny();
		if(opt.isPresent()) {
			
			if(output == null) {
				dto.setStatus(HttpStatus.BAD_REQUEST);
			    dto.setMensagem("Parâmetro de formato de saída não reconhecido!");
			}
			else if(output.equals("days") || output.equals("months") || output.equals("years")) {
				String dataNascimento = opt.get().getDataNascimento();
			    LocalDate hoje = LocalDate.now();
		        LocalDate outraData = LocalDate.of(Integer.parseInt(dataNascimento.substring(6, 10)), 
		        		                           Integer.parseInt(dataNascimento.substring(3, 5)), 
		        		                           Integer.parseInt(dataNascimento.substring(0, 2)));
		        
	            if(output.equals("days")) {
                	dto.setMensagem("Total de dias: "+ChronoUnit.DAYS.between(outraData, hoje));
				}else if(output.equals("months")) {
					dto.setMensagem("Total de meses: "+ChronoUnit.MONTHS.between(outraData, hoje));
				}else if(output.equals("years")) {
					dto.setMensagem("Total de anos: "+ChronoUnit.YEARS.between(outraData, hoje));
				}
                dto.setStatus(HttpStatus.OK);	
			}else {
				dto.setStatus(HttpStatus.BAD_REQUEST);
			    dto.setMensagem("Parâmetro de formato de saída não reconhecido!");
			}
		
		}else {
			dto.setStatus(HttpStatus.NOT_FOUND);
		    dto.setMensagem("Registro não encontrado!");
		    return dto;
		}
		
		return dto;
	}
	
	
	public RetornoReponseDTO obterSalarioPessoa(int id, String output) {
		RetornoReponseDTO dto = new RetornoReponseDTO();
		Optional<Person> opt =listPerson.stream()
		  .filter(person -> id == person.getId().intValue())
		  .findAny();
		double salario = 1558.00;
		double salarioMinimo = 1302.00;
		DecimalFormat df = new DecimalFormat("#,##0.00");
		if(opt.isPresent()) {
			
			if(output == null) {
				dto.setStatus(HttpStatus.BAD_REQUEST);
			    dto.setMensagem("Parâmetro de formato de saída não reconhecido!");
			}else {
				
				String dataAdmissao = opt.get().getDataAdmissao();
				LocalDate hoje = LocalDate.now();
			    LocalDate outraData = LocalDate.of(Integer.parseInt(dataAdmissao.substring(6, 10)), 
			        		                           Integer.parseInt(dataAdmissao.substring(3, 5)), 
			        		                           Integer.parseInt(dataAdmissao.substring(0, 2)));
				long totalAnos = ChronoUnit.YEARS.between(outraData, hoje);
				
				for(int i=0; i < totalAnos; i++) {
					salario = ((salario * 18)/100) + salario + 500;
				}
				BigDecimal salarioAtual = new BigDecimal(salario).setScale(2, RoundingMode.HALF_UP);
				
				if(output.equals("full")) {
					dto.setStatus(HttpStatus.OK);
					dto.setMensagem("Salário atual: R$ "+df.format(salarioAtual.doubleValue()));
				}
				else if(output.equals("min")){
					double dSalarioMin = salario/salarioMinimo;
					
					BigDecimal salarioMin = new BigDecimal(dSalarioMin).setScale(2, RoundingMode.HALF_UP);
					dto.setStatus(HttpStatus.OK);
					dto.setMensagem("Quantidade de salário mínimo: "+df.format(salarioMin));
				
				}else {
					dto.setStatus(HttpStatus.BAD_REQUEST);
				    dto.setMensagem("Parâmetro de formato de saída não reconhecido!");
				}
			}
	
		}else {
			dto.setStatus(HttpStatus.NOT_FOUND);
		    dto.setMensagem("Registro não encontrado!");
		    return dto;
		}
		
		return dto;
	}
	
    public RetornoReponseDTO cadastrarPerson(PersonRequestDTO personRequestDTO) {
    	RetornoReponseDTO dto = new RetornoReponseDTO();
	    if(personRequestDTO.getId() == null || personRequestDTO.getId() == 0) {
	       personRequestDTO.setId(listPerson.stream().max(Comparator.comparing(Person::getId)).get().getId() + 1);
	       Person person = new Person(personRequestDTO.getId(), personRequestDTO.getNome(), personRequestDTO.getDataNascimento(), personRequestDTO.getDataAdmissao());
	       listPerson.add(person);
	       dto.setStatus(HttpStatus.OK);
	       dto.setMensagem("Registro cadastrado com sucesso.");
         }else {
        	 
        	if(listPerson.stream()
        			  .filter(person -> personRequestDTO.getId().intValue() == person.getId().intValue())
        			  .findAny().isPresent()) {
        		dto.setStatus(HttpStatus.CONFLICT);
        		dto.setMensagem("Registro já existe com o mesmo id!");
        	}else {
	    	   Person person = new Person(personRequestDTO.getId(), personRequestDTO.getNome(), personRequestDTO.getDataNascimento(), personRequestDTO.getDataAdmissao());
	 	       listPerson.add(person);
	 	       dto.setStatus(HttpStatus.OK);
	 	      dto.setMensagem("Registro cadastrado com sucesso.");
	    	}
        	
	     }
	     return dto;
	}
    
    public RetornoReponseDTO alterarPessoa(int id, PersonRequestDTO personRequestDTO)  {
    	RetornoReponseDTO dto = new RetornoReponseDTO();
    	if(listPerson.stream()
  			  .filter(person -> id == person.getId().intValue())
  			  .findAny().isPresent()) {
    		
    		Person p = listPerson
                    .stream()
                    .filter(person -> person.getId() == id)
                    .findFirst()
                    .get();
    		listPerson.remove(p);
    		
    		Person person = new Person(p.getId(), personRequestDTO.getNome(), personRequestDTO.getDataNascimento(), personRequestDTO.getDataAdmissao());
	 	    listPerson.add(person);
    		
	  		dto.setStatus(HttpStatus.OK);
	  		dto.setMensagem("Registro alterado com sucesso");
    	}else {
    		dto.setStatus(HttpStatus.NOT_FOUND);
    		dto.setMensagem("Registro não encontrado!");
    	}
    	
    	return dto;
    }
    
    
    public RetornoReponseDTO removerPessoa(int id) {
    	RetornoReponseDTO dto = new RetornoReponseDTO();
    	if(listPerson.stream()
  			  .filter(person -> id == person.getId().intValue())
  			  .findAny().isPresent()) {
    		
    		Person p = listPerson
                    .stream()
                    .filter(person -> person.getId() == id)
                    .findFirst()
                    .get();
    		listPerson.remove(p);
    		
	  		dto.setStatus(HttpStatus.OK);
	  		dto.setMensagem("Registro removido com sucesso");
    	}else {
    		dto.setStatus(HttpStatus.NOT_FOUND);
    		dto.setMensagem("Registro não encontrado!");
    	}
    	
    	return dto;
    }
	

	public void populaPerson() {
		listPerson.add(new Person(1, "José da Silva", "06/04/2000", "10/05/2020"));	
		listPerson.add(new Person(2, "Danilo Marques", "03/08/2010", "07/08/2023"));	
		listPerson.add(new Person(3, "Antonio Costa", "01/03/1980", "21/12/2000"));	
	}
	

	public Map<String, Object> getListaPerson() {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("person", listPerson
					  .stream()
					  .sorted((object1, object2) -> object1.getNome().compareTo(object2.getNome())));
	  	return response;		
	}
	
}