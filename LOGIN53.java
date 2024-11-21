import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LOGIN53 extends JFrame{


    private JTextField textUsuario;
    private JPasswordField textPass;
    private JButton ingresarBoton;
    private JButton crearUsuarioBoton;
    private JPanel PanellLogin;
    Connection conexion;
    Statement st;
    ResultSet rs;
    public LOGIN53(){

        ingresarBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validarusuarios();
            }
        });
    }

    public void conectar(){
        try{

            conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/proyectointegradorFinal","root","Kenneth18");

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    void validarusuarios(){
        conectar();
        int validacion=0;
        String USERNAME= textUsuario.getText();
        String CONTRASEÑA = String.valueOf(textPass.getText());
        try{
            st= conexion.createStatement();
            rs= st.executeQuery("select * from LOGIN where USERNAME = '"+USERNAME+"' and CONTRASEÑA = '"+CONTRASEÑA+"'");
            if (rs.next()){
                validacion=1;
                if (validacion==1){
                    JOptionPane.showMessageDialog(null, "las credenciales del usuario son correctas");
                    Emergente enlace = new Emergente();
                    enlace.mostrarVentanaEmergente();
                }
            }else {
                JOptionPane.showMessageDialog(null, "Las credenciales no son correctas");
            }
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Error"+e.getMessage());
        }

    }

    public static void main(String[] args) {
        LOGIN53 LOGIN531= new   LOGIN53();
        LOGIN531.setContentPane(new LOGIN53().PanellLogin);
        LOGIN531.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        LOGIN531.setVisible(true);
        LOGIN531.pack();
    }

}