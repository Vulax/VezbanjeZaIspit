package vezbanjezaispit;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class VezbanjeZaIspit {

    public static void main(String[] args) {
        List<IndustrijskiRobot> lista = IndustrijskiRobot.load("ulaz.dat");
        prikazi(lista);
        double asEfikasnost = 0;
        double sumaEfikasnosti = 0;
        for (IndustrijskiRobot r : lista) {
            sumaEfikasnosti += r.getEfikasnost();

        }
        asEfikasnost = sumaEfikasnosti / lista.size();
        System.out.println(asEfikasnost);

        for (int i = 0; i < lista.size(); i++) {
            if ((lista.get(i).getEfikasnost() < (asEfikasnost * 0.75)) || (lista.get(i).getEfikasnost() > (asEfikasnost * 1.25))) {
                lista.remove(i);
            }
        }

        prikazi(lista);

        try {
            FileOutputStream fos = new FileOutputStream("izlaz.txt");
            PrintWriter pw = new PrintWriter(fos);
            for (IndustrijskiRobot ir:lista){
                pw.println(ir);
            }
            pw.flush();
            pw.close();
        } catch(Exception e)  {
            System.err.print("Greska!" + e.getMessage());
        }

    }

    public static void prikazi(List<IndustrijskiRobot> lista) {
        for (IndustrijskiRobot r : lista) {
            System.out.println(r);
        }
    }

}
