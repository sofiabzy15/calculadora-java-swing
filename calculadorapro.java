import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// O segredo está aqui: o nome abaixo agora é igual ao seu arquivo
public class calculadorapro extends JFrame implements ActionListener {

    private JTextField display;
    private ArrayList<String> historico = new ArrayList<>();
    private String operador = "";
    private double num1 = 0;
    private boolean novaOperacao = true;

    public calculadorapro() {
        setTitle("Minha Calculadora Java");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        display = new JTextField("0");
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(5, 4, 5, 5));

        String[] botoes = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", "C", "=", "+",
                "Histórico"
        };

        for (String texto : botoes) {
            JButton botao = new JButton(texto);
            botao.addActionListener(this);
            painelBotoes.add(botao);
        }

        add(painelBotoes, BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        if ("0123456789".contains(comando)) {
            if (novaOperacao) {
                display.setText(comando);
                novaOperacao = false;
            } else {
                display.setText(display.getText() + comando);
            }
        } else if (comando.equals("C")) {
            display.setText("0");
            novaOperacao = true;
        } else if (comando.equals("=")) {
            if (!operador.isEmpty()) {
                calcular(Double.parseDouble(display.getText()));
                operador = "";
                novaOperacao = true;
            }
        } else if (comando.equals("Histórico")) {
            exibirHistorico();
        } else {
            num1 = Double.parseDouble(display.getText());
            operador = comando;
            novaOperacao = true;
        }
    }

    private void calcular(double num2) {
        double resultado = 0;
        try {
            switch (operador) {
                case "+": resultado = num1 + num2; break;
                case "-": resultado = num1 - num2; break;
                case "*": resultado = num1 * num2; break;
                case "/":
                    if (num2 == 0) throw new ArithmeticException("Divisão por zero!");
                    resultado = num1 / num2;
                    break;
            }
            String operacaoCompleta = num1 + " " + operador + " " + num2 + " = " + resultado;
            historico.add(operacaoCompleta);
            display.setText(String.valueOf(resultado));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            display.setText("0");
        }
    }

    private void exibirHistorico() {
        StringBuilder sb = new StringBuilder("Histórico:\n");
        for (String item : historico) {
            sb.append(item).append("\n");
        }
        JOptionPane.showMessageDialog(this, historico.isEmpty() ? "Vazio" : sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new calculadorapro());
    }
}