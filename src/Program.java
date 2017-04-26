import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created by Artem on 25.03.2017.
 */
public class Program {
    public static void main(String[]args){

        EventQueue.invokeLater(()->{
            try {
                MyWindow frame = new MyWindow();            //создем окно в котором будем рисовать
                ClientGamer gamer = new ClientGamer(frame);      //создаем объект класса Геймер

                //gamer.addWindow(frame);                     //переделал передачу в конструктор, для того чтобы сразу можно было обрабатывать сообщения от сервера
                frame.addGamer(gamer);                      //передаем окну геймера для обратной связи

                frame.setSize(500,400);       //стандартные операции
                frame.setTitle("Client!!!!");               //
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }catch (IOException e){
                e.printStackTrace();
            }

        });
    }
}
