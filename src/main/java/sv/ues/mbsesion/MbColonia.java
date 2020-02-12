/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.ues.mbsesion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import sv.ues.dao.ColoniasDao;
import sv.ues.dominio.ColoniaCanton;
import sv.ues.dominio.Municipio;

/**
 *
 * @author Flor Diaz
 */
@ManagedBean(name = "mbColonia")
@ViewScoped
public class MbColonia implements Serializable{

    private String codMuniSele;
    private Integer codColoniaSele;
    private List<SelectItem> items_colonias;
    private ColoniaCanton coloniaSele;

    /**
     * Creates a new instance of MbColonia
     */
    public MbColonia() {
        codMuniSele = "";
        codColoniaSele = 0;
        coloniaSele = new ColoniaCanton();
    }
    
    //Este es un getter modificado
    public ColoniaCanton getColoniaSele() {
        ColoniasDao coloniasdao = new ColoniasDao();
        try {
            this.coloniaSele = coloniasdao.obtenerColoniaCanton_por_id(codColoniaSele);

        } catch (Exception ex) {
            //Logger.getLogger(MbColonia.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.coloniaSele;
    }

    public void setColoniaSele(ColoniaCanton coloniaSele) {
        this.coloniaSele = coloniaSele;
    }

    public Integer getCodColoniaSele() {
        return codColoniaSele;
    }

    public void setCodColoniaSele(Integer codColoniaSele) {
        this.codColoniaSele = codColoniaSele;
    }

    public String getCodMuniSele() {
        return codMuniSele;
    }

    public void setCodMuniSele(String codMuniSele) {
        this.codMuniSele = codMuniSele;
    }

    //este es un getter modifcado
    public List<SelectItem> getItems_colonias() {
        this.items_colonias = new ArrayList();
        ColoniasDao colonias = new ColoniasDao();

        Municipio muni_seleccionado = new Municipio();
        muni_seleccionado.setCodMunicipio(codMuniSele);

        List<ColoniaCanton> lista_colonias_por_muni = colonias.obtenerCantonesByMunicipio(muni_seleccionado);
        items_colonias.clear();
        for (ColoniaCanton col : lista_colonias_por_muni) {
            SelectItem item = new SelectItem(col.getIdColCan(), col.getNomUbicacion());
            items_colonias.add(item);
        }
        return items_colonias;
    }

    public void setItems_colonias(List<SelectItem> items_colonias) {
        this.items_colonias = items_colonias;
    }
    
    public void resetCodColonia(){
        codColoniaSele = 0;
    }
}
