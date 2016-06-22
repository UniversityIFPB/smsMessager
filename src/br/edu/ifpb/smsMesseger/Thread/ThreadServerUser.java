package br.edu.ifpb.smsMesseger.Thread;

import br.edu.ifpb.smsMesseger.model.Group;
import br.edu.ifpb.smsMesseger.model.Member;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 * Created by root on 20/06/16.
 */
public class ThreadServerUser implements Runnable{
    private String work;
    private Socket s;
    private Member member;
    private Group g;

    public ThreadServerUser(Socket s, Group g){
        this.s = s;
        this.member = null;
        this.work = null;
        this.g = g;
    }

    private int auth_login(){
        try{
            //objeto de gravcação para o cliente
            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            // verificando se o usuario já esta cadastrado
            if(member == null){
                //verifica se existe o usuario registrado
                Member m = g.getMemberForIp(s.getInetAddress().getHostAddress());

                if( m != null){
                    this.member = m;
                    out.writeUTF("\n -->Sessão recuperada! <--\n");
                    return 0;
                }

                //enviando informações para o servidor
                out.writeUTF("Qual o seu nome?\n");

                //esperando o clinte
                while(this.work == null){
                    //pegando aas informações que o servidor mandou
                    DataInputStream in = new DataInputStream(this.s.getInputStream());
                    this.work = in.readUTF();
                    System.out.print(this.work);
                }

                //cria o novo membro
                Member member_new = new Member(s.getInetAddress().getHostAddress(), this.work);
                g.setMember(member_new);
                this.member = member_new;

                out.writeUTF("Cadastrado com sucesso\n");
            }
        }catch (Exception e){
            System.out.print(e.getMessage());
        }
        return 1;
    }

    @Override
    public void run() {
        try{
            //ruperando ou criando user
            this.auth_login();

            while(true){
                //pegando aas informações que o servidor mandou
                DataInputStream in = new DataInputStream(this.s.getInputStream());
                this.work = in.readUTF();


                //enviando informações para o servidor
                DataOutputStream out = new DataOutputStream(s.getOutputStream());
                out.writeUTF(s.getInetAddress()+":"+s.getPort()+"/"+work+"\n");
            }

        }catch (Exception e){
            System.out.print(e.getMessage());
        }
    }
}
