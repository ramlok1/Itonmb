package itonmb.mobilesd.itonmb.Utils;

/**
 * Created by Conrado_Mobiles on 30/06/2017.
 */

public class Utilerias {



    //Validar Strings nulos
            public static boolean isNull(String str){
                boolean isEmpty = str == null || str.trim().length() == 0 || str.equals("0");
                return isEmpty;
            }

    //Validar numeros nukk
    public static int toNumero(String str){
        int response;

        if(str == null || str.trim().length() == 0 ) {
            response=0;
        }else{
            response= Integer.parseInt(str);
        }
        return response;
    }

    public static double toDouble(String str){
        double response;

        if(str == null || str.trim().length() == 0 ) {
            response=0;
        }else{
            response= Double.parseDouble(str);
        }
        return response;
    }
}
