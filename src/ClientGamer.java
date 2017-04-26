import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Artem on 25.03.2017.
 * основной объект
 */
public class ClientGamer {
    boolean isAnonim;                   //флаг (залогинился ли пользователь)
    boolean isActive;                   //
    private String login;               //логин Текущего пользователя
    private String password;            //
    private MyWindow myWindow;          //переменная для хренеия связанного окна
    private ClientReceiver myReceiver;  //переменная для храения объекта-получателя сообщений
    private Socket clientSocket;        //переменная для хранения сокета - нужна ли она тут?
    private PrintWriter toServer;       //стандартный способ писать сообщения

    public ClientGamer(MyWindow Frame) throws IOException {
        /**/
        myWindow = Frame;
        isAnonim = true;
        isActive = false;
        login = null;
        password = null;

        //подключаемся к серверу
        String host = "192.168.1.35";
        clientSocket = new Socket(host,4444);

        //запоминаем поток куда слать сообщения
        toServer = new PrintWriter(clientSocket.getOutputStream(),true);

        //запускаем поток получения сообщений от сервера
        myReceiver = new ClientReceiver(this);
        Thread myReceiverThread = new Thread(myReceiver);
        myReceiverThread.start();
    }

    public void handle(Message message){
        int numberOfCommand = message.getNumberOfCommand();

        switch(numberOfCommand){
            case 101:{
                login=message.getLogin();

            };break;
            case 200:myWindow.addTextMessage(message);break;    //если получили сообщение типа 200 сразу его обрабатываем
        }
    }

    public void sendTextMessage(String text){
        if(isAnonim){
            toServer.println(new Message(200,login,text));
        }
        //toServer.println(text);
    }
    public void sendMessage(Message message){
        int numberOfCommand = message.getNumberOfCommand();
        switch(numberOfCommand){
            case 102:{
                toServer.println(message);
            } break;
            case 200:{
                toServer.println(message);
            };break;
        }
    }
    public void addMessage(String message){

    }

    //устанавливаем зависимость от окна
    public void addWindow (MyWindow window){
        myWindow = window;
        myWindow.addTextMessage(new Message(200,"SYSTEM","произошло подключение игрока к интерфейсу"));
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getLogin() {

        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    public Socket getClientSocket() {
        return clientSocket;
    }
}
