/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.ues.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;


/**
 *
 * @author PC
 */
public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{
    
    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String role = auth.getAuthorities().toString();

		String targetUrl = null;

		if (role.contains("ADMIN")) {
			targetUrl = "/faces/roles/gestionRoles.xhtml";
		} else if (role.contains("USUARIO")) {
			targetUrl = "/vistas/usuario/usuarios.xhtml";
		}

		return targetUrl;
	}

}
