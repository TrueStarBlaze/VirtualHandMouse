/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package the_app;

/**
 *
 * @author 1100015542
 */
public class TheAppRunner {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            System.err.println(ex);
        } catch (InstantiationException ex) {
            System.err.println(ex);
        } catch (IllegalAccessException ex) {
            System.err.println(ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            System.err.println(ex);
        }

        java.awt.EventQueue.invokeLater(new TheApp());
    
    }
    
}
