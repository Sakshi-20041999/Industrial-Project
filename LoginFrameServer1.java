import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class LoginFrameServer extends JFrame implements ActionListener
 {

    JTextField txtMessage;
    JLabel lblResult;
    PrintWriter out;
    BufferedReader in;

    public LoginFrameServer() 
    {
        setTitle("Marvellous Chat Server");
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

        startServer();
    }

    public void startServer() 
    {
        try
         {
            System.out.println("Server waiting at port 5100...");
            ServerSocket ss = new ServerSocket(5100);

            Socket s = ss.accept();
            System.out.println("Client connected.");

            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new PrintWriter(s.getOutputStream(), true);

            
            new Thread(() -> 
            {
                try 
                {
                    while (true) 
                        {
                        String str = in.readLine();
                        if (str != null) 
                            {
                            System.out.println("Client says: " + str);
                            lblResult.setText("Client says: " + str);
                        }
                    }
                } catch (Exception e) {}
            }).start();

        } catch (Exception e)
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
        new LoginFrameServer();
    }
}
