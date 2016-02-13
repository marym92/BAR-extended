/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.unipi.webdev.bar.services;

import gr.unipi.webdev.bar.entities.BARnymUsers;
import gr.unipi.webdev.bar.entities.BarSignupData;
import gr.unipi.webdev.bar.entities.SentNymUsers;
import static gr.unipi.webdev.bar.security.CoordinatorKeys.getCoordiPK;
import static gr.unipi.webdev.bar.security.CoordinatorKeys.getCoordiSK;
import gr.unipi.webdev.bar.security.RSAdecrypt;
import static gr.unipi.webdev.bar.security.RSAdecrypt.RSAdecSignup;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.util.ArrayList;
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

/**
 *
 * @author mary
 */
@Stateless
@Path("gr.unipi.webdev.bar.entities.barnymusers")
public class BARnymUsersFacadeREST extends AbstractFacade<BARnymUsers> {

    private static String signAlgo = "SHA256withRSA";
    
    @EJB
    private BARusersFacadeREST uFacadeREST;
    
    @PersistenceContext(unitName = "BAR_RestWSPU")
    private EntityManager em;

    public BARnymUsersFacadeREST() {
        super(BARnymUsers.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(BARnymUsers entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, BARnymUsers entity) {
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
    public BARnymUsers find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<BARnymUsers> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<BARnymUsers> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    @POST
    @Path("/register-bar")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String registerBar(String encData) throws Exception {
        String result = "-1";
        
        BarSignupData bsd = RSAdecSignup(encData);
        
        int userID = Integer.parseInt(bsd.userID);
        if (uFacadeREST.find(userID) == null) {
            result = "-503";
            return result;
        }
        
        List<BARnymUsers> nymUsers = super.findAll();
        
        for (BARnymUsers u:nymUsers) {
            if (u.getPseudonym().equalsIgnoreCase(bsd.pseudonym)) {
                result = "-501"; // pseudonym exists
                return result;
            }
            if (u.getPk().equalsIgnoreCase(bsd.pk)) {
                result = "-502"; // pk exists
                return result;
            }
        }
        
        byte[] sig = signData(bsd.pseudonym, bsd.pk);
        
        BARnymUsers nymUser = new BARnymUsers(userID, bsd.pseudonym, bsd.pk, sig);
        create(nymUser);
         
        result = "0";
        return result;
    }
    
    @GET
    @Path("/verify-sign/{userID}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String verifySign(@PathParam("userID") Integer id) throws Exception {
        String result = "-1";
        
        if (uFacadeREST.find(id) == null) {
            result = "-503";
            return result;
        }
        
        BARnymUsers nymUser = super.find(id);
        PublicKey publicKey = getCoordiPK();
        
        // Verifying the Signature
        Signature myVerifySign = Signature.getInstance(signAlgo);
        myVerifySign.initVerify(publicKey);
        
        // Get hash function of data
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        String data = nymUser.getPseudonym() + "-" + nymUser.getPk();

        md.update(data.getBytes("UTF-8"));
        byte[] digest = md.digest();
        
        myVerifySign.update(digest);

        boolean verifySign = myVerifySign.verify(nymUser.getSig());
        if (verifySign == false) {
            System.out.println(" Error in validating Signature ");
            result = "-800";
            return result;
        }
        else
            System.out.println(" Successfully validated Signature ");
        
        result = "0";
        return result;
    }
    
    @GET
    @Path("/getNymUsers")
    @Produces({"application/json"})
    public List<SentNymUsers> getNymUsers() {
        List<BARnymUsers> allbnu = super.findAll();
        List<SentNymUsers> allnymusers = new ArrayList<>();
        
        for (BARnymUsers nu: allbnu) {
            SentNymUsers snu = new SentNymUsers(nu.getPseudonym(), nu.getPk());
            // SentNymUsers snu = new SentNymUsers(nu.getPseudonym(), nu.getPk(), nu.getSig());
            allnymusers.add(snu);
        }
        
        return allnymusers;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    private byte[] signData(String pseudonym, String pk) throws Exception {
        PrivateKey privateKey = getCoordiSK();
       
        // initialize the signature with signature algorithm and private key 
        Signature signature = Signature.getInstance(signAlgo);
        signature.initSign(privateKey, new SecureRandom());
       
        // Get hash function of data
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        String data = pseudonym + "-" + pk;

        md.update(data.getBytes("UTF-8"));
        byte[] digest = md.digest();
       
        // update signature with data to be signed 
        signature.update(digest);
       
        // sign the data
        byte[] signBytes = signature.sign();
       
        return signBytes;
    }
    
}
