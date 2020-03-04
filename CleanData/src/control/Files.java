/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.*;
import java.text.Normalizer;
import java.util.Locale;
import java.util.Scanner;

/**
 *
 * @author HP OMEN
 */
public class Files {
    
    String origen = "C:\\Users\\HP OMEN\\Desktop\\datain.csv";
    String destino = "C:\\Users\\HP OMEN\\Desktop\\dataout.csv";
    File fr;
    FileWriter fw;
    BufferedWriter bw;
    
    public Files() throws FileNotFoundException, IOException {
        fr = new File(origen);
        fw = new FileWriter(destino);
        bw = new BufferedWriter(fw);
    }
    
    public void readFile() throws IOException {
        Locale loc = new Locale("es", "ES");
        try (Scanner scanner = new Scanner(new FileInputStream(fr), "UTF-8")) {
            scanner.useLocale(loc);
            writeFile(scanner.nextLine());//aqui se copia los encabezados
            while (scanner.hasNextLine()) {
                String cadena = scanner.nextLine().trim();
                if (isValidtData(cadena)) {
                    writeFile(quitaDiacriticos(cadena));
                }
            }
            bw.close();
        }
    }
    
    public void writeFile(String cad) throws IOException {
        bw.write(cad + "\n");
    }
    
    public String quitaDiacriticos(String s) {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s.toLowerCase();
    }
    
    public boolean isValidtData(String cad) {
        String[] parts = cad.split(";");
        
        if ("0".equals(parts[1]) && !"".equals(parts[3])) {
            if ("1".equals(parts[2]) || "2".equals(parts[2])) {
                return true;
            }
        }
        
        return false;
    }
}
