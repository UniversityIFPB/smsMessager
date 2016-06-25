package br.edu.ifpb.smsMesseger.util;

/**
 * Created by root on 25/06/16.
 */
public class Util {
    /**
     * esse metodo é responsavel por retirar o commandos da mensagem que
     * será enviado para a os outros clientes.
     * **/
    public static String prepare_commnads(String str){
        String[] arr = str.split(" ");

        //limpando variavel
        str = "";

        for(int i=2; i<arr.length; ++i){
            str += " "+arr[i];
        }


        return str;
    }
}
