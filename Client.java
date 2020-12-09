package BMI;

/*
 * Author: Naimah-Joy Chapman
 * Course: SE320
 * Date: 12/8/2020
 * Run the server program and leave open then run client program
 * Resources:http://www.javased.com/index.php?api=java.io.InputStreamReader
 * https://www.programcreek.com/java-api-examples/?class=java.awt.Frame&method=setLayout
 */
//All the needed imports
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.io.OutputStreamWriter;
import java.awt.event.WindowEvent;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.BufferedReader;
import javax.swing.JButton;
import java.net.InetAddress;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.awt.event.WindowAdapter;
import java.awt.event.ActionListener;

public class Client {
	public JFrame mainFrame;
	static TextArea Txt = null;

	Client() {
		mainFrame = new JFrame("Client");
		mainFrame.setSize(600, 600);
		mainFrame.setLayout(new GridLayout(3, 1));
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		JLabel weightLabel = new JLabel("Weight (kg): ");// Creates weight label
		weightLabel.setBounds(20, 20, 200, 20);
		JTextField weightText = new JTextField(20);
		weightText.setBounds(130, 20, 200, 20);

		JLabel heightLabel = new JLabel("Height (m): ");// Creates height label
		heightLabel.setBounds(20, 60, 200, 20);
		JTextField heightText = new JTextField(20);
		heightText.setBounds(130, 60, 200, 20);

		JButton submitButton = new JButton("Submit");// creates submit button
		submitButton.setBounds(200, 100, 100, 20);
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connect(weightText.getText(), heightText.getText()); // pass all the form parameters here
			}
		});
		Txt = new TextArea();
		Txt.setBounds(20, 130, 450, 300);
		mainFrame.setLayout(null);
		mainFrame.add(weightLabel);
		mainFrame.add(weightText);
		mainFrame.add(heightLabel);
		mainFrame.add(heightText);
		mainFrame.add(submitButton);
		mainFrame.add(Txt);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}

	public static void main(String args[]) {
		new Client();
	}

	public static void connect(String weight, String height) {
		try {
			Txt.append("\nWeight: " + weight);
			Txt.append("\nHeight: " + height);
			Socket socket = null;
			String host = "localhost";
			InetAddress address = InetAddress.getByName(host);
			socket = new Socket(address, 2525);

//Send the message to the server
			OutputStream os = socket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedWriter bw = new BufferedWriter(osw);
			String WeightHeight = weight + " " + height + "\n";
			bw.write(WeightHeight);
			bw.flush();

//Get the return message from the server
			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String message = br.readLine();
			Txt.append("\n" + message);
		} catch (UnknownHostException ex) {
			Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
		} catch (Exception ex) {
			Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
		}

	}
}