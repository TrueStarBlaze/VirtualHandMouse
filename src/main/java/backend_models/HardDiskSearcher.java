/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend_models;

/**
 *
 * @author erick
 */
import java.io.File;
import javax.swing.filechooser.FileSystemView;

public class HardDiskSearcher {

    static boolean fileFound = false;

    final static String searchTerm = "opencv_java455.dll";
    static String path = "";
    static String systemBit = "x64";

    public static void setSystemBit(String bit) {
        HardDiskSearcher.systemBit = bit;
    }

    static void findAndSetSystemType() {
        String systemProp = System.getProperty("com.ibm.vm.bitmode");
        if (systemProp != null) {
            if ("64".equals(systemProp)) {
                HardDiskSearcher.setSystemBit(systemProp);
                return;
            }
        }

        systemProp = System.getProperty("sun.arch.data.model");
        if (systemProp != null) {
            if ("64".equals(systemProp)) {
                HardDiskSearcher.setSystemBit(systemProp);
                return;
            }
        }

        systemProp = System.getProperty("java.vm.version");
        if (systemProp != null) {
            if ("64".equals(systemProp)) {
                HardDiskSearcher.setSystemBit(systemProp);
                return;
            }
        }

        HardDiskSearcher.setSystemBit("x86");
    }

    public static void recursiveSearch(String searchTerm, File... files) {

        for (File file : files) {

            if (file.isDirectory()) {
                File[] filesInFolder = file.listFiles();

                if (filesInFolder != null) {
                    for (File f : filesInFolder) {
                        if (f.isFile()) {
                            if (isTheSearchedFile(f, searchTerm)) {
                                fileFound = true;
                            }
                        }
                    }

                    for (File f : filesInFolder) {
                        if (f.isDirectory()) {
                            recursiveSearch(searchTerm, f);
                        }
                    }
                }
            } else if (isTheSearchedFile(file, searchTerm)) {
                fileFound = true;
            }
        }
    }

    private static boolean isTheSearchedFile(File file, String searchTerm) {
        if (file.isFile() && (searchTerm.equals(file.getName()))) {
            if (file.getParent().contains(systemBit)) {
                path = file.getAbsolutePath();
                return true;
            }
        }

        return false;
    }

}
