/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.unipi.webdev.bar.services;

import gr.unipi.webdev.bar.entities.BARcontacts;
import gr.unipi.webdev.bar.entities.BARcontactsPK;
import gr.unipi.webdev.bar.entities.BARnymUsers;
import gr.unipi.webdev.bar.entities.ContactsPairingData;
import java.util.List;
import javax.ejb.EJB;
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
import javax.ws.rs.core.PathSegment;

/**
 *
 * @author mary
 */
@Stateless
@Path("gr.unipi.webdev.bar.entities.barcontacts")
public class BARcontactsFacadeREST extends AbstractFacade<BARcontacts> {

    @EJB
    private BARnymUsersFacadeREST nuFacadeREST;
    
    @PersistenceContext(unitName = "BAR_RestWSPU")
    private EntityManager em;

    private BARcontactsPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;pseudonym=pseudonymValue;data=dataValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        gr.unipi.webdev.bar.entities.BARcontactsPK key = new gr.unipi.webdev.bar.entities.BARcontactsPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> pseudonym = map.get("pseudonym");
        if (pseudonym != null && !pseudonym.isEmpty()) {
            key.setPseudonym(pseudonym.get(0));
        }
        java.util.List<String> data = map.get("data");
        if (data != null && !data.isEmpty()) {
            key.setData(data.get(0));
        }
        return key;
    }

    public BARcontactsFacadeREST() {
        super(BARcontacts.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(BARcontacts entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") PathSegment id, BARcontacts entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        gr.unipi.webdev.bar.entities.BARcontactsPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public BARcontacts find(@PathParam("id") PathSegment id) {
        gr.unipi.webdev.bar.entities.BARcontactsPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<BARcontacts> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<BARcontacts> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    @GET
    @Path("/getContacts")
    @Produces({"application/json"})
    public List<BARcontacts> getContacts() {
        List<BARcontacts> allcontacts = super.findAll();
       
        return allcontacts;
    }
    
    @POST
    @Path("/addToContacts")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String addToContacts(ContactsPairingData cpd) throws Exception {
        String result = "-1";
        
        List<BARnymUsers> nymusers = nuFacadeREST.findAll();

        for (BARnymUsers u:nymusers) {
            if (u.getPseudonym().equals(cpd.pseudonym)) {
                BARcontacts c = new BARcontacts(cpd.pseudonym, cpd.data);
                create(c);

                result = "0";
                return result;
            }
            
        }
        
        result = "-601";
        return result;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
