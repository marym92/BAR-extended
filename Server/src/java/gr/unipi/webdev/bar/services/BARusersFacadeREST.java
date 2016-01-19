/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.unipi.webdev.bar.services;

import gr.unipi.webdev.bar.entities.*;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author mary
 */
@Stateless
@Path("gr.unipi.webdev.bar.entities.barusers")
public class BARusersFacadeREST extends AbstractFacade<BARusers> {

    @EJB
    private BARloginAttemptsFacadeREST laFacadeREST;
    private BARactiveUsersFacadeREST auFacadeREST;
    
    @PersistenceContext(unitName = "BAR_RestWSPU")
    private EntityManager em;

    public BARusersFacadeREST() {
        super(BARusers.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(BARusers entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, BARusers entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("remove/{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public BARusers find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<BARusers> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<BARusers> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    @POST
    @Path("/login")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String login(LoginData ld) throws Exception {
        String result = "-1";
        
        // Check username existence
        BARusers user = findByUsername(ld.username);
        
        if (user == null) {
            result = "-105"; // username not exist
            return result;
        }
        if (user.getLocked()) {
            result = "-101"; // locked user account
            return result;
        }
        
        // Create hash value of password
        String encrSalt = user.getEncryptSalt();
        //BASE64Decoder decoder = new BASE64Decoder();
        //byte[] salt = decoder.decodeBuffer(encrSalt);
        byte[] salt = DatatypeConverter.parseBase64Binary(encrSalt);
        String encrPass = SHAencrypt(ld.password, salt);
        
        BARloginAttempts logAtt = (BARloginAttempts) laFacadeREST.find(user.getUserID());
        
        // Verify password
        if (user.getPassword().equals(encrPass)) {
            
            // Update login attempts
            if (logAtt == null) {
                laFacadeREST.create(new BARloginAttempts(user.getUserID(), 0));
            } else {
                laFacadeREST.edit(user.getUserID(), new BARloginAttempts(user.getUserID(), 0));
            }
            
            List<BARactiveUsers> activeList = auFacadeREST.findAll();
            
            // Chooses a random userBarID, not in use
            Random rnd = new Random(System.nanoTime());
            int userBarId;
            
            do {
                userBarId = rnd.nextInt(999999) + 1;
            } while (auFacadeREST.getActiveUserBarId().contains(userBarId));
            
            // Add user to Active Users
            auFacadeREST.create(new BARactiveUsers(userBarId, ld.ip, ld.bridgedPk));
            
            result = "0"; // success
            return result;
        }
        
        if (logAtt == null) {
            laFacadeREST.create(new BARloginAttempts(user.getUserID(), 1));
        } else {
            laFacadeREST.edit(user.getUserID(), new BARloginAttempts(user.getUserID(), logAtt.getAttempts()+1));
            
            // login attempts >=3 -> user account=locked
            if (logAtt.getAttempts() >= 3) {
                int temp = em.createQuery("UPDATE BARusers SET locked=TRUE WHERE userID = '" + user.getUserID() + "'").executeUpdate();
            }
        }

        result = "-105"; // incorrect password
        return result;
    }
    
    @POST
    @Path("/register")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String register(SignupData sd) throws Exception {
        String result = "-1";
        
        // Check if password match
        if (!sd.password.equals(sd.passwordVer)) {
            result = "-201";
            return result;
        }
        
        if (!checkMail(sd.email)) {
            result = "-202";
            return result;
        }
        
        List<BARusers> users = super.findAll();
        
        for (BARusers u:users) {
            if (u.getUsername().equalsIgnoreCase(sd.username)) {
                result = "-203"; // username exists
                return result;
            }
            if (u.getEmail().equalsIgnoreCase(sd.email)) {
                result = "-204"; // email exists
                return result;
            }
        }
        
        // Create new salt, encypt password and encode salt
        byte[] salt = getSalt();
        String encrPass = SHAencrypt(sd.password, salt);
        String encrSalt = DatatypeConverter.printBase64Binary(salt);
        
        // Validate birthdate
        Date now = new Date();
        Date dateObj, minDate, maxDate;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            dateObj = sdf.parse(sd.birthdate);
        } catch (Exception e) {
            result = "-205";
            return result;
        }
        
        minDate = new Date(now.getYear()-99, Calendar.JANUARY, 1);
        maxDate = new Date(now.getYear()-18, Calendar.DECEMBER, 31);
        if (dateObj.before(minDate) || dateObj.after(maxDate)) {
            result = "-206";
            return result;
        }
        
        BARusers user = new BARusers(sd.username, encrPass, encrSalt, sd.email, dateObj, false);
        create(user);
         
        result = "0";
        return result;
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    private boolean checkMail(String email) {
        boolean verified;
        
        String regex = "\\A(?=[a-z0-9@.!#$%&'*+/=?^_`{|}~-]{6,254}\\z)" +
                        "(?=[a-z0-9!#$%&'*+/=?^_`{|}~-]{1,64}@)" +
                        "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*" +
                        "@(?:(?=[a-z0-9-]{1,63}\\.)[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+" +
                        "(?=[a-z0-9-]{1,63}\\z)[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\z";
        Pattern pattern = Pattern.compile(regex);
     
        Matcher matcher = pattern.matcher(email);
        verified = matcher.find();
        
        return verified;
    }
    
    private BARusers findByUsername(String username) {
        List<BARusers> allusers = findAll();
        BARusers user = null;
        
        for (BARusers u:allusers) {
            if (u.getUsername().equals(username)) {
                user = new BARusers(u.getUserID(), username, u.getPassword(), u.getEncryptSalt(), u.getEmail(), u.getBirthdate(), u.getLocked());
                break;
            }
        }
        
        return user;
    }
    
    private static String SHAencrypt (String password, byte[] salt) throws Exception {
        String encrPass = null;
        
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);
        byte[] hash = md.digest(password.getBytes("UTF-8"));
        
        encrPass = bytesToHex(hash);
        
        return encrPass;
    }
    
    private static byte[] getSalt() throws Exception {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        
        return salt;
    }
            
    public static String bytesToHex(byte[] b) {
        char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7',
                           '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        StringBuffer buf = new StringBuffer();
        for (int j=0; j<b.length; j++) {
           buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
           buf.append(hexDigit[b[j] & 0x0f]);
        }
        return buf.toString();
   }
    
}
