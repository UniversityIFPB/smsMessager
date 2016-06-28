package br.edu.ifpb.smsMesseger.Thread;

import br.edu.ifpb.smsMesseger.model.Group;
import br.edu.ifpb.smsMesseger.model.Member;
import br.edu.ifpb.smsMesseger.util.Util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;

/**
 * Created by root on 20/06/16.
 */
public class ThreadServerUser implements Runnable{
    private String work;
    protected Socket s;
    private Member member;
    protected Group g;
    private int countRegisterGlobal;
    private int countRegisterIndividual;
    private DataOutputStream out;


    public ThreadServerUser(Socket s, Group g){
        this.s = s;
        this.member = null;
        this.work = null;
        this.g = g;

        this.countRegisterGlobal = 0;
        this.countRegisterIndividual = 0;
    }


    /**
     * metodo responsavel por atualizar o clinte cmo as
     * infoamções novas.
     * **/
    public synchronized String update_messages(){
        String str = " ";

        if(this.countRegisterGlobal == 0){
            for(Object ss: this.g.getMsg()){
                str += (String) ss;
            }
        }

        return str;
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

            //pegando aas informações que o servidor mandou
            DataInputStream in = new DataInputStream(this.s.getInputStream());
            //enviando informações para o servidor
            out = new DataOutputStream(s.getOutputStream());

            //out.writeUTF(update_messages());

            while(true){



                //pegando a informação do cliente
                this.work = in.readUTF();

                System.out.print(this.work);

                //quebra a string que é enviada pelo usuario e quebra os parametros
                String[] param = this.work.toLowerCase().split(" ");



                //pegando os oparametros que é "send -all"
                if((param[0].equals("send")) && (param[1].equals("-all"))){

                    //gravando a mensagem
                    this.g.setMessage(s.getInetAddress()+":"+s.getPort()+"/~"+this.member.getName()+" : "+Util.prepare_commnads(this.work.toLowerCase())+" "+Util.get_date_time()+"\n");

                    //enviando para o cliente a ultima mensagem enviada
                    out.writeUTF(this.g.getMessagesGlobal());
                }else if((param[0].equals("send")) && (param[1].equals("-user"))){
                    Member m = this.g.getMemberForName(param[2]);

                    //caso o usuario não exista
                    if(m == null){
                        out.writeUTF("Usuário invalido, "+param[2]+" não existe.");
                        continue;
                    }

                    //setando a mensagem individual para o usuario.
                    m.setMsg(s.getInetAddress()+":"+s.getPort()+"/~"+this.member.getName()+" : "+param[2]+" "+Util.get_date_time()+"\n");

                }else if(param[0].equals("list")){//mandando pra o cliente as informações dos usuarios logados

                    if(this.g.getListMember() == null)
                        out.writeUTF("\n\nNão há nenhum usuário cadastrado.\n");
                    else
                        out.writeUTF(this.g.getListMember());
                }else if(param[0].equals("rename")){

                    Member m = this.g.getMemberForName(param[1]);

                    if(m == null){
                        this.g.renameMember(this.member.getName(), param[1]);
                        out.writeUTF("\n\nRenomeado com sucesso!!\n");
                    }
                    else
                        out.writeUTF("\n\nNome de usuário já em uso.\n");
                }else if(param[0].equals("bye")){ //remove e desconectao usuario do grupo

                    out.writeUTF("\n\n --- >Removido com sucesso!!< --- \n");
                    this.g.removeMember(this.member.getIp());
                    out.writeUTF("[close]");

                }else{
                    out.writeUTF("Comando não encontrado.");
                }
            }

        }catch (Exception e){
            System.out.print(e.getMessage());
        }
    }
}
