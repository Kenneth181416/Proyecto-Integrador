import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Detalle_Producto extends JFrame {


    private JPanel panelProducto;
    private JTextField compraText;
    private JTextField produText;
    private JTextField cantidadText;
    private JTextField totalText;
    private JButton consultarBoton;
    private JButton agregarBoton;
    private JButton actualizarBoton;
    private JButton eliminarBoton;
    private JList Lista2;
    private JTable Tabla2;

    Connection conexion;

    DefaultListModel moodLista2 = new DefaultListModel<>();
    PreparedStatement ps;
    String[] campos = {"COD_COMPRA", "COD_PRODUCTO", "CANTIDAD", "TOTAL"};
    String[] registros = new String[10];

    DefaultTableModel modTabla2 = new DefaultTableModel(null, campos);
    Statement st;
    ResultSet rs;

    public Detalle_Producto() {
        setContentPane(panelProducto); // Asegúrate de que panelProducto esté en tu JFrame
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();

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
        modTabla2.setRowCount(0); // Limpia la tabla antes de cargar nuevos datos
        Tabla2.setModel(modTabla2); // Asigna el modelo a la tabla

        st = conexion.createStatement();
        rs = st.executeQuery("SELECT COD_COMPRA, COD_PRODUCTO, CANTIDAD, TOTAL FROM DETALLE_PRODUCTO");

        // Ajusta el tamaño del arreglo para los 4 campos que quieres mostrar
        String[] registros = new String[4];

        while (rs.next()) {
            registros[0] = rs.getString("COD_COMPRA");
            registros[1] = rs.getString("COD_PRODUCTO");
            registros[2] = rs.getString("CANTIDAD");
            registros[3] = rs.getString("TOTAL");
            modTabla2.addRow(registros); // Agrega la fila con los datos al modelo de tabla
        }
    }
    void insertar() throws SQLException {
        conectar();
        ps = conexion.prepareStatement("INSERT INTO DETALLE_PRODUCTO (COD_COMPRA, COD_PRODUCTO, CANTIDAD, TOTAL) VALUES (?, ?, ?, ?)");

        ps.setInt(1, Integer.parseInt(compraText.getText()));
        ps.setInt(2, Integer.parseInt(produText.getText()));
        ps.setInt(3, Integer.parseInt(cantidadText.getText()));
        ps.setDouble(4, Double.parseDouble(totalText.getText()));

        if (ps.executeUpdate() > 0) {
            Lista2.setModel(new DefaultListModel<>());
            ((DefaultListModel) Lista2.getModel()).addElement("Elemento Agregado");

            // Limpia los campos después de insertar
            compraText.setText("");
            produText.setText("");
            cantidadText.setText("");
            totalText.setText("");

            consultar(); // Actualiza la tabla
        }
    }
    void actualizar() throws SQLException {
        conectar();
        ps = conexion.prepareStatement("UPDATE DETALLE_PRODUCTO SET CANTIDAD=?, TOTAL=? WHERE COD_VENTA=? AND COD_PRODUCTO=?");

        ps.setInt(1, Integer.parseInt(cantidadText.getText()));
        ps.setDouble(2, Double.parseDouble(totalText.getText()));
        ps.setInt(3, Integer.parseInt(compraText.getText()));
        ps.setInt(4, Integer.parseInt(produText.getText()));

        if (ps.executeUpdate() > 0) {
            Lista2.setModel(new DefaultListModel<>());
            ((DefaultListModel) Lista2.getModel()).addElement("Elemento Actualizado");

            // Limpia los campos después de actualizar
            compraText.setText("");
            produText.setText("");
            cantidadText.setText("");
            totalText.setText("");

            consultar(); // Actualiza la tabla
        }
    }
    void eliminar() throws SQLException {
        conectar();
        ps = conexion.prepareStatement("DELETE FROM DETALLE_PRODUCTO WHERE COD_COMPRA=? AND COD_PRODUCTO=?");

        ps.setInt(1, Integer.parseInt(compraText.getText()));
        ps.setInt(2, Integer.parseInt(produText.getText()));

        if (ps.executeUpdate() > 0) {
            Lista2.setModel(new DefaultListModel<>());
            ((DefaultListModel) Lista2.getModel()).addElement("Elemento Eliminado");

            // Limpia los campos después de eliminar
            compraText.setText("");
            produText.setText("");

            consultar(); // Actualiza la tabla
        }
    }
}



