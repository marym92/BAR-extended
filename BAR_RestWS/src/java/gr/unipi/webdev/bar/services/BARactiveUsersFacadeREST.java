/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.unipi.webdev.bar.services;

import gr.unipi.webdev.bar.entities.BARactiveUsers;
import java.util.ArrayList;
import java.util.List;
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
@Path("gr.unipi.webdev.bar.entities.baractiveusers")
public class BARactiveUsersFacadeREST extends AbstractFacade<BARactiveUsers> {

    @PersistenceContext(unitName = "BAR_RestWSPU")
    private EntityManager em;

    public BARactiveUsersFacadeREST() {
        super(BARactiveUsers.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(BARactiveUsers entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, BARactiveUsers entity) {
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
    public BARactiveUsers find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<BARactiveUsers> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<BARactiveUsers> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    @GET
    @Path("/getActiveUsers")
    @Produces({"application/json"})
    public List<BARactiveUsers> getActiveUsers() {
        List<BARactiveUsers> activeUsers = super.findAll();
       
        return activeUsers;
    }
    
    @GET
    @Path("/logout/{userBarID}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public String logout(@PathParam("userBarID") String userBarID) {
        String result = "-1";
        int ubID = Integer.parseInt(userBarID);
        
        List<Integer> allActiveBarIds = getActiveUserBarId();
        if (allActiveBarIds.isEmpty()) {
            result = "-308";
            return result;
        }
        if (!(allActiveBarIds.contains(ubID))) {
            result = "-308";
            return result;
        }
        
        remove(ubID);
        
        result = "0";
        return result;
    }

    public List getActiveUserBarId() {
        List<BARactiveUsers> allActiveUsers = super.findAll();
        ArrayList<Integer> allActiveIds = new ArrayList<>();
        
        for (BARactiveUsers u : allActiveUsers) {
            allActiveIds.add(u.getUserBarID());
        }
        
        return allActiveIds;
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
