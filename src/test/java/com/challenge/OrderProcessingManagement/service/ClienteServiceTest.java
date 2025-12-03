package com.challenge.OrderProcessingManagement.service;

import com.challenge.OrderProcessingManagement.dto.ClienteDTO;
import com.challenge.OrderProcessingManagement.exception.ResourceNotFoundException;
import com.challenge.OrderProcessingManagement.model.Cliente;
import com.challenge.OrderProcessingManagement.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;
    private ClienteDTO clienteDTO;

    @BeforeEach
    void setUp() {
        cliente = Cliente.builder()
                .id(1L)
                .nome("João Silva")
                .email("joao@email.com")
                .cpf("123.456.789-00")
                .dataCriacao(LocalDateTime.now())
                .build();

        clienteDTO = ClienteDTO.builder()
                .nome("João Silva")
                .email("joao@email.com")
                .cpf("123.456.789-00")
                .build();
    }

    @Test
    void deveCriarClienteComSucesso() {
        when(clienteRepository.existsByEmail(clienteDTO.getEmail())).thenReturn(false);
        when(clienteRepository.existsByCpf(clienteDTO.getCpf())).thenReturn(false);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        ClienteDTO resultado = clienteService.criar(clienteDTO);

        assertNotNull(resultado);
        assertEquals(cliente.getNome(), resultado.getNome());
        assertEquals(cliente.getEmail(), resultado.getEmail());
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void deveLancarExcecaoQuandoEmailJaExiste() {
        when(clienteRepository.existsByEmail(clienteDTO.getEmail())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> clienteService.criar(clienteDTO));
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    void deveLancarExcecaoQuandoCpfJaExiste() {
        when(clienteRepository.existsByEmail(clienteDTO.getEmail())).thenReturn(false);
        when(clienteRepository.existsByCpf(clienteDTO.getCpf())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> clienteService.criar(clienteDTO));
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    void deveBuscarClientePorId() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        ClienteDTO resultado = clienteService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(cliente.getId(), resultado.getId());
        assertEquals(cliente.getNome(), resultado.getNome());
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoEncontrado() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clienteService.buscarPorId(1L));
    }

    @Test
    void deveListarTodosClientes() {
        List<Cliente> clientes = Arrays.asList(cliente);
        when(clienteRepository.findAll()).thenReturn(clientes);

        List<ClienteDTO> resultado = clienteService.listarTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(cliente.getNome(), resultado.get(0).getNome());
    }
}

