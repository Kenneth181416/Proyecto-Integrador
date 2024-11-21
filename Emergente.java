import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class Emergente extends JFrame{

    private JPanel panelInventario;
    private JTextField idText;
    private JTextField stockText;
    private JTextField nombreText;
    private JButton consultarBoton;
    private JButton agregarBoton;
    private JButton actualizarBoton;
    private JButton eliminarBoton;
    private JList lista;
    private JTable tabla;
    private JButton ventasBoton;
    private JButton comprasBoton;




    Connection conexion;


    DefaultListModel moodLista = new DefaultListModel<>();
    PreparedStatement ps;
    String[] campos= {"COD_PRODUCTO","NOMBRE","CANTIDAD_EN_STOCK"};
    String[] registros = new String[10];

   DefaultTableModel modTabla = new DefaultTableModel(null, campos);
   Statement st;
   ResultSet rs;


    public Emergente(){




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
                    Actualizar();
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
        ventasBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Detalle_Venta newframe = new Detalle_Venta();
                newframe.setVisible(true); // Abre la ventana de detalle de ventas
            }
        });
        comprasBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Detalle_Producto newframe= new Detalle_Producto();
                newframe.setVisible(true);
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
        modTabla.setRowCount(0);
        tabla.setModel(modTabla);
        st= conexion.createStatement();
        rs = st.executeQuery("SELECT INVENTARIO.COD_PRODUCTO, INVENTARIO.CANTIDAD_EN_STOCK, PRODUCTO.NOMBRE " +
                "FROM INVENTARIO " +
                "JOIN PRODUCTO ON INVENTARIO.COD_PRODUCTO = PRODUCTO.COD_PRODUCTO");

        while (rs.next()){
            registros[0]=rs.getString("COD_PRODUCTO");
            registros[1]=rs.getString("CANTIDAD_EN_STOCK");
            registros[2]=rs.getString("NOMBRE");
            modTabla.addRow(registros);
        }
    }
    void insertar() throws  SQLException{
        conectar();
        ps = conexion.prepareStatement("insert into INVENTARIO(COD_PRODUCTO, CANTIDAD_EN_STOCK) values (?,?)");
        ps.setInt(1, Integer.parseInt(idText.getText()));
        ps.setString(2,stockText.getText());

        if (ps.executeUpdate()>0) {
            lista.setModel(moodLista);
            moodLista.removeAllElements();
            moodLista.addElement("Elemento Agregado");

            idText.setText("");
            stockText.setText("");

            consultar();
        }
    }

    void Actualizar() throws  SQLException{
        conectar();
        ps = conexion.prepareStatement("update  INVENTARIO set CANTIDAD_EN_STOCK=? where COD_PRODUCTO=?");
        ps.setString(1,stockText.getText());
        ps.setInt(2, Integer.parseInt(idText.getText()));
        if (ps.executeUpdate()>0) {
            lista.setModel(moodLista);
            moodLista.removeAllElements();
            moodLista.addElement("Elemento Actualizado");

            idText.setText("");
            stockText.setText("");

            consultar();
        }
    }

    void eliminar() throws  SQLException{
        conectar();
        ps = conexion.prepareStatement("delete from INVENTARIO where COD_PRODUCTO=?");
        ps.setInt(1, Integer.parseInt(idText.getText()));
        if (ps.executeUpdate() > 0) {
            moodLista.removeAllElements();  // Limpiar la lista
            moodLista.addElement("Elemento Eliminado");  // Mensaje de eliminación
            idText.setText("");
            stockText.setText("");

            consultar();  // Recargar la lista con los productos actualizados
        }
    }

    public void mostrarVentanaEmergente(){
        Emergente emergente1 = new Emergente();
        emergente1.setContentPane(new Emergente().panelInventario);
        emergente1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        emergente1.setVisible(true);
        emergente1.pack();
    }
}