package sv.ues.dominio;
// Generated 10-28-2019 09:05:07 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * TipoMuestra generated by hbm2java
 */
public class TipoMuestra  implements java.io.Serializable {


     private Integer idTipoMues;
     private String nomMuestra;
     private Set muestras = new HashSet(0);

    public TipoMuestra() {
    }

	
    public TipoMuestra(Integer idTipoMues, String nomMuestra) {
        this.idTipoMues = idTipoMues;
        this.nomMuestra = nomMuestra;
    }
    public TipoMuestra(Integer idTipoMues, String nomMuestra, Set muestras) {
       this.idTipoMues = idTipoMues;
       this.nomMuestra = nomMuestra;
       this.muestras = muestras;
    }
   
    public Integer getIdTipoMues() {
        return this.idTipoMues;
    }
    
    public void setIdTipoMues(Integer idTipoMues) {
        this.idTipoMues = idTipoMues;
    }
    public String getNomMuestra() {
        return this.nomMuestra;
    }
    
    public void setNomMuestra(String nomMuestra) {
        this.nomMuestra = nomMuestra;
    }
    public Set getMuestras() {
        return this.muestras;
    }
    
    public void setMuestras(Set muestras) {
        this.muestras = muestras;
    }


     @Override
    public String toString() {
        return "TipoMuestra[idTipoMues=" + idTipoMues + "]";
    }

 

    @Override
    public boolean equals(Object object) {
          // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoMuestra)) {
            return false;
        }
        TipoMuestra other = (TipoMuestra) object;
        if ((this.idTipoMues == null && other.idTipoMues != null) || (this.idTipoMues != null && !this.idTipoMues.equals(other.idTipoMues))) {
            return false;
        }
        return true;
    }

}


