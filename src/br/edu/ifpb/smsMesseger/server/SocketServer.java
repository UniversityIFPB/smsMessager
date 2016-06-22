package br.edu.ifpb.smsMesseger.server;

import br.edu.ifpb.smsMesseger.Thread.ThreadServerUser;
import br.edu.ifpb.smsMesseger.model.Group;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by root on 20/06/16.
 */
public class SocketServer {
    public static void main(String[] args) {
        try{

            ServerSocket server = new ServerSocket(56789);
            //criando grupo de pessoas
            Group g = new Group("ifpb");

            while(true){
                Socket s = server.accept();

                //passando para tread a execução do parametros de leituda e escrita
                new Thread(new ThreadServerUser(s,g)).start();

            }
        }catch(Exception e){
            System.out.print(e.getMessage());
        }
    }
}
