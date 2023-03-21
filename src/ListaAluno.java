import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.transform.Result;
import java.sql.*;
public class ListaAluno extends JFrame{
    private JTable tblTable;
    private JPanel PNLListaAluno;
    private JButton btnExcluir;
    final String URL = "jdbc:mysql://localhost:3306/cadaluno";
    final String USER = "root";
    final String PASSWORD = "root";
    final String EXCLUIR = "DELETE FROM aluno WHERE matricula = ?";
    final String CONSULTAR = "SELECT * FROM aluno";
    public ListaAluno(){
        AddListiner();
        IniciarComponetes();
    }
    public void IniciarComponetes(){
        setSize(500, 300);
        setContentPane(PNLListaAluno);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Lista de Alunos");
        setVisible(true);
    }
    public void AddListiner(){
        DefaultTableModel alunos = new DefaultTableModel();
        alunos.addColumn("Nome");
        alunos.addColumn("Matricula");
        tblTable.setModel(alunos);
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement stmt = null;
            stmt = connection.createStatement();
            ResultSet rs = null;
            rs = stmt.executeQuery(CONSULTAR);
            while (rs.next()){
                Object[] row = new Object[2];
                row[0] =rs.getObject(1);
                row[1] =rs.getObject(2);
                alunos.addRow(row);
            }
        }catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }
    public static void main(String[] args) {
        ListaAluno listaAluno = new ListaAluno();
    }
}
