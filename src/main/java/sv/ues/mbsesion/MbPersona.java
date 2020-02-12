package sv.ues.mbsesion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.primefaces.PrimeFaces;
import org.springframework.security.crypto.bcrypt.BCrypt;
import sv.ues.dao.DepartamentoDao;
import sv.ues.dao.MunicipioDao;
import sv.ues.dao.PersonaDao;
import sv.ues.dominio.Departamento;
import sv.ues.dominio.Municipio;
import sv.ues.dominio.Persona;
import sv.ues.dominio.Rol;
import sv.ues.dominio.Usuario;
import org.primefaces.event.FlowEvent;
import sv.ues.dao.UsuarioDao;
import sv.ues.dominio.Cargo;
import sv.ues.utils.EnvioCorreos;

//@author Miguel Martinez


@ManagedBean(name="MbPersona")
@ViewScoped
public class MbPersona  implements Serializable
{
    
    private Persona persona;
    private Persona personaSeleccionada;
    private Municipio municipio;
    private Municipio municipiomodificar;
    private Departamento departamento;
    private Usuario usuario;
    private PersonaDao personaDao;
    private MunicipioDao municipioDao;
    private Rol rol;
    private Cargo cargo;
    private UsuarioDao usuarioDao;

    private List<SelectItem> items_municipio;
    private List<SelectItem> items_departamento;
    private List<Rol> listaroles;
    
    private Set rolset;
    
    private String codigodepartamento = "";
    private String codigomunicipio = "";
    private String listarolesconsulta = "";
    private String clave = "";
    private String hash = "";
   
    private Date fecha;
    private Date maximafecha;
    private Date minimafecha;
    
    private Calendar calendario;

    
    //CONSTRUCTOR DEL BEAN
    public MbPersona()
    {
        inicializarVariables();
    }
    
    //METODO PARA INICIALIZAR LAS VARIABLES GLOBALES
    private void inicializarVariables()
    {
        this.persona = new Persona();
        this.municipio = new Municipio();
        this.municipiomodificar = new Municipio();
        this.departamento = new Departamento();
        this.usuario = new Usuario();
        this.items_departamento = new ArrayList();
        this.items_municipio = new ArrayList();
        this.personaDao = new PersonaDao();
        this.rol = new Rol();
        this.rolset = new HashSet(0);
        this.cargo = new Cargo();
        this.usuarioDao = new UsuarioDao();
        this.municipioDao = new MunicipioDao();
        this.fecha = new Date();
        this.calendario = Calendar.getInstance();
        this.calendario.add(Calendar.YEAR, -18);
        this.maximafecha = calendario.getTime();
        this.calendario = Calendar.getInstance();
        this.calendario.add(Calendar.YEAR, -85);
        this.minimafecha = calendario.getTime();
        this.codigodepartamento = "";
        this.codigomunicipio = "";
        this.listarolesconsulta = "";
        this.clave = "";
        this.hash = "";
    }
    
    //METODO PARA REGISTRAR UNA PERSONA
    public void registrar()
    {
        try
        {
            this.usuario.setNomUsuario(this.persona.getPrimerNombre().substring(0,1).toLowerCase()+this.persona.getSegundoNombre().substring(0,1).toLowerCase()+this.persona.getPrimerApellido());
            this.usuario.setFechaRegistro(this.fecha);
            this.usuario.setFechaUltimaModificacion(this.fecha);
            this.usuario.setActivo(false);
            this.clave = generarPassword(8);
            this.usuario.setClave(BCrypt.hashpw(this.clave, BCrypt.gensalt()));
            this.hash = this.usuario.getClave()+""+System.nanoTime();
            this.usuario.setHash(this.hash);
            this.persona.setMunicipio(this.municipio);
            this.persona.setFechaRegistro(this.fecha);
            this.persona.setFechaUltimaModificacion(this.fecha);
            this.rol.setIdRol(6);
            this.rolset.add(this.rol);
            this.cargo.setIdCargo(5);
            this.usuario.setCargo(this.cargo);
            this.usuario.setRols(this.rolset);
            this.personaDao.registrar(this.persona,this.usuario);
            this.usuario = this.usuarioDao.obtener_usuario_hash(this.hash);

            //ESTE CODIGO MODIFICARLO, EL ASUNTO Y CUERPO DEL CORREO DEBE ESTAR EN LA BASE
            EnvioCorreos correos = new EnvioCorreos(this.persona.getCorreoPersona(), "Registro SIRDEETV", asunto_email(this.usuario.getNomUsuario(),this.clave,this.persona.getPrimerNombre().substring(0, 1).toUpperCase() + this.persona.getPrimerNombre().substring(1).toLowerCase(),this.persona.getPrimerApellido().substring(0, 1).toUpperCase() + this.persona.getPrimerApellido().substring(1).toLowerCase()));
            correos.start();
            
            inicializarVariables();
            PrimeFaces.current().ajax().update("F01");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Informacion","Persona registrada con exito")); 

        }
        catch(Exception x)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"",x.toString())); 
        }
    }
    
    
    public void modificar()
    {
        try
        {
            this.municipiomodificar.setCodMunicipio(this.codigomunicipio);
            this.personaSeleccionada.setMunicipio(this.municipiomodificar);
            this.personaSeleccionada.setFechaUltimaModificacion(this.fecha);
            this.personaDao.modificar(this.personaSeleccionada);
            
            inicializarVariables();
            PrimeFaces.current().ajax().update("registros");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Informacion","Persona modificada con exito")); 
            PrimeFaces.current().executeScript("PF('dialogoModificar').hide();");
        }
        catch(Exception x)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"",x.toString())); 
        }
        
    }
    
    public List<Persona> lista_personas()
    {
        try
        {
            return this.personaDao.obtener_personas();
        }
        catch(Exception x)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"",x.toString()));
            return null;
        }
    }
    
    

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public List<SelectItem> getItems_municipio() throws Exception 
    {
        this.items_municipio = new ArrayList();
        MunicipioDao municipios = new MunicipioDao();
        Departamento departamento_seleccionado = new Departamento();
        departamento_seleccionado.setCodDepto(codigodepartamento);
        List<Municipio> lista_municipios_por_dpto = municipios.obtener_municipios_por_id_del_departamento(departamento_seleccionado);
        this.items_municipio.clear();
        for(Municipio muni:lista_municipios_por_dpto)
        {
            SelectItem item = new SelectItem(muni.getCodMunicipio(),muni.getNomMunicipio());
            this.items_municipio.add(item);
        }
        return items_municipio;
    }

    public void setItems_municipio(List<SelectItem> items_municipio) {
        this.items_municipio = items_municipio;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public List<SelectItem> getItems_departamento() throws Exception 
    {
        this.items_departamento = new ArrayList();
        DepartamentoDao departamentos = new DepartamentoDao();
        List<Departamento> lista_departamentos = departamentos.obtener_todos_los_departamentos();
        this.items_departamento.clear();
        for(Departamento dep:lista_departamentos)
        {
            SelectItem item = new SelectItem(dep.getCodDepto(),dep.getNomDepto());
            this.items_departamento.add(item);
        }
        return items_departamento;
    }

    public void setItems_departamento(List<SelectItem> items_departamento) {
        this.items_departamento = items_departamento;
    }

    public String getCodigodepartamento() {
        return codigodepartamento;
    }

    public void setCodigodepartamento(String codigodepartamento) {
        this.codigodepartamento = codigodepartamento;
    }

    public String getCodigomunicipio() {
        return codigomunicipio;
    }
    
    public void setCodigomunicipio(String codigomunicipio) {
        this.codigomunicipio = codigomunicipio;
    }

    public void resetCodigoMunicipio() 
    {
        codigomunicipio = "";
    }
    
    public void asignarMunicipio() throws Exception 
    {
        this.municipio = this.municipioDao.obtener_municipio(this.codigomunicipio);
        if(this.municipio != null)
        {
            this.codigomunicipio = this.municipio.getCodMunicipio();
        }
        else
        {
            this.codigomunicipio = ""; 
        }
    }

    
 static String generarPassword(int n) 
    { 
        String AlphaNumericString = "ABCDEFGHJKMNPRSTUVWXYZ2356789abcdefghjkmnprstuvxyz$#%*+-&.-_"; 

        StringBuilder sb = new StringBuilder(n); 
  
        for (int i = 0; i < n; i++)
        { 

            int index = (int)(AlphaNumericString.length()* Math.random()); 
  
            sb.append(AlphaNumericString.charAt(index)); 
        } 
        return sb.toString(); 
    }
 
 private String asunto_email(String user, String password, String nombre, String apellido)
 {
     return "<h1>SIRDEETV</h1>\n" +
            "\n" +
            "<p>Bienvenid@ "+nombre+" " +apellido+", al Sistema Informático para Registro de Datos Epidemiológicos Ligados a Enfermedades Transmitidas por Vectores y su Evaluacion con Modelos Animales</p>\n" +
            "\n" +
            "<h4>Credenciales</h4>\n" +
            "<spam>Usuario: <strong>"+user+"</strong></spam><br/>\n" +
            "<spam>Contraseña: <strong>"+password+"</strong></spam>\n" +
            "\n" +
            "<br/><br/>\n" +
            "\n" +
            "<spam>- Se recomienda ingresar a SIRDEETV y cambiar la contraseña generada por defecto.</spam><br/>\n" +
            "<spam>- Si en algún momento olvida sus credenciales, contacte con el administrador de SIRDEETV.</spam>";
 }
 

    public Persona getPersonaSeleccionada() 
    {
        return personaSeleccionada;
    }

    public void setPersonaSeleccionada(Persona personaSeleccionada) 
    {
        this.personaSeleccionada = personaSeleccionada;
        this.codigomunicipio = personaSeleccionada.getMunicipio().getCodMunicipio();
        this.codigodepartamento = personaSeleccionada.getMunicipio().getDepartamento().getCodDepto();
        listarolesconsulta = "";
        try
        {
            this.personaSeleccionada.getUsuario().getRols().toArray();
            listaroles = new ArrayList<Rol>(this.personaSeleccionada.getUsuario().getRols());
            
            for(int i = 0 ;i<listaroles.size();i++)
            {
                listarolesconsulta = listarolesconsulta + listaroles.get(i).getNomRol();
                if(i+1!=listaroles.size())
                {
                    listarolesconsulta = listarolesconsulta +", ";
                }
            }
        }
        catch(Exception x)
        {
            System.out.println("Excepcion: " + x.toString());
        }
        
    }

    public String getListarolesconsulta() {
        return listarolesconsulta;
    }

    public void setListarolesconsulta(String listarolesconsulta) {
        this.listarolesconsulta = listarolesconsulta;
    }

    public List<Rol> getListaroles() {
        return listaroles;
    }

    public void setListaroles(List<Rol> listaroles) {
        this.listaroles = listaroles;
    }
    
    
    public static boolean validarDui(String dui) {
        //String mensaje = "";
        boolean mensaje = false;
        if (dui.length() < 9) {
            return false;//"Caracteres incompletos en el DUI";
        }
        if (dui.length() > 10) {
            return false;//"Demasiados caracteres en el DUI";
        }

        String strDigitos = dui.replace("-","").substring(0, 8);
        int verificador = 0;
        char verifi=Character.MIN_VALUE;
        if (dui.contains("-")) {
            verifi=dui.substring(9, 10).charAt(0);
            if(Character.isDigit(verifi)){
                verificador = Integer.parseInt(dui.substring(9, 10));
            }
            else{
                return false;//"El DUI no puede tener letras";
            }
        } else {
            verifi=dui.substring(8, 9).charAt(0);
            if(Character.isDigit(verifi)){
                verificador = Integer.parseInt(dui.substring(8, 9));
            }
            else{
                return false;//"El DUI no puede tener letras";
            }
        }
        char[] charDui = strDigitos.toCharArray();
        int j = 9;
        int digito = 0, suma = 0, multiplicacion = 0, validaDigitos;

        for (int i = 0; i < charDui.length; i++) {
            if (Character.isDigit(charDui[i])) {
                digito = Character.getNumericValue(charDui[i]);
                multiplicacion = digito * j;
                suma += multiplicacion;
                j--;
            } else {
                return false;//"El DUI contiene caracteres incorrectos";
            }
        }
        int mod = suma % 10;
        if(mod==0 && verificador==0){
            //mensaje="OK";
            mensaje = true;
        }else{
            if ((10 - mod) == verificador) {
                //mensaje = "OK";
                mensaje = true;
            } else {
                //mensaje = "DUI no válido, favor verificar";
                mensaje = false;
            }
        }


        return mensaje;
    }

    public static boolean validarNIT( String nit ){//Creamos metodo estatico para poderlo llamar en cualquier parte; pedimos como datos una cadena string donde se aloja el nit
        int calculo = 0;//Variable para llevar el control de la suma del algoritmo
        int digitos = Integer.parseInt(nit.substring(12, 15));//Tomamos los digitos que estan entre la posicion 12 y 15
        boolean resultado;
        
        if ( digitos <= 100 ) {//Verificamos que estos digitos sean menores o iguales a 100
            for ( int posicion = 0; posicion <= 14; posicion++ ) {//Ciclo que nos ayuda a ir aumentando la posicion que se utiliza posteriormente en el algoritmo
                if ( !( posicion == 4 || posicion == 11 ) ){
                    calculo += ( 14 * (int) ( Character.getNumericValue( nit.charAt( posicion ) ) ) );
                }//Si la posicion no es 4 ni 11 (que son los guiones) se ejecuta esta operacion
                calculo = calculo % 11;//Al calculo se le va sacando el modular de 11
            }
        } 
        else {
            int n = 1;//Variable contadora
            for ( int posicion = 0; posicion <= 14; posicion++ ){//Ciclo que nos ayuda a ir aumentando la posicion que se utiliza posteriormente en el algoritmo
                if ( !( posicion == 4 || posicion == 11 ) ){
                    calculo = (int) ( calculo + ( ( (int) Character.getNumericValue( nit.charAt( posicion ) ) ) * ( ( 3 + 6 * Math.floor( Math.abs( ( n + 4) / 6 ) ) ) - n ) ) );
                    n++;
                }//Si la posicion no es 4 ni 11 (que son los guiones) se ejecuta esta operacion
            }
            
            calculo = calculo % 11;//sacamos el modular 11 de calculo
            if ( calculo > 1 ){
                calculo = 11 - calculo;//Si el resultado nos da mayor a uno se le resta a 11 esta respuesta
            } else {
                calculo = 0;//Sino el calculo lo hacemos 0
            }
        }
        
        resultado = (calculo == (int) ( Character.getNumericValue( nit.charAt( 16 ) ) ) ); //Verificamos si el calculo es direfente del resultado de nuestro algoritmo, si lo es entonces es falso
        return resultado;//enviamos el resultado
    }
    
    //Este metodo controla el flujo del wizard de registro
    public String flujoResgistrar(FlowEvent event) throws Exception 
    {
        String cadena_validadora = "";
        
        //VALIDACION AVANZAR PERSONAL -> DOMICILIO
        if(event.getOldStep().equals("personal") && event.getNewStep().equals("domicilio"))
        {
            //VERIFICA SI ES UN DUI VALIDO
            if(validarDui(this.persona.getDui()) == true)
            {
                //VERIFICAR SI EL DUI YA EXISTE
                if(this.personaDao.obtener_persona_dui(this.persona.getDui()) != null)
                {
                    cadena_validadora = cadena_validadora + "B";
                }
            }
            else
            {
                cadena_validadora = cadena_validadora + "A";
            }
            
            if(validarNIT(this.persona.getNit()) == true)
            {
                //VERIFICAR SI EL NIT YA EXISTE
                if(this.personaDao.obtener_persona_nit(this.persona.getNit())!=null)
                {
                    cadena_validadora = cadena_validadora + "G";
                }
            }
            else
            {
                cadena_validadora = cadena_validadora + "F";
            }
        }
        
        //VERIFICAR QUE LA DIRECCION NO ESTE VACIA
        if(event.getOldStep().equals("domicilio") && event.getNewStep().equals("contacto"))
        {
            if(this.persona.getDireccionResidencia().trim().length() == 0)
            {
                cadena_validadora = cadena_validadora + "D";
            }
        }
        
        //VERIFICAR SI EL EMAIL YA EXISTE
        if(event.getOldStep().equals("contacto") && event.getNewStep().equals("confirmar"))
        {
            if(this.personaDao.obtener_persona_email(this.persona.getCorreoPersona())!=null)
            {
                cadena_validadora = cadena_validadora + "E";
            }
        }
        
        //MOSTRAR MENSAJES
        if(cadena_validadora.length() > 0)
        {
            if(cadena_validadora.contains("A"))
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"","DUI " + this.persona.getDui() + " invalido")); 
            }
            if(cadena_validadora.contains("B"))
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"","DUI " + this.persona.getDui() + " duplicado")); 
            }
            if(cadena_validadora.contains("C"))
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"","NIT " + this.persona.getNit() + " duplicado")); 
            }
            if(cadena_validadora.contains("D"))
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"","Digite la direccion de domicilio")); 
            }
            if(cadena_validadora.contains("E"))
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"","Email " + this.persona.getCorreoPersona() + " duplicado")); 
            }
            if(cadena_validadora.contains("F"))
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"","NIT " + this.persona.getNit() + " invalido")); 
            }
            if(cadena_validadora.contains("G"))
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"","NIT " + this.persona.getNit() + " duplicado")); 
            }
            return event.getOldStep();
        }
        else
        {
            return event.getNewStep();
        }
    }

    
    //Este metodo controla el flujo del wizard de modificacion
    public String flujoModificar(FlowEvent event) throws Exception 
    {
        //PersonaDao personaDao = new PersonaDao();
        String cadena_validadora = "";
        
        //VALIDACION AVANZAR PERSONAL -> DOMICILIO
        if(event.getOldStep().equals("personal") && event.getNewStep().equals("domicilio"))
        {
            //VERIFICA SI ES UN DUI VALIDO
            if(validarDui(this.personaSeleccionada.getDui()) == true)
            {
                //VERIFICAR SI EL DUI YA EXISTE
                if(this.personaDao.validar_persona_modificar_dui(this.personaSeleccionada.getDui(), this.personaSeleccionada.getIdPersona()) != false)
                {
                    cadena_validadora = cadena_validadora + "B";
                }
            }
            else
            {
                cadena_validadora = cadena_validadora + "A";
            }
            
            if(validarNIT(this.personaSeleccionada.getNit()) == true)
            {
                //VERIFICAR SI EL NIT YA EXISTE
                if(this.personaDao.validar_persona_modificar_nit(this.personaSeleccionada.getNit(),this.personaSeleccionada.getIdPersona())!=false)
                {
                    cadena_validadora = cadena_validadora + "G";
                }
            }
            else
            {
                cadena_validadora = cadena_validadora + "F";
            }
        }
        
        //VERIFICAR QUE LA DIRECCION NO ESTE VACIA
        if(event.getOldStep().equals("domicilio") && event.getNewStep().equals("contacto"))
        {
            if(this.personaSeleccionada.getDireccionResidencia().trim().length() == 0)
            {
                cadena_validadora = cadena_validadora + "D";
            }
        }
        
        //VERIFICAR SI EL EMAIL YA EXISTE
        if(event.getOldStep().equals("contacto") && event.getNewStep().equals("confirmar"))
        {
            if(this.personaDao.validar_persona_modificar_email(this.personaSeleccionada.getCorreoPersona(),this.personaSeleccionada.getIdPersona())!=false)
            {
                cadena_validadora = cadena_validadora + "E";
            }
        }
        
        //MOSTRAR MENSAJES
        if(cadena_validadora.length() > 0)
        {
            if(cadena_validadora.contains("A"))
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"","DUI " + this.personaSeleccionada.getDui() + " invalido")); 
            }
            if(cadena_validadora.contains("B"))
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"","DUI " + this.personaSeleccionada.getDui() + " duplicado")); 
            }
            if(cadena_validadora.contains("C"))
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"","NIT " + this.personaSeleccionada.getNit() + " duplicado")); 
            }
            if(cadena_validadora.contains("D"))
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"","Digite la direccion de domicilio")); 
            }
            if(cadena_validadora.contains("E"))
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"","Email " + this.personaSeleccionada.getCorreoPersona() + " duplicado")); 
            }
            if(cadena_validadora.contains("F"))
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"","NIT " + this.personaSeleccionada.getNit() + " invalido")); 
            }
            if(cadena_validadora.contains("G"))
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"","NIT " + this.personaSeleccionada.getNit() + " duplicado")); 
            }
            return event.getOldStep();
        }
        else
        {
            return event.getNewStep();
        }
    }
    
    
    public Date getMaximafecha() {
        return maximafecha;
    }

    public void setMaximafecha(Date maximafecha) {
        this.maximafecha = maximafecha;
    }

    public Date getMinimafecha() {
        return minimafecha;
    }

    public void setMinimafecha(Date minimafecha) {
        this.minimafecha = minimafecha;
    }
    
     public void resetCodigoDepto(){
	codigodepartamento ="";
}
public void resetCodigos(){
	resetCodigoDepto();
	resetCodigoMunicipio();
}
    
 
}
