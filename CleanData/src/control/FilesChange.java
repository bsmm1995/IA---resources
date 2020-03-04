package control;

import java.io.*;
import java.text.Normalizer;
import java.util.Locale;
import java.util.Scanner;

/**
 *
 * @author HP OMEN
 */
public class FilesChange {

    String origenSource = "C:\\Users\\bladt\\Desktop\\datain.csv";
    String origenId = "C:\\Users\\bladt\\Desktop\\datain2.csv";
    String destino = "C:\\Users\\bladt\\Desktop\\dataout.csv";
    File fOrigenSource, fOrigenId;
    FileWriter fw;
    BufferedWriter bw;

    public FilesChange() throws FileNotFoundException, IOException {
        fOrigenSource = new File(origenSource);
        fOrigenId = new File(origenId);
        fw = new FileWriter(destino);
        bw = new BufferedWriter(fw);
    }

    public void run() throws FileNotFoundException, IOException {
        Locale loc = new Locale("es", "ES");
        try (Scanner scanner = new Scanner(new FileInputStream(fOrigenId), "UTF-8")) {
            scanner.useLocale(loc);
            while (scanner.hasNextLine()) {
                String dni = scanner.nextLine().trim();
                readFile(dni);
            }
        }
        bw.close();
    }

    public void MantenerSoloLosQTienen5Ciclos(String dni) throws IOException {
        Locale loc = new Locale("es", "ES");
        try (Scanner scanner = new Scanner(new FileInputStream(fOrigenSource), "UTF-8")) {
            scanner.useLocale(loc);
            String cadenaIn;
            String cadenaOut = "";
            String DNI;
            String[] array = loadArray();
            boolean has5Ciclos = false;
            while (scanner.hasNextLine()) {
                cadenaIn = quitaDiacriticos(scanner.nextLine());
                DNI = corregirDNI(getDNI(cadenaIn));
                if (DNI.equals(dni)) {
                    if (cadenaOut.isEmpty()) {
                        array = copyArray(cadenaIn, array);
                    }
                    String asignatura = getAsignatura(cadenaIn);

                    System.out.println(asignatura + "  index= " + getIndexColumn(asignatura) + " estado= " + getEstadoAsignatura(cadenaIn));

                    array[getIndexColumn(asignatura)] = getEstadoAsignatura(cadenaIn);
                    has5Ciclos = (tiene5ciclo(cadenaIn).equals("5"));
                    cadenaOut = createNewStringForFile(array);
                }
            }
            if (has5Ciclos) {
                writeFile(cadenaOut);
            }
        }
    }

    private String corregirDNI(String dni) {
        if (dni.length() == 11) {
            return dni.substring(1).trim();
        }
        return dni;
    }

    private String tiene5ciclo(String cad) {
        String[] parts = cad.split(",");
        return parts[5].trim();
    }

    private void readFile(String dni) throws FileNotFoundException, IOException {
        Locale loc = new Locale("es", "ES");
        try (Scanner scanner = new Scanner(new FileInputStream(fOrigenSource), "UTF-8")) {
            scanner.useLocale(loc);
            String cadenaIn;
            String cadenaOut = "";
            String DNI;
            String[] array = loadArray();

            while (scanner.hasNextLine()) {
                cadenaIn = quitaDiacriticos(scanner.nextLine());
                DNI = getDNI(cadenaIn);

                if (DNI.equals(dni)) {
                    if (cadenaOut.isEmpty()) {
                        array = copyArray(cadenaIn, array);
                    }
                    array[getIndexColumn(getAsignatura(cadenaIn).trim())] = getEstadoAsignatura(cadenaIn);
                    cadenaOut = createNewStringForFile(array);
                }
            }
            writeFile(cadenaOut);
        }
    }

    private String getDNI(String cad) {
        String[] parts = cad.split(",");
        return parts[0].trim();
    }

    private String getAsignatura(String cad) {
        String[] parts = cad.split(",");
        return parts[9].trim();
    }

    private String getEstadoAsignatura(String cad) {
        String[] parts = cad.split(",");
        return parts[10].trim();
    }

    public void writeFile(String cad) throws IOException {
        bw.write(cad + "\n");
    }

    public String quitaDiacriticos(String s) {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s.toUpperCase();
    }

    private String createNewStringForFile(String[] array) {
        String newcad = "";
        for (int i = 0; i < array.length; i++) {
            newcad += array[i];
            if (i < array.length - 1) {
                newcad += ",";
            }
        }
        return newcad;
    }

    private int getIndexColumn(String asignatura) {
        int index = 15;
        switch (asignatura) {
            case "ALGEBRA LINEAL":
                return 9;
            case "COMUNICACION PROFESIONAL":
                return 10;
            case "ELECTRICIDAD":
                return 11;
            case "INTRODUCCION A LAS CIENCIAS DE LA COMPUTACION":
                return 12;
            case "TEORIA DE LA PROGRAMACION":
                return 13;
//            case "CALCULO DIFERENCIAL":
//                index = 1;
//                break;
//            case "FUNDAMENTOS INFORMATICOS":
//                index = 2;
//                break;
//            case "CALCULO INTEGRAL":
//                index = 3;
//                break;
//            case "ALGEBRA LINEAL":
//                index = 4;
//                break;
//            case "METODOLOGIA DE LA PROGRAMACION":
//                index = 5;
//                break;
//            case "PROGRAMACION I":
//                index = 6;
//                break;
//            case "PROBABILIDAD E INFERENCIA ESTADISTICA":
//                index = 7;
//                break;
//            case "ESTRUCTURA DE DATOS":
//                index = 8;
//                break;
//            case "INGENIERIA DEL SOFTWARE I":
//                index = 9;
//                break;
//            case "ESTRUCTURA DE DATOS II":
//                index = 10;
//                break;
//            case "METODOLOGIA DE LA INVESTIGACION":
//                index = 11;
//                break;
//            case "PROGRAMACION II":
//                index = 12;
//                break;
//            case "BASE DE DATOS I":
//                index = 13;
//                break;
//            case "DISENO Y GESTION DE BASE DE DATOS":
//                index = 14;
//                break;
//            case "ESTADISTICA INFERENCIAL":
//                index = 15;
//                break;
//            case "PROGRAMACION AVANZADA":
//                index = 16;
//                break;
//            case "ANALISIS Y DISENO DE SISTEMAS":
//                index = 25;
//                break;
//            case "LENGUAJE ENSAMBLADOR":
//                index = 26;
//                break;
//            case "DISENO DE SISTEMAS":
//                index = 28;
//                break;
//            case "ECUACIONES DIFERENCIALES":
//                index = 29;
//                break;
//            case "SIMULACION":
//                index = 30;
//                break;
//            case "SISTEMAS OPERATIVOS":
//                index = 31;
//                break;
//            case "DERECHO INFORMATICO":
//                index = 32;
//                break;
//            case "ANALISIS NUMERICO":
//                index = 33;
//                break;
//            case "AUDITORIA INFORMATICA":
//                index = 34;
//                break;
//            case "INTELIGENCIA ARTIFICIAL":
//                index = 35;
//                break;
//            case "INGENIERIA DE SOFTWARE II":
//                index = 36;
//                break;
//            case "MODELAMIENTO MATEMATICO":
//                index = 37;
//                break;
//            case "COMPILADORES":
//                index = 38;
//                break;
//            case "SISTEMAS EXPERTOS":
//                index = 39;
//                break;
        }
        return index;
    }

    private String[] copyArray(String cadenaIn, String[] array) {
        String[] parts = cadenaIn.split(",");
        System.arraycopy(parts, 0, array, 0, 9);
        return array;
    }

    private String[] loadArray() {
        String[] array = new String[15];
        for (int i = 0; i < array.length; i++) {
            array[i] = "?";
        }
        return array;
    }
}
