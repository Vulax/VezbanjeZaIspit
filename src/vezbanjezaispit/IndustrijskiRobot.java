
package vezbanjezaispit;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IndustrijskiRobot {
    public String imeDatoteke;
    public String naziv, model;
    public VrstaPogona vrstaPogona;
    public int godinaProizvodnje;
    public int brojStepeniSlobodeKretanja;
    public double potrosnjaElEnergije;
    public int maxGarantBrojSati;
    
    public String getNaziv() {
        return naziv;
    }

    public String getModel() {
        return model;
    }

    public VrstaPogona getVrstaPogona() {
        return vrstaPogona;
    }

    public int getGodinaProizvodnje() {
        return godinaProizvodnje;
    }

    public int getBrojStepeniSlobodeKretanja() {
        return brojStepeniSlobodeKretanja;
    }

    public double getPotrosnjaElEnergije() {
        return potrosnjaElEnergije;
    }

    public int getMaxGarantBrojSati() {
        return maxGarantBrojSati;
    }

    public IndustrijskiRobot(String naziv, String model, VrstaPogona vrstaPogona, int godinaProizvodnje, int brojStepeniSlobodeKretanja, double potrosnjaElEnergije, int maxGarantBrojSati) {
        this.naziv = naziv;
        this.model = model;
        this.vrstaPogona = vrstaPogona;
        this.godinaProizvodnje = godinaProizvodnje;
        this.brojStepeniSlobodeKretanja = brojStepeniSlobodeKretanja;
        this.potrosnjaElEnergije = potrosnjaElEnergije;
        this.maxGarantBrojSati = maxGarantBrojSati;
    }
    
    public double getEfikasnost(){
        double l=0;
        if (this.vrstaPogona == VrstaPogona.MAGNETNI_LINEARNI_AKTUATOR)
            l=2.00;
        else if (this.vrstaPogona == VrstaPogona.ELEKTROMAGNETNI_POGON)
            l=1.00;
        else if(this.vrstaPogona == VrstaPogona.HIDRAULICNI_POGON)
            l=0.33;
        else
            System.out.print("Lose unesena vrsta pogona!");
            
        
        double efikasnost = (this.potrosnjaElEnergije * Math.sqrt(this.maxGarantBrojSati))/((this.brojStepeniSlobodeKretanja - 2)*l);
        
        
    return efikasnost;    
    }

    @Override
    public String toString() {
        return String.format(
                " |%-20s|%-20s|%-30s|%9.2f W|"
                + "\n |%-20s|%-20s|%-30s|%6d sati|"
                + "\n |%-20s|%20s|%-30s|%6d sslk|"
                + "\n |%-5s %-34s|%-30s|%9.2f #|", 
                "Naziv proizvodjaca:",
                this.naziv,
                "Potrosnja energije:",
                this.potrosnjaElEnergije,
                "Naziv modela:",
                this.model,
                "Maks. broj sati rada:",
                this.maxGarantBrojSati,
                "Godina proizvodnje:",
                this.godinaProizvodnje,
                "Broj stepeni slobode kretanja:",
                this.brojStepeniSlobodeKretanja,
                "Pogon:",
                this.vrstaPogona,
                "Efikasnost:",
                getEfikasnost()   
                );
    }
    
    public static List<IndustrijskiRobot> load(String imeDatoteke){
        List<IndustrijskiRobot> lista = new ArrayList<>();
        try{
            FileInputStream fis = new FileInputStream(imeDatoteke);
            Scanner s = new Scanner(fis);
            while (s.hasNextLine()){
                VrstaPogona vp = null;
                double potrosnja = s.nextDouble();
                int max = s.nextInt();
                int godina = s.nextInt();
                String np = s.nextLine().trim();
                int stepen = s.nextInt();
                String pogon = s.next().trim();
                if(pogon.equals("MLA"))
                    vp = VrstaPogona.MAGNETNI_LINEARNI_AKTUATOR;
                else if(pogon.equals("EMP"))
                    vp = VrstaPogona.ELEKTROMAGNETNI_POGON;
                else if(pogon.equals("HP"))
                    vp = VrstaPogona.HIDRAULICNI_POGON;
                String naziv1 = s.nextLine().trim();             
                lista.add(new IndustrijskiRobot(naziv1, np, vp, godina, stepen, potrosnja, max ));
                System.out.println("Uspesno dodat u listu!");          
            }
            
            fis.close();
            s.close();
            
        }catch(Exception e){
            System.err.print("Greska!" + e.getMessage());
        }
        return lista;
    }
}
