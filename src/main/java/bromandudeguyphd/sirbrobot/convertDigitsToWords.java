package bromandudeguyphd.sirbrobot;

public class convertDigitsToWords {


public static String NumberToSingalDigits(int n){
        String word[]={"zero","one","two","three","four","five","six","seven","eight","nine"};
        int digit;
        String answer="";
        for(int temp=n; temp>0; temp/=10){
            digit=temp%10;
            answer=word[digit]+" "+answer+" ";
        }
        return answer.trim();
    }
}