package br.edu.ifpb.smsMesseger.br.edu.ifpb.smsMesseger.model;

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

    public Group(String name){
        this.name = name;
        this.members = new ArrayList<Member>();
    }

    public void setMember(Member m) {
        this.members.add(m);
    }

    public List getMembers() {
        return members;
    }

    /**
     * busca por pelo mebro que possue o ip e depois deleta ele do grupo
     * **/
    public void removeMember(String ip){
        Iterator it = this.members.iterator();
        while (it.hasNext()){
            Member m = (Member) it.next();

            if(m.getIp().equals(ip))
                this.members.remove(m);
        }
    }
    /**
     * Metodo responsavel por fazer buscas no grupo por ip, ou seja,
     * ele fará busca por ip para pegar as informações globais
     * **/
    public Member getMemberForIp(String ip){
        Iterator it = this.members.iterator();

        while(it.hasNext()){
            Member m = (Member) it.next();

            if(m.getIp().equals(ip))
                return m;
        }

        return null;
    }
}
