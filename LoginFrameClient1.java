import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class LoginFrameClient extends JFrame implements ActionListener 
{

    JTextField txtMessage;
    JLabel lblResult;
    PrintWriter out;
    BufferedReader in;

    public LoginFrameClient() 
    {
        setTitle("Marvellous Chat Client");
        setLayout(null);
        setSize(450, 300);

        JLabel lbl = new JLabel("Message:");
        lbl.setBounds(30, 50, 100, 30);
        add(lbl);

        txtMessage = new JTextField();
        txtMessage.setBounds(130, 50, 200, 30);
        add(txtMessage);

        JButton btn = new JButton("SEND");
        btn.setBounds(170, 100, 100, 30);
        btn.addActionListener(this);
        add(btn);

        lblResult = new JLabel("");
        lblResult.setBounds(30, 150, 350, 30);
        add(lblResult);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        connectToServer();
    }

    public void connectToServer() 
    {
        try {
            System.out.println("Connecting to server...");
            Socket s = new Socket("localhost", 5100);

            System.out.println("Connected to server.");

            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new PrintWriter(s.getOutputStream(), true);

            new Thread(() -> 
            {
                try 
                {
                    while (true) 
                        {
                        String str = in.readLine();
                        if (str != null) {
                            System.out.println("Server says: " + str);
                            lblResult.setText("Server says: " + str);
                        }
                    }
                } catch (Exception e) {}
            }).start();

        }
         catch (Exception e)
          {
            System.out.println(e);
        }
    }

    public void actionPerformed(ActionEvent ae) 
    {
        String msg = txtMessage.getText();
        out.println(msg);

        lblResult.setText("You: " + msg);
        System.out.println("You: " + msg);
    }

    public static void main(String[] args) 
    {
        new LoginFrameClient();
    }
}
