/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.unipi.webdev.bar.services;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author mary
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(gr.unipi.webdev.bar.services.BARactiveUsersFacadeREST.class);
        resources.add(gr.unipi.webdev.bar.services.BARcaptchaFacadeREST.class);
        resources.add(gr.unipi.webdev.bar.services.BARcontactsFacadeREST.class);
        resources.add(gr.unipi.webdev.bar.services.BARloginAttemptsFacadeREST.class);
        resources.add(gr.unipi.webdev.bar.services.BARnymUsersFacadeREST.class);
        resources.add(gr.unipi.webdev.bar.services.BARsystemParamsFacadeREST.class);
        resources.add(gr.unipi.webdev.bar.services.BARusersFacadeREST.class);
    }
    
}
