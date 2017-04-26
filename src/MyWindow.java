import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by Artem on 25.03.2017.
 */
public class MyWindow extends JFrame {
    private JTextArea textArea;
    private JTextField textFieldForMessage;
    private JTextField textFieldForCoordX;
    private JTextField textFieldForCoordY;
    private JButton buttonFire;
    private JButton buttonSend;
    private ClientGamer myGamer;
    public MyWindow(){
        JPanel myPanel = new JPanel();
        myPanel.setLayout(new FlowLayout());
        textArea = new JTextArea();

        textArea.setEditable(false);
        textArea.setText("Вася!!!");
        textArea.setPreferredSize(new Dimension(300,300));
        textFieldForMessage = new JTextField();
        textFieldForMessage.setPreferredSize(new Dimension(100,20));
        buttonSend = new JButton("->");
        //button.setSize(40,40);
        buttonSend.setPreferredSize(new Dimension(40,40));
        buttonSend.addActionListener(event ->{
            String s = textFieldForMessage.getText();
            if (s!=null){
                myGamer.sendTextMessage(s);
                textFieldForMessage.setText(null);
            }
        });


        textFieldForCoordY = new JTextField();
        textFieldForCoordY.setPreferredSize(new Dimension(20,20));

        textFieldForCoordX = new JTextField();
        textFieldForCoordX.setPreferredSize(new Dimension(20,20));

        buttonFire = new JButton("FIRE!");
        buttonFire.setPreferredSize(new Dimension(50,50));

        buttonFire.addActionListener(event ->{
            String coordX = textFieldForCoordX.getText();
            String coordY = textFieldForCoordY.getText();
            try{
                int x = Integer.parseInt(coordX);
                int y = Integer.parseInt(coordY);
                if((x<10&&y<10)&&(x>=0&&y>=0))
                {
                    myGamer.sendMessage(new Message(myGamer.getLogin(),x,y));
                }
                else
                    JOptionPane.showMessageDialog(this,"Ошибка ввода координат!:");
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(this,"Ошибка ввода координат:"+e.getMessage());
            }
        });

        myPanel.add(textFieldForCoordY);
        myPanel.add(textFieldForCoordX);
        myPanel.add(textArea);
        myPanel.add(textFieldForMessage);
        myPanel.add(buttonSend);
        myPanel.add(buttonFire);
        add(myPanel);
    }
    public void addTextMessage(Message message){
        String text = textArea.getText()+"\n";
        text+=message.getLogin();
        text+=":";
        text+=message.getMessage();
        textArea.setText(text);
        textArea.repaint();
    }

    public void addGamer(ClientGamer gamer){
        myGamer = gamer;
    }
}
