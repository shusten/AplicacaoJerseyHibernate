package br.com.cov.webservice.model.dao;

import br.com.cov.webservice.model.domain.Produto;
import br.com.cov.webservice.model.exceptions.DAOException;
import br.com.cov.webservice.model.exceptions.ErrorCode;

import javax.persistence.EntityManager;
import java.util.List;

public class ProdutoDAO {
    public List<Produto> getAll() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Produto> produtos = null;

        try {
            produtos = em.createQuery("select p from Produto p", Produto.class).getResultList();
        } catch ( RuntimeException ex ) {
            throw new DAOException("Erro ao recuperar todos os produtos do banco: " + ex.getMessage(), ErrorCode.SERVER_ERROR.getCode());
        } finally {
            em.close();
        }
        return produtos;
    }

    public Produto getById(long id) {
        EntityManager em = JPAUtil.getEntityManager();
        Produto produto = null;

        if ( id <= 0 ) {
            throw new DAOException("O id precisa ser maior do que 0.", ErrorCode.BAD_REQUEST.getCode());
        }
        try {
            produto = em.find(Produto.class, id);
        } catch ( RuntimeException ex ) {
            throw new DAOException("Erro ao buscar produto por id no banco de dados: " + ex.getMessage(), ErrorCode.SERVER_ERROR.getCode());
        } finally {
            em.close();
        }
        if ( produto == null ) {
            throw new DAOException("Produto de id " + id + " nao existe.", ErrorCode.NOT_FOUND.getCode());
        }
        return produto;
    }

    public Produto save(Produto produto) {
        EntityManager em = JPAUtil.getEntityManager();

        if ( !produtoIsValid(produto) ) {
            throw new DAOException("Produto com dados incompletos.", ErrorCode.BAD_REQUEST.getCode());
        }
        try {
            em.getTransaction().begin();
            em.persist(produto);
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            em.getTransaction().rollback();
            throw new DAOException("Erro ao salvar produto no banco de dados: " + ex.getMessage(), ErrorCode.SERVER_ERROR.getCode());
        } finally {
            em.close();
        }
        return produto;
    }

    public Produto update(Produto produto) {
        EntityManager em = JPAUtil.getEntityManager();
        Produto produtoManaged = null;

        if ( produto.getId() <= 0 ) {
            throw new DAOException("O id precisa ser maior do que 0.", ErrorCode.BAD_REQUEST.getCode());
        }
        if ( !produtoIsValid(produto) ) {
            throw new DAOException("Produto com dados incompletos.", ErrorCode.BAD_REQUEST.getCode());
        }

        try {
            em.getTransaction().begin();
            produtoManaged = em.find(Produto.class, produto.getId());
            produtoManaged.setNome(produto.getNome());
            produtoManaged.setQuantidade(produto.getQuantidade());
            em.getTransaction().commit();
        } catch (NullPointerException ex) {
            em.getTransaction().rollback();
            throw new DAOException("Produto informado para atualizacao nao existe: " + ex.getMessage(), ErrorCode.NOT_FOUND.getCode());
        } catch (RuntimeException ex) {
            em.getTransaction().rollback();
            throw new DAOException("Erro ao atualizar produto no banco de dados: " + ex.getMessage(), ErrorCode.SERVER_ERROR.getCode());
        }finally {
            em.close();
        }

        return produtoManaged;
    }

    public Produto delete(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        Produto produto = null;
        if ( id <= 0 ) {
            throw new DAOException("O id precisa ser maior do que 0.", ErrorCode.BAD_REQUEST.getCode());
        }

        try {
            em.getTransaction().begin();
            produto = em.find(Produto.class, id);
            em.remove(produto);
            em.getTransaction().commit();
        } catch (IllegalArgumentException ex) {
            em.getTransaction().rollback();
            throw new DAOException("Produto informado para remocao nao existe: " + ex.getMessage(), ErrorCode.NOT_FOUND.getCode());
        } catch (RuntimeException ex) {
            em.getTransaction().rollback();
            throw new DAOException("Erro ao remover produto do banco de dados: " + ex.getMessage(), ErrorCode.SERVER_ERROR.getCode());
        }finally {
            em.close();
        }
        return produto;
    }

    private boolean produtoIsValid(Produto produto) {
        try {
            if ( (produto.getNome().isEmpty()) || (produto.getQuantidade() < 0) )
                return false;
        } catch (NullPointerException ex) {
            throw new DAOException("Produto com dados incompletos.",
                    ErrorCode.BAD_REQUEST.getCode());
        }

        return true;
    }

}
