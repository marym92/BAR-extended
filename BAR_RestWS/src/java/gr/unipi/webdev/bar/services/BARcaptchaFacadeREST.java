/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.unipi.webdev.bar.services;

import gr.unipi.webdev.bar.entities.BARcaptcha;
import java.util.List;
import java.util.Random;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author mary
 */
@Stateless
@Path("gr.unipi.webdev.bar.entities.barcaptcha")
public class BARcaptchaFacadeREST extends AbstractFacade<BARcaptcha> {

    private static final int MAXCAPTCHA = 1000;
    
    @PersistenceContext(unitName = "BAR_RestWSPU")
    private EntityManager em;

    public BARcaptchaFacadeREST() {
        super(BARcaptcha.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(BARcaptcha entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, BARcaptcha entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public BARcaptcha find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<BARcaptcha> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<BARcaptcha> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    @GET
    @Path("/getRandomCaptcha")
    @Produces({"application/json"})
    public BARcaptcha getRandomCaptcha() {
        BARcaptcha c = new BARcaptcha();
        
        // Chooses a random captcha ID
        Random rnd = new Random(System.nanoTime());
        
        // Create a new Captcha object
        c.setCID(rnd.nextInt(MAXCAPTCHA));
        c.setCaptcha(find(c.getCID()).getCaptcha());
        
        return c;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
