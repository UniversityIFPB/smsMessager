package br.edu.ifpb.smsMesseger.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 * Created by root on 17/06/16.
 */
public class ClientSocket {
    public static void main(String[] args) {
        try{
            Socket socket = new Socket("localhost", 56789);

            //condicionais iniciais para informar e preparar o cliente para as ações do servidor
            if(socket.isConnected())
                System.out.print("\nServidor conectado!!");
            else {
                socket.close();
                //verificando e mandando mensagem de fechamento para o cliente
                if(socket.isClosed())
                    System.out.print("\n - Fechado!!");
            }


            while(true){


                //enviando informações para o servidor
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                out.writeUTF("oier");

                //pegando aas informações que o servidor mandou
                DataInputStream in = new DataInputStream(socket.getInputStream());

            }
        }catch(Exception e){
            System.out.print(e.getMessage());
        }
    }
}
