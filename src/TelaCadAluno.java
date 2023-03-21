import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
public class TelaCadAluno extends JFrame {
    private JPanel PNLTelaCadAluno;
    private JTextField textNome;
    private JTextField textMatricula;
    private JButton btnInserir;
    private JButton btnLimpar;
    private JButton btnLista;

    final String URL = "jdbc:mysql://localhost:3306/cadaluno";
    final String USER = "root";
    final String PASSWORD = "root";
    final String INSERIR = "INSERT INTO aluno (nome, matricula) VALUES (?, ?)";
    final String CONSULTA = "select * from aluno";

    String nome;
    String matricula;

    public TelaCadAluno() {
        AddListeners();
        IniciarComponentes();
        Conecta();
    }

    public void AddListeners(){
        btnLimpar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textNome.setText("");
                textMatricula.setText("");
            }
        });
    }

    public void Conecta() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conectado");

            final PreparedStatement stmtInserir;

            stmtInserir = connection.prepareStatement(INSERIR);

            btnInserir.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    String INSERIR = "insert into aluno (nome, matricula) values (?,?)";

                    String nome = textNome.getText();
                    String matriculaStr = textMatricula.getText();
                    try {
                        int matricula = Integer.parseInt(matriculaStr);
                        stmtInserir.setString(1, nome);
                        stmtInserir.setInt(2, matricula);
                        stmtInserir.executeUpdate();
                        System.out.println("Dados inseridos");
                        JOptionPane.showMessageDialog(btnInserir,"Dados inseridos");
                        textNome.setText("");
                        textMatricula.setText("");
                    } catch (NumberFormatException ex) {
                        System.out.println("A matricula informada não é um número");
                    } catch (Exception ex) {
                        System.out.println("Erro ao inserir dados no banco");
                    }
                }
            });
        } catch (Exception ex) {
            System.out.println("Erro ao conectar ao banco de dados");
        }
    }
    public void IniciarComponentes(){
        JPanel telaCalc = new JPanel();
        setSize(500, 300);
        setContentPane(PNLTelaCadAluno);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Cadastro de Alunos");
        setVisible(true);
    }

    public static void main(String[] args) {
        TelaCadAluno TelaCadAluno = new TelaCadAluno();
    }
}






