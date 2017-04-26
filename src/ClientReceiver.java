import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Artem on 26.03.2017.
 *
 * бесконечный цикл ожидающий сообщения на вход
 */
public class ClientReceiver implements Runnable {
    private ClientGamer myClient;

    public ClientReceiver(ClientGamer gamer){
        myClient = gamer;
    }

    @Override
    public void run(){
        try{
            BufferedReader r = new BufferedReader(new InputStreamReader(myClient.getClientSocket().getInputStream()));
            String fromServer;
            while(true){
                fromServer = r.readLine();
                if(fromServer!=null){                           //при получении сообщения обращаемся к геймеру с просьбой обработать сообщения
                    Message message = new Message(fromServer);  //При этом передаем в конструктор строку полученную от сервера, а конструктор это сообщение сразу расшифровывает
                    if(message.getNumberOfCommand()!=-1)
                        myClient.handle(message);               //если сообщение удачно расшифрованно - отправляем его клиенту (то есть геймеру) вызывая обработчик
                }else{
                    break;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
