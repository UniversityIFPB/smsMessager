package br.edu.ifpb.smsMesseger.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by root on 17/06/16.
 */

//todo adicionar depois o atualizar não vejo uma forma pratica de usar isso e nem de o porquer de implementa-lo agora
public class Group {
    private String name;
    private List members;
    private List msg;

    public Group(String name){
        this.name = name;
        this.members = new ArrayList<Member>();
        this.msg = new ArrayList<String>();
    }

    public synchronized void setMember(Member m) {
        this.members.add(m);
    }

    public List getMembers() {
        return members;
    }

    /**
     * busca por pelo mebro que possue o ip e depois deleta ele do grupo
     * **/
    public synchronized void removeMember(String ip){
        Iterator it = this.members.iterator();
        while (it.hasNext()){
            Member m = (Member) it.next();

            if(m.getIp().equals(ip))
                this.members.remove(m);
        }
    }
    /**
     * metodo responsavel por adicinar as mensagens globais para todos verem
     * **/
    public synchronized void setMessage(String msg){
        this.msg.add(msg);
    }

    /**
     * metodo responsavel por pegar todos as mensagens
     * enviadas para o global
     * **/
    public String getMessagesGlobal(){
        return (String) this.msg.get(this.msg.size() - 1);
    }

    /**
     * lista completa de todas as mensagens
     * **/
    public List getMsg() {
        return msg;
    }

    /**
     * Metodo responsavel por fazer buscas no grupo por ip, ou seja,
     * ele fará busca por ip para pegar as informações globais
     * **/
    public synchronized Member getMemberForIp(String ip){
        Iterator it = this.members.iterator();

        while(it.hasNext()){
            Member m = (Member) it.next();

            if(m.getIp().equals(ip))
                return m;
        }

        return null;
    }

    /**
     * Metodo responsavel por fazer buscas no grupo por ip, ou seja,
     * ele fará busca por ip para pegar as informações globais
     * **/
    public synchronized Member getMemberForName(String name){
        Iterator it = this.members.iterator();

        while(it.hasNext()){
            Member m = (Member) it.next();

            if(m.getName().equals(name))
                return m;
        }

        return null;
    }

    /**
     * apresenta a lista de nomes de todos so usuarios logados no momento
     * **/

    public synchronized String getListMember(){
        String nomes = "";
        Iterator it = this.members.iterator();

        while(it.hasNext()){
            Member m = (Member) it.next();

            nomes += m.getName()+"\n";
        }

        return nomes;
    }

    /**
     * esse metodo é responsavel por renomear o membro do grupo
     * **/

    public synchronized void renameMember(String old, String novo){
        Member m = this.getMemberForName(old);
        m.setName(novo);
    }

}
