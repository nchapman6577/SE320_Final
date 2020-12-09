package BMI;

/*
 * Author: Naimah-Joy Chapman
 * Course: SE320
 * Date: 12/8/2020
 * Run the server program and leave open then run client program
 * Resources:http://www.javased.com/index.php?api=java.io.InputStreamReader
 * https://www.codota.com/code/java/methods/java.util.logging.Logger/getLogger
 */
//All the needed imports
import java.awt.GridLayout;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.logging.Level;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import javax.swing.JFrame;
import java.awt.event.WindowEvent;
import java.awt.TextArea;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.logging.Logger;
import java.awt.event.WindowAdapter;

public class Server {
	public JFrame mainFrame;
	static TextArea Txt = null;

	Server() {
		mainFrame = new JFrame("Server");
		mainFrame.setSize(500, 400);
		mainFrame.setLayout(new GridLayout(3, 1));
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		Txt = new TextArea();
		Txt.setBounds(20, 20, 450, 300);
		mainFrame.setLayout(null);
		mainFrame.add(Txt);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}

	public static void main(String args[]) {
		Socket socket = null;
		try {
			new Server();
			ServerSocket serverSocket = new ServerSocket(2525);
			Txt.append("\nServer Started at: " + new Date());
			while (true) // server will run 24/7
			{
				socket = serverSocket.accept();
				Txt.append("\nConnection to the client at: " + new Date());
				InputStream is = socket.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				String WeightHeight = br.readLine();
				//This gets the data from the clients program
				Double weight = Double.parseDouble(WeightHeight.substring(0, WeightHeight.indexOf(" ")));
				Double height = Double
						.parseDouble(WeightHeight.substring(WeightHeight.indexOf(" ") + 1, WeightHeight.length()));
				Txt.append("\nWeight: " + weight);
				Txt.append("\nHeight: " + height);

				Double BMI = (weight / (height * height));// Calculation of BMI
				String classification = "";
				if (BMI > 30) {// BMI category's OBESE
					classification = "You are Obese";
				}
				if (BMI > 25 && BMI < 29.9) {// BMI category's OVERWEIGHT
					classification = "You are Overweight";
				}
				if (BMI > 18.5 && BMI < 24.9) {// BMI category's NORMAL
					classification = "You are Normal";
				}
				if (BMI < 18.5) {// BMI category's UNDERWEIGHT
					classification = "You are Underweight";
				}
				//Sends the data back to the client program
				OutputStream os = socket.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(os);
				BufferedWriter bw = new BufferedWriter(osw);
				bw.write("The clients BMI is " + BMI + ". " + classification + "\n");//write the clients bmi and classification
				Txt.append("\nThe clients BMI is " + BMI + ". " + classification);
				bw.flush();
			}
		} catch (Exception ex) {
			Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}