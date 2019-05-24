package views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Model;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.Timer;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import java.awt.event.WindowFocusListener;

public class Home extends JFrame {

	private JPanel contentPane;
	private Model model = new Model();
	private JComboBox cbPortas;
	private ActionListener actMonitorEstado;
	private Timer timerMonitorEstado;
	private JLabel lblStatus;
	private boolean statusConexao = false;
	private JTextArea textArea;
	private JComboBox cbPortasUSB;

	public Home() {
		addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent arg0) {
			}
			public void windowLostFocus(WindowEvent arg0) {
				show();
			}
		});
		setAlwaysOnTop(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				System.out.println("Fechando porta antes de fechar a aplicacao");
				closePort();
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 665, 375);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JPanel panel_1 = new JPanel();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 320, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 303, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 326, Short.MAX_VALUE)
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		
		JLabel lblGravarNoPendrive = new JLabel("Gravar no PenDrive");
		
		cbPortasUSB = new JComboBox();
		
		JLabel lblPortas = new JLabel("Portas");
		
		JButton btnIniciar = new JButton("Iniciar Backup");
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salvarBackup();
			}
		});
		
		JProgressBar progressBar = new JProgressBar();
		
		JLabel lblProgressoDaTranferencia = new JLabel("Progresso da tranferencia");
		
		JButton btnListar = new JButton("Listar");
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				listarPortasUsb();
			}
		});
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(btnIniciar, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
						.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(cbPortasUSB, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnListar))
						.addComponent(lblGravarNoPendrive)
						.addComponent(lblPortas, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblProgressoDaTranferencia))
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblGravarNoPendrive)
					.addGap(20)
					.addComponent(lblPortas)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(cbPortasUSB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnListar))
					.addGap(49)
					.addComponent(btnIniciar, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 103, Short.MAX_VALUE)
					.addComponent(lblProgressoDaTranferencia)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		panel_1.setLayout(gl_panel_1);
		
		cbPortas = new JComboBox();
		
		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AtualizarPortas();
			}
		});
		
		JButton btnAbrir = new JButton("Conectar");
		btnAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirPorta();
			}
		});
		
		JButton btnFechar = new JButton("Desconectar");
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				closePort();
			}
		});
		
		JButton btnNewButton = new JButton("Ler Buffer:");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (statusConexao)
					LerBuffer();
			}
		});
		
		JLabel lblPortasDisponiveis = new JLabel("Portas Disponiveis");
		
		lblStatus = new JLabel("Status: Desconectado");
		
		JScrollPane scrollPane = new JScrollPane();
		
		textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		scrollPane.setViewportView(textArea);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
						.addComponent(lblPortasDisponiveis)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(cbPortas, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnAtualizar))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(btnAbrir)
							.addGap(10)
							.addComponent(btnFechar))
						.addComponent(btnNewButton)
						.addComponent(lblStatus))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(lblPortasDisponiveis)
					.addGap(6)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(1)
							.addComponent(cbPortas, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnAtualizar))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnAbrir)
						.addComponent(btnFechar))
					.addGap(11)
					.addComponent(btnNewButton)
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
					.addGap(13)
					.addComponent(lblStatus)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
		iniciaMonitorPortaSerial();
		AtualizarPortas();
		System.out.println("Versao 1.8 - v9600");
	}

	protected void salvarBackup() {
		if (!model.salvarBackup(cbPortasUSB.getSelectedItem().toString()))
			JOptionPane.showMessageDialog(null, "Houve problemas ao salvar o arquivo");
		else
			JOptionPane.showMessageDialog(null, "Salvo com sucesso");
	}

	protected void listarPortasUsb() {
		ArrayList<String> portas = model.listarPortasUSB();
		cbPortasUSB.removeAllItems();
		for (String porta : portas) {
			cbPortasUSB.addItem(porta);
		}
	}

	protected void closePort() {
		model.closePort();
		lblStatus.setText("Status: Desconectado");
		statusConexao = false;
	}

	protected void LerBuffer() {
		textArea.append(model.LerPorta());
	}

	protected void abrirPorta() {
		model.AbrirPorta(cbPortas.getSelectedItem().toString());
		lblStatus.setText("Status: Conectado na " + cbPortas.getSelectedItem().toString());
		statusConexao = true;
	}

	protected void AtualizarPortas() {
		ArrayList<String> portas = model.ListarPortas();
		cbPortas.removeAllItems();
		for (String item : portas) {
			cbPortas.addItem(item);
		}
		
		
	}
	
	private void iniciaMonitorPortaSerial() {
		actMonitorEstado = new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if (statusConexao)
					LerBuffer();
		}};
		
		timerMonitorEstado = new Timer(1000, actMonitorEstado);
		//timerMonitorEstado.start();
	}
}
