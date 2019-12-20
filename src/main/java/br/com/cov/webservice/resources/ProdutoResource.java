package br.com.cov.webservice.resources;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.cov.webservice.model.domain.Produto;
import br.com.cov.webservice.resources.beans.ProdutoFilterBean;
import br.com.cov.webservice.service.ProdutoService;

@Path("/produtos")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class ProdutoResource {

    private ProdutoService service = new ProdutoService();

    @GET
    public List<Produto> getProdutos(@BeanParam ProdutoFilterBean produtoFilter) {
        if ( ( produtoFilter.getOffset() >= 0 ) && ( produtoFilter.getLimit() > 0 ) ) {
            return service.getProdutosByPagination(produtoFilter.getOffset(), produtoFilter.getLimit());
        } if ( produtoFilter.getName() != null ) {
            return service.getProdutoByName(produtoFilter.getName());
        }
        return service.getProdutos();
    }

    @GET
    @Path("{produtoId}")
    public Produto getProduto(@PathParam("produtoId") long id) {
        return service.getProduto(id);
    }

    @POST
    public Response save(Produto produto) {
        produto = service.saveProduto(produto);
        return  Response.status(Status.CREATED)
                .entity(produto)
                .build();
    }

    @PUT
    @Path("{produtoId}")
    public void update(@PathParam("produtoId") long id, Produto produto) {
        produto.setId(id);
         service.updateProduto(produto);
    }

    @DELETE
    @Path("{produtoId}")
    public void delete(@PathParam("produtoId") long id) {
        service.deleteProduto(id);
    }
}
