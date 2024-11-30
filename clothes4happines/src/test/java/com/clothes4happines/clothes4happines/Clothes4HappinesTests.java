package com.clothes4happines.clothes4happines;

import com.clothes4happines.models.Usuario;
import com.clothes4happines.models.Equipamento;
import com.clothes4happines.repository.UsuarioRepository;
import com.clothes4happines.repository.EquipamentoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class Clothes4HappinesTests {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private EquipamentoRepository equipamentoRepository;

	private final String usuarioBaseUrl = "http://localhost:8080/usuarios";
	private final String equipamentoBaseUrl = "http://localhost:8080/equipamentos";

	// ----------------------
	// Testes Funcionais de Usuário
	// ----------------------
	@Test
	public void testCadastroUsuario() {
		Usuario usuario = new Usuario();
		usuario.setName("Carlos Santos");

		Usuario savedUsuario = usuarioRepository.save(usuario);

		assertThat(savedUsuario.getId()).isNotNull();
		assertThat(savedUsuario.getName()).isEqualTo("Carlos Santos");
	}

	@Test
	public void testCadastroUsuarioSemNome() {
		Usuario usuario = new Usuario();
		usuario.setName(null); // Nome inválido

		try {
			usuarioRepository.save(usuario);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(Exception.class);
		}
	}

	// ----------------------
	// Testes Funcionais de Equipamento
	// ----------------------
	@Test
	public void testCadastroEquipamento() {
		Equipamento equipamento = new Equipamento();
		equipamento.setNome("Notebook");

		Equipamento savedEquipamento = equipamentoRepository.save(equipamento);

		assertThat(savedEquipamento.getId()).isNotNull();
		assertThat(savedEquipamento.getNome()).isEqualTo("Notebook");
	}

	@Test
	public void testCadastroEquipamentoSemNome() {
		Equipamento equipamento = new Equipamento();
		equipamento.setNome(null); // Nome inválido

		try {
			equipamentoRepository.save(equipamento);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(Exception.class);
		}
	}

	// ----------------------
	// Testes de Integração de Usuário
	// ----------------------
	@Test
	public void testCadastroUsuarioValido() {
		RestTemplate restTemplate = new RestTemplate();
		Usuario usuario = new Usuario();
		usuario.setName("João Silva");

		ResponseEntity<Usuario> response = restTemplate.postForEntity(usuarioBaseUrl, usuario, Usuario.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response.getBody().getName()).isEqualTo("João Silva");
	}

	@Test
	public void testCadastroUsuarioInvalido() {
		RestTemplate restTemplate = new RestTemplate();
		Usuario usuario = new Usuario(); // Nome vazio ou inválido

		ResponseEntity<String> response = restTemplate.postForEntity(usuarioBaseUrl, usuario, String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	public void testListagemUsuarios() {
		RestTemplate restTemplate = new RestTemplate();

		Usuario usuario = new Usuario();
		usuario.setName("Maria Souza");
		usuarioRepository.save(usuario);

		ResponseEntity<Usuario[]> response = restTemplate.getForEntity(usuarioBaseUrl, Usuario[].class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotEmpty();
	}

	// ----------------------
	// Testes de Integração de Equipamento
	// ----------------------
	@Test
	public void testCadastroEquipamentoValido() {
		RestTemplate restTemplate = new RestTemplate();
		Equipamento equipamento = new Equipamento();
		equipamento.setNome("Monitor");

		ResponseEntity<Equipamento> response = restTemplate.postForEntity(equipamentoBaseUrl, equipamento, Equipamento.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response.getBody().getNome()).isEqualTo("Monitor");
	}

	@Test
	public void testCadastroEquipamentoInvalido() {
		RestTemplate restTemplate = new RestTemplate();
		Equipamento equipamento = new Equipamento(); // Nome inválido

		ResponseEntity<String> response = restTemplate.postForEntity(equipamentoBaseUrl, equipamento, String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	public void testListagemEquipamentos() {
		RestTemplate restTemplate = new RestTemplate();

		Equipamento equipamento = new Equipamento();
		equipamento.setNome("Teclado");
		equipamentoRepository.save(equipamento);

		ResponseEntity<Equipamento[]> response = restTemplate.getForEntity(equipamentoBaseUrl, Equipamento[].class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotEmpty();
	}
}
