package br.edu.ifpb.smsMesseger.Thread;

import br.edu.ifpb.smsMesseger.model.Group;
import br.edu.ifpb.smsMesseger.model.Member;
import br.edu.ifpb.smsMesseger.util.Util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by root on 20/06/16.
 */
public class ThreadServerUser implements Runnable{
    private String work;
    private Socket s;
    private Member member;
    private Group g;
    private int countRegisterGlobal;
    private int countRegisterIndividual;

    public ThreadServerUser(Socket s, Group g){
        this.s = s;
        this.member = null;
        this.work = null;
        this.g = g;

        this.countRegisterGlobal = 0;
        this.countRegisterIndividual = 0;
    }

    /**
     * metodo usado basicamente para dormatar a data a ser apresentada
     * direitos : http://www.guj.com.br/t/pegar-data-hora-sistema/76055/9
     * author: rogerpsantos
     * **/
    private String get_date_time() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    /**
     * metodo responsavel por atualizar o clinte cmo as
     * infoamções novas.
     * **/
    private String update_messages(){
        String str = "";

        //caso ele não tenha imprimido nada ele vai mostrar tudo
        if(this.countRegisterGlobal == 0){
            Iterator it = this.g.getMsg().iterator();
            while(it.hasNext()){
                String ss = (String) it.next();
                str += ss;
            }


            this.countRegisterGlobal = this.g.getMsg().size();
        }else if(this.g.getMsg().size() != this.countRegisterGlobal){
            int total = this.g.getMsg().size();
            //pega só as ultimas onde enseridas que não foram mostradas para o cliente
            for(int size = this.countRegisterGlobal; total > size; total--){
                str += this.g.getMsg().get(total);
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

            while(true){

                //pegando aas informações que o servidor mandou
                DataInputStream in = new DataInputStream(this.s.getInputStream());
                //enviando informações para o servidor
                DataOutputStream out = new DataOutputStream(s.getOutputStream());

                //update de mensagens envidas pelos outros cliente
                //out.writeUTF(this.update_messages());

                System.out.print("\n rodando");

                //pegando a informação do cliente
                this.work = in.readUTF();

                //quebra a string que é enviada pelo usuario e quebra os parametros
                String[] param = this.work.toLowerCase().split(" ");

                //pegando os oparametros que é "send -all"
                if((param[0].equals("send")) && (param[1].equals("-all"))){

                    //gravando a mensagem
                    this.g.setMessage(s.getInetAddress()+":"+s.getPort()+"/~"+this.member.getName()+" : "+Util.prepare_commnads(this.work.toLowerCase())+" "+this.get_date_time()+"\n");

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
                    m.setMsg(s.getInetAddress()+":"+s.getPort()+"/~"+this.member.getName()+" : "+param[2]+" "+this.get_date_time()+"\n");

                }




            }

        }catch (Exception e){
            System.out.print(e.getMessage());
        }
    }
}
