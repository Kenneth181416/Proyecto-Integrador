import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Detalle_Venta extends JFrame {

    private JTextField IdVText;
    private JTextField IdPText;
    private JTextField CantidadText;
    private JTextField TotalText;
    private JButton consultarBoton;
    private JButton agregarBoton;
    private JButton actualizarBoton;
    private JButton eliminarBoton;
    private JList Lista1;
    private JTable Tabla1;
    private JPanel PanelVenta;

    Connection conexion;
    DefaultListModel moodLista1 = new DefaultListModel<>();
    PreparedStatement ps;
    String[] campos = {"COD_VENTA", "COD_PRODUCTO", "CANTIDAD", "TOTAL"};
    String[] registros = new String[10];

    DefaultTableModel modTabla1 = new DefaultTableModel(null, campos);
    Statement st;
    ResultSet rs;


    public Detalle_Venta() {
        setContentPane(PanelVenta); // Asegúrate de que PanelVenta está en tu JFrame
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();

        consultarBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        agregarBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        actualizarBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        eliminarBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        consultarBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    consultar();
                }catch (SQLException ex){
                    throw new RuntimeException(ex);
                }
            }
        });
        agregarBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    insertar();
                }catch (SQLException ex){
                    throw new RuntimeException(ex);
                }
            }
        });
        actualizarBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    actualizar();
                }catch (SQLException ex){
                    throw new RuntimeException(ex);
                }
            }
        });
        eliminarBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    eliminar();
                }catch (SQLException ex){
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void conectar() {
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/proyectointegradorFinal", "root", "Kenneth18");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void consultar() throws SQLException {
        conectar();
        modTabla1.setRowCount(0); // Limpia la tabla antes de cargar nuevos datos
        Tabla1.setModel(modTabla1); // Asigna el modelo a la tabla

        st = conexion.createStatement();
        rs = st.executeQuery("SELECT COD_VENTA, COD_PRODUCTO, CANTIDAD, TOTAL FROM DETALLE_VENTA");

        // Ajusta el tamaño del arreglo para los 4 campos que quieres mostrar
        String[] registros = new String[4];

        while (rs.next()) {
            registros[0] = rs.getString("COD_VENTA");
            registros[1] = rs.getString("COD_PRODUCTO");
            registros[2] = rs.getString("CANTIDAD");
            registros[3] = rs.getString("TOTAL");
            modTabla1.addRow(registros); // Agrega la fila con los datos al modelo de tabla
        }
    }
    void insertar() throws SQLException {
        conectar();
        ps = conexion.prepareStatement("INSERT INTO DETALLE_VENTA (COD_VENTA, COD_PRODUCTO, CANTIDAD, TOTAL) VALUES (?, ?, ?, ?)");

        ps.setInt(1, Integer.parseInt(IdVText.getText()));
        ps.setInt(2, Integer.parseInt(IdPText.getText()));
        ps.setInt(3, Integer.parseInt(CantidadText.getText()));
        ps.setDouble(4, Double.parseDouble(TotalText.getText()));

        if (ps.executeUpdate() > 0) {
            Lista1.setModel(new DefaultListModel<>());
            ((DefaultListModel) Lista1.getModel()).addElement("Elemento Agregado");

            // Limpia los campos después de insertar
            IdVText.setText("");
            IdPText.setText("");
            CantidadText.setText("");
            TotalText.setText("");

            consultar(); // Actualiza la tabla
        }
    }
    void actualizar() throws SQLException {
        conectar();
        ps = conexion.prepareStatement("UPDATE DETALLE_VENTA SET CANTIDAD=?, TOTAL=? WHERE COD_VENTA=? AND COD_PRODUCTO=?");

        ps.setInt(1, Integer.parseInt(CantidadText.getText()));
        ps.setDouble(2, Double.parseDouble(TotalText.getText()));
        ps.setInt(3, Integer.parseInt(IdVText.getText()));
        ps.setInt(4, Integer.parseInt(IdPText.getText()));

        if (ps.executeUpdate() > 0) {
            Lista1.setModel(new DefaultListModel<>());
            ((DefaultListModel) Lista1.getModel()).addElement("Elemento Actualizado");

            // Limpia los campos después de actualizar
            IdVText.setText("");
            IdPText.setText("");
            CantidadText.setText("");
            TotalText.setText("");

            consultar(); // Actualiza la tabla
        }
    }
    void eliminar() throws SQLException {
        conectar();
        ps = conexion.prepareStatement("DELETE FROM DETALLE_VENTA WHERE COD_VENTA=? AND COD_PRODUCTO=?");

        ps.setInt(1, Integer.parseInt(IdVText.getText()));
        ps.setInt(2, Integer.parseInt(IdPText.getText()));

        if (ps.executeUpdate() > 0) {
            Lista1.setModel(new DefaultListModel<>());
            ((DefaultListModel) Lista1.getModel()).addElement("Elemento Eliminado");

            // Limpia los campos después de eliminar
            IdVText.setText("");
            IdPText.setText("");

            consultar(); // Actualiza la tabla
        }
    }
}


