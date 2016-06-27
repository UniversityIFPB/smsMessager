package br.edu.ifpb.smsMesseger.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    /**
     * metodo usado basicamente para dormatar a data a ser apresentada
     * direitos : http://www.guj.com.br/t/pegar-data-hora-sistema/76055/9
     * author: rogerpsantos
     * **/
    public static String get_date_time() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
