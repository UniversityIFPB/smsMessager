package br.edu.ifpb.smsMesseger.Thread;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 * Created by root on 20/06/16.
 */
public class ThreadServerUser implements Runnable{
    private String work;
    private Socket s;

    public ThreadServerUser(Socket s){
        this.s = s;
    }

    @Override
    public void run() {
        try{

          while(true){
            //pegando aas informações que o servidor mandou
            DataInputStream in = new DataInputStream(this.s.getInputStream());
            this.work = in.readUTF();
            System.out.print(work);

            //enviando informações para o servidor
            // to só fazendo echo
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            out.writeUTF(work);
          }

        }catch (Exception e){
            System.out.print(e.getMessage());
        }
    }
}
