package com.challenge.OrderProcessingManagement.service;

import com.challenge.OrderProcessingManagement.dto.AtualizarStatusPedidoDTO;
import com.challenge.OrderProcessingManagement.dto.ItemPedidoDTO;
import com.challenge.OrderProcessingManagement.dto.PedidoDTO;
import com.challenge.OrderProcessingManagement.exception.ResourceNotFoundException;
import com.challenge.OrderProcessingManagement.model.*;
import com.challenge.OrderProcessingManagement.repository.ClienteRepository;
import com.challenge.OrderProcessingManagement.repository.PedidoRepository;
import com.challenge.OrderProcessingManagement.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    private Cliente cliente;
    private Produto produto;
    private Pedido pedido;
    private PedidoDTO pedidoDTO;

    @BeforeEach
    void setUp() {
        cliente = Cliente.builder()
                .id(1L)
                .nome("João Silva")
                .email("joao@email.com")
                .cpf("123.456.789-00")
                .build();

        produto = Produto.builder()
                .id(1L)
                .nome("Produto Teste")
                .preco(new BigDecimal("99.99"))
                .quantidadeEstoque(100)
                .build();

        pedido = Pedido.builder()
                .id(1L)
                .cliente(cliente)
                .status(StatusPedido.PENDENTE)
                .valorTotal(new BigDecimal("99.99"))
                .dataCriacao(LocalDateTime.now())
                .build();

        ItemPedidoDTO itemDTO = ItemPedidoDTO.builder()
                .produtoId(1L)
                .quantidade(1)
                .build();

        pedidoDTO = PedidoDTO.builder()
                .clienteId(1L)
                .itens(Arrays.asList(itemDTO))
                .build();
    }

    @Test
    void deveCriarPedidoComSucesso() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);

        PedidoDTO resultado = pedidoService.criar(pedidoDTO);

        assertNotNull(resultado);
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
        verify(produtoRepository, times(1)).save(any(Produto.class));
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoExiste() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> pedidoService.criar(pedidoDTO));
    }

    @Test
    void deveLancarExcecaoQuandoEstoqueInsuficiente() {
        produto.setQuantidadeEstoque(0);
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        assertThrows(IllegalArgumentException.class, () -> pedidoService.criar(pedidoDTO));
    }

    @Test
    void deveBuscarPedidoPorId() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        PedidoDTO resultado = pedidoService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(pedido.getId(), resultado.getId());
    }

    @Test
    void deveAtualizarStatusPedido() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);

        AtualizarStatusPedidoDTO statusDTO = AtualizarStatusPedidoDTO.builder()
                .status(StatusPedido.CONFIRMADO)
                .build();

        PedidoDTO resultado = pedidoService.atualizarStatus(1L, statusDTO);

        assertNotNull(resultado);
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    void deveListarPedidosPorCliente() {
        List<Pedido> pedidos = Arrays.asList(pedido);
        when(pedidoRepository.findByClienteId(1L)).thenReturn(pedidos);

        List<PedidoDTO> resultado = pedidoService.listarPorCliente(1L);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }
}

