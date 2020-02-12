/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.ues.utils;

import java.util.Properties;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Miguel
 */
public class EnvioCorreos extends Thread 
{
    public String destinatario;
    public String asunto;
    public String cuerpo;
    
    public EnvioCorreos(String Destinatario,String Asunto,String Cuerpo)
    {
        this.destinatario = Destinatario;
        this.asunto = Asunto;
        this.cuerpo = Cuerpo;
    }
    
    
    @Override
    public void run() 
    {
        try
        {
           String remitente = "mm09255@ues.edu.sv";
           Properties props = System.getProperties();
           props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
           props.put("mail.smtp.user", remitente);
           props.put("mail.smtp.clave", "miguel751991");    //La clave de la cuenta
           props.put("mail.smtp.auth", "true");    //Usar autenticaci�n mediante usuario y clave
           props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
           props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google

           Session session = Session.getDefaultInstance(props);
           MimeMessage message = new MimeMessage(session);
           //message.setDataHandler(new DataHandler(new HTMLDataSource(cuerpo)));
           Address address = new InternetAddress(destinatario);
           try {
              message.setFrom(new InternetAddress(remitente));
              message.addRecipient(Message.RecipientType.TO, address);   //Se podr�an a�adir varios de la misma manera
              message.setSubject(asunto);
              //message.setText(cuerpo);
              message.setContent(cuerpo,"text/html");
              Transport transport = session.getTransport("smtp");
              transport.connect("smtp.gmail.com", remitente, "miguel751991");
              transport.sendMessage(message, message.getAllRecipients());
              transport.close();
           }
           catch (MessagingException me) 
           {
              me.printStackTrace();   //Si se produce un error
              //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia","Sin conexion, el email no ha sido enviado")); 
           }
        }
        catch(Exception x)
        {
           //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia","Sin conexion, el email no ha sido enviado")); 
        }
    }

}
