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
}
