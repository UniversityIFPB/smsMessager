package br.edu.ifpb.smsMesseger.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by root on 17/06/16.
 */
public class ClientSocket {
    public static void main(String[] args) {
        try{
            Socket socket = new Socket("localhost", 56789);
            //classe responsavel por ler o teclado
            BufferedReader inWork = new BufferedReader(new InputStreamReader(System.in));
            //palavras digitadas pelo terminal
            String work = "";

            //condicionais iniciais para informar e preparar o cliente para as ações do servidor
            if(socket.isConnected())
                System.out.print("\n----------- > Cliente conectado!! < ------------\n\n");
            else {
                socket.close();
                //verificando e mandando mensagem de fechamento para o cliente
                if(socket.isClosed())
                    System.out.print("\n -> Cliente fechado!! < - ");
            }

            //pegando aas informações que o servidor mandou que é perguntando o nome meu.
            DataInputStream in = new DataInputStream(socket.getInputStream());
            System.out.print(in.readUTF());

            while(true){

                work = inWork.readLine();
                //System.out.println("Você digitou: " + work);

                //enviando informações para o servidor
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                out.writeUTF(work);

                //pegando aas informações que o servidor mandou
                in = new DataInputStream(socket.getInputStream());
                System.out.print(in.readUTF());

            }
        }catch(Exception e){
            System.out.print(e.getMessage());
        }
    }
}
