package com.challenge.OrderProcessingManagement.service;

import com.challenge.OrderProcessingManagement.dto.ProdutoDTO;
import com.challenge.OrderProcessingManagement.exception.ResourceNotFoundException;
import com.challenge.OrderProcessingManagement.model.Produto;
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
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    private Produto produto;
    private ProdutoDTO produtoDTO;

    @BeforeEach
    void setUp() {
        produto = Produto.builder()
                .id(1L)
                .nome("Produto Teste")
                .descricao("Descrição do produto")
                .preco(new BigDecimal("99.99"))
                .quantidadeEstoque(100)
                .dataCriacao(LocalDateTime.now())
                .dataAtualizacao(LocalDateTime.now())
                .build();

        produtoDTO = ProdutoDTO.builder()
                .nome("Produto Teste")
                .descricao("Descrição do produto")
                .preco(new BigDecimal("99.99"))
                .quantidadeEstoque(100)
                .build();
    }

    @Test
    void deveCriarProdutoComSucesso() {
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        ProdutoDTO resultado = produtoService.criar(produtoDTO);

        assertNotNull(resultado);
        assertEquals(produto.getNome(), resultado.getNome());
        assertEquals(produto.getPreco(), resultado.getPreco());
        verify(produtoRepository, times(1)).save(any(Produto.class));
    }

    @Test
    void deveBuscarProdutoPorId() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        ProdutoDTO resultado = produtoService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(produto.getId(), resultado.getId());
        assertEquals(produto.getNome(), resultado.getNome());
    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoEncontrado() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> produtoService.buscarPorId(1L));
    }

    @Test
    void deveListarTodosProdutos() {
        List<Produto> produtos = Arrays.asList(produto);
        when(produtoRepository.findAll()).thenReturn(produtos);

        List<ProdutoDTO> resultado = produtoService.listarTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(produto.getNome(), resultado.get(0).getNome());
    }

    @Test
    void deveAtualizarProduto() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        ProdutoDTO atualizado = ProdutoDTO.builder()
                .nome("Produto Atualizado")
                .descricao("Nova descrição")
                .preco(new BigDecimal("149.99"))
                .quantidadeEstoque(50)
                .build();

        ProdutoDTO resultado = produtoService.atualizar(1L, atualizado);

        assertNotNull(resultado);
        verify(produtoRepository, times(1)).save(any(Produto.class));
    }

    @Test
    void deveExcluirProduto() {
        when(produtoRepository.existsById(1L)).thenReturn(true);

        produtoService.excluir(1L);

        verify(produtoRepository, times(1)).deleteById(1L);
    }

    @Test
    void deveLancarExcecaoAoExcluirProdutoInexistente() {
        when(produtoRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> produtoService.excluir(1L));
        verify(produtoRepository, never()).deleteById(anyLong());
    }
}

