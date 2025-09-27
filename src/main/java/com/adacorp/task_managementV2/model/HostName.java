package com.adacorp.task_managementV2.model;

import com.adacorp.task_managementV2.controller.HomeController;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
//import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Michael NYOBE
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HostName {
    private String hostname;

    /**
     * Logger de la classe HomeController
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(HostName.class);


    /** Création de la méthode  */
    public String getPosteClient(HttpServletRequest request){

        String hostNamePoste=null;
        try {
            // Trouver l'addresse IP du Client Connecté
            String ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null) {
                ipAddress = request.getHeader("X_FORWARDED_FOR");
                if (ipAddress == null){
                    ipAddress = request.getRemoteAddr();
                }
            }

            InetAddress inetAddr = InetAddress.getByName(""+ipAddress);

            // Get the host name
            String hostname = inetAddr.getHostName();

            // Get canonical host name
            String canonicalHostname = inetAddr.getCanonicalHostName();

            //  Observer dans la console GlassFish
            LOGGER.info("ipAdress: {} ", ipAddress);
            LOGGER.info("Hostname: {} ", hostname);
            LOGGER.info("Canonical Hostname: {} ", canonicalHostname);
            //  Fin Observation

            hostNamePoste = hostname;

            LOGGER.info(hostNamePoste);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hostNamePoste;
    }

}

