import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
public class ListaAluno extends JFrame{
    private JTable tblTable;
    private JPanel PNLListaAluno;
    private JButton btnExcluir;
    private JButton btnVoltar;
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
        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaCadAluno telaCadAluno = new TelaCadAluno();
                telaCadAluno.setVisible(true);
                dispose();
            }
        });
        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection connection = null;
                try{
                    int linhaSelecionada = tblTable.getSelectedRow();
                    if (linhaSelecionada < 0){
                        JOptionPane.showMessageDialog(null, "Selecione uma linha para excluir");
                        return;
                    }
                    int matricula = (int) tblTable.getValueAt(linhaSelecionada,1);

                    connection = DriverManager.getConnection(URL, USER, PASSWORD);
                    PreparedStatement stmt = connection.prepareStatement(EXCLUIR);
                    stmt .setInt(1, matricula);
                    int resultado = stmt.executeUpdate();
                    if (resultado == 1){
                        JOptionPane.showMessageDialog(null, "Registro exlcuido com sucesso!");
                    }else {
                        JOptionPane.showMessageDialog(null, "Erro ao excluir registro!");
                    }
                    DefaultTableModel aluno = (DefaultTableModel) tblTable.getModel();
                    aluno.removeRow(linhaSelecionada);
                }catch (SQLException ex){
                    System.out.println("Erro ao exluir registro: "+ex.getMessage());
                }catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "Selecione uma linha para excluir");
                }
            }
        });

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
