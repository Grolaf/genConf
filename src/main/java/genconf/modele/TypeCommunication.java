package genconf.modele; 

import java.time.LocalDate;


public class TypeCommunication{

    private String libelle;
    private boolean videoObligatoire;
    private boolean pdfObligatoire;

    // Constructeur : 
    public TypeCommunication(String libelle, boolean videoObligatoire, boolean pdfObligatoire) {

     this.libelle=libelle;
     this.videoObligatoire=videoObligatoire;
     this.pdfObligatoire=pdfObligatoire;
     
    }

    // Getters :
    
    public String getLibelle()
    {
        return this.libelle;
    }

    public boolean getLienPDFObligatoire(){
      return this.pdfObligatoire;
    }
    
    public boolean getLienVideoObligatoire()
    {
        return this.videoObligatoire;
    }

    // Setters

    public boolean setLibelle(String libelle){
       this.libelle = libelle;
       return true;
    }

    public boolean setpdfObligatoire(boolean pdfObligatoire){
        this.pdfObligatoire=pdfObligatoire;
        return true;
    }

    public boolean setVideoObligatoire(boolean videoObligatoire){
        this.videoObligatoire=videoObligatoire;
        return true;
    }

    // Fonctions usuelles
    
    public String toString()
    {
        return "TypeCommunication : " + this.libelle + "\n" +
                "\tPDF Obligatoire : " + this.pdfObligatoire + "\n" +
                "\tVideo Obligatoire : " + this.videoObligatoire;
    }

}