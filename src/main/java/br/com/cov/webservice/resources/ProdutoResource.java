package br.com.cov.webservice.resources;

import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.cov.webservice.model.domain.Produto;
import br.com.cov.webservice.service.ProdutoService;

@Path("/produtos")
public class ProdutoResource {

    private ProdutoService service = new ProdutoService();

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public List<Produto> getProdutos() {
        return service.getProdutos();
    }

    @GET
    @Path("{produtoId}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Produto getProduto(@PathParam("produtoId") long id) {
        return service.getProduto(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Produto save(Produto produto) {
        return service.saveProduto(produto);
    }

    @PUT
    @Path("{produtoId}")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Produto update(@PathParam("produtoId") long id, Produto produto) {
        produto.setId(id);
        return service.updateProduto(produto);
    }

    @DELETE
    @Path("{produtoId}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Produto delete(@PathParam("produtoId") long id) {
        return service.deleteProduto(id);
    }
}
