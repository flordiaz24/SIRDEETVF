/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.ues.utils;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import sv.ues.dao.PerfilInvDao;
import sv.ues.dominio.PerfilInv;

/**
 *
 * @author PC
 */
@ManagedBean
public class ConverterPerfil implements Converter{
    
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        try{
            
            PerfilInvDao perfilInvDao=new PerfilInvDao();
            if(value == null || value.length() == 0 || JsfUtil.isDummySelectItem(uic, value)){
                return null;
            }
            PerfilInv inv=perfilInvDao.getInvestigacionById(Integer.parseInt(value));
          
            
            return inv;
        }catch(Throwable ex){
            return null;  
        }
    }

   String getStringKey(java.lang.Integer value) {
        StringBuffer sb = new StringBuffer();
        sb.append(value);
        return sb.toString();
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null
                || (object instanceof String && ((String) object).length() == 0)) {
            return null;
        }
        if (object instanceof PerfilInv) {
            PerfilInv o = (PerfilInv) object;
            return getStringKey(o.getIdPerfil());
        } else {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PerfilInv.class.getName()});
            return null;
        }
    }
    
}
