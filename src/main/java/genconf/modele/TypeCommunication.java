package modele; 

import java.time.LocalDate;


public class TypeCommunication(){

    private String libelle;
    private boolean videoObligatoire;
    private boolean pdfObligatoire;~

    // Constructeur : 
    public TypeCommunication(String libelle, boolean videoObligatoire, boolean pdfObligatoire) {

     this.libelle=libelle;
     this.videoObligatoire=videoObligatoire;
     this.pdfObligatoire=pdfObligatoire;
     
    }

    // Getters :

    public boolean getLienPDFObligatoire(){
      return this.LienPDFObligatoire
    }

    public TypeCommunication getTypeCommunication(String libelle){
      return this.libelle=libelle;

    }

    // Setters

    public void setLibelle(String libelle){
       this.libelle=libelle:
    }

    public void setpdfObligatoire(boolean pdfObligatoire){
          this.pdfObligatoire=pdfObligatoire;
    }

    public void setVideoObligatoire(boolean videoObligatoire){
          this.videoObligatoire=videoObligatoire;
    }

    // Destruceur ? 

}